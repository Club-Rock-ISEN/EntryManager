package org.clubrockisen.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.Constants;
import org.clubrockisen.dao.abstracts.EntryManagerAbstractDAOFactory;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.EntryMemberParty.EntryColumn;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.Party.PartyColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.abstracts.IEntryManager;
import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;

import com.alexrnl.commons.database.dao.DAO;
import com.alexrnl.commons.time.Time;

/**
 * Implementation of the {@link IEntryManager} interface.<br />
 * Methods to enter {@link Member members} in a {@link Party parties}.
 * @author Alex
 */
public class EntryManager implements IEntryManager {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(EntryManager.class.getName());
	
	/** The member DAO */
	private final DAO<Member>			memberDAO;
	/** The party DAO */
	private final DAO<Party>			partyDAO;
	/** The entry member party DAO */
	private final DAO<EntryMemberParty>	entryDAO;
	
	/**
	 * Constructor #1.<br />
	 * @param daoFactory the DAO factory.
	 */
	public EntryManager (final EntryManagerAbstractDAOFactory daoFactory) {
		super();
		memberDAO = daoFactory.getMemberDAO();
		partyDAO = daoFactory.getPartyDAO();
		entryDAO = daoFactory.getEntryMemberPartyDAO();
	}
	
	@Override
	public Party getCurrentParty () {
		final SimpleDateFormat storedDateFormat = new SimpleDateFormat(Constants.STORED_DATE_FORMAT);
		final Set<Party> result = partyDAO.search(Party.getColumns().get(PartyColumn.DATE),
				storedDateFormat.format(new Date(System.currentTimeMillis())));
		
		// More than 1 result => error
		if (result.size() > 1) {
			lg.warning("Error, found " + result.size() + " parties at current date, listing parties found:");
			for (final Party party : result) {
				lg.warning("\t" + party);
			}
			return null;
		}
		
		// A party was found for the date
		if (result.size() == 1) {
			return result.iterator().next();
		}
		
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Today's party was not found, creating new one.");
		}
		// Creating today's party
		final Party party = new Party(System.currentTimeMillis());
		final String strPartyCost = ServiceFactory.getImplementation().getParameterManager().get(ParametersEnum.PARTY_COST).getValue();
		party.setProfit(-1 * Double.parseDouble(strPartyCost));
		
