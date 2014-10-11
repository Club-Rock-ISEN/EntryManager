package org.clubrockisen.view.parameter;

import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.clubrockisen.service.abstracts.ParametersEnum;

/**
 * A spinner for editing an parameter.<br />
 * @author Alex
 */
public abstract class Spinner extends ParameterComponent {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(IntegerSpinner.class.getName());
	
	/** The spinner */
	private JSpinner		spinner;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter the spinner is displaying.
	 */
	public Spinner (final ParametersEnum parameter) {
		super(parameter);
	}
	
	@Override
	public JComponent getComponent () {
		if (spinner == null) {
			spinner = new JSpinner(getSpinnerModel());
			final JComponent editor = getSpinnerEditor(spinner);
			if (editor != null) {
				spinner.setEditor(editor);
			}
		}
		return spinner;
	}
	
	/**
	 * Return the spinner model to be used.
	 * @return the spinner model.
	 */
	protected abstract SpinnerModel getSpinnerModel ();
	
	/**
	 * Return the spinner editor to be used.<br />
	 * Override this method if required.
	 * @param targetSpinner
	 *        the spinner used.
	 * @return the spinner editor.
	 */
	protected JComponent getSpinnerEditor (final JSpinner targetSpinner) {
		// Nothing to be done
		return null;
	}
	
	@Override
	public void installListener (final ParameterChangeListener listener) {
		if (spinner == null) {
			lg.warning("Cannot install listener on null spinner for parameter " + getParameter().getName());
			return;
		}
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged (final ChangeEvent e) {
				listener.parameterChangeValue(getParameter(), getValue());
			}
		});
	}
	
	@Override
	public String getValue () {
		return spinner == null ? null : spinner.getValue().toString();
	}
	
	@Override
	public void setValue (final Object value) {
		if (spinner == null) {
			lg.warning("Cannot set value on null spinner for parameter " + getParameter().getName());
			return;
		}
		
		spinner.setValue(value);
	}
}
