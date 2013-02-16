package org.clubrockisen.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Entity;

/**
 * Utility methods to build SQL queries.
 * @author Alex
 */
public final class QueryGenerator {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(QueryGenerator.class.getName());
	
	/** Map between the entities and their id columns */
	private static Map<Class<? extends Entity>, Column>	idColumns 			= new HashMap<>();
	
	/**
	 * Default constructor.<br />
	 */
	private QueryGenerator () {
		super();
	}
	
	/**
	 * Return the id {@link Column} of the entity.
	 * @param object
	 *        the object which represent the entity.
	 * @return the {@link Column} which is defined as the unique (and identifying) column of the
	 *         object.
	 * @throws NoIdException
	 *         if no such {@link Column} could be found.
	 */
	public static Column getIDColumn (final Entity object) throws NoIdException {
		// If the id column has previously been found
		if (idColumns.containsKey(object.getClass())) {
			return idColumns.get(object.getClass());
		}
		// Searching the id column
		for (final Column column : object.getEntityColumns().values()) {
			if (column.isID()) {
				if (lg.isLoggable(Level.FINE)) {
					lg.fine("ID column found for entity " + object.getEntityName() + ": " + column.getName());
				}
				idColumns.put(object.getClass(), column);
				return idColumns.get(object.getClass());
			}
		}
		// If none, throw exception
		throw new NoIdException(object.getClass());
	}
	
	/**
	 * 
	 * @param string
	 *        the string to escape.
	 * @return the escaped string.
	 */
	public static String escapeSpecialChars (final String string) {
		String escapedString = string.replace("'", "''");
		escapedString = escapedString.replace("\"", "\\\"");
		return escapedString;
	}
	
	/**
	 * Generates the beginning of the insert query in SQL syntax:<br />
	 * <code>INSERT INTO (<i>column names</i>) VALUES</code>
	 * @param object
	 *        the object which represent the entity.
	 * @return the query ready for the entity specific data.
	 * @see #insert(Entity,boolean) Method that allows to specify if the ID column
	 *      should be in the query
	 */
	public static String insert (final Entity object) {
		return insert(object, true);
	}
	
	/**
	 * Generates the beginning of the insert query in SQL syntax:<br />
	 * <code>INSERT INTO (<i>column names</i>) VALUES</code><br />
	 * The {@link Column}s will be inserted in the order of their declaration in the corresponding
	 * enumeration.
	 * @param object
	 *        the object which represent the entity.
	 * @param putID
	 *        <code>true</code> if the {@link Column#isID() ID column} should be present in the
	 *        query.
	 * @return the query ready for the entity specific data.
	 */
	public static String insert (final Entity object, final boolean putID) {
		final StringBuilder columnsNames = new StringBuilder();
		
		for (final Column currentColumn : object.getEntityColumns().values()) {
			if (!putID && currentColumn.isID()) {
				continue;
			}
			// Adding a comma between fields
			if (columnsNames.length() > 0) {
				columnsNames.append(",");
			}
			columnsNames.append("`");
			columnsNames.append(currentColumn.getName());
			columnsNames.append("`");
		}
		
		return "INSERT INTO " + object.getEntityName() + "(" + columnsNames.toString() + ") VALUES ";
	}
	
	/**
	 * Generates a string with the delete statement for a SQL database.<br />
	 * <code>DELETE FROM entity WHERE idColumn = value</code>
	 * @param object
	 *        the object which represent the entity.
	 * @return the query which delete an object from the table.
	 * @throws NoIdException
	 *         if the query could not be generated.
	 */
	public static String delete (final Entity object) throws NoIdException {
		return delete(object, false);
	}
	
	/**
	 * Generates a string with the delete statement for a SQL database.<br />
	 * <code>DELETE FROM entity WHERE idColumn = value</code>
	 * @param object
	 *        the object which represent the entity.
	 * @param preparedStatement
	 *        <code>true</code> if the query is for a prepared statement.
	 * @return the query which delete an object from the table.
	 * @throws NoIdException
	 *         if the query could not be generated.
	 */
	public static String delete (final Entity object, final boolean preparedStatement) throws NoIdException {
		return "DELETE FROM " + object.getEntityName() + whereID(object, preparedStatement ? null : object.getID());
	}
	
	/**
	 * Generates the beginning of the update query of the entity. <code>UPDATE entity SET </code>
	 * @param object
	 *        the object which represent the entity.
	 * @return the query ready for adding the entity specific data.
	 */
	public static String update (final Entity object) {
		return "UPDATE " + object.getEntityName() + " SET ";
	}
	
	/**
	 * Generates the 'WHERE' part of a query which identifies it by its id.<br />
	 * <code> WHERE idColumn = value</code>
	 * @param object
	 *        the object which represent the entity.
	 * @return the WHERE clause of a query.
	 * @throws NoIdException
	 *         if the query could not be generated.
	 */
	public static String whereID (final Entity object) throws NoIdException {
		return whereID(object, object.getID());
	}
	
	/**
	 * Generates the 'WHERE' part of a query which identifies it by its id.<br />
	 * <code> WHERE idColumn = value</code><br />
	 * If the id is <code>null</code>, then a query for a prepared statement is generated (with <code>?</code>).
	 * @param object
	 *        the object which represent the entity.
	 * @param id
	 *        the id of the object to find.
	 * @return the WHERE clause of a query.
	 * @throws NoIdException
	 *         if the query could not be generated.
	 */
	public static String whereID (final Entity object, final Object id) throws NoIdException {
		String fieldValue = " = ";
		if (id == null) {
			fieldValue += "?";
		} else if (getIDColumn(object).getType().getSuperclass().equals(Number.class)) {
			fieldValue += escapeSpecialChars(id.toString());
		} else {
			fieldValue += "'" + escapeSpecialChars(id.toString()) + "'";
		}
		return " WHERE " + getIDColumn(object).getName() + fieldValue;
	}
	
	/**
	 * Generates the 'SELECT *' query for the current object.<br />
	 * <code>SELECT * FROM entity</code>
	 * @param object
	 *        the object which represent the entity.
	 * @return the SQL query for retrieving all the rows from an entity.
	 */
	public static String searchAll (final Entity object) {
		return "SELECT * FROM " + object.getEntityName();
	}
}
