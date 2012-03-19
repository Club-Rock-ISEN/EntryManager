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
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Parameter.ParameterColumn;
import org.clubrockisen.services.ParametersEnum;

/**
 * Class used to manipulating the parameters in the database.<br />
 * This class should be the only access point to the {@link Parameter} table in the database.
 * @author Alex
 */
public class MySQLParameterDAO implements DAO<Parameter> {
	private static Logger							lg	= Logger.getLogger(MySQLParameterDAO.class
			.getName());
	
	private final Connection						connection;
	private final Map<? extends Enum<?>, Column>	columns;
	
	/**
	 * Constructor #1.<br />
	 * @param connection the connection to the database.
	 */
	public MySQLParameterDAO(final Connection connection) {
		this.connection = connection;
		lg.fine("New " + this.getClass().getCanonicalName() + ".");
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		columns = new Parameter().getEntityColumns();
	}
	
	/**
	 * {@inheritDoc}
	 * Not available for the {@link Parameter} entity, parameters should only be created using the
	 * database and adding the appropriate enumeration in {@link ParametersEnum}.
	 */
	@Override
	public boolean create (final Parameter obj) {
		return false;
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
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			final String query = obj.generateUpdateQuerySQL() +
					columns.get(ParameterColumn.VALUE).getName() + " = '" + obj.getValue() + "', " +
					columns.get(ParameterColumn.TYPE).getName() + " = '" + obj.getType() + "'" +
					obj.generateWhereIDQuerySQL();
			lg.info(query);
			statement.executeUpdate(query);
			statement.close();
		} catch (final Exception e) {
			lg.warning("Exception while updating a parameter: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 * Not available for the {@link Parameter} entity, parameters should only be deleted using the
	 * database and deleting the appropriate enumeration in {@link ParametersEnum}.
	 */
	@Override
	public boolean delete (final Parameter obj) {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#retrieveAll()
	 */
	@Override
	public List<Parameter> retrieveAll () {
		final List<Parameter> allParameters = new ArrayList<>();
		lg.fine("Retrieving all parameters");
		
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final String query = new Parameter().generateSearchAllQuery();
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				allParameters.add(createParameterFromResult(result));
			}
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while retrieving all parameters: " + e.getMessage());
			return allParameters;
		}
		
		return allParameters;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#search(org.clubrockisen.entities.Column, java.lang.String)
	 */
	@Override
	public List<Parameter> search (final Column field, final String value) {
		if (field == null || value == null) {
			return retrieveAll();
		}
		
		final List<Parameter> parameters = new ArrayList<>();
		lg.fine("Searching members");
		
		try {
			final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			final String query = new Parameter().generateSearchAllQuery() + " WHERE " + field.getName() + " LIKE '" + value + "%'";
			lg.info(query);
			final ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				parameters.add(createParameterFromResult(result));
			}
			
			result.close();
			statement.close();
		} catch (final SQLException e) {
			lg.warning("Exception while searching parameters: " + e.getMessage());
			return parameters;
		}
		
		return parameters;
	}
	
	/**
	 * Creates a parameter from a row of result.<br />
	 * Do not move to the next result.
	 * @param result
	 *        the result of the query.
	 * @return the newly created parameter.
	 * @throws SQLException
	 *         if there was a problem while reading the data from the columns.
	 */
	private Parameter createParameterFromResult (final ResultSet result) throws SQLException {
		final Parameter newParameter = new Parameter(result.getString(columns.get(ParameterColumn.NAME).getName()), null, null);
		
		// Setting parameters properties
		newParameter.setType(result.getString(columns.get(ParameterColumn.TYPE).getName()));
		newParameter.setValue(result.getString(columns.get(ParameterColumn.VALUE).getName()));
		
		return newParameter;
	}
}
