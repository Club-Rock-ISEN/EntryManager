package org.clubrockisen.dao;

import java.sql.Connection;

import org.clubrockisen.connection.MySQLConnection;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;

/**
 * The factory for the MySQL DAO classes.
 * @author Alex
 */
public class MySQLDAOFactory extends AbstractDAOFactory {
	/**
	 * The connection to the MySQL database.
	 */
	protected static final Connection	CONNECTION	= MySQLConnection.getInstance();
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getMemberDAO()
	 */
	@Override
	public DAO<Member> getMemberDAO () {
		return new MySQLMemberDAO(CONNECTION);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getParameterDAO()
	 */
	@Override
	public DAO<Parameter> getParameterDAO () {
		return new MySQLParameterDAO(CONNECTION);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getPartyDAO()
	 */
	@Override
	public DAO<Party> getPartyDAO() {
		return new MySQLPartyDAO(CONNECTION);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getEntryMemberPartyDAO()
	 */
	@Override
	public DAO<EntryMemberParty> getEntryMemberPartyDAO () {
		return new MySQLEntryMemberParty(CONNECTION);
	}
}
