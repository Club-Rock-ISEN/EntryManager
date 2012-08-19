package org.clubrockisen.entities;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * TODO
 * @author Alex
 */
public class Parameter extends Entity {
	/** Logger */
	private static Logger						lg	= Logger.getLogger(Parameter.class.getName());
	
	/** Map between the enumeration and the actual columns in the database */
	private static Map<ParameterColumn, Column>	columns;
	/** Name of the entity */
	private static String						entityName = "parameter";
	
	/** Name of the parameter */
	private final String						name;
	/** Value of the parameter */
	private String								value;
	/** Type of the parameter */
	private String								type;
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#getEntityName()
	 */
	@Override
	public String getEntityName () {
		return entityName;
	}
	
	/**
	 * Enumeration for the columns of the table associated to the type.
	 * @author Alex
	 */
	@SuppressWarnings("javadoc")
	public enum ParameterColumn {
		NAME, VALUE, TYPE
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#setEntityColumns()
	 */
	@Override
	protected void setEntityColumns () {
		if (columns != null) {
			return;
		}
		columns = new EnumMap<ParameterColumn, Column>(ParameterColumn.class);
		columns.put(ParameterColumn.NAME, new Column(String.class, "name", true));
		columns.put(ParameterColumn.VALUE, new Column(String.class, "value"));
		columns.put(ParameterColumn.TYPE, new Column(String.class, "type"));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#getEntityColumns()
	 */
	@Override
	public Map<? extends Enum<?>, Column> getEntityColumns () {
		return columns;
	}
	
	/**
	 * Return the list of the columns of the table.
	 * @return the list of the columns.
	 */
	public static Map<? extends Enum<?>, Column> getColumns () {
		return columns;
	}
	
	/**
	 * Constructor #1.<br />
	 * Constructor using only the name of the parameter.
	 * @param name
	 *            the name of the parameter.
	 */
	public Parameter (final String name) {
		this (name, null, null);
	}
	
	/**
	 * Constructor #2.<br />
	 * Constructor using all the fields.
	 * @param name
	 *            the name of the parameter.
	 * @param value
	 *            the value of the parameter.
	 * @param type
	 *            the type of the parameter.
	 */
	public Parameter (final String name, final String value, final String type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
		lg.fine("New " + this.getClass().getCanonicalName() + ": " + this.name + " = " + this.value);
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor.
	 */
	public Parameter () {
		this (null, null, null);
	}
	
	@Override
	public String getID () {
		return getName();
	}
	
	/**
	 * Return the name.
	 * @return the name.
	 */
	public String getName () {
		return name == null ? "" : name;
	}
	
	/**
	 * Return the value.
	 * @return the value.
	 */
	public String getValue () {
		return value == null ? "" : value;
	}
	
	/**
	 * Set the value.
	 * @param value
	 *            the value to set.
	 */
	public void setValue (final String value) {
		this.value = value;
	}
	
	/**
	 * Return the type.
	 * @return the type.
	 */
	public String getType () {
		return type == null ? "" : type;
	}
	
	/**
	 * Set the type.
	 * @param type
	 *            the type to set.
	 */
	public void setType (final String type) {
		this.type = type;
	}
	
}
