package org.clubrockisen.entities;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class representing a parameter of the application.<br />
 * @author Alex
 */
public class Parameter extends Entity {
	/** Logger */
	private static Logger						lg					= Logger.getLogger(Parameter.class.getName());
	
	/** Serial Version UID */
	private static final long					serialVersionUID	= 5157162601363481112L;
	
	/** Map between the enumeration and the actual columns in the database */
	private static Map<ParameterColumn, Column>	columns;
	/** Lock for the columns */
	private static Object						lock				= new Object();
	/** Name of the entity */
	private static String						entityName			= "parameter";
	
	/** Name of the parameter */
	private String								name;
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
	public enum ParameterColumn {
		/** The parameter's name */
		NAME("Name"),
		/** The parameter's value */
		VALUE("Value"),
		/** The parameter's type */
		TYPE("Type");
		
		/** The name of the property in the class */
		private final String	propertyName;
		
		/**
		 * Constructor #1.<br />
		 * Build a new enumeration, based on the name of the attribute in the class.
		 * @param propertyName
		 *        the name of the property.
		 */
		private ParameterColumn (final String propertyName) {
			this.propertyName = propertyName;
		}
		
		/**
		 * Return the name of the attribute in the class.
		 * @return the name of the property.
		 */
		public String getPropertyName () {
			return propertyName;
			
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#setEntityColumns()
	 */
	@Override
	protected void setEntityColumns () {
		synchronized (lock) {
			if (columns != null) {
				return;
			}
			columns = new EnumMap<ParameterColumn, Column>(ParameterColumn.class);
			columns.put(ParameterColumn.NAME, new Column(String.class, "name", true));
			columns.put(ParameterColumn.VALUE, new Column(String.class, "value"));
			columns.put(ParameterColumn.TYPE, new Column(String.class, "type"));
		}
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
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
		if (!(obj instanceof Parameter)) {
			return false;
		}
		final Parameter other = (Parameter) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Parameter clone () throws CloneNotSupportedException {
		final Parameter clone = (Parameter) super.clone();
		clone.name = name;
		clone.type = type;
		clone.value = value;
		return clone;
	}
	
}