		return partyDAO.create(party);
	}
	
	@Override
	public double getPrice (final Member member) {
		final IParametersManager parametersManager = ServiceFactory.getImplementation().getParameterManager();
		double price = Double.valueOf(parametersManager.get(ParametersEnum.ENTRY_PRICE_TOTAL).getValue());
		
		if (!Status.MEMBER.equals(member.getStatus())) {
			price = 0.0;
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Free entry because the member is a special member (" + member.getStatus() + ")");
			}
		} else if (member.getEntries() != 0 &&
				(member.getEntries() + 1)  % Integer.valueOf(parametersManager.get(ParametersEnum.FREE_ENTRY_FREQUENCY).getValue()) == 0) {
			price = 0.0;
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Free entry");
			}
		} else if (Time.getCurrent().after(Time.get(parametersManager.get(ParametersEnum.TIME_LIMIT).getValue()))) {
			price = Double.valueOf(parametersManager.get(ParametersEnum.ENTRY_PRICE_SECOND_PART).getValue());
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Price after the limit for the member");
			}
		}
		
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Price to pay for " + member.toString() + ": " + price);
		}
		
		return price;
	}
	
	@Override
	public boolean entry (final Member member, final Party party) {
		if (member == null || party == null) {
			return false;
		}
		return entry(member.getIdMember(), party.getIdParty());
	}
	
	@Override
	public boolean entry (final int memberId, final int partyId) {
		if (!canEnter(memberId)) {
			lg.warning("The member " + memberId + " cannot enter the party " + partyId + " (already in).");
			return false;
		}
		final IParametersManager parametersManager = ServiceFactory.getImplementation().getParameterManager();
		final Member member = memberDAO.find(memberId);
		final Party party = partyDAO.find(partyId);
		final double price = getPrice(member);
		
		// Update member
		member.setEntries(member.getEntries() + 1);
		if (member.getNextFree() > 1) {
			member.setNextFree(member.getNextFree() - 1);
		} else {
			member.setNextFree(Integer.valueOf(parametersManager.get(ParametersEnum.FREE_ENTRY_FREQUENCY).getValue()));
			party.setEntriesFree(party.getEntriesFree() + 1);
		}
		
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Next free entry: " + member.getNextFree());
		}
		
		// Update party
		party.setEntriesTotal(party.getEntriesTotal() + 1);
		if (Gender.FEMALE.equals(member.getGender())) {
			party.setEntriesFemale(party.getEntriesFemale() + 1);
		} else {
			party.setEntriesMale(party.getEntriesMale() + 1);
		}
		if (Time.getCurrent().before(Time.get(parametersManager.get(ParametersEnum.TIME_LIMIT).getValue()))) {
			party.setEntriesFirstPart(party.getEntriesFirstPart() + 1);
		} else {
			party.setEntriesSecondPart(party.getEntriesSecondPart() + 1);
		}
		party.setRevenue(party.getRevenue() + price);
		party.setProfit(party.getProfit() + price);
		
		// Create the entry which links both entities
		final EntryMemberParty entry = new EntryMemberParty(member, party);
		boolean successful = (entryDAO.create(entry) != null);
		// And commit modifications to member and party
		successful &= memberDAO.update(member);
		successful &= partyDAO.update(party);
		return successful;
	}
	
	@Override
	public boolean cancelEntry (final Member member, final Party party) {
		if (member == null || party == null) {
			return false;
		}
		return cancelEntry(member.getIdMember(), party.getIdParty());
	}
	
	@Override
	public boolean cancelEntry (final int memberId, final int partyId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Cancel entry method no yet implemented.");
	}
	
	@Override
	public boolean entry (final Member member) {
		return entry(member.getIdMember());
	}
	
	@Override
	public boolean entry (final int memberId) {
		return entry(memberId, getCurrentParty().getIdParty());
	}
	
	@Override
	public boolean cancelEntry (final Member member) {
		return cancelEntry(member.getIdMember());
	}
	
	@Override
	public boolean cancelEntry (final int memberId) {
		return cancelEntry(memberId, getCurrentParty().getIdParty());
	}
	
	@Override
	public SortedSet<Party> getParties (final Member member) {
		if (member == null) {
			return null;
		}
		return getParties(member.getIdMember());
	}
	
	@Override
	public SortedSet<Party> getParties (final int memberId) {
		final Set<EntryMemberParty> entries = entryDAO.search(EntryMemberParty.getColumns().get(EntryColumn.MEMBER_ID),
				Integer.toString(memberId));
		final SortedSet<Party> parties = new TreeSet<>();
		
		for (final EntryMemberParty entry : entries) {
			final Party party = partyDAO.find(entry.getIdParty());
			if (party != null) {
				parties.add(party);
			} else {
				lg.warning("Could not find party with id " + entry.getIdParty());
			}
		}
		
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Successfully retrieved " + parties.size() + " parties");
		}
		
		return parties;
	}
	
	@Override
	public Set<Member> getMembers (final Party party) {
		if (party == null) {
			return null;
		}
		return getMembers(party.getIdParty());
	}
	
	@Override
	public Set<Member> getMembers (final int partyId) {
		final Set<EntryMemberParty> entries = entryDAO.search(EntryMemberParty.getColumns().get(EntryColumn.PARTY_ID),
				Integer.toString(partyId));
		final Set<Member> members = new HashSet<>(entries.size());
		
		for (final EntryMemberParty entry : entries) {
			final Member member = memberDAO.find(entry.getIdMember());
			if (member != null) {
				members.add(member);
			} else {
				lg.warning("Could not find member with id " + entry.getIdMember());
			}
		}
		
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Successfully retrieved " + members.size() + " members");
		}
		
		return members;
	}
	
	@Override
	public boolean canEnter (final Member member) {
		if (member == null) {
			return false;
		}
		return canEnter(member.getIdMember());
	}
	
	@Override
	public boolean canEnter (final int memberId) {
		return !getMembers(getCurrentParty()).contains(memberDAO.find(memberId));
	}
	
	@Override
	public boolean canEnter (final Member member, final Party party) {
		if (member == null || party == null) {
			return false;
		}
		return canEnter(member.getIdMember(), party.getIdParty());
	}
	
	@Override
	public boolean canEnter (final int memberId, final int partyId) {
		return !getMembers(partyId).contains(memberDAO.find(memberId));
	}
	
}
