package org.clubrockisen.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.NoIdException;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

/**
 * Class used to manipulating the members in the database.<br />
 * This class should be the only access point to the {@link Member} table in the database.
 * @author Alex
 */
public class MySQLMemberDAO extends MySQLDAO<Member> {
	/** Logger */
	private static Logger							lg	= Logger.getLogger(MySQLMemberDAO.class.getName());
	
	/** Map between the columns enumeration and their name in the database */
	private final Map<? extends Enum<?>, Column>	columns;
	/** Sample to be used by the super class */
	private Member									memberSample;
	
	/**
	 * Constructor #1.<br />
	 * @param connection
	 *            the connection to the database.
	 */
	public MySQLMemberDAO(final Connection connection) {
		super(connection);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("New " + this.getClass().getCanonicalName() + ".");
		}
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		columns = getEntitySample().getEntityColumns();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#getEntitySample()
	 */
	@Override
	protected Member getEntitySample () {
		if (memberSample == null) {
			memberSample = new Member();
		}
		return memberSample;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#createEntityFromResult(java.sql.ResultSet)
	 */
	@Override
	protected Member createEntityFromResult (final ResultSet result) throws SQLException {
		final Member newMember = new Member();
		
		// Setting member properties
		newMember.setIdMember(result.getInt(columns.get(MemberColumn.ID).getName()));
		newMember.setName(result.getString(columns.get(MemberColumn.NAME).getName()));
		newMember.setGender(Gender.fromValue(result.getString(columns.get(MemberColumn.GENDER).getName()).charAt(0)));
		newMember.setEntries(result.getInt(columns.get(MemberColumn.ENTRIES).getName()));
		newMember.setNextFree(result.getInt(columns.get(MemberColumn.NEXT_FREE).getName()));
		newMember.setCredit(result.getDouble(columns.get(MemberColumn.CREDIT).getName()));
		newMember.setStatus(Status.fromValue(result.getString(columns.get(MemberColumn.STATUS).getName())));
		
		return newMember;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#create(java.lang.Object)
	 */
	@Override
	public Member create (final Member obj) {
		if (obj == null) {
			return null;
		}
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Creating the member " + obj.getName());
		}
		
		Member newMember = null;
		try (final Statement statement = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE)) {
			final String query = obj.generateInsertQuerySQL(false) + " (" + "'" + obj.getName() + "',"
					+ "'" + obj.getGender().getAbbreviation() + "',"
					+ "'" + obj.getEntries() + "',"
					+ "'" + obj.getNextFree() + "',"
					+ "'" + obj.getCredit() + "',"
					+ "'" + obj.getStatus().getName() + "');";
			if (lg.isLoggable(Level.INFO)) {
				lg.info(query);
			}
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			// Retrieving the created member
			try (final ResultSet resultSet = statement.getGeneratedKeys()) {
				if (resultSet.next()) {
					newMember = find(resultSet.getInt(1));
				} else {
					throw new SQLException("Could not retrieve last inserted id.");
				}
			}
		} catch (final SQLException e) {
			lg.warning("Exception while creating a member: " + e.getMessage());
			return null;
		}
		return newMember;
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
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Updating the member with id = " + obj.getIdMember());
		}
		
		try (final Statement statement = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE)) {
			final String query = obj.generateUpdateQuerySQL() +
					columns.get(MemberColumn.NAME).getName() + " = '" + obj.getName() + "', " +
					columns.get(MemberColumn.GENDER).getName() + " = '" + obj.getGender().getAbbreviation() + "', " +
					columns.get(MemberColumn.ENTRIES).getName() + " = '" + obj.getEntries() + "', " +
					columns.get(MemberColumn.NEXT_FREE).getName() + " = '" + obj.getNextFree() + "', " +
					columns.get(MemberColumn.CREDIT).getName() + " = '" + obj.getCredit() + "', " +
					columns.get(MemberColumn.STATUS).getName() + " = '" + obj.getStatus().getName() + "'" +
					obj.generateWhereIDQuerySQL();
			if (lg.isLoggable(Level.INFO)) {
				lg.info(query);
			}
			statement.executeUpdate(query);
		} catch (final SQLException | NoIdException e) {
			lg.warning("Could not update member: " + e.getMessage());
			return false;
		}
		return true;
	}
	
}
