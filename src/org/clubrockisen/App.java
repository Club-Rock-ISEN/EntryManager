package org.clubrockisen;

import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.clubrockisen.dao.AbstractDAOFactory;
import org.clubrockisen.dao.AbstractDAOFactory.DAOType;
import org.clubrockisen.gui.MainWindow;
import org.clubrockisen.tools.ParametersManager;

/**
 * Launcher for the club rock ISEN application
 * 
 * @author Alex
 */
public final class App {
	private static Logger	lg	= Logger.getLogger(App.class.getName());

	private App () {
	}

	/**
	 * Entry point for the launcher.
	 * @param args
	 *            the arguments from the command line.
	 */
	public static void main (final String[] args) {
		lg.info("Starting Club Rock ISEN application.");

		String translationFile;
		if (args.length < 1) {
			translationFile = "data/locale/fr.xml";
			lg.warning("No language file defined. Using default locale file : " + translationFile);
		} else {
			translationFile = args[0];
		}
		lg.fine("Language locale file defined: " + translationFile);

		setLookAndFeel();

		final DAOType daoType = DAOType.MYSQL;
		try {
			AbstractDAOFactory daoFactory;
			daoFactory = AbstractDAOFactory.getFactory(daoType);
			ParametersManager.create(daoFactory);
			final MainWindow window = new MainWindow(translationFile, daoFactory);
			window.setVisible(true);
		} catch (final InstantiationException e) {
			JOptionPane.showMessageDialog(null, "Could not instantiate a DAO of type " + daoType
					+ "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Sets the look and feel of the application
	 */
	private static void setLookAndFeel () {
		final String lookAndFeelName = "Nimbus"; // TODO move into parameter table
		boolean lookAndFeelFound = false;
		for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			lg.fine(laf.getName());
			if (laf.getName().equals(lookAndFeelName)) {
				try {
					UIManager.setLookAndFeel(laf.getClassName());
					lookAndFeelFound = true;
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					lg.warning("Could not set the look and feel " + laf.getName());
					lookAndFeelFound = false;
				}
			}
		}
		if (!lookAndFeelFound) {
			lg.warning("Could not find (or set) the look and feel " + lookAndFeelName
					+ ". Using default look and feel.");
		}
	}
}
