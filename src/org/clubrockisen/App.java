package org.clubrockisen;

import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory.DAOType;
import org.clubrockisen.service.ParametersManager;
import org.clubrockisen.view.MainWindow;

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
		
		final DAOType daoType = DAOType.MYSQL;
		try {
			AbstractDAOFactory daoFactory;
			daoFactory = AbstractDAOFactory.getFactory(daoType);
			ParametersManager.create(daoFactory);
			try {
				MainWindow.setLookAndFeel();
			} catch (final NullPointerException e) {
				lg.warning("Could not set look and feel (" + e.getMessage() + ")");
			}
			final MainWindow window = new MainWindow(translationFile, daoFactory);
			window.setVisible(true);
		} catch (final InstantiationException e) {
			JOptionPane.showMessageDialog(null, "Could not instantiate a DAO of type " + daoType
					+ "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
