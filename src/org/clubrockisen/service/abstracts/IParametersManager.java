package org.clubrockisen.service.abstracts;

import org.clubrockisen.entities.Parameter;
import org.clubrockisen.service.ParametersEnum;

/**
 * Interface for the managers for the parameters.<br />
 * @author Alex
 */
public interface IParametersManager {
	
	/**
	 * Return the specified parameter.
	 * @param parameter
	 *        the parameter to retrieve.
	 * @return the object which contains the information about the parameter (value, type).
	 */
	Parameter get (final ParametersEnum parameter);
	
	/**
	 * Update the value and/or the type of the parameter.<br />
	 * Do not update the name of the parameter.
	 * @param parameter
	 *        the parameter to update
	 * @return <code>true</code> if the parameter has been successfully updated.
	 */
	boolean set (final Parameter parameter);
}
