package org.clubrockisen.view.parameter;

import org.clubrockisen.service.abstracts.ParametersEnum;


/**
 * Interface for a listener on a parameter component change.
 * @author Alex
 */
public interface ParameterChangeListener {
	
	/**
	 * Called when the {@link ParameterComponent}'s value changes.
	 * @param parameter
	 *        the parameter with the new value.
	 * @param value
	 *        the new value of the parameter.
	 */
	void parameterChangeValue (ParametersEnum parameter, String value);
	
}
