package org.clubrockisen.view;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.JTextComponent;

import org.clubrockisen.common.Time;
import org.clubrockisen.model.SpinnerTimeModel;
import org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.view.renderers.SpinnerTimeRenderer;

/**
 * Utility class for GUI related methods.<br />
 * @author Alex
 */
public final class Utils {
	/** Logger */
	private static Logger				lg				= Logger.getLogger(Utils.class.getName());
	
	/** The services */
	private static final ServiceFactory	SERVICES		= ServiceFactory.getImplementation();
	
	/** Default insets to be used */
	private static final Insets			DEFAULT_INSETS	= new Insets(5, 5, 5, 5);
	
	/**
	 * Constructor #1.<br />
	 * Private constructor to avoid access from outer class.
	 */
	private Utils () {
		super();
	}
	
	/**
	 * Sets the look and feel of the application by using the value from the database parameter.
	 */
	public static void setLookAndFeel () {
		setLookAndFeel(SERVICES.getParameterManager().get(ParametersEnum.LOOK_AND_FEEL).getValue());
	}
	
	/**
	 * Sets the look and feel of the application.
	 * @param lookAndFeelName
	 *        the name of the look and feel to set.
	 */
	public static void setLookAndFeel (final String lookAndFeelName) {
		boolean lookAndFeelFound = false;
		for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine(laf.getName());
			}
			if (laf.getName().equals(lookAndFeelName)) {
				try {
					UIManager.setLookAndFeel(laf.getClassName());
					if (lg.isLoggable(Level.FINE)) {
						lg.fine("Look and feel properly setted (" + laf.getName() + ").");
					}
					lookAndFeelFound = true;
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					lg.warning("Could not set the look and feel " + laf.getName() + ". " +
							e.getClass() + ", " + e.getMessage());
					lookAndFeelFound = false;
				}
			}
		}
		// Applying changes to application
		if (lookAndFeelFound) {
			for (final Frame frame : Frame.getFrames()) {
				SwingUtilities.updateComponentTreeUI(frame);
				frame.pack();
			}
		} else {
			lg.warning("Could not find (or set) the look and feel " + lookAndFeelName
					+ ". Using default look and feel.");
		}
	}
	
	/**
	 * Show the dialog with translation picked from the dialog specified.
	 * @param parent
	 *        the parent window (may be <code>null</code>).
	 * @param dialog
	 *        the key to the translations to use.
	 * @param type
	 *        the type of the dialog. Generally, {@link JOptionPane#ERROR_MESSAGE error},
	 *        {@link JOptionPane#WARNING_MESSAGE warning}, {@link JOptionPane#INFORMATION_MESSAGE
	 *        information}, {@link JOptionPane#QUESTION_MESSAGE question} or
	 *        {@link JOptionPane#PLAIN_MESSAGE plain} message.
	 * @see JOptionPane#showMessageDialog(Component, Object, String, int, javax.swing.Icon)
	 */
	public static void showMessageDialog (final Component parent, final AbstractDialog dialog,
			final int type) {
		JOptionPane.showMessageDialog(parent, SERVICES.getTranslator().get(dialog.message()),
				SERVICES.getTranslator().get(dialog.title()), type);
	}
	
	/**
	 * Return the default {@link Insets} to be used with most of the component of the application.
	 * @return the default insets.
	 */
	public static Insets getDefaultInsets () {
		return DEFAULT_INSETS;
	}
	
	/**
	 * Creates the component which allow to edit a parameter.<br />
	 * Set the appropriate listener on the component.
	 * @param parameter
	 *        the parameter.
	 * @return the component.
	 */
	public static JComponent getParameterComponent (final ParametersEnum parameter) {
		JComponent comp = null;
		
		switch (parameter) {
			case LOOK_AND_FEEL:
				final String[] lafNames = new String[UIManager.getInstalledLookAndFeels().length];
				int index = 0;
				for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
					lafNames[index] = laf.getName();
					++index;
				}
				final JComboBox<String> comboBox = new JComboBox<>(lafNames);
				comboBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed (final ActionEvent e) {
						setLookAndFeel((String) comboBox.getSelectedItem());
					}
				});
				comp = comboBox;
				break;
			case ENTRY_PRICE_TOTAL:
			case ENTRY_PRICE_FIRST_PART:
			case ENTRY_PRICE_SECOND_PART:
			case MAX_CREDIT:
			case MIN_CREDIT:
				final JSpinner doubleSpinner = new JSpinner(new SpinnerNumberModel(0.0,
						Integer.MIN_VALUE, Integer.MAX_VALUE, 0.01));
				comp = doubleSpinner;
				break;
			case FREE_ENTRY_FREQUENCY:
				final JSpinner intSpinner = new JSpinner(new SpinnerNumberModel(0,
						Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
				comp = intSpinner;
				break;
			case TIME_LIMIT:
				final JSpinner timeSpinner = new JSpinner(new SpinnerTimeModel(Time.get("22:00"),
						Time.get("00:01"),
						Time.get("23:59"),
						Time.get("00:00")));
				timeSpinner.setEditor(new SpinnerTimeRenderer(timeSpinner));
				comp = timeSpinner;
				break;
			default:
				comp = new JTextField();
				break;
		}
		
		return comp;
	}
	
	/**
	 * Return the value set by the user in the component.<br />
	 * @param component
	 *        the component to check.
	 * @return the value set in the component.
	 */
	public static String getValue (final JComponent component) {
		if (component instanceof JTextComponent) {
			return ((JTextComponent) component).getText();
		}
		if (component instanceof JComboBox<?>) {
			final Object item = ((JComboBox<?>) component).getSelectedItem();
			return item.toString();
		}
		if (component instanceof JSpinner) {
			return ((JSpinner) component).getValue().toString();
		}
		
		return null;
	}
	
	/**
	 * Set the value in the component.<br />
	 * @param component
	 *        the target component.
	 * @param value
	 *        the value to set.
	 */
	public static void setValue (final JComponent component, final Object value) {
		if (component instanceof JTextComponent) {
			((JTextComponent) component).setText(value.toString());
		} else if (component instanceof JComboBox<?>) {
			((JComboBox<?>) component).setSelectedItem(value);
		} else if (component instanceof JSpinner) {
			((JSpinner) component).setValue(value);
		}
	}
}
