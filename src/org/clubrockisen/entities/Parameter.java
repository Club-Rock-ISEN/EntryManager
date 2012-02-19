package org.clubrockisen.entities;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 
 * @author Alex
 */
public class Parameter extends Entity {
	private static Logger						lg	= Logger.getLogger(Parameter.class.getName());

	private static Map<ParameterColumn, Column>	columns;
	private static String						entityName = "parameters";

	private String								name;
	private String								value;
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
	 * Return the value.
	 * @return the value.
	 */
	public String getValue () {
		return value;
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
		return type;
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
