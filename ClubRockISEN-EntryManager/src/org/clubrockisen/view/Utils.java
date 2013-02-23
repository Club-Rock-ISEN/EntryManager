package org.clubrockisen.view;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.clubrockisen.common.Constants;
import org.clubrockisen.common.TranslationKeys.Gui.Dialog.AbstractDialog;
import org.clubrockisen.common.TranslationKeys.Gui.GUIElement;
import org.clubrockisen.common.TranslationKeys.Parametrable;
import org.clubrockisen.service.abstracts.ITranslator;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;

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
	private static final ITranslator	TRANSLATOR	= SERVICES.getTranslator();
	
	/**
	 * Constructor #1.<br />
	 * Private constructor to avoid access from outer class.
	 */
	private Utils () {
		super();
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
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Writing next line '" + nextLine + "'");
			}
			nextLine = nextLine.substring(0, nextLine.lastIndexOf(Constants.SPACE));
			result.append(nextLine).append(Constants.NEW_LINE);
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
		final StringBuilder result = new StringBuilder(Constants.HTML_HTML_START);
		result.append(multiLine(input)).append(Constants.HTML_HTML_END);
		return result.toString().replace("" + Constants.NEW_LINE, Constants.HTML_NEW_LINE);
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
	 * Return the message of a dialog with the parameters included, if the message contained some.
	 * @param dialog
	 *        the dialog to use.
	 * @return the message to display.
	 */
	private static String getMessage (final AbstractDialog dialog) {
		String message;
		if (Parametrable.class.isInstance(dialog)) {
			message = TRANSLATOR.get(dialog.message(), ((Parametrable) dialog).getParameters());
		} else {
			message = TRANSLATOR.get(dialog.message());
		}
		return message;
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
		JOptionPane.showMessageDialog(parent, multiLineHTML(getMessage(dialog)),
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
		final int choice = JOptionPane.showConfirmDialog(parent, multiLineHTML(getMessage(dialog)),
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
		
		final String choice = (String) JOptionPane.showInputDialog(parent, multiLineHTML(getMessage(dialog)),
				TRANSLATOR.get(dialog.title()), JOptionPane.QUESTION_MESSAGE, null,
				translationMap.keySet().toArray(new Object[0]), translationMap.keySet().iterator().next());
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("The user has choose " + choice);
		}
		
		return translationMap.get(choice);
	}
	
	/**
	 * Creates a {@link JMenuItem} based on the parameters provided.<br />
	 * <ul>
	 * <li>Set a mnemonic (using the character following the {@link Constants#MNEMONIC_MARK} defined).</li>
	 * <li>Set the shortcut define in the translation file.</li>
	 * <li>Set the listener specified.</li>
	 * <li>Set the tool tip.</li>
	 * </ul>
	 * @param element
	 *        the element to use to build the JMenuItem (use text and accelerator).
	 * @param actionListener
	 *        the listener to add on the menu item.
	 * @return the menu item created.
	 * @see #getMenu(String)
	 */
	public static JMenuItem getMenuItem (final GUIElement element, final ActionListener actionListener) {
		final JMenuItem item = new JMenuItem();
		
		installMnemonics(item, element.getText());
		
		// Set shortcut
		if (TRANSLATOR.has(element.getShortcut())) {
			final String shortcut = TRANSLATOR.get(element.getShortcut());
			item.setAccelerator(KeyStroke.getKeyStroke(shortcut));
		}
		// Set listener
		if (actionListener != null) {
			item.addActionListener(actionListener);
		}
		// Set tool tip
		if (TRANSLATOR.has(element.getToolTip())) {
			final String toolTip = TRANSLATOR.get(element.getToolTip());
			item.setToolTipText(toolTip);
		}
		return item;
	}
	
	/**
	 * Creates a {@link JMenu} based on the text provided.<br />
	 * 
	 * @param element
	 *        the translation key to use.
	 * @return a menu parsed to retrieve the Mnemonic, if set.
	 */
	public static JMenu getMenu (final String element) {
		final JMenu menu = new JMenu();
		installMnemonics(menu, element);
		return menu;
	}
	
	/**
	 * Install the text and the mnemonics on the specified menu component.<br />
	 * @param menu
	 *        the menu item to initialize.
	 * @param key
	 *        the translation key to use.
	 */
	private static void installMnemonics (final JMenuItem menu, final String key) {
		String text = TRANSLATOR.get(key);
		final int mnemonicIndex = text.indexOf(Constants.MNEMONIC_MARK);
		if (mnemonicIndex > -1) {
			text = text.substring(0, mnemonicIndex) + text.substring(mnemonicIndex + 1);
			menu.setText(text);
			menu.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(text.charAt(mnemonicIndex)));
		} else {
			menu.setText(text);
		}
	}
	
}