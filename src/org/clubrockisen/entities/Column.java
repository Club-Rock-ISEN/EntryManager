package org.clubrockisen.entities;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing a column in a entity.<br />
 * It is defined by a name and a type. The column should be load once and for all to avoid unnecessary
 * duplicates.
 * @author Alex
 */
public class Column {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(Column.class.getName());
	
	/** The Java type associated to the column */
	private Class<?>		type;
	/** The name (identifier) of the column in the data base */
	private String			name;
	/** <code>true</code> if the column is an index of the table */
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
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("New " + this.getClass().getCanonicalName() + ": " + this.name + ", type: "
					+ this.type.getName() + (this.isID ? " index" : ""));
		}
	}
	
	/**
	 * Constructor #2.<br />
	 * Build a column which is not an index of the table.
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
	 * @return <code>true</code> if the column is an index of the table.
	 */
	public boolean isID () {
		return isID;
	}
	
	/**
	 * Set the isID.
	 * @param isID
	 *            <code>true</code> if the column is an index of the table.
	 */
	public void setID (final boolean isID) {
		this.isID = isID;
	}
	
}
