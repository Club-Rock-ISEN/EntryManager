package org.clubrockisen.entities;

import java.util.logging.Logger;

/**
 * 
 * @author Alex
 */
public class Column {
	private static Logger	lg	= Logger.getLogger(Column.class.getName());

	private Class<?>		type;
	private String			name;
	private boolean			isIndex;

	/**
	 * Constructor #1.<br />
	 * @param type
	 *            the type of the column.
	 * @param name
	 *            the name of the column.
	 * @param isIndex
	 *            <code>true</code> if the column is an index.
	 */
	public Column (final Class<?> type, final String name, final boolean isIndex) {
		this.type = type;
		this.name = name;
		this.isIndex = isIndex;
		lg.fine("New " + this.getClass().getCanonicalName() + ": " + this.name + ", type: " + this.type.getName() +
				(this.isIndex ? " index" : ""));
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
	 * Return the isIndex.
	 * @return the isIndex.
	 */
	public boolean isIndex () {
		return isIndex;
	}

	/**
	 * Set the isIndex.
	 * @param isIndex
	 *            the isIndex to set.
	 */
	public void setIndex (final boolean isIndex) {
		this.isIndex = isIndex;
	}

}
