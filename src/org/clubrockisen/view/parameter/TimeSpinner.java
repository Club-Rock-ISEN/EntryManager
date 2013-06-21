package org.clubrockisen.view.parameter;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import org.clubrockisen.common.Constants;
import org.clubrockisen.model.SpinnerTimeModel;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.view.renderers.SpinnerEditableRenderer;

/**
 * A spinner for editing an time parameter.<br />
 * @author Alex
 */
public class TimeSpinner extends Spinner {
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter the integer spinner is displaying.
	 */
	public TimeSpinner (final ParametersEnum parameter) {
		super(parameter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.parameter.Spinner#getSpinnerModel()
	 */
	@Override
	protected SpinnerModel getSpinnerModel () {
		return new SpinnerTimeModel(Constants.TIME_MIN_SPINNER, Constants.TIME_STEP_SPINNER,
				Constants.TIME_MAX_SPINNER, Constants.TIME_MIN_SPINNER);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.parameter.Spinner#getSpinnerEditor(javax.swing.JSpinner)
	 */
	@Override
	protected JComponent getSpinnerEditor (final JSpinner targetSpinner) {
		return new SpinnerEditableRenderer(targetSpinner);
	}
	
}
