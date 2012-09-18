package org.clubrockisen.view;

import java.awt.Component;
import java.awt.Frame;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.clubrockisen.common.Constants;
import org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;

/**
 * Utility class for GUI related methods.<br />
 * @author Alex
 */
public final class Utils {
	/** Logger */
	private static Logger				lg				= Logger.getLogger(Utils.class.getName());
	
	/** The services */
	private static final ServiceFactory	SERVICES		= ServiceFactory.getImplementation();
	
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
		boolean lookAndFeelApplied = false;
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
					lookAndFeelApplied = true;
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					lg.warning("Could not set the look and feel " + laf.getName() + ". " +
							e.getClass() + ", " + e.getMessage());
					lookAndFeelApplied = false;
				}
			}
		}
		// Applying changes to application
		if (lookAndFeelApplied) {
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
		final String message = multiLineHTML(SERVICES.getTranslator().get(dialog.message()));
		JOptionPane.showMessageDialog(parent, message, SERVICES.getTranslator().get(dialog.title()), type);
	}
	
	/**
	 * Cut a {@link String} in multiple line, so they don't exceed a certain length.<br />
	 * @param input
	 *        the input string to be cut.
	 * @return the string divided in several lines.
	 */
	public static String multiLine (final String input) {
		// If input is shorter than the limit
		if (input.trim().length() < Constants.LINE_MAX_LENGTH) {
			return input.trim();
		}
		
		// Other cases
		final StringBuilder result = new StringBuilder();
		String remaining = input;
		while (remaining.length() > Constants.LINE_MAX_LENGTH) {
			String nextLine = remaining.substring(0, Constants.LINE_MAX_LENGTH);
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Writing next line '" + nextLine + "'");
			}
			nextLine = nextLine.substring(0, nextLine.lastIndexOf(' '));
			result.append(nextLine).append("<br />");
			remaining = remaining.substring(nextLine.length()).trim();
		}
		result.append(remaining);
		
		return result.toString();
	}
	
	/**
	 * Cut a {@link String} in multiple line, so they don't exceed a certain length.<br />
	 * @param input
	 *        the input string to be cut.
	 * @return the content spread on several lines (using <code>&lt;br /&gt;</code>) and between
	 *         <code>&lt;html&gt;</code> tags.
	 * @see #multiLine(String)
	 */
	public static String multiLineHTML (final String input) {
		final StringBuilder result = new StringBuilder("<html>");
		result.append(multiLine(input)).append("</html>");
		return result.toString().replace("\n", "<br />");
	}
	
}