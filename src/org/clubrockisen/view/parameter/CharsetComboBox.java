package org.clubrockisen.view.parameter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.view.renderers.CharsetRenderer;

/**
 * Combo box for the charset available.
 * @author Alex
 */
public class CharsetComboBox extends ParameterComponent {
	/** Logger */
	private static Logger		lg	= Logger.getLogger(CharsetComboBox.class.getName());
	
	/** The main char set (will be placed at the top of the list). */
	private static Set<String>	mainCharset;
	
	/**
	 * Load main char sets.
	 */
	static {
		mainCharset = new HashSet<>();
		mainCharset.add(StandardCharsets.ISO_8859_1.toString());
		mainCharset.add(StandardCharsets.UTF_8.toString());
		mainCharset.add(StandardCharsets.US_ASCII.toString());
		mainCharset.add(StandardCharsets.UTF_16.toString());
	}
	
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
			final TreeSet<String> charsets = new TreeSet<>(new Comparator<String>() {
				@Override
				public int compare (final String c1, final String c2) {
					// If both strings are in (or out) of the main char sets
					if ((mainCharset.contains(c1) && mainCharset.contains(c2)) ||
							(!mainCharset.contains(c1) && !mainCharset.contains(c2))) {
						return c1.compareTo(c2);
					}
					
					// If the first string is in the main char set
					if (mainCharset.contains(c1)) {
						return -1;
					}
					
					// Finally, the second string is in the main char set
					return 1;
				}
				
			});
			charsets.addAll(Charset.availableCharsets().keySet());
			comboBox = new JComboBox<>(charsets.toArray(new String[0]));
			comboBox.setRenderer(new CharsetRenderer(mainCharset));
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
