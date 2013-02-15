package org.clubrockisen.dao.mysql;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.Utils;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;

/**
 * The factory for the MySQL DAO classes.<br />
 * A factory is only handling one DAO of each type and thus will not instantiate more DAOs than
 * needed. However, if the factory is load several times, several DAOs will be created.
 * @author Alex
 */
public class MySQLDAOFactory extends AbstractDAOFactory {
	/** Logger */
	private static Logger				lg		= Logger.getLogger(AbstractDAOFactory.class.getName());
	
	/** The connection to the database */
	private final Connection			connection;
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
		
		connection = Utils.getConnection();
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Creating DAOs");
		}
		// Instantiating all DAOs once to avoid multiple DAOs
		memberDao = new MySQLMemberDAO(connection);
		parameterDao = new MySQLParameterDAO(connection);
		partyDao = new MySQLPartyDAO(connection);
		entryMemberPartyDao = new MySQLEntryMemberPartyDAO(connection);
	}
	
	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close () throws IOException {
		Utils.close(connection);
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
