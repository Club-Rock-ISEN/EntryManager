package org.clubrockisen.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * @author Alex
 * 
 */
public class MySQLConnection {
	private static Logger		T			= Logger.getLogger(MySQLConnection.class.getName());

	/**
	 * The URL for connecting the database.
	 */
	private static String		url			= "jdbc:mysql://localhost/crock";

	/**
	 * The user name of the schema.
	 */
	private static String		user		= "crock";

	/**
	 * The password associated to the user.
	 */
	private static String		password	= "burgerking";

	/**
	 * Object
	 */
	private static Connection	connection;

	/**
	 * Creates the connection to the database if it hasn't been established yet and return the
	 * connection
	 * @return the connection to the data base.
	 */
	public static Connection getInstance () {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (final SQLException e) {
				T.severe(e.getMessage());
				JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
			}
		}
		return connection;
	}

}
