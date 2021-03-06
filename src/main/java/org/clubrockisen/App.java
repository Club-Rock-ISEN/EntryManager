package org.clubrockisen;

import static org.clubrockisen.common.ConfigurationKeys.KEY;

import java.awt.SplashScreen;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.clubrockisen.common.ConfigurationKeys;
import org.clubrockisen.controller.MainWindowController;
import org.clubrockisen.dao.abstracts.EntryManagerAbstractDAOFactory;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.view.abstracts.EntryManagerFrame;

import com.alexrnl.commons.database.dao.AbstractDAOFactory;
import com.alexrnl.commons.database.dao.DataSourceConfiguration;
import com.alexrnl.commons.error.TopLevelError;
import com.alexrnl.commons.gui.swing.SwingUtils;
import com.alexrnl.commons.utils.Configuration;

/**
 * Launcher for the Club Rock ISEN application.
 * @author Alex
 */
public final class App {
	/** Logger */
	private static Logger					lg		= Logger.getLogger(App.class.getName());
	
	/** Access to the configuration */
	private final Configuration				config;
	/** The splash screen of the application */
	private final SplashScreen				splashScreen;
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor which load the arguments and set the configuration of the program.
	 * @param args
	 *        the arguments from the command line.
	 */
	private App (final String[] args) {
		super();
		
		// Load splash screen
		splashScreen = SplashScreen.getSplashScreen();
		if (splashScreen != null) {
			splashScreen.createGraphics();
		} else {
			lg.warning("Could not load splash screen");
		}
		
		// Load configuration
		config = new Configuration(Paths.get(args.length > 0 ? args[0] : ConfigurationKeys.FILE));
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
		
		MainWindowController mainWindow = null;
		try {
			// Loading DAO factory and services
			final DataSourceConfiguration dbInfos = new DataSourceConfiguration(config, KEY.db().toString());
			final EntryManagerAbstractDAOFactory factory = AbstractDAOFactory.buildFactory(config.get(KEY.daoFactory()),
					dbInfos, EntryManagerAbstractDAOFactory.class);
			ServiceFactory.createFactory(config, factory);
			
			// Loading GUI
			EntryManagerFrame.setIcon(Paths.get(config.get(KEY.iconFile())));
			SwingUtils.setLookAndFeel(ServiceFactory.getImplementation().getParameterManager().get(ParametersEnum.LOOK_AND_FEEL).getValue());
			mainWindow = new MainWindowController(Paths.get(config.get(KEY.helpFile())));
			closeSplashScreen();
			mainWindow.show();
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Club Rock ISEN application running.");
			}
		} catch (final TopLevelError e) {
			closeSplashScreen();
			lg.severe("Cannot run application: " + e.getClass() + "; details: " + e.getMessage());
			lg.severe("Caused by " + e.getCause());
			// Cannot use the method in view.Utils because the translator may not be loaded
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Severe error - Application will not run", JOptionPane.ERROR_MESSAGE);
			if (mainWindow != null) {
				mainWindow.dispose();
			}
		}
	}
	
	/**
	 * Close the splash screen if it has been created.
	 */
	private void closeSplashScreen () {
		if (splashScreen != null) {
			splashScreen.close();
		}
	}
	
	/**
	 * Entry point for the launcher.
	 * @param args
	 *        the arguments from the command line.
	 */
	public static void main (final String[] args) {
		new App(args).launch();
	}
	
}
