package org.clubrockisen.view.parameter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.clubrockisen.service.abstracts.ParametersEnum;

import com.alexrnl.commons.gui.swing.SwingUtils;
import com.alexrnl.commons.gui.swing.renderers.LafRenderer;

/**
 * A combo box for editing an look and feel parameter.<br />
 * @author Alex
 */
public class LAFComboBox extends ParameterComponent {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(LAFComboBox.class.getName());
	
	/** Combo box for Look and Feel */
	private JComboBox<LookAndFeelInfo>	comboBox;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter displayed.
	 */
	public LAFComboBox (final ParametersEnum parameter) {
		super(parameter);
	}
	
	@Override
	public JComponent getComponent () {
		if (comboBox == null) {
			comboBox = new JComboBox<>(UIManager.getInstalledLookAndFeels());
			comboBox.setRenderer(new LafRenderer());
		}
		return comboBox;
	}
	
	@Override
	public void installListener (final ParameterChangeListener listener) {
		if (comboBox == null) {
			lg.warning("Cannot set value on null combo box for parameter " + getParameter().getName());
			return;
		}
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				listener.parameterChangeValue(getParameter(), getValue());
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run () {
						SwingUtils.setLookAndFeel(getValue());
					}
				});
			}
		});
	}
	
	@Override
	public String getValue () {
		return comboBox == null ? null : ((LookAndFeelInfo) comboBox.getSelectedItem()).getName();
	}
	
	@Override
	public void setValue (final Object value) {
		if (comboBox == null) {
			lg.warning("Cannot set value on null combo box for parameter " + getParameter().getName());
			return;
		}
		
		// TODO exception on not found LAF?
		boolean found = false;
		for (int lafIndex = 0; lafIndex < comboBox.getItemCount() && !found; lafIndex++) {
			if (comboBox.getItemAt(lafIndex).getName().equals(value)) {
				comboBox.setSelectedIndex(lafIndex);
				found = true;
			}
		}
		if (!found) {
			lg.warning("Look and feel not found: " + value);
		}
	}
}
