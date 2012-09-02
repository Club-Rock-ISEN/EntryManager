package org.clubrockisen.view;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.view.renderers.LafRenderer;

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
	 * Sets the look and feel of the application.
	 */
	public static void setLookAndFeel () {
		final String lookAndFeelName = SERVICES.getParameterManager().get(ParametersEnum.LOOK_AND_FEEL).getValue();
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
				final JComboBox<LookAndFeelInfo> comboBox = new JComboBox<>(UIManager.getInstalledLookAndFeels());
				comboBox.setRenderer(new LafRenderer());
				comp = comboBox;
				break;
				
			default:
				comp = new JTextField();
				break;
		}
		
		return comp;
	}
}
