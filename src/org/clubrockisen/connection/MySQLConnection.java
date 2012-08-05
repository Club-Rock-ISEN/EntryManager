package org.clubrockisen.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.clubrockisen.service.Configuration;
import org.clubrockisen.service.ConfigurationKey;

/**
 * The singleton for accessing the MySQL database.<br />
 * The parameters are loaded from the configuration file defined in {@link ConfigurationKey}.
 * @author Alex
 */
public final class MySQLConnection {
	/** Logger */
	private static Logger					lg		= Logger.getLogger(MySQLConnection.class.getName());
	
	/** The unique instance of the class */
	private static MySQLConnection			singleton;
	
	/** Access to the configuration */
	private final Configuration		config	= Configuration.getInstance();
	/** Access to the key structure of the configuration */
	private final ConfigurationKey	KEYS	= ConfigurationKey.CONFIGURATION_KEY;
	/** The URL for connecting the database */
	private String							url;
	/** The user name of the schema */
	private String							user;
	/** The password associated to the user */
	private String							password;
	/** The connection to the database */
	private Connection						connection;
	
	/**
	 * Constructor #1.<br />
	 * Private constructor which build a unique connection to the database.
	 */
	private MySQLConnection () {
		loadParameters();
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (final SQLException e) {
			lg.severe("Failed to create the connection to the database" + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Load the parameters from the configuration file.
	 */
	private void loadParameters () {
		url = config.get(KEYS.DB.URL());
		user = config.get(KEYS.DB.USER_NAME());
		password = config.get(KEYS.DB.PASSWORD());
	}
	
	/**
	 * Creates the connection to the database if it hasn't been established yet and return the
	 * connection.
	 * @return the connection to the data base.
	 */
	public static Connection getInstance () {
		if (singleton == null) {
			singleton = new MySQLConnection();
		}
		return singleton.connection;
	}
	
}
