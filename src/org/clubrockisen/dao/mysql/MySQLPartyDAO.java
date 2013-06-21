package org.clubrockisen.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.Party.PartyColumn;

import com.alexrnl.commons.database.Column;
import com.alexrnl.commons.database.sql.SQLDAO;

/**
 * Class used to manipulate the parties in the database.<br />
 * The class should be the only access point to the {@link Party} table in the database.
 * @author Alex
 */
public class MySQLPartyDAO extends SQLDAO<Party> {
	/** Logger */
	private static Logger							lg	= Logger.getLogger(MySQLPartyDAO.class.getName());
	
	/** Map between the columns enumeration and their name in the database */
	private final Map<? extends Enum<?>, Column>	columns;
	/** Sample to be used by the super class */
	private Party									partySample;
	
	/**
	 * Constructor #1.
	 * @param connection
	 *        the connection to the database.
	 * @throws SQLException
	 *         The prepared statements could not be created.
	 */
	public MySQLPartyDAO (final Connection connection) throws SQLException {
		super(connection);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("New " + this.getClass().getCanonicalName());
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
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#fillInsertStatement(java.sql.PreparedStatement,
	 * org.clubrockisen.entities.Entity)
	 */
	@Override
	protected void fillInsertStatement (final PreparedStatement statement, final Party obj)
			throws SQLException {
		int index = 1;
		statement.setDate(index++, new java.sql.Date(obj.getDate()));
		statement.setInt(index++, obj.getEntriesTotal());
		statement.setInt(index++, obj.getEntriesFirstPart());
		statement.setInt(index++, obj.getEntriesSecondPart());
		statement.setInt(index++, obj.getEntriesNewMembers());
		statement.setInt(index++, obj.getEntriesFree());
		statement.setInt(index++, obj.getEntriesMale());
		statement.setInt(index++, obj.getEntriesFemale());
		statement.setDouble(index++, obj.getRevenue());
		statement.setDouble(index++, obj.getProfit());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#fillUpdateStatement(java.sql.PreparedStatement,
	 * org.clubrockisen.entities.Entity)
	 */
	@Override
	protected void fillUpdateStatement (final PreparedStatement statement, final Party obj)
			throws SQLException {
		fillInsertStatement(statement, obj);
		statement.setInt(Party.getColumns().size(), obj.getIdParty());
	}
	
}