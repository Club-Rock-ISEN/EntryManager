package org.clubrockisen.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

/**
 * Class used to manipulating the members in the database.<br />
 * This class should be the only access point to the {@link Member} table in the database.
 * @author Alex
 */
public class MySQLMemberDAO implements DAO<Member> {
	private static Logger							lg	= Logger.getLogger(MySQLMemberDAO.class
			.getName());
	
	private final Connection						connection;
	private final Map<? extends Enum<?>, Column>	columns;
	
	/**
	 * Constructor #1.<br />
	 * @param connection
	 *            the connection to the database.
	 */
	public MySQLMemberDAO(final Connection connection) {
		this.connection = connection;
		lg.fine("New " + this.getClass().getCanonicalName() + ".");
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		columns = new Member().getEntityColumns();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#create(java.lang.Object)
	 */
	@Override
	public boolean create (final Member obj) {
		if (obj == null) {
			return false;
		}
		lg.fine("Creating the member " + obj.getName());
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateInsertQuerySQL(false) + " ("
					+ "'" + obj.getName() + "',"
					+ "'" + obj.getGender().getAbbreviation() + "',"
					+ "'" + obj.getEntries() + "',"
					+ "'" + obj.getCredit() + "',"
					+ "'" + obj.getStatus().getName() + "');";
			lg.info(query);
			statement.executeUpdate(query);
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while creating a member: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#find(int)
	 */
	@Override
	public Member find (final int id) {
		Member member = null;
		lg.fine("Finding the member with id = " + id);
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final Member m = new Member();
			final String query = m.generateSearchAllQuery() + m.generateWhereIDQuerySQL(id);
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			if (result.first()) {
				member = createMemberFromResult(result);
			}
			result.close();
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while retrieving a member: " + e.getMessage());
			return null;
		}
		return member;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#update(java.lang.Object)
	 */
	@Override
	public boolean update (final Member obj) {
		if (obj == null) {
			return false;
		}
		lg.fine("Updating the member with id = " + obj.getIdMember());
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateUpdateQuerySQL() +
					columns.get(MemberColumn.NAME).getName() + " = '" + obj.getName() + "', " +
					columns.get(MemberColumn.GENDER).getName() + " = '" + obj.getGender().getAbbreviation() + "', " +
					columns.get(MemberColumn.ENTRIES).getName() + " = '" + obj.getEntries() + "', " +
					columns.get(MemberColumn.CREDIT).getName() + " = '" + obj.getCredit() + "', " +
					columns.get(MemberColumn.STATUS).getName() + " = '" + obj.getStatus().getName() + "'" +
					obj.generateWhereIDQuerySQL();
			lg.info(query);
			statement.executeUpdate(query);
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while updating a member: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#delete(java.lang.Object)
	 */
	@Override
	public boolean delete (final Member obj) {
		if (obj == null) {
			return true;
		}
		lg.fine("Deleting member " + obj.getName() + "(id: " + obj.getIdMember() + ")");
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateDeleteQuerySQL();
			lg.info(query);
			statement.executeUpdate(query);
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while deleting member: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public List<Member> retrieveAll () {
		final List<Member> allMembers = new ArrayList<>();
		lg.fine("Retrieving all members");
		
		try {
			final long time1 = System.currentTimeMillis();
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final String query = new Member().generateSearchAllQuery();
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			final long time2 = System.currentTimeMillis();
			while (result.next()) {
				allMembers.add(createMemberFromResult(result));
			}
			final long time3 = System.currentTimeMillis();
			lg.fine("Time for request: " + (time2-time1) + " ms");
			lg.fine("Time for building list: " + (time3-time2) + " ms");
			lg.fine("Time for both: " + (time3-time1) + "ms");
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while retrieving all members: " + e.getMessage());
			return allMembers;
		}
		
		return allMembers;
	}
	
	@Override
	public List<Member> search (final Column field, final String value) {
		if (field == null || value == null) {
			return retrieveAll();
		}
		
		final List<Member> members = new ArrayList<>();
		lg.fine("Searching members with " + field.getName() + " = " + value);
		
		try {
			final long time1 = System.currentTimeMillis();
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final String query = new Member().generateSearchAllQuery() + " WHERE " + field.getName() +
					" LIKE '" + value + "%'";
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			final long time2 = System.currentTimeMillis();
			while (result.next()) {
				members.add(createMemberFromResult(result));
			}
			final long time3 = System.currentTimeMillis();
			lg.fine("Time for request: " + (time2 - time1) + " ms");
			lg.fine("Time for building list: " + (time3 - time2) + " ms");
			lg.fine("Time for both: " + (time3 - time1) + "ms");
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while searching members: " + e.getMessage());
			return members;
		}
		
		return members;
	}
	
	/**
	 * Creates a member from a row of result.<br />
	 * Do not move to the next result.
	 * @param result
	 *        the result of the query.
	 * @return the newly created member.
	 * @throws SQLException
	 *         if there was a problem while reading the data from the columns.
	 */
	private Member createMemberFromResult (final ResultSet result) throws SQLException {
		final Member newMember = new Member();
		
		// Setting member properties
		newMember.setIdMember(result.getInt(columns.get(MemberColumn.ID).getName()));
		newMember.setName(result.getString(columns.get(MemberColumn.NAME).getName()));
		newMember.setGender(Gender.fromValue(result.getString(columns.get(MemberColumn.GENDER).getName()).charAt(0)));
		newMember.setEntries(result.getInt(columns.get(MemberColumn.ENTRIES).getName()));
		newMember.setCredit(result.getDouble(columns.get(MemberColumn.CREDIT).getName()));
		newMember.setStatus(Status.fromValue(result.getString(columns.get(MemberColumn.STATUS).getName())));
		
		return newMember;
	}
}
