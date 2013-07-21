package org.clubrockisen.view;

import java.awt.Component;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.clubrockisen.common.Constants;
import org.clubrockisen.service.Translator;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;

import com.alexrnl.commons.gui.swing.SwingUtils;
import com.alexrnl.commons.translation.AbstractDialog;
import com.alexrnl.commons.utils.StringUtils;

/**
 * Utility class for GUI related methods.<br />
 * @author Alex
 */
public final class Utils {
	/** Logger */
	private static Logger				lg			= Logger.getLogger(Utils.class.getName());
	
	/** The services */
	private static final ServiceFactory	SERVICES	= ServiceFactory.getImplementation();
	/** The translator */
	private static final Translator		TRANSLATOR	= SERVICES.getTranslator();
	
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
		SwingUtils.setLookAndFeel(SERVICES.getParameterManager().get(ParametersEnum.LOOK_AND_FEEL).getValue());
	}
	
	/**
	 * Return the message of a dialog with the parameters included, if the message contained some.
	 * @param dialog
	 *        the dialog to use.
	 * @return the message to display.
	 */
	private static String getMessage (final AbstractDialog dialog) {
		return TRANSLATOR.get(dialog.message(), dialog.getParameters());
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
		JOptionPane.showMessageDialog(parent, StringUtils.splitInLinesHTML(getMessage(dialog), Constants.LINE_MAX_LENGTH),
				TRANSLATOR.get(dialog.title()), type);
	}
	
	/**
	 * Show a confirmation dialog to the user.<br/>
	 * @param parent
	 *        the parent component.
	 * @param dialog
	 *        the dialog to display.
	 * @return <code>true</code> if the use confirmed the dialog (clicked 'yes').
	 */
	public static boolean askConfirmation (final Component parent, final AbstractDialog dialog) {
		final int choice = JOptionPane.showConfirmDialog(parent, StringUtils.splitInLinesHTML(getMessage(dialog), Constants.LINE_MAX_LENGTH),
				TRANSLATOR.get(dialog.title()), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return choice == JOptionPane.YES_OPTION;
	}
	
	/**
	 * Ask the user to choose an element from a list.<br />
	 * The elements of the list will be translated using their {@link Object#toString()} method.<br />
	 * The collection provided should not contain identical object or identical text translations.
	 * @param <T>
	 *        the type of element the dialog offers.
	 * @param parent
	 *        the parent component.
	 * @param dialog
	 *        the dialog to display.
	 * @param elements
	 *        the elements to display in the list.
	 * @return the selected element, or <code>null</code> if user canceled.
	 */
	public static <T> T askChoice (final Component parent, final AbstractDialog dialog, final Collection<T> elements) {
		final Map<String, T> translationMap = new LinkedHashMap<>(elements.size());
		for (final T t : elements) {
			translationMap.put(TRANSLATOR.get(t.toString()), t);
		}
		
		final String choice = (String) JOptionPane.showInputDialog(parent, StringUtils.splitInLinesHTML(getMessage(dialog), Constants.LINE_MAX_LENGTH),
				TRANSLATOR.get(dialog.title()), JOptionPane.QUESTION_MESSAGE, null,
				translationMap.keySet().toArray(new Object[0]), translationMap.keySet().iterator().next());
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("The user has choose " + choice);
		}
		
		return translationMap.get(choice);
	}
	
}