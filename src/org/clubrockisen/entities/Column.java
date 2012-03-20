package org.clubrockisen.entities;

import java.util.logging.Logger;

/**
 * Class representing a column in a entity.<br />
 * It is defined by a name and a type. The column should be load once and for all to avoid unnecessary
 * duplicates.
 * @author Alex
 */
public class Column {
	private static Logger	lg	= Logger.getLogger(Column.class.getName());
	
	private Class<?>		type;
	private String			name;
	private boolean			isID;
	
	/**
	 * Constructor #1.<br />
	 * @param type
	 *            the type of the column.
	 * @param name
	 *            the name of the column.
	 * @param isID
	 *            <code>true</code> if the column is an index.
	 */
	public Column (final Class<?> type, final String name, final boolean isID) {
		this.type = type;
		this.name = name;
		this.isID = isID;
		lg.fine("New " + this.getClass().getCanonicalName() + ": " + this.name + ", type: " + this.type.getName() +
				(this.isID ? " index" : ""));
	}
	
	/**
	 * Constructor #2.<br />
	 * @param type
	 *            the type of the column.
	 * @param name
	 *            the name of the column.
	 */
	public Column (final Class<?> type, final String name) {
		this(type, name, false);
	}
	
	/**
	 * Return the type.
	 * @return the type
	 */
	public Class<?> getType () {
		return type;
	}
	
	/**
	 * Set the type.
	 * @param type
	 *            the type to set.
	 */
	public void setType (final Class<?> type) {
		this.type = type;
	}
	
	/**
	 * Return the name.
	 * @return the name.
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * Set the name.
	 * @param name
	 *            the name to set.
	 */
	public void setName (final String name) {
		this.name = name;
	}
	
	/**
	 * Return the isID.
	 * @return the isID.
	 */
	public boolean isID () {
		return isID;
	}
	
	/**
	 * Set the isID.
	 * @param isID
	 *            the isID to set.
	 */
	public void setID (final boolean isID) {
		this.isID = isID;
	}
	
}
