package org.clubrockisen;

import java.util.logging.Logger;

import org.clubrockisen.connection.MySQLConnection;

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
		MySQLConnection.getInstance();
	}
}
