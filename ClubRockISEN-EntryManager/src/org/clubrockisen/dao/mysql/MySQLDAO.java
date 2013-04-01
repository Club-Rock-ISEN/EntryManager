package org.clubrockisen.dao.mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.NoIdException;
import org.clubrockisen.dao.QueryGenerator;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Entity;

/**
 * This class shall be the super class of all MySQL {@link DAO}.<br />
 * Contains method which factorise similar code between classes.
 * @author Alex
 * @param <T>
 *        The class of the object to manipulate.
 */
public abstract class MySQLDAO<T extends Entity> implements DAO<T> {
	/** Logger */
	private static Logger							lg	= Logger.getLogger(MySQLDAO.class.getName());
	
	/** The connection to the database */
	private final Connection						connection;
	/** Name of the entity manipulated */
	private final String							entityName;
	
	// Prepared statements
	/** Prepared statement for the create operation */
	private final PreparedStatement					create;
	/** Prepared statement for the read operation */
	private final PreparedStatement					find;
	/** Prepared statement for the delete operation */
	private final PreparedStatement					delete;
	/** Prepared statement for the search all operation */
	private final PreparedStatement					searchAll;
	/** Prepared statement for the search operation (one per column) */
	private final Map<Column, PreparedStatement>	searches;
	
	/**
	 * Constructor #1.<br />
	 * @param connection
	 *        the connection to the database.
	 * @throws SQLException
	 *         The prepared statements could not be created.
	 * @throws NoIdException
	 *         The {@link Entity} has no ID column.
	 */
	public MySQLDAO (final Connection connection) throws SQLException, NoIdException {
		super();
		this.connection = connection;
		this.entityName = getEntitySample().getEntityName();
		
		// Create prepared statements
		create = connection.prepareStatement(QueryGenerator.insertPrepared(getEntitySample()),
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		find = connection.prepareStatement(QueryGenerator.searchAll(getEntitySample()) +
				QueryGenerator.whereID(getEntitySample(), null));
		delete = connection.prepareStatement(QueryGenerator.delete(getEntitySample(), true));
		searchAll = connection.prepareStatement(QueryGenerator.searchAll(getEntitySample()));
		searches = new HashMap<>(getEntitySample().getEntityColumns().size());
		for (final Column column : getEntitySample().getEntityColumns().values()) {
			final PreparedStatement statement;
			if (column.getType().equals(String.class)) {
				statement = connection.prepareStatement(QueryGenerator.searchAll(getEntitySample()) +
						QueryGenerator.whereLike(column, null));
			} else {
				statement = connection.prepareStatement(QueryGenerator.searchAll(getEntitySample()) +
						QueryGenerator.where(column, null));
			}
			
			searches.put(column, statement);
		}
	}
	
	/**
	 * Return the connection to the database.
	 * @return the MySQL connection.
	 */
	protected Connection getConnection () {
		return connection;
	}
	
	/**
	 * Return an plain entity.<br />
	 * Used to access the method defined in the {@link Entity} class.<br />
	 * <em>Will not be inserted into the database nor returned to the user of the class.</em>
	 * @return an entity of the manipulated DAO.
	 */
	protected abstract T getEntitySample ();
	
	/**
	 * Creates an entity from a row of result.<br />
	 * Do not move to the next result.
	 * @param result
	 *        the result of the query.
	 * @return the newly created entity.
	 * @throws SQLException
	 *         if there was a problem while reading the data from the columns.
	 */
	protected abstract T createEntityFromResult (final ResultSet result) throws SQLException;
	
	/**
	 * Fill the prepared statement with the object value for the insert query.<br />
	 * The values must be set in the order of their column declaration, and the id column should not
	 * be set as it is an insert statement.
	 * @param statement
	 *        the statement to fill.
	 * @param obj
	 *        the object to use.
	 * @throws SQLException
	 *         if there was a problem while filling the statement.
	 */
	protected abstract void fillInsertStatement (final PreparedStatement statement, final T obj)
			throws SQLException;
	
	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close () throws IOException {
		try {
			create.close();
			find.close();
			delete.close();
			searchAll.close();
			for (final PreparedStatement statement : searches.values()) {
				statement.close();
			}
			searches.clear();
		} catch (final SQLException e) {
			lg.warning("Error while closing statements (" + e.getClass() + "; " + e.getMessage() + ")");
			throw new IOException("Exception while closing statements", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.abstracts.DAO#create(org.clubrockisen.entities.Entity)
	 */
	@Override
	public T create (final T obj) {
		if (obj == null) {
			return null;
		}
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Creating the " + entityName + ": " + obj.toString());
		}
		
		T newEntity = null;
		
		try {
			fillInsertStatement(create, obj);
			create.executeUpdate();
			// Retrieving the created member
			try (final ResultSet resultSet = create.getGeneratedKeys()) {
				if (resultSet.next()) {
					newEntity = find(resultSet.getInt(1));
				} else {
					throw new SQLException("Could not retrieve last inserted id.");
				}
			}
		} catch (final SQLException e) {
			lg.warning("Exception while creating a member: " + e.getMessage());
			return null;
		}
		return newEntity;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.abstracts.DAO#find(int)
	 */
	@Override
	public T find (final int id) {
		T entity = null;
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Finding the " + entityName + " with id = " + id);
		}
		
		try {
			find.setInt(1, id);
			try (final ResultSet result = find.executeQuery()) {
				if (result.first()) {
					entity = createEntityFromResult(result);
				} else if (lg.isLoggable(Level.INFO)) {
					lg.info("Could not retrieve " + entityName + " with id = " + id);
				}
			}
		} catch (final SQLException e) {
			lg.warning("Could not find " + entityName + ": " + e.getMessage());
			return null;
		}
		return entity;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.abstracts.DAO#delete(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean delete (final T obj) {
		if (obj == null) {
			return true;
		}
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Deleting " + entityName + " " + obj);
		}
		try {
			delete.setObject(1, obj.getID());
			delete.execute();
		} catch (final SQLException e) {
			lg.warning("Could not delete " + entityName + ": " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.abstracts.DAO#retrieveAll()
	 */
	@Override
	public Set<T> retrieveAll () {
		final Set<T> allEntities = new HashSet<>();
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Retrieving all " + entityName);
		}
		
		// Time before query
		long timeBefore = 0;
		if (lg.isLoggable(Level.FINE)) {
			timeBefore = System.currentTimeMillis();
		}
		
		try {
			try (final ResultSet result = searchAll.executeQuery()) {
				while (result.next()) {
					allEntities.add(createEntityFromResult(result));
				}
			}
		} catch (final SQLException e) {
			lg.warning("Could not retrieve all " + entityName + ": " + e.getMessage());
			return allEntities;
		}
		
		// Time for query logging
		if (lg.isLoggable(Level.FINE)) {
			final long timeAfter = System.currentTimeMillis();
			lg.fine("Time for retrieving all " + entityName + ": " + (timeAfter - timeBefore) + " ms");
		}
		
		return allEntities;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.abstracts.DAO#search(org.clubrockisen.entities.Column, java.lang.String)
	 */
	@Override
	public Set<T> search (final Column field, final String value) {
		if (field == null || value == null) {
			return retrieveAll();
		}
		
		final Set<T> entities = new HashSet<>();
		try {
			final PreparedStatement statement = searches.get(field);
			if (field.getType().equals(String.class)) {
				statement.setString(1, value + "%"); //FIXME remove, might not be always relevant
			} else {
				statement.setString(1, value);
			}
			try (final ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					entities.add(createEntityFromResult(result));
				}
			}
		} catch (final SQLException e) {
			lg.warning("Could not retrieve " + entityName + ": " + e.getMessage());
			return entities;
		}
		
		return entities;
	}
	
	
	
}
