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
	 * Method that creates the columns (or fields) of the entity that is represent.
	 */
	protected abstract void setEntityColumns ();

	/**
	 * Return the list of the columns of the table.
	 * @return the list of the columns.
	 */
	public abstract Map<? extends Enum<?>, Column> getEntityColumns ();
}
