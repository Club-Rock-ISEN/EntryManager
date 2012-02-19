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
	protected Entity () {
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
	 * <code>INSERT INTO (<i>column names</i>) VALUES</code>
	 * @param putID
	 *            <code>true</code> if the ID column should be present in the query. In order for
	 *            this to work properly, the id column name <strong>MUST</strong> starts with 'id'.
	 * @return the query ready for the entity specific data.
	 */
	public String generateInsertQuerySQL (final boolean putID) {
		String query = "";

		for (final Column currentColumn : getEntityColumns().values()) {
			if (!putID && currentColumn.getName().startsWith("id")) {
				continue;
			}
			query += (query.isEmpty() ? "" : ",") + "`" + currentColumn.getName() + "`";
		}
		
		return "INSERT INTO " + getEntityName() + "(" + query + ") VALUES ";
	}
}
