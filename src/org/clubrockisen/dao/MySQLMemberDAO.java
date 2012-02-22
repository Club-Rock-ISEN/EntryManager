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
	public MySQLMemberDAO(final Connection connection) {
		this.connection = connection;
		lg.fine("New " + this.getClass().getCanonicalName() + ".");
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		new Member();
		columns = Member.getColumns();
	}

	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#create(java.lang.Object)
	 */
	@Override
	public boolean create (final Member obj) {
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
		lg.fine("finding the member with id = " + id);
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final ResultSet result = statement.executeQuery("SELECT * FROM member WHERE " +
					columns.get(MemberColumn.ID).getName() + " = " + id);
			if (result.first()) {
				member = createMemberFromResult(result);
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
	 * @see org.clubrockisen.dao.DAO#update(java.lang.Object)
	 */
	@Override
	public boolean update (final Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#delete(java.lang.Object)
	 */
	@Override
	public boolean delete (final Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Member> retrieveAll () {
		final List<Member> allMembers = new ArrayList<>();
		lg.fine("Retrieving all members");

		try {
			final long time1 = System.currentTimeMillis();
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final ResultSet result = statement.executeQuery("SELECT * FROM member");
			final long time2 = System.currentTimeMillis();
			while (result.next()) {
				allMembers.add(createMemberFromResult(result));
			}
			final long time3 = System.currentTimeMillis();
			lg.info("Time for request: " + (time2-time1) + " ms");
			lg.info("Time for building list: " + (time3-time2) + " ms");
			lg.info("Time for both: " + (time3-time1) + "ms");
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while retrieving all members: " + e.getMessage());
		}

		return allMembers;
	}

	@Override
	public List<Member> search (final Column field, final String value) {
		final List<Member> members = new ArrayList<>();
		lg.fine("Searching members");

		try {
			final long time1 = System.currentTimeMillis();
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final ResultSet result = statement.executeQuery("SELECT * FROM member WHERE "
					+ field.getName() + " LIKE '" + value + "%'");
			final long time2 = System.currentTimeMillis();
			while (result.next()) {
				members.add(createMemberFromResult(result));
			}
			final long time3 = System.currentTimeMillis();
			lg.info("Time for request: " + (time2 - time1) + " ms");
			lg.info("Time for building list: " + (time3 - time2) + " ms");
			lg.info("Time for both: " + (time3 - time1) + "ms");
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while searching members: " + e.getMessage());
		}

		return members;
	}
	
	/**
	 * Creates a member from a row of result.<br />
	 * Do not move to the next result.
	 * @param result the result of the query.
	 * @return the newly created member.
	 * @throws SQLException if there was a problem while reading the data from the columns.
	 */
	private Member createMemberFromResult (final ResultSet result) throws SQLException {
		final Member newMember = new Member();
		
		// Setting member properties
		newMember.setIdMember(result.getInt(columns.get(MemberColumn.ID).getName()));
		newMember.setName(result.getString(columns.get(MemberColumn.NAME).getName()));
		newMember.setGender(Gender.fromValue(result.getString(
				columns.get(MemberColumn.GENDER).getName()).charAt(0)));
		newMember.setEntries(result.getInt(columns.get(MemberColumn.ENTRIES).getName()));
		newMember.setCredit(result.getInt(columns.get(MemberColumn.CREDIT).getName()));
		newMember.setStatus(Status.fromValue(result.getString(columns.get(MemberColumn.STATUS)
				.getName())));

		return newMember;
	}
}
