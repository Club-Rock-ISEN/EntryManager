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
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.EntryMemberParty.EntryMemberColumn;

/**
 * Class used to manipulating the entries of the members in the database.<br />
 * This class should be the only access point to the {@link EntryMemberParty} table in the database.
 * @author Alex
 */
public class MySQLEntryMemberParty implements DAO<EntryMemberParty> {
	private static Logger	lg	= Logger.getLogger(MySQLEntryMemberParty.class.getName());
	
	private final Connection						connection;
	private final Map<? extends Enum<?>, Column>	columns;
	
	/**
	 * Constructor #1.<br />
	 * @param connection
	 *        the connection to the database.
	 */
	public MySQLEntryMemberParty (final Connection connection) {
		this.connection = connection;
		lg.fine("New " + this.getClass().getCanonicalName() + ".");
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		columns = new EntryMemberParty().getEntityColumns();
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
		
		EntryMemberParty newEntry = null;
		lg.fine("Creating the entry for member " + obj.getIdMember() + " at party " + obj.getIdParty());
		
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateInsertQuerySQL(false) + " ("
					+ "'" + obj.getIdMember() + "',"
					+ "'" + obj.getIdParty() + "');";
			lg.info(query);
			statement.executeUpdate(query);
			final ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				newEntry = find(resultSet.getInt(1));
			} else {
				throw new SQLException("could not retrieve last inserted id.");
			}
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while creating a member: " + e.getMessage());
			return null;
		}
		return newEntry;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#find(int)
	 */
	@Override
	public EntryMemberParty find (final int id) {
		EntryMemberParty entry = null;
		lg.fine("Finding the member with id = " + id);
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final EntryMemberParty e = new EntryMemberParty();
			final String query = e.generateSearchAllQuery() + e.generateWhereIDQuerySQL(id);
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			if (result.first()) {
				entry = createEntryMemberPartyformResult(result);
			}
			result.close();
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while retrieving a member: " + e.getMessage());
			return null;
		}
		return entry;
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
		lg.fine("Updating the entry with id = " + obj.getIdEntryMemberParty());
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateUpdateQuerySQL() +
					columns.get(EntryMemberColumn.MEMBER_ID).getName() + " = '" + obj.getIdMember() + "', " +
					columns.get(EntryMemberColumn.PARTY_ID).getName() + " = '" + obj.getIdParty() + "'" +
					obj.generateWhereIDQuerySQL();
			lg.info(query);
			statement.executeUpdate(query);
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while updating an entry: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#delete(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean delete (final EntryMemberParty obj) {
		if (obj == null) {
			return true;
		}
		lg.fine("Deleting entry " + obj.getIdEntryMemberParty());
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateDeleteQuerySQL();
			lg.info(query);
			statement.executeUpdate(query);
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while deleting entry: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#retrieveAll()
	 */
	@Override
	public List<EntryMemberParty> retrieveAll () {
		final List<EntryMemberParty> allEntries = new ArrayList<>();
		lg.fine("Retrieving all members");
		
		try {
			final long time1 = System.currentTimeMillis();
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final String query = new EntryMemberParty().generateSearchAllQuery();
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			final long time2 = System.currentTimeMillis();
			while (result.next()) {
				allEntries.add(createEntryMemberPartyformResult(result));
			}
			final long time3 = System.currentTimeMillis();
			lg.fine("Time for request: " + (time2-time1) + " ms");
			lg.fine("Time for building list: " + (time3-time2) + " ms");
			lg.fine("Time for both: " + (time3-time1) + " ms");
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while retrieving all entries: " + e.getMessage());
			return allEntries;
		}
		
		return allEntries;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#search(org.clubrockisen.entities.Column, java.lang.String)
	 */
	@Override
	public List<EntryMemberParty> search (final Column field, final String value) {
		if (field == null || value == null) {
			return retrieveAll();
		}
		
		final List<EntryMemberParty> entries = new ArrayList<>();
		lg.fine("Searching entries with " + field.getName() + " = " + value);
		
		try {
			final long time1 = System.currentTimeMillis();
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String query = new EntryMemberParty().generateSearchAllQuery() + " WHERE " + field.getName();
			if (field.getType().equals(String.class)) {
				query += " LIKE '" + value + "%'";
			} else if (field.getType().getSuperclass().equals(Number.class)) {
				query += " = " + value;
			} else {
				query += " = '" + value + "'";
			}
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			final long time2 = System.currentTimeMillis();
			while (result.next()) {
				entries.add(createEntryMemberPartyformResult(result));
			}
			final long time3 = System.currentTimeMillis();
			lg.fine("Time for request: " + (time2 - time1) + " ms");
			lg.fine("Time for building list: " + (time3 - time2) + " ms");
			lg.fine("Time for both: " + (time3 - time1) + "ms");
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while searching entries: " + e.getMessage());
			return entries;
		}
		
		return entries;
	}
	
	/**
	 * Creates an entry from a row of result.<br />
	 * Do not move to the next result.
	 * @param result
	 *        the result of the query.
	 * @return the newly created entry.
	 * @throws SQLException
	 *         if there was a problem while reading the data from the columns.
	 */
	private EntryMemberParty createEntryMemberPartyformResult (final ResultSet result) throws SQLException {
		final EntryMemberParty newEntry = new EntryMemberParty();
		
		// Setting entry properties
		newEntry.setIdEntryMemberParty(result.getInt(columns.get(EntryMemberColumn.ID).getName()));
		newEntry.setIdMember(result.getInt(columns.get(EntryMemberColumn.MEMBER_ID).getName()));
		newEntry.setIdParty(result.getInt(columns.get(EntryMemberColumn.PARTY_ID).getName()));
		
		return newEntry;
	}
	
}
