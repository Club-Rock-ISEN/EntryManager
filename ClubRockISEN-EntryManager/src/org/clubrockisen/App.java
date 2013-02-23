package org.clubrockisen;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.clubrockisen.common.Configuration;
import org.clubrockisen.common.ConfigurationKeys;
import org.clubrockisen.common.error.TopLevelError;
import org.clubrockisen.controller.MainWindowController;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.service.Translator;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.view.Utils;

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
	private static final ConfigurationKeys	KEYS	= ConfigurationKeys.KEY;
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor which load the arguments and set the configuration of the program.
	 * @param args
	 *        the arguments from the command line.
	 */
	private App (final String[] args) {
		super();
		
		// Loading configuration file from arguments
		if (args.length > 0) {
			Configuration.setFile(args[0]);
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Loading configuration file from program command line " + args[0]);
			}
		}
		// Pre-loading configuration and translator.
		config = Configuration.getInstance();
		Translator.getInstance();
	}
	
	/**
	 * Launch the application.<br />
	 * Load the DAO configured and the services of the application.
	 * Then, load the GUI show it once it is fully loaded.
	 */
	private void launch () {
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Club Rock ISEN application starting...");
		}
		
		try {
			// Loading DAO factory and services
			AbstractDAOFactory.createFactory(config.get(KEYS.daoFactory()));
			ServiceFactory.createFactory(config.get(KEYS.serviceFactory()));
			
			// Loading GUI
			Utils.setLookAndFeel();
			final MainWindowController mainWindow = new MainWindowController();
			mainWindow.show();
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Club Rock ISEN application running.");
			}
		} catch (final TopLevelError e) {
			lg.severe("Cannot run application: " + e.getClass() + "; details: " + e.getMessage());
			lg.severe("Caused by " + e.getCause());
			// Cannot use the method in view.Utils because the translator may not be loaded
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
