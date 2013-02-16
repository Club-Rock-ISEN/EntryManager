package org.clubrockisen.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.dao.NoIdException;
import org.clubrockisen.dao.QueryGenerator;
import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Parameter.ParameterColumn;

/**
 * Class used to manipulating the parameters in the database.<br />
 * This class should be the only access point to the {@link Parameter} table in the database.
 * @author Alex
 */
public class MySQLParameterDAO extends MySQLDAO<Parameter> {
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
	 * @throws NoIdException
	 *         The {@link Parameter} has no ID column.
	 */
	public MySQLParameterDAO(final Connection connection) throws SQLException, NoIdException {
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
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#update(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean update (final Parameter obj) {
		if (obj == null) {
			return false;
		}
		try (final Statement statement = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE)) {
			final String query = QueryGenerator.update(obj) +
					columns.get(ParameterColumn.VALUE).getName() + " = '" + obj.getValue() + "', " +
					columns.get(ParameterColumn.TYPE).getName() + " = '" + obj.getType() + "', " +
					columns.get(ParameterColumn.COMPONENT_CLASS).getName() + " = '" + obj.getComponentClass() + "'" +
					QueryGenerator.whereID(obj);
			lg.info(query);
			statement.executeUpdate(query);
		} catch (final SQLException | NoIdException e) {
			lg.warning("Could not update parameter: " + e.getMessage());
			return false;
		}
		return true;
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
