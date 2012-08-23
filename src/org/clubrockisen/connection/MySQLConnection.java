package org.clubrockisen.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.exception.SQLConfigurationError;
import org.clubrockisen.service.Configuration;
import org.clubrockisen.service.ConfigurationKey;

/**
 * The singleton for accessing the MySQL database.<br />
 * The parameters are loaded from the configuration file defined in {@link ConfigurationKey}.
 * @author Alex
 */
public final class MySQLConnection {
	/** Logger */
	private static Logger			lg		= Logger.getLogger(MySQLConnection.class.getName());
	
	/** The unique instance of the class */
	private static MySQLConnection	singleton;
	
	/** Access to the configuration */
	private final Configuration		config	= Configuration.getInstance();
	/** Access to the key structure of the configuration */
	private final ConfigurationKey	keys	= ConfigurationKey.KEY;
	/** The URL for connecting the database */
	private String					url;
	/** The user name of the schema */
	private String					user;
	/** The password associated to the user */
	private String					password;
	/** The connection to the database */
	private Connection				connection;
	
	/**
	 * Constructor #1.<br />
	 * Private constructor which build a unique connection to the database.
	 */
	private MySQLConnection () {
		loadParameters();
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (final SQLException e) {
			lg.severe("Failed to create the connection to the database " + e.getMessage());
			throw new SQLConfigurationError("Failed to connect to MySQL database: "
					+ e.getMessage(), e);
		}
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Successfully connected to database " + url);
		}
	}
	
	/**
	 * Load the parameters from the configuration file.
	 */
	private void loadParameters () {
		url = config.get(keys.db().url());
		user = config.get(keys.db().username());
		password = config.get(keys.db().password());
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
	
	/**
	 * Close the current connection to the database.<br />
	 * Do nothing is the connection is not valid.
	 */
	public static void close () {
		if (singleton == null || singleton.connection == null) {
			return;
		}
		try {
			singleton.connection.close();
		} catch (final SQLException e) {
			lg.warning("Could not close the connection to the database: " + e.getMessage());
		}
		singleton.connection = null;
		singleton = null;
	}
	
}
