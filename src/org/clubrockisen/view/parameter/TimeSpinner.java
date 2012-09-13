package org.clubrockisen.view.parameter;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import org.clubrockisen.common.Time;
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
		return new SpinnerTimeModel(Time.get("22:00"), Time.get("00:01"), Time.get("23:59"), Time.get("00:00"));
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
