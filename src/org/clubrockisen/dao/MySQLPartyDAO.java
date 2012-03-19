package org.clubrockisen.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.Party.PartyColumn;

/**
 * Class used to manipulate the parties in the database.<br />
 * The class should be the only access point to the {@link Party} table in the database.
 * @author Alex
 */
public class MySQLPartyDAO implements DAO<Party> {
	private static Logger							lg	= Logger.getLogger(MySQLPartyDAO.class
			.getName());
	
	private final Connection						connection;
	private final Map<? extends Enum<?>, Column>	columns;
	private final SimpleDateFormat						dateFormat;
	
	/**
	 * Constructor #1.
	 * @param connection
	 *        the connection to the database.
	 */
	public MySQLPartyDAO (final Connection connection) {
		this.connection = connection;
		lg.fine("New " + this.getClass().getCanonicalName() + ".");
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		columns = new Party().getEntityColumns();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#create(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean create (final Party obj) {
		if (obj == null) {
			return false;
		}
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateInsertQuerySQL(false) + " ("
					+ "'" + dateFormat.format(obj.getDate()) + "',"
					+ "'" + obj.getEntriesTotal() + "',"
					+ "'" + obj.getEntriesFirstPart() + "',"
					+ "'" + obj.getEntriesSecondPart() + "',"
					+ "'" + obj.getEntriesNewMembers() + "',"
					+ "'" + obj.getEntriesFree() + "',"
					+ "'" + obj.getEntriesMale() + "',"
					+ "'" + obj.getEntriesFemale() + "',"
					+ "'" + obj.getRevenue() + "',"
					+ "'" + obj.getProfit() + "');";
			lg.info(query);
			statement.executeUpdate(query);
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while creating a party: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#find(int)
	 */
	@Override
	public Party find (final int id) {
		Party party = null;
		lg.fine("Finding the party with id = " + id);
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final Party p = new Party();
			final String query = p.generateSearchAllQuery() + p.generateWhereIDQuerySQL(id);
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			if (result.first()) {
				party = createPartyFromResult(result);
			}
			result.close();
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while retrieving a party: " + e.getMessage());
			return null;
		}
		
		return party;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#update(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean update (final Party obj) {
		if (obj == null) {
			return false;
		}
		lg.fine("Updating the party with id = " + obj.getIdParty());
		
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateUpdateQuerySQL() +
					columns.get(PartyColumn.DATE).getName() + " = '" + dateFormat.format(obj.getDate()) + "', " +
					columns.get(PartyColumn.ENTRIES_TOTAL).getName() + " = '" + obj.getEntriesTotal() + "', " +
					columns.get(PartyColumn.ENTRIES_FIRST_PART).getName() + " = '" + obj.getEntriesFirstPart() + "', " +
					columns.get(PartyColumn.ENTRIES_SECOND_PART).getName() + " = '" + obj.getEntriesSecondPart() + "', " +
					columns.get(PartyColumn.ENTRIES_NEW_MEMBER).getName() + " = '" + obj.getEntriesNewMembers() + "', " +
					columns.get(PartyColumn.ENTRIES_FREE).getName() + " = '" + obj.getEntriesFree() + "', " +
					columns.get(PartyColumn.ENTRIES_MALE).getName() + " = '" + obj.getEntriesMale() + "', " +
					columns.get(PartyColumn.ENTRIES_FEMALE).getName() + " = '" + obj.getEntriesFemale() + "', " +
					columns.get(PartyColumn.REVENUE).getName() + " = '" + obj.getRevenue() + "', " +
					columns.get(PartyColumn.PROFIT).getName() + " = '" + obj.getProfit() + "'" +
					obj.generateWhereIDQuerySQL();
			lg.info(query);
			statement.executeUpdate(query);
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while updating a party (" + e.getMessage() + ")");
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#delete(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean delete (final Party obj) {
		if (obj == null) {
			return true;
		}
		lg.fine("Deleting party " + obj.getDate() + " (id: " + obj.getIdParty() + ")");
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateDeleteQuerySQL();
			lg.info(query);
			statement.execute(query);
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while deleting member: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#retrieveAll()
	 */
	@Override
	public List<Party> retrieveAll () {
		final List<Party> allParties = new ArrayList<>();
		lg.fine("Retrieving all parties");
		
		try {
			final long time1 = System.currentTimeMillis();
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final String query = new Party().generateSearchAllQuery();
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			final long time2 = System.currentTimeMillis();
			while (result.next()) {
				allParties.add(createPartyFromResult(result));
			}
			final long time3 = System.currentTimeMillis();
			lg.fine("Time for request: " + (time2 - time1) + " ms");
			lg.fine("Time for building list: " + (time3 - time2) + " ms");
			lg.fine("Time for both: " + (time3 - time1) + "ms");
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while retrieving all parties: " + e.getMessage());
			return allParties;
		}
		
		return allParties;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#search(org.clubrockisen.entities.Column, java.lang.String)
	 */
	@Override
	public List<Party> search (final Column field, final String value) {
		if (field == null || value == null) {
			return retrieveAll();
		}
		
		final List<Party> parties = new ArrayList<>();
		lg.fine("Searching parties with " + field.getName() + " = " + value);
		
		try {
			final long time1 = System.currentTimeMillis();
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final String query = new Party().generateSearchAllQuery() + " WHERE " + field.getName() +
					" LIKE '" + value + "%'";
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			final long time2 = System.currentTimeMillis();
			while (result.next()) {
				parties.add(createPartyFromResult(result));
			}
			final long time3 = System.currentTimeMillis();
			lg.fine("Time for request: " + (time2 - time1) + " ms");
			lg.fine("Time for building list: " + (time3 - time2) + " ms");
			lg.fine("Time for both: " + (time3 - time1) + "ms");
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while searching parties: " + e.getMessage());
			return parties;
		}
		
		return parties;
	}
	
	/**
	 * Creates a party from a row of a result.<br />
	 * Do not move to the next result.
	 * @param result
	 *        the result of the query
	 * @return the newly created party.
	 * @throws SQLException
	 *         if there was a problem while reading the data from the columns.
	 */
	private Party createPartyFromResult (final ResultSet result) throws SQLException {
		final Party newParty = new Party();
		
		newParty.setIdParty(result.getInt(columns.get(PartyColumn.ID).getName()));
		newParty.setEntriesTotal(result.getInt(columns.get(PartyColumn.ENTRIES_TOTAL).getName()));
		newParty.setEntriesFirstPart(result.getInt(columns.get(PartyColumn.ENTRIES_FIRST_PART).getName()));
		newParty.setEntriesSecondPart(result.getInt(columns.get(PartyColumn.ENTRIES_SECOND_PART).getName()));
		newParty.setEntriesNewMembers(result.getInt(columns.get(PartyColumn.ENTRIES_NEW_MEMBER).getName()));
		newParty.setEntriesFree(result.getInt(columns.get(PartyColumn.ENTRIES_FREE).getName()));
		newParty.setEntriesMale(result.getInt(columns.get(PartyColumn.ENTRIES_MALE).getName()));
		newParty.setEntriesFemale(result.getInt(columns.get(PartyColumn.ENTRIES_FEMALE).getName()));
		newParty.setRevenue(result.getDouble(columns.get(PartyColumn.REVENUE).getName()));
		newParty.setProfit(result.getDouble(columns.get(PartyColumn.PROFIT).getName()));
		
		return newParty;
	}
}
