package org.clubrockisen.dao;

import java.sql.Connection;

import org.clubrockisen.connection.MySQLConnection;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;

/**
 * The factory for the MySQL DAO classes.
 * @author Alex
 */
public class MySQLDAOFactory extends AbstractDAOFactory {
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
}
