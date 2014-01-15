package org.clubrockisen.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Parameter.ParameterColumn;

import com.alexrnl.commons.database.sql.SQLDAO;
import com.alexrnl.commons.database.structure.Column;

/**
 * Class used to manipulating the parameters in the database.<br />
 * This class should be the only access point to the {@link Parameter} table in the database.
 * @author Alex
 */
public class MySQLParameterDAO extends SQLDAO<Parameter> {
	/** Logger */
	private static Logger							lg	= Logger.getLogger(MySQLParameterDAO.class.getName());
	
	/** Map between the columns enumeration and their name in the database */
	private final Map<? extends Enum<?>, Column>	columns;
	/** Sample to be used by the super class */
	private Parameter								parameterSample;
	
	/**
	 * Constructor #1.<br />
	 * @param connection the connection to the database.
	 * @throws SQLException
	 *         The prepared statements could not be created.
	 */
	public MySQLParameterDAO(final Connection connection) throws SQLException {
		super(connection);
		lg.fine("New " + this.getClass().getCanonicalName());
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		columns = getEntitySample().getEntityColumns();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#getEntitySample()
	 */
	@Override
	protected Parameter getEntitySample () {
		if (parameterSample == null) {
			parameterSample = new Parameter();
		}
		return parameterSample;
	}
	
	@Override
	protected Parameter createEntityFromResult (final ResultSet result) throws SQLException {
		final Parameter newParameter = new Parameter(result.getString(columns.get(ParameterColumn.NAME).getName()));
		
		// Setting parameters properties
		newParameter.setType(result.getString(columns.get(ParameterColumn.TYPE).getName()));
		newParameter.setValue(result.getString(columns.get(ParameterColumn.VALUE).getName()));
		newParameter.setComponentClass(result.getString(columns.get(ParameterColumn.COMPONENT_CLASS).getName()));
		
		return newParameter;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#fillInsertStatement(java.sql.PreparedStatement,
	 * org.clubrockisen.entities.Entity)
	 */
	@Override
	protected void fillInsertStatement (final PreparedStatement statement, final Parameter obj)
			throws SQLException {
		// Leave empty for parameter DAO
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.mysql.MySQLDAO#fillUpdateStatement(java.sql.PreparedStatement,
	 * org.clubrockisen.entities.Entity)
	 */
	@Override
	protected void fillUpdateStatement (final PreparedStatement statement, final Parameter obj)
			throws SQLException {
		int index = 1;
		statement.setString(index++, obj.getValue());
		statement.setString(index++, obj.getType());
		statement.setString(index++, obj.getComponentClass());
		// For the parameter table, the id is the name
		statement.setString(index++, obj.getName());
	}
	
	/**
	 * {@inheritDoc}
	 * Not available for the {@link Parameter} entity, parameters should only be created using the
	 * database.
	 */
	@Override
	public Parameter create (final Parameter obj) {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 * Not available for the {@link Parameter} entity, the unique ID of the parameter being a
	 * {@link String}.
	 * Use {@link #search(Column, String)} instead.
	 * @see #search(Column, String)
	 */
	@Override
	public Parameter find (final int id) {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 * Not available for the {@link Parameter} entity, parameters should only be deleted using the
	 * database.
	 */
	@Override
	public boolean delete (final Parameter obj) {
		return false;
	}
}
