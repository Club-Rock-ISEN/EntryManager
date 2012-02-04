package org.clubrockisen.dao;

import java.sql.Connection;

import org.clubrockisen.connection.MySQLConnection;
import org.clubrockisen.entities.Member;

/**
 * The factory for the MySQL DAO classes.
 * @author Alex
 */
public class MySQLDAOFactory extends AbstractDAOFactory {
	protected static final Connection connection = MySQLConnection.getInstance();

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getMemberDAO()
	 */
	@Override
	public DAO<Member> getMemberDAO () {
		return new MySQLMemberDAO();
	}
}
