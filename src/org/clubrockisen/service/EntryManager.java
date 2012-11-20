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
import org.clubrockisen.common.Time;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.EntryMemberParty.EntryColumn;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.Party.PartyColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.service.abstracts.IEntryManager;
import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;

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
	public EntryManager (final AbstractDAOFactory daoFactory) {
		super();
		memberDAO = daoFactory.getMemberDAO();
		partyDAO = daoFactory.getPartyDAO();
		entryDAO = daoFactory.getEntryMemberPartyDAO();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#getCurrentParty()
	 */
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
		
		// Creating today's party
		final Party party = new Party(System.currentTimeMillis());
		final String strPartyCost = ServiceFactory.getImplementation().getParameterManager().get(ParametersEnum.PARTY_COST).getValue();
		party.setRevenue(-1 * Double.parseDouble(strPartyCost));
		
		return partyDAO.create(party);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#entry(org.clubrockisen.entities.Member,
	 * org.clubrockisen.entities.Party)
	 */
	@Override
	public boolean entry (final Member member, final Party party) {
		if (member == null || party == null) {
			return false;
		}
		return entry(member.getIdMember(), party.getIdParty());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#entry(int, int)
	 */
	@Override
	public boolean entry (final int memberId, final int partyId) {
		final IParametersManager parametersManager = ServiceFactory.getImplementation().getParameterManager();
		final Member member = memberDAO.find(memberId);
		final Party party = partyDAO.find(partyId);
		
		// Update member
		member.setEntries(member.getEntries() + 1);
		if (member.getNextFree() != 1) { // Back to user
			member.setNextFree(member.getNextFree() - 1);
		} else {
			member.setNextFree(Integer.valueOf(parametersManager.get(ParametersEnum.FREE_ENTRY_FREQUENCY).getValue()));
			party.setEntriesFree(party.getEntriesFree() + 1);
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
		
		// TODO prices !
		
		// Create the entry which links both entities
		final EntryMemberParty entry = new EntryMemberParty(member, party);
		boolean successful = (entryDAO.create(entry) != null);
		// And commit modifications to member and party
		successful &= memberDAO.update(member);
		successful &= partyDAO.update(party);
		return successful;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.IEntryManager#cancelEntry(org.clubrockisen.entities.Member
	 * , org.clubrockisen.entities.Party)
	 */
	@Override
	public boolean cancelEntry (final Member member, final Party party) {
		if (member == null || party == null) {
			return false;
		}
		return cancelEntry(member.getIdMember(), party.getIdParty());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#cancelEntry(int, int)
	 */
	@Override
	public boolean cancelEntry (final int memberId, final int partyId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#entry(org.clubrockisen.entities.Member)
	 */
	@Override
	public boolean entry (final Member member) {
		return entry(member.getIdMember());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#entry(int)
	 */
	@Override
	public boolean entry (final int memberId) {
		return entry(memberId, getCurrentParty().getIdParty());
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.IEntryManager#cancelEntry(org.clubrockisen.entities.Member
	 * )
	 */
	@Override
	public boolean cancelEntry (final Member member) {
		return cancelEntry(member.getIdMember());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#cancelEntry(int)
	 */
	@Override
	public boolean cancelEntry (final int memberId) {
		return cancelEntry(memberId, getCurrentParty().getIdParty());
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.IEntryManager#getParties(org.clubrockisen.entities.Member)
	 */
	@Override
	public SortedSet<Party> getParties (final Member member) {
		if (member == null) {
			return null;
		}
		return getParties(member.getIdMember());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#getParties(int)
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.IEntryManager#getMembers(org.clubrockisen.entities.Party)
	 */
	@Override
	public Set<Member> getMembers (final Party party) {
		if (party == null) {
			return null;
		}
		return getMembers(party.getIdParty());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#getMembers(int)
	 */
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
}
