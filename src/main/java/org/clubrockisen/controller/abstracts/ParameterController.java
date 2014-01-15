package org.clubrockisen.controller.abstracts;


/**
 * Interface for a Parameter controller.
 * @author Alex
 */
public interface ParameterController {
	
	/**
	 * Change the value of the parameter in the model.
	 * @param newValue
	 *        the new value for the parameter.
	 */
	void changeValue (final String newValue);
	
	/**
	 * Change the type of the parameter
	 * @param newType
	 *        the new type for the parameter.
	 */
	void changeType (final String newType);
	
	/**
	 * Persists any changes to the database.<br />
	 * @return <code>true</code> if the operation succeeded.
	 */
	boolean persist ();
	
	/**
	 * Reload the models registered from the database.<br />
	 */
	void reload ();
}
