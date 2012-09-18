package org.clubrockisen.view.parameter;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.clubrockisen.common.Constants;
import org.clubrockisen.service.abstracts.ParametersEnum;

/**
 * A spinner for editing an double parameter.<br />
 * @author Alex
 */
public class DoubleSpinner extends Spinner {
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter the integer spinner is displaying.
	 */
	public DoubleSpinner (final ParametersEnum parameter) {
		super(parameter);
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.view.parameter.Spinner#getSpinnerModel()
	 */
	@Override
	protected SpinnerModel getSpinnerModel () {
		return new SpinnerNumberModel(0.0, Integer.MIN_VALUE, Integer.MAX_VALUE, Constants.STEP_MONEY);
	}
	
}
