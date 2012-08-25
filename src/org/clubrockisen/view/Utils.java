package org.clubrockisen.view;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog;
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
			lg.fine(laf.getName());
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
		if (!lookAndFeelFound) {
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
	 *        the type of the dialog.
	 */
	public static void showMessageDialog (final Component parent, final AbstractDialog dialog,
			final int type) {
		JOptionPane.showMessageDialog(parent, SERVICES.getTranslator().get(dialog.message()),
				SERVICES.getTranslator().get(dialog.title()), type);
	}
}
