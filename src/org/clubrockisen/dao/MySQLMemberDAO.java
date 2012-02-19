package org.clubrockisen.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

/**
 * TODO implement.
 * @author Alex
 */
public class MySQLMemberDAO implements DAO<Member> {
	private static Logger		lg	= Logger.getLogger(MySQLMemberDAO.class.getName());

	private final Connection						connection;
	private final Map<? extends Enum<?>, Column>	columns;

	/**
	 * Constructor #1.<br />
	 * @param connection
	 *            the connection to the database.
	 */
	public MySQLMemberDAO (final Connection connection) {
		this.connection = connection;
		lg.fine("New " + this.getClass().getCanonicalName() + ".");
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		new Member();
		columns = Member.getColumns();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.clubrockisen.dao.DAO#create(java.lang.Object)
	 */
	@Override
	public boolean create (final Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#find(int)
	 */
	@Override
	public Member find (final int id) {
		Member member = null;
		lg.fine("finding the member with id = " + id);
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final ResultSet result = statement.executeQuery("SELECT * FROM member WHERE " +
					columns.get(MemberColumn.ID).getName() + " = " + id);
			if (result.first()) {
				member = new Member(result.getInt(columns.get(MemberColumn.ID).getName()),
						result.getString(columns.get(MemberColumn.NAME).getName()),
						Gender.fromValue(result.getString(columns.get(MemberColumn.GENDER).getName()).charAt(0)),
						result.getInt(columns.get(MemberColumn.ENTRIES).getName()),
						result.getInt(columns.get(MemberColumn.CREDIT).getName()),
						Status.fromValue(result.getString(columns.get(MemberColumn.STATUS).getName())));
			}
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while retrieving a member: " + e.getMessage());
		}
		return member;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.clubrockisen.dao.DAO#update(java.lang.Object)
	 */
	@Override
	public boolean update (final Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.clubrockisen.dao.DAO#delete(java.lang.Object)
	 */
	@Override
	public boolean delete (final Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Member> retrieveAll () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Member> search (final Column field, final String value) {
		// TODO Auto-generated method stub
		return null;
	}
}
