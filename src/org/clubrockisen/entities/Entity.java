package org.clubrockisen.entities;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represent an abstract entity of the database.<br />
 * @author Alex
 */
public abstract class Entity implements Serializable {
	/** Logger */
	private static Logger								lg					= Logger.getLogger(Entity.class.getName());
	
	/** Serial Version UID */
	private static final long							serialVersionUID	= 1337771320210441402L;
	
	/**
	 * Constructor #1.<br />
	 * Unique constructor of the class. Ensure to call the {@link #setEntityColumns()} method.
	 */
	protected Entity () {
		if (getEntityColumns() == null) {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Calling the #setColumn() method.");
			}
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Entity clone () throws CloneNotSupportedException {
		return (Entity) super.clone();
	}
}
