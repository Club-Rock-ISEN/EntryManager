package org.clubrockisen.service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration file key structure.<br />
 * Should be accessed using {@link #KEY}.
 * @author Alex
 */
public final class ConfigurationKey {
	/** Logger */
	private static Logger					lg		= Logger.getLogger(ConfigurationKey.class.getName());
	
	/** The path to the configuration file */
	public static final String				FILE	= "conf/configuration.xml";
	/** The access to the configuration key structure */
	public static final ConfigurationKey	KEY		= new ConfigurationKey();
	
	/** The root key */
	private final String					rootKey	= "configuration";
	
	/**
	 * Constructor #1.<br />
	 * Unique default and private constructor. Allow access to the configuration key only through
	 * {@link #KEY}.
	 */
	private ConfigurationKey () {
		super();
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Creating the configuration key structure.");
		}
	}
	
	/**
	 * Configuration regarding the database.
	 * @author Alex
	 */
	public class Db {
		/** The root key for the database configuration */
		private final String	dbKey;
		
		/**
		 * Constructor #1.<br />
		 * @param parentKey
		 *        the key from the parent category.
		 */
		public Db (final String parentKey) {
			this.dbKey = parentKey + "." + "db";
		}
		
		/**
		 * The URL for the database connection
		 * @return the key to the URL parameter.
		 */
		public String url () {
			return dbKey + "." + "url";
		}
		
		/**
		 * The user name to be used by the application for the database.
		 * @return the key to the user name parameter.
		 */
		public String username () {
			return dbKey + "." + "username";
		}
		
		/**
		 * The password to be used by the application for connecting the database.
		 * @return the key to the password.
		 */
		public String password () {
			return dbKey + "." + "password";
		}
	}
	
	/** Attribute which holds the structure of the database configuration */
	private final Db	db	= new Db(rootKey);
	
	/**
	 * Access to the keys regarding the database.
	 * @return the access to the database key structure.
	 */
	public Db db () {
		return db;
	}
	
	/**
	 * The DAO factory to be used.
	 * @return the key to the DAO type.
	 */
	public String daoFactory () {
		return rootKey + "." + "dao";
	}
	
	/**
	 * The path to the translation file.
	 * @return the key to the translation file.
	 */
	public String translationFile () {
		return rootKey + "." + "translationFile";
	}
	
}