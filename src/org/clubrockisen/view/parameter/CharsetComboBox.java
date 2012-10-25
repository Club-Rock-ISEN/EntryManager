package org.clubrockisen.view.parameter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.clubrockisen.service.abstracts.ParametersEnum;

/**
 * Combo box for the charset available.
 * @author Alex
 */
public class CharsetComboBox extends ParameterComponent {
	/** Logger */
	private static Logger		lg	= Logger.getLogger(CharsetComboBox.class.getName());
	
	/** Combo box for the file charset available */
	private JComboBox<String>	comboBox;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter displayed.
	 */
	public CharsetComboBox (final ParametersEnum parameter) {
		super(parameter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.parameter.ParameterComponent#getComponent()
	 */
	@Override
	public JComponent getComponent () {
		if (comboBox == null) {
			comboBox = new JComboBox<>(Charset.availableCharsets().keySet().toArray(new String[0]));
		}
		return comboBox;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.view.parameter.ParameterComponent#installListener(org.clubrockisen.view.
	 * parameter.ParameterChangeListener)
	 */
	@Override
	public void installListener (final ParameterChangeListener listener) {
		if (comboBox == null) {
			lg.warning("Cannot set value on null combo box for parameter " + getParameter().getName());
			return;
		}
		
		comboBox.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
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
		return comboBox == null ? null : (String) comboBox.getSelectedItem();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.parameter.ParameterComponent#setValue(java.lang.Object)
	 */
	@Override
	public void setValue (final Object value) {
		if (comboBox == null) {
			lg.warning("Cannot set value on null combo box for parameter " + getParameter().getName());
			return;
		}
		
		// TODO exception on not found char set?
		boolean found = false;
		for (int charsetIndex = 0; charsetIndex < comboBox.getItemCount() && !found; charsetIndex++) {
			if (comboBox.getItemAt(charsetIndex).equals(value)) {
				comboBox.setSelectedIndex(charsetIndex);
				found = true;
			}
		}
		if (!found) {
			lg.warning("Charset not found: " + value);
		}
	}
}
