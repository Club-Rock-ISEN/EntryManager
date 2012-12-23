package org.clubrockisen.entities;

/**
 * Interface which represent a property of an entity.<br />
 * The name of the property allow too dynamically access an entity field (via reflection).
 * @author Alex
 */
public interface Property {
	
	/**
	 * Return the name of the attribute in the class.
	 * @return the name of the property.
	 */
	public String getPropertyName ();
}
