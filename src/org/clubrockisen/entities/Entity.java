package org.clubrockisen.entities;

import java.util.Map;
import java.util.logging.Logger;

/**
 * 
 * @author Alex
 */
public abstract class Entity {
	private static Logger	lg	= Logger.getLogger(Entity.class.getName());

	/**
	 * Constructor #1.<br />
	 * Unique constructor of the class. Ensure to call the {@link #setEntityColumns()} method.
	 */
	protected Entity() {
		if (getEntityColumns() == null) {
			lg.fine("Calling the #setColumn() method.");
			setEntityColumns();
		}
	}

	/**
	 * Return the name of the represented entity.
	 * @return the name of the represented entity.
	 */
	public abstract String getEntityName ();

	/**
	 * Method that creates the columns (or fields) of the entity that is represent.
	 */
	protected abstract void setEntityColumns ();

	/**
	 * Return the list of the columns of the table.
	 * @return the list of the columns.
	 */
	public abstract Map<? extends Enum<?>, Column> getEntityColumns ();

	/**
	 * Return the id field (as a {@link String}) of the entity.
	 * @return the id of the object.
	 */
	public abstract String getID ();

	/**
	 * Return the id {@link Column} of the entity.
	 * @return the {@link Column} which is defined as the unique (and identifying) column of the
	 *         object.
	 * @throws Exception if no such {@link Column} could be found.
	 */
	public Column getIDColumn () throws Exception {
		for (final Column column : getEntityColumns().values()) {
			if (column.isID()) {
				return column;
			}
		}
		throw new Exception("No ID column for entity ");
	}

	/**
	 * Generates the beginning of the insert query in SQL syntax:<br />
	 * <code>INSERT INTO (<i>column names</i>) VALUES</code>
	 * @return the query ready for the entity specific data.
	 * @see #generateInsertQuerySQL(boolean) Method that allow you to specify if the ID column
	 *      should be in the query
	 */
	public String generateInsertQuerySQL () {
		return generateInsertQuerySQL(true);
	}

	/**
	 * Generates the beginning of the insert query in SQL syntax:<br />
	 * <code>INSERT INTO (<i>column names</i>) VALUES</code><br />
	 * The {@link Column}s will be inserted in the order of their declaration in the corresponding
	 * enumeration.
	 * @param putID
	 *            <code>true</code> if the {@link Column#isID() ID column} should be present in the
	 *            query.
	 * @return the query ready for the entity specific data.
	 */
	public String generateInsertQuerySQL (final boolean putID) {
		String query = "";

		for (final Column currentColumn : getEntityColumns().values()) {
			if (!putID && currentColumn.isID()) {
				continue;
			}
			query += (query.isEmpty() ? "" : ",") + "`" + currentColumn.getName() + "`";
		}

		return "INSERT INTO " + getEntityName() + "(" + query + ") VALUES ";
	}

	/**
	 * Generates a string with the delete statement for a SQL database.<br />
	 * <code>DELETE FROM entity WHERE idColumn = value</code>
	 * @return the query which delete an object from the table.
	 * @throws Exception if the query could not be generated.
	 */
	public String generateDeleteQuerySQL () throws Exception {
		return "DELETE FROM " + getEntityName() + generateWhereIDQuerySQL(getID());
	}

	/**
	 * Generates the beginning of the update query of the entity.
	 * <code>UPDATE entity SET </code>
	 * @return the query ready for adding the entity specific data.
	 */
	public String generateUpdateQuerySQL () {
		return "UPDATE " + getEntityName() + " SET ";
	}
	
	/**
	 * Generates the 'WHERE' part of a query which identifies it by its id.<br />
	 * <code> WHERE idColumn = value</code>
	 * @return the WHERE clause of a query.
	 * @throws Exception if the query could not be generated.
	 */
	public String generateWhereIDQuerySQL () throws Exception {
		return generateWhereIDQuerySQL(getID());
	}

	/**
	 * Generates the 'WHERE' part of a query which identifies it by its id.<br />
	 * <code> WHERE idColumn = value</code>
	 * @param id TODO
	 * @return the WHERE clause of a query.
	 * @throws Exception if the query could not be generated.
	 */
	public String generateWhereIDQuerySQL (final Object id) throws Exception {
		final Column idColumn = getIDColumn();
		String fieldValue = " = ";
		if (idColumn.getType().equals(Integer.class)) {
			fieldValue += id.toString();
		} else {
			fieldValue += "'" + id.toString() + "'";
		}
		return " WHERE " + getIDColumn().getName() + fieldValue;
	}
	
	/**
	 * Generates the 'SELECT *' query for the current object.<br />
	 * <code>SELECT * FROM entity</code>
	 * @return the SQL query for retrieving all the rows from an entity.
	 */
	public String generateSearchAllQuery () {
		return "SELECT * FROM " + getEntityName();
	}
}
