package org.clubrockisen.service;

import java.util.logging.Logger;

/**
 * Configuration file key structure.<br />
 * Should be accessed using {@link #CONFIGURATION_KEY}.
 * @author Alex
 */
public final class ConfigurationKey {
	/** Logger */
	private static Logger		lg		= Logger.getLogger(ConfigurationKey.class.getName());
	
	/** The path to the configuration file */
	public static final String	FILE	= "conf/configuration.xml";
	
	/** The root key */
	private final String		key		= "configuration";
	
	/**
	 * Constructor #1.<br />
	 * Unique default and private constructor. Allow access to the configuration key only through
	 * {@link #CONFIGURATION_KEY}.
	 */
	private ConfigurationKey () {
		super();
		lg.info("Creating the configuration key structure.");
	}
	
	/**
	 * Configuration regarding the database.
	 * @author Alex
	 */
	public class DB {
		/** The root key for the database configuration */
		private final String	keyDB;
		
		/**
		 * Constructor #1.<br />
		 * @param parentKey
		 *        the key from the parent category.
		 */
		public DB (final String parentKey) {
			this.keyDB = parentKey + "." + "db";
		}
		
		/**
		 * The URL for the database connection
		 * @return the key to the URL parameter.
		 */
		public String URL () {
			return keyDB + "." + "url";
		}
		
		/**
		 * The user name to be used by the application for the database.
		 * @return the key to the user name parameter.
		 */
		public String USER_NAME () {
			return keyDB + "." + "username";
		}
		
		/**
		 * The password to be used by the application for connecting the database.
		 * @return the key to the password.
		 */
		public String PASSWORD () {
			return keyDB + "." + "password";
		}
	}
	
	/** Access to the keys regarding the database */
	public DB	DB	= new DB(key);
	
	/**
	 * The path to the translation file.
	 * @return the key to the translation file.
	 */
	public String TRANSLATION_FILE () {
		return key + "." + "translationFile";
	}
	
	/** The access to the configuration key structure */
	public static ConfigurationKey	CONFIGURATION_KEY	= new ConfigurationKey();
	
}
