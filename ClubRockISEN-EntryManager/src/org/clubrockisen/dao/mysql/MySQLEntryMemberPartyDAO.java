package org.clubrockisen.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.NoIdException;
import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.EntryMemberParty.EntryColumn;

/**
 * Class used to manipulating the entries of the members in the database.<br />
 * This class should be the only access point to the {@link EntryMemberParty} table in the database.
 * @author Alex
 */
public class MySQLEntryMemberPartyDAO extends MySQLDAO<EntryMemberParty> {
	/** Logger */
	private static Logger							lg	= Logger.getLogger(MySQLEntryMemberPartyDAO.class.getName());
	
	/** Map between the columns enumeration and their name in the database */
	private final Map<? extends Enum<?>, Column>	columns;
	/** Sample to be used by the super class */
	private EntryMemberParty						entrySample;
	
	/**
	 * Constructor #1.<br />
	 * @param connection
	 *        the connection to the database.
	 * @throws SQLException
	 *         The prepared statements could not be created.
	 * @throws NoIdException
	 *         The {@link EntryMemberParty} has no ID column.
	 */
	public MySQLEntryMemberPartyDAO (final Connection connection) throws SQLException, NoIdException {
		super(connection);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("New " + this.getClass().getCanonicalName());
		}
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		columns = getEntitySample().getEntityColumns();
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#getEntitySample()
	 */
	@Override
	protected EntryMemberParty getEntitySample () {
		if (entrySample == null) {
			entrySample = new EntryMemberParty();
		}
		return entrySample;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#createEntityFromResult(java.sql.ResultSet)
	 */
	@Override
	protected EntryMemberParty createEntityFromResult (final ResultSet result) throws SQLException {
		final EntryMemberParty newEntry = new EntryMemberParty();
		
		// Setting entry properties
		newEntry.setIdEntryMemberParty(result.getInt(columns.get(EntryColumn.ID).getName()));
		newEntry.setIdMember(result.getInt(columns.get(EntryColumn.MEMBER_ID).getName()));
		newEntry.setIdParty(result.getInt(columns.get(EntryColumn.PARTY_ID).getName()));
		
		return newEntry;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#fillInsertStatement(java.sql.PreparedStatement,
	 * org.clubrockisen.entities.Entity)
	 */
	@Override
	protected void fillInsertStatement (final PreparedStatement statement, final EntryMemberParty obj)
			throws SQLException {
		int index = 1;
		statement.setInt(index++, obj.getIdMember());
		statement.setInt(index++, obj.getIdParty());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#fillUpdateStatement(java.sql.PreparedStatement,
	 * org.clubrockisen.entities.Entity)
	 */
	@Override
	protected void fillUpdateStatement (final PreparedStatement statement, final EntryMemberParty obj)
			throws SQLException {
		fillInsertStatement(statement, obj);
		statement.setInt(EntryMemberParty.getColumns().size(), obj.getIdEntryMemberParty());
	}
	
}
