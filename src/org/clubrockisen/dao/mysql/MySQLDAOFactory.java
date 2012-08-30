package org.clubrockisen.dao.mysql;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.connection.MySQLConnection;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;

/**
 * The factory for the MySQL DAO classes.<br />
 * A factory is only handling one DAO of each type and thus will not instantiate more DAOs than
 * needed. However, if the factory is load several times,
 * @author Alex
 */
public class MySQLDAOFactory extends AbstractDAOFactory {
	/** Logger */
	private static Logger				lg			= Logger.getLogger(AbstractDAOFactory.class.getName());
	
	/** The connection to the MySQL database */
	protected static final Connection	CONNECTION	= MySQLConnection.getInstance();
	
	/** The member DAO */
	private final DAO<Member>			memberDao;
	/** The parameter DAO */
	private final DAO<Parameter>		parameterDao;
	/** The party DAO */
	private final DAO<Party>			partyDao;
	/** The party DAO */
	private final DAO<EntryMemberParty>	entryMemberPartyDao;
	
	/**
	 * Constructor #1.<br />
	 */
	public MySQLDAOFactory () {
		super();
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Creating DAOs...");
		}
		// Instantiating all DAOs once to avoid multiple DAOs
		memberDao = new MySQLMemberDAO(CONNECTION);
		parameterDao = new MySQLParameterDAO(CONNECTION);
		partyDao = new MySQLPartyDAO(CONNECTION);
		entryMemberPartyDao = new MySQLEntryMemberPartyDAO(CONNECTION);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getMemberDAO()
	 */
	@Override
	public DAO<Member> getMemberDAO () {
		return memberDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getParameterDAO()
	 */
	@Override
	public DAO<Parameter> getParameterDAO () {
		return parameterDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getPartyDAO()
	 */
	@Override
	public DAO<Party> getPartyDAO () {
		return partyDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getEntryMemberPartyDAO()
	 */
	@Override
	public DAO<EntryMemberParty> getEntryMemberPartyDAO () {
		return entryMemberPartyDao;
	}
}
