package org.clubrockisen.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.error.SQLConfigurationError;

import com.alexrnl.commons.database.DataSourceConfiguration;

/**
 * Utility methods for DAOs (connection, statement generation, etc).
 * @author Alex
 */
public final class Utils {
	/** Logger */
	private static Logger					lg	= Logger.getLogger(Utils.class.getName());
	
	/**
	 * Constructor #1.<br />
	 */
	private Utils () {
		super();
	}
	
	/**
	 * Return the connection to the database with the information specified.
	 * @param dbInfos
	 *        the informations to use to connect to the database.
	 * @return the connection to the database.
	 */
	public static Connection getConnection (final DataSourceConfiguration dbInfos) {
		final Connection connection;
		try {
			connection = DriverManager.getConnection(dbInfos.getUrl(), dbInfos.getUsername(), dbInfos.getPassword());
			if (connection.isValid(0)) {
				connection.setAutoCommit(true);
				if (lg.isLoggable(Level.INFO)) {
					lg.info("Successfully connected to database " + dbInfos.getUrl());
				}
			} else {
				throw new SQLException("Database connection invalid.");
			}
		} catch (final SQLException e) {
			lg.severe("Failed to create the connection to the database " + e.getMessage());
			throw new SQLConfigurationError("Failed to connect to database: "
					+ e.getMessage(), e);
		}
		return connection;
	}
	
	/**
	 * Close a database connection.
	 * @param connection
	 *        the connection to close.
	 */
	public static void close (final Connection connection) {
		try {
			connection.close();
		} catch (final SQLException e) {
			lg.warning("Error while closing database connection (" + e.getMessage() + ")");
		}
	}
}
