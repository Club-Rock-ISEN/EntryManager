package org.clubrockisen.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.EntryMemberParty.EntryMemberColumn;

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
	 */
	public MySQLEntryMemberPartyDAO (final Connection connection) {
		super(connection);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("New " + this.getClass().getCanonicalName() + ".");
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
		newEntry.setIdEntryMemberParty(result.getInt(columns.get(EntryMemberColumn.ID).getName()));
		newEntry.setIdMember(result.getInt(columns.get(EntryMemberColumn.MEMBER_ID).getName()));
		newEntry.setIdParty(result.getInt(columns.get(EntryMemberColumn.PARTY_ID).getName()));
		
		return newEntry;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#create(org.clubrockisen.entities.Entity)
	 */
	@Override
	public EntryMemberParty create (final EntryMemberParty obj) {
		if (obj == null) {
			return null;
		}
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Creating the entry for member " + obj.getIdMember() + " at party " + obj.getIdParty());
		}
		
		EntryMemberParty newEntry = null;
		try (final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE)) {
			final String query = obj.generateInsertQuerySQL(false) + " ("
					+ "'" + obj.getIdMember() + "',"
					+ "'" + obj.getIdParty() + "');";
			if (lg.isLoggable(Level.INFO)) {
				lg.info(query);
			}
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			// Retrieve last inserted entry member party
			try (final ResultSet result = statement.getGeneratedKeys()) {
				if (result.next()) {
					newEntry = find(result.getInt(1));
				} else {
					throw new SQLException("Could not retrieve last inserted id.");
				}
			}
		} catch (final SQLException e) {
			lg.warning("SQL exception while creating an entry member party: " + e.getMessage());
			return null;
		}
		return newEntry;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#update(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean update (final EntryMemberParty obj) {
		if (obj == null) {
			return false;
		}
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Updating the entry with id = " + obj.getIdEntryMemberParty());
		}
		
		try (final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE)) {
			final String query = obj.generateUpdateQuerySQL() +
					columns.get(EntryMemberColumn.MEMBER_ID).getName() + " = '" + obj.getIdMember() + "', " +
					columns.get(EntryMemberColumn.PARTY_ID).getName() + " = '" + obj.getIdParty() + "'" +
					obj.generateWhereIDQuerySQL();
			if (lg.isLoggable(Level.INFO)) {
				lg.info(query);
			}
			statement.executeUpdate(query);
		} catch (final Exception e) {
			lg.warning("Exception while updating an entry: " + e.getMessage());
			return false;
		}
		return true;
	}
	
}
