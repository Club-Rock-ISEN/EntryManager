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
	
	@Override
	public JComponent getComponent () {
		if (textField != null) {
			textField = new JTextField();
		}
		return textField;
	}
	
	@Override
	public void installListener (final ParameterChangeListener listener) {
		if (textField == null) {
			lg.warning("Cannot install listener on null textfield for parameter " + getParameter().getName());
			return;
		}
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (final FocusEvent e) {
				listener.parameterChangeValue(getParameter(), getValue());
			}
		});
	}
	
	@Override
	public String getValue () {
		return textField != null ? textField.getText() : null;
	}
	
	@Override
	public void setValue (final Object value) {
		if (textField == null) {
			lg.warning("Cannot set value on null text field for parameter " + getParameter().getName());
			return;
		}
		
		textField.setText(value == null ? "" : value.toString());
	}
}
