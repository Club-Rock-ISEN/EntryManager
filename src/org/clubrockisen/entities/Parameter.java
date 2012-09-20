package org.clubrockisen.entities;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.common.Auto;
import org.clubrockisen.common.Comparable;

/**
 * Class representing a parameter of the application.<br />
 * @author Alex
 */
public class Parameter extends Entity implements Cloneable {
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
	/** Component class */
	private String								componentClass;
	
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
		TYPE("Type"),
		/** The parameter's component class */
		COMPONENT_CLASS("ComponentClass");
		
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
			columns = new EnumMap<>(ParameterColumn.class);
			columns.put(ParameterColumn.NAME, new Column(String.class, "name", true));
			columns.put(ParameterColumn.VALUE, new Column(String.class, "value"));
			columns.put(ParameterColumn.TYPE, new Column(String.class, "type"));
			columns.put(ParameterColumn.COMPONENT_CLASS, new Column(String.class, "componentClass"));
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
		this (name, null, null, null);
	}
	
	/**
	 * Constructor #2.<br />
	 * Constructor using all the fields but the component class.
	 * @param name
	 *            the name of the parameter.
	 * @param value
	 *            the value of the parameter.
	 * @param type
	 *            the type of the parameter.
	 */
	public Parameter (final String name, final String value, final String type) {
		this(name, value, type, null);
	}
	
	/**
	 * Constructor #3.<br />
	 * Constructor using all the fields.
	 * @param name
	 *            the name of the parameter.
	 * @param value
	 *            the value of the parameter.
	 * @param type
	 *            the type of the parameter.
	 * @param componentClass
	 *            the component class of the parameter
	 */
	public Parameter (final String name, final String value, final String type, final String componentClass) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
		this.componentClass = componentClass;
		lg.fine("New " + this.getClass().getCanonicalName() + ": " + this.name + " = " + this.value);
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor.
	 */
	public Parameter () {
		this (null, null, null, null);
	}
	
	@Override
	public String getID () {
		return getName();
	}
	
	/**
	 * Return the name.
	 * @return the name.
	 */
	@Comparable
	public String getName () {
		return name == null ? "" : name;
	}
	
	/**
	 * Return the value.
	 * @return the value.
	 */
	@Comparable
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
	@Comparable
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
	
	/**
	 * Return the attribute component.
	 * @return the attribute component.
	 */
	@Comparable
	public String getComponentClass () {
		return componentClass == null ? "" : componentClass;
	}
	
	/**
	 * Set the attribute component.
	 * @param component the attribute component.
	 */
	public void setComponentClass (final String component) {
		this.componentClass = component;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode () {
		return Auto.getInstance().hashCode(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (final Object obj) {
		if (!(obj instanceof Parameter)) {
			return false;
		}
		return Auto.getInstance().compare(this, (Parameter) obj);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#clone()
	 */
	@Override
	public Parameter clone () throws CloneNotSupportedException {
		final Parameter clone = (Parameter) super.clone();
		clone.name = name;
		clone.type = type;
		clone.value = value;
		clone.componentClass = componentClass;
		return clone;
	}
	
}
