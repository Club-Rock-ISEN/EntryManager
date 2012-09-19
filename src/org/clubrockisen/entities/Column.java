package org.clubrockisen.entities;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.AttributeComparator;
import org.clubrockisen.common.Auto;
import org.clubrockisen.common.Comparable;

/**
 * Class representing a column in a entity.<br />
 * It is defined by a name and a type. The column should be load once and for all to avoid unnecessary
 * duplicates.
 * @author Alex
 */
public class Column implements Serializable {
	/** Logger */
	private static Logger		lg					= Logger.getLogger(Column.class.getName());
	
	/** Serial Version UID */
	private static final long	serialVersionUID	= 7764202331118428284L;
	
	/** The Java type associated to the column */
	private Class<?>			type;
	/** The name (identifier) of the column in the data base */
	private String				name;
	/** <code>true</code> if the column is an index of the table */
	private boolean				isID;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public Column () {
		this(null, null);
	}
	
	/**
	 * Constructor #2.<br />
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
	 * Constructor #3.<br />
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
	@Comparable
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
	@Comparable
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
	@Comparable
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return "name: " + name + ", type: " + type.getSimpleName() + ", is id: " + isID;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode () {
		return AttributeComparator.hashCode(new Object[] {name, type, isID});
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Column)) {
			return false;
		}
		final Column other = (Column) obj;
		long begin = System.nanoTime();
		final boolean auto = Auto.getInstance().compare(this, other);
		lg.info("autoCompare in " + (System.nanoTime() - begin) + "ms = " + auto);
		begin = System.nanoTime();
		final AttributeComparator comparator = new AttributeComparator();
		comparator.add(type, other.type);
		comparator.add(name, other.name);
		comparator.add(isID, other.isID);
		final boolean res = comparator.areEquals();
		lg.info("compare in " + (System.nanoTime() - begin) + "ms = " + res);
		return res;
	}
	
}
