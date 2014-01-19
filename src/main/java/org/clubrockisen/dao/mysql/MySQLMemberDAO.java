package org.clubrockisen.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

import com.alexrnl.commons.database.sql.SQLDAO;
import com.alexrnl.commons.database.structure.Column;

/**
 * Class used to manipulating the members in the database.<br />
 * This class should be the only access point to the {@link Member} table in the database.
 * @author Alex
 */
public class MySQLMemberDAO extends SQLDAO<Member> {
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
	 * @throws SQLException
	 *         The prepared statements could not be created.
	 */
	public MySQLMemberDAO(final Connection connection) throws SQLException {
		super(connection);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("New " + this.getClass().getCanonicalName());
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
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#fillInsertStatement(java.sql.PreparedStatement,
	 * org.clubrockisen.entities.Entity)
	 */
	@Override
	protected void fillInsertStatement (final PreparedStatement statement, final Member obj)
			throws SQLException {
		int index = 1;
		statement.setString(index++, obj.getName());
		statement.setObject(index++, obj.getGender().getAbbreviation(), Types.CHAR);
		statement.setInt(index++, obj.getEntries());
		statement.setInt(index++, obj.getNextFree());
		statement.setDouble(index++, obj.getCredit());
		statement.setString(index++, obj.getStatus().getName());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#fillUpdateStatement(java.sql.PreparedStatement,
	 * org.clubrockisen.entities.Entity)
	 */
	@Override
	protected void fillUpdateStatement (final PreparedStatement statement, final Member obj)
			throws SQLException {
		fillInsertStatement(statement, obj);
		statement.setInt(Member.getColumns().size(), obj.getIdMember());
		
	}
	
}
