package org.clubrockisen;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.clubrockisen.dao.DAOType;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.exception.TopLevelError;
import org.clubrockisen.service.Configuration;
import org.clubrockisen.service.ConfigurationKey;
import org.clubrockisen.service.ParametersManager;
import org.clubrockisen.service.Translator;
import org.clubrockisen.view.MainWindow;

/**
 * Launcher for the Club Rock ISEN application.
 * @author Alex
 */
public final class App {
	/** Logger */
	private static Logger					lg		= Logger.getLogger(App.class.getName());
	
	/** Access to the configuration */
	private final Configuration				config;
	/** Access to the key structure of the configuration */
	private static final ConfigurationKey	KEYS	= ConfigurationKey.KEY;
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor which load the arguments and set the configuration of the program.
	 * @param args
	 *        the arguments from the command line.
	 */
	private App (final String[] args) {
		super();
		
		/** Loading configuration file from arguments */
		if (args.length > 0) {
			Configuration.setFile(args[0]);
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Loading configuration file from program command line " + args[0]);
			}
		}
		/** Pre-loading configuration and translator. */
		config = Configuration.getInstance();
		Translator.getInstance();
	}
	
	/**
	 * Launch the application.
	 */
	private void launch () {
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Club Rock ISEN application starting...");
		}
		
		try {
			/** Loading DAO factory and parameters */
			final DAOType daoType = DAOType.fromValue(config.get(KEYS.daoFactory()));
			final AbstractDAOFactory daoFactory = AbstractDAOFactory.getFactory(daoType);
			ParametersManager.create(daoFactory);
			
			/** Loading GUI */
			MainWindow.setLookAndFeel();
			final MainWindow window = new MainWindow(daoFactory);
			// Waiting for the window to build itself
			synchronized (window) {
				try {
					while (!window.isReady()) {
						window.wait();
					}
				} catch (final InterruptedException e) {
					lg.warning("Main thread interrupted: " + e.getMessage());
				}
			}
			window.setVisible(true);
			
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Club Rock ISEN application running.");
			}
		} catch (final TopLevelError e) {
			lg.severe("Cannot run application: " + e.getClass() + "; details: " + e.getMessage());
			lg.severe("Caused by " + e.getCause());
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Severe error - Application will not run", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Entry point for the launcher.
	 * @param args
	 *        the arguments from the command line.
	 */
	public static void main (final String[] args) {
		final App app = new App(args);
		app.launch();
	}
	
}
