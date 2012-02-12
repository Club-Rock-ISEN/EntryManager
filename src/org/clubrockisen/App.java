package org.clubrockisen;

import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.clubrockisen.dao.AbstractDAOFactory;
import org.clubrockisen.dao.AbstractDAOFactory.DAOType;
import org.clubrockisen.gui.MainWindow;

/**
 * Launcher for the club rock ISEN application
 * 
 * @author Alex
 */
public class App {
	private static Logger	lg	= Logger.getLogger(App.class.getName());

	/**
	 * Entry point for the launcher.
	 * @param args
	 *            the arguments from the command line.
	 */
	public static void main (final String[] args) {
		lg.info("Starting Club Rock ISEN application.");

		final String lookAndFeelName = "Nimbus";
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

		final AbstractDAOFactory daoFactory = AbstractDAOFactory.getFactory(DAOType.MYSQL);
		final MainWindow window = new MainWindow(daoFactory);
		window.setVisible(true);
	}
}
