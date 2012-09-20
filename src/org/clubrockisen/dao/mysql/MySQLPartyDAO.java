package org.clubrockisen.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.Constants;
import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.NoIdException;
import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.Party.PartyColumn;

/**
 * Class used to manipulate the parties in the database.<br />
 * The class should be the only access point to the {@link Party} table in the database.
 * @author Alex
 */
public class MySQLPartyDAO extends MySQLDAO<Party> {
	/** Logger */
	private static Logger							lg	= Logger.getLogger(MySQLPartyDAO.class.getName());
	
	/** Map between the columns enumeration and their name in the database */
	private final Map<? extends Enum<?>, Column>	columns;
	/** Date format used in the database */
	private final SimpleDateFormat					dateFormat = new SimpleDateFormat(Constants.SQL_DATE_FORMAT);
	/** Sample to be used by the super class */
	private Party									partySample;
	
	/**
	 * Constructor #1.
	 * @param connection
	 *        the connection to the database.
	 */
	public MySQLPartyDAO (final Connection connection) {
		super(connection);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("New " + this.getClass().getCanonicalName() + ".");
		}
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		columns = new Party().getEntityColumns();
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#getEntitySample()
	 */
	@Override
	protected Party getEntitySample () {
		if (partySample == null) {
			partySample = new Party();
		}
		return partySample;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#createEntityFromResult(java.sql.ResultSet)
	 */
	@Override
	protected Party createEntityFromResult (final ResultSet result) throws SQLException {
		final Party newParty = new Party();
		
		newParty.setIdParty(result.getInt(columns.get(PartyColumn.ID).getName()));
		newParty.setDate(result.getDate(columns.get(PartyColumn.DATE).getName()).getTime());
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
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#create(org.clubrockisen.entities.Entity)
	 */
	@Override
	public Party create (final Party obj) {
		if (obj == null) {
			return null;
		}
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Creating the party " + obj.getDate());
		}
		
		Party newParty = null;
		try (final Statement statement = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE)) {
			final String query = obj.generateInsertQuerySQL(false) + " ("
					+ "'" + dateFormat.format(new Date(obj.getDate())) + "',"
					+ "'" + obj.getEntriesTotal() + "',"
					+ "'" + obj.getEntriesFirstPart() + "',"
					+ "'" + obj.getEntriesSecondPart() + "',"
					+ "'" + obj.getEntriesNewMembers() + "',"
					+ "'" + obj.getEntriesFree() + "',"
					+ "'" + obj.getEntriesMale() + "',"
					+ "'" + obj.getEntriesFemale() + "',"
					+ "'" + obj.getRevenue() + "',"
					+ "'" + obj.getProfit() + "');";
			if (lg.isLoggable(Level.INFO)) {
				lg.info(query);
			}
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			try (final ResultSet resultSet = statement.getGeneratedKeys()) {
				if (resultSet.next()) {
					newParty = find(resultSet.getInt(1));
				} else {
					throw new SQLException("Could not retrieve last inserted id.");
				}
			}
		} catch (final SQLException e) {
			lg.warning("Exception while creating a party: " + e.getMessage());
			return null;
		}
		return newParty;
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
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Updating the party with id = " + obj.getIdParty());
		}
		
		try (final Statement statement = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE)) {
			final String query = obj.generateUpdateQuerySQL() +
					columns.get(PartyColumn.DATE).getName() + " = '" + dateFormat.format(new Date(obj.getDate())) + "', " +
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
			if (lg.isLoggable(Level.INFO)) {
				lg.info(query);
			}
			statement.executeUpdate(query);
		} catch (final NoIdException | SQLException e) {
			lg.warning("Exception while updating a party (" + e.getMessage() + ")");
			return false;
		}
		return true;
	}
	
}