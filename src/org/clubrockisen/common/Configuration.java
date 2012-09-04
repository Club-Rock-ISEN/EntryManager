package org.clubrockisen.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to be used when accessing to the configuration file.<br />
 * To retrieve the properties, use the {@link ConfigurationKey} class which defines the key
 * structure of the document.<br />
 * To retrieve a property, the method {@link #get(String)} should be used.
 * @author Alex
 */
public final class Configuration {
	/** Logger */
	private static Logger			lg	= Logger.getLogger(Configuration.class.getName());
	
	/** Unique instance of the class */
	private static Configuration	singleton;
	/** Lock for singleton initialization */
	private static Object					lock	= new Object();
	/** The configuration file to load */
	private static String			configurationFile = ConfigurationKey.FILE;
	
	/** The configuration properties */
	private final Properties		configuration;
	
	/**
	 * Constructor #1.<br />
	 * Private default constructor, load the configuration properties from the configuration file.
	 */
	private Configuration () {
		super();
		configuration = new Properties();
		load();
	}
	
	/**
	 * Return the unique instance of the singleton.<br />
	 * Build the properties which will load the configuration.
	 * @return the instance of the configuration.
	 */
	public static Configuration getInstance () {
		if (singleton == null) {
			synchronized (lock) {
				singleton = new Configuration();
			}
		}
		return singleton;
	}
	
	/**
	 * Set the configuration file to load.
	 * @param filePath
	 *        the path to the configuration file to load.
	 */
	public static void setFile (final String filePath) {
		configurationFile = filePath;
		if (singleton == null) {
			getInstance();
		} else {
			getInstance().load();
		}
	}
	
	/**
	 * Load the properties from the configuration file.
	 */
	private void load () {
		// Load the properties
		try {
			configuration.loadFromXML(new FileInputStream( configurationFile != null ?
					configurationFile : ConfigurationKey.FILE));
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Properties successfully loaded:");
				for (final Entry<Object, Object> currentProp : configuration.entrySet()) {
					lg.fine("\t" + currentProp.getKey() + " = " + currentProp.getValue());
				}
			}
		} catch (final IOException e) {
			lg.warning("Exception while loading configuration (" + e.getMessage() + ")");
		}
	}
	
	/**
	 * Get the property matching to the property name specified.
	 * @param propertyName
	 *        the name of the property to retrieve.
	 * @return the value of the property.
	 */
	public String get (final String propertyName) {
		if (!configuration.containsKey(propertyName)) {
			lg.warning("No properties with name " + propertyName);
			return null;
		}
		return configuration.getProperty(propertyName);
	}
}