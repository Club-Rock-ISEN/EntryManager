package org.clubrockisen.entities;

import java.util.List;
import java.util.logging.Logger;

/**
 * 
 * @author Alex
 */
public abstract class Entity {
	private static Logger			lg	= Logger.getLogger(Entity.class.getName());

	protected static List<Column>	columns;

	/**
	 * Constructor #1.<br />
	 * Unique constructor of the class. Ensure to call the {@link #setColumns()} method.
	 */
	protected Entity () {
		if (columns == null) {
			lg.info("Calling the #setColumn() method.");
			setColumns();
		}
	}

	/**
	 * Method that creates the columns (or fields) of the entity that is represent.
	 */
	protected abstract void setColumns ();

	/**
	 * Return the list of the columns of the table.
	 * @return the list of the columns.
	 */
	public static List<Column> getColumns () {
		return columns;
	}
}
