package org.clubrockisen.view.parameter;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.clubrockisen.service.abstracts.ParametersEnum;

/**
 * A spinner for editing an integer parameter.<br />
 * @author Alex
 */
public class IntegerSpinner extends Spinner {
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter the integer spinner is displaying.
	 */
	public IntegerSpinner (final ParametersEnum parameter) {
		super(parameter);
	}
	
	@Override
	protected SpinnerModel getSpinnerModel () {
		return new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
	}
	
}
