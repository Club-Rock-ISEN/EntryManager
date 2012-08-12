package org.clubrockisen;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.DAOType;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.service.Configuration;
import org.clubrockisen.service.ConfigurationKey;
import org.clubrockisen.service.ParametersManager;
import org.clubrockisen.view.MainWindow;

/**
 * Launcher for the Club Rock ISEN application.
 * @author Alex
 */
public final class App {
	/** Logger */
	private static Logger					lg		= Logger.getLogger(App.class.getName());
	
	/** Access to the configuration */
	private static final Configuration		CONFIG	= Configuration.getInstance();
	/** Access to the key structure of the configuration */
	private static final ConfigurationKey	KEYS	= ConfigurationKey.CONFIGURATION_KEY;
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor to avoid instantiating the class.
	 */
	private App () {
		super();
	}
	
	/**
	 * Entry point for the launcher.
	 * @param args
	 *        the arguments from the command line.
	 */
	public static void main (final String[] args) {
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Starting Club Rock ISEN application.");
		}
		
		/** Loading DAO factory and parameters */
		final DAOType daoType = DAOType.fromValue(CONFIG.get(KEYS.DAO_FACTORY()));
		final AbstractDAOFactory daoFactory = AbstractDAOFactory.getFactory(daoType);
		ParametersManager.create(daoFactory);
		
		/** Loading GUI */
		final String translationFile = CONFIG.get(KEYS.TRANSLATION_FILE());
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Language locale file defined: " + translationFile);
		}
		MainWindow.setLookAndFeel();
		final MainWindow window = new MainWindow(translationFile, daoFactory);
		window.setVisible(true);
		
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Club Rock ISEN application running.");
		}
	}
	
}
