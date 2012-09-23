package org.clubrockisen.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.EntryMemberParty.EntryColumn;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;
import org.clubrockisen.service.abstracts.IEntryManager;

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
	 * @see org.clubrockisen.service.abstracts.IEntryManager#entry(org.clubrockisen.entities.Member,
	 * org.clubrockisen.entities.Party)
	 */
	@Override
	public boolean entry (final Member member, final Party party) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.IEntryManager#cancelEntry(org.clubrockisen.entities.Member
	 * , org.clubrockisen.entities.Party)
	 */
	@Override
	public boolean cancelEntry (final Member member, final Party party) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IEntryManager#entry(org.clubrockisen.entities.Member)
	 */
	@Override
	public boolean entry (final Member member) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.IEntryManager#cancelEntry(org.clubrockisen.entities.Member
	 * )
	 */
	@Override
	public boolean cancelEntry (final Member member) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.IEntryManager#getParties(org.clubrockisen.entities.Member)
	 */
	@Override
	public SortedSet<Party> getParties (final Member member) {
		final List<EntryMemberParty> entries = entryDAO.search(EntryMemberParty.getColumns().get(EntryColumn.MEMBER_ID), member.getID());
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
		final List<EntryMemberParty> entries = entryDAO.search(EntryMemberParty.getColumns().get(EntryColumn.PARTY_ID), party.getID());
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
