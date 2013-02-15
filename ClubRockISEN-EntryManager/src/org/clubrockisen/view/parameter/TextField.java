package org.clubrockisen.view.parameter;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.clubrockisen.service.abstracts.ParametersEnum;

/**
 * A simple text field for editing a parameter.<br />
 * @author Alex
 * @see JTextField
 */
public class TextField extends ParameterComponent {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(TextField.class.getName());
	
	/** The text field */
	private JTextField		textField;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter the text field is displaying.
	 */
	public TextField (final ParametersEnum parameter) {
		super(parameter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.parameter.ParameterComponent#getComponent()
	 */
	@Override
	public JComponent getComponent () {
		if (textField != null) {
			textField = new JTextField();
		}
		return textField;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.view.parameter.ParameterComponent#installListener(org.clubrockisen.view.
	 * ParametersView.ParameterChangeListener)
	 */
	@Override
	public void installListener (final ParameterChangeListener listener) {
		if (textField == null) {
			lg.warning("Cannot install listener on null textfield for parameter " + getParameter().getName());
			return;
		}
		textField.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusLost (final FocusEvent e) {
				listener.parameterChangeValue(getParameter(), getValue());
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.parameter.ParameterComponent#getValue()
	 */
	@Override
	public String getValue () {
		return textField != null ? textField.getText() : null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.parameter.ParameterComponent#setValue(java.lang.Object)
	 */
	@Override
	public void setValue (final Object value) {
		if (textField == null) {
			lg.warning("Cannot set value on null text field for parameter " + getParameter().getName());
			return;
		}
		
		textField.setText(value == null ? "" : value.toString());
	}
}
