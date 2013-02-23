package org.clubrockisen.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.Configuration;
import org.clubrockisen.common.ConfigurationKeys;
import org.clubrockisen.common.Constants;
import org.clubrockisen.common.TranslationKey;
import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Entity;
import org.clubrockisen.service.abstracts.ITranslator;

/**
 * Implementation of the translator.<br />
 * Singleton which provide utilities method to translate key to the local set in the configuration
 * file.
 * @author Alex
 */
public final class Translator implements ITranslator {
	/** Logger */
	private static Logger					lg			= Logger.getLogger(Translator.class.getName());
	
	// Configuration
	/** Access to the configuration */
	private final Configuration				config		= Configuration.getInstance();
	/** Access to the key structure of the configuration */
	private static final ConfigurationKeys	KEYS		= ConfigurationKeys.KEY;
	
	// Translations
	/** Unique instance of the class */
	private static Translator				singleton	= new Translator();
	/** The map between the keys and their translation */
	private final Properties				translations;
	
	/**
	 * Constructor #1.<br />
	 * Private constructor which load the translation file.
	 */
	private Translator () {
		final String translationFile = config.get(KEYS.translationFile());
		translations = new Properties();
		try {
			translations.loadFromXML(new FileInputStream(translationFile));
		} catch (final IOException e) {
			lg.severe("Could not load translation file: " + translationFile + " (" + e.getMessage()
					+ ")");
			translations.clear();
			return;
		}
		
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Language locale file " + translationFile + " successfully loaded ("
					+ translations.size() + " keys loaded)");
		}
	}
	
	/**
	 * Return the translator.
	 * @return the unique instance of the class.
	 */
	public static Translator getInstance () {
		return singleton;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#has(java.lang.String)
	 */
	@Override
	public boolean has (final String key) {
		return translations.containsKey(key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.lang.String)
	 */
	@Override
	public String get (final String key) {
		if (!has(key)) {
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Cannot find translation for key " + key);
			}
			return key;
		}
		
		String translation = translations.getProperty(key);
		
		// Replace other translations included in the current one
		while (translation.contains("" + Constants.INCLUDE_PREFIX)) {
			// Isolate the string to replace, from the Constants.INCLUDE_PREFIX to the next space
			final int location = translation.indexOf(Constants.INCLUDE_PREFIX);
			final int endLocation = translation.substring(location).indexOf(Constants.SPACE) + location;
			final String strToReplace;
			if (endLocation == -1) {
				// End of string case
				strToReplace = translation.substring(location);
			} else {
				strToReplace = translation.substring(location, endLocation);
			}
			// Replace the prefix + key with the translation of the key (hence the substring)
			translation = translation.replace(strToReplace, get(strToReplace.substring(1)));
		}
		
		return translation;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.util.Set)
	@Override
	public <T extends Collection<?>> T get (final T keys) {
		try {
			final T translatedKeys = (T) keys.getClass().newInstance();
			for (final Object key : keys) {
				Object newObj = key.getClass().newInstance();
				
				translatedKeys.add(newObj);
			}
			return translatedKeys;
		} catch (InstantiationException | IllegalAccessException e) {
			lg.warning("Could not instantiate a collection of type " + keys.getClass().getSimpleName()
					+ ", " + e.getClass() + ", " + e.getMessage() + ")");
			return keys;
		}
	}
	 */
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.lang.String, java.lang.Object[])
	 */
	@Override
	public String get (final String key, final Object... parameters) {
		String translation = get(key);
		// Case with no parameters or no translation found
		if (key.equals(translation) || parameters == null || parameters.length == 0) {
			return translation;
		}
		
		// Replace parameters
		for (int indexParameter = 0; indexParameter < parameters.length; ++indexParameter) {
			final Object parameter = parameters[indexParameter];
			final String strToReplace = "" + Constants.PARAMETER_PREFIX + indexParameter;
			if (translation.indexOf(strToReplace) == -1) {
				lg.warning("Parameter '" + parameter + "' cannot be put into the translation, '" +
						strToReplace + "' was not found.");
				continue;
			}
			translation = translation.replace(strToReplace, parameter.toString());
		}
		
		return translation;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#getField(java.lang.String)
	 */
	@Override
	public String getField (final String key) {
		return get(key) + get(TranslationKey.MISC.fieldValueSeparator());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.lang.Enum)
	 */
	@Override
	public String get (final Enum<?> key) {
		return get(TranslationKey.ENUM.toString() + "."
				+ key.getClass().getSimpleName().toLowerCase(Locale.ENGLISH) + "."
				+ key.name().toLowerCase(Locale.ENGLISH));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#getField(java.lang.Enum)
	 */
	@Override
	public String getField (final Enum<?> key) {
		return get(key) + get(TranslationKey.MISC.fieldValueSeparator());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(org.clubrockisen.entities.Entity)
	 */
	@Override
	public String get (final Entity entity) {
		return get(entity, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.ITranslator#getField(org.clubrockisen.entities.Entity)
	 */
	@Override
	public String getField (final Entity entity) {
		return getField(entity, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(org.clubrockisen.entities.Entity,
	 * org.clubrockisen.entities.Column)
	 */
	@Override
	public String get (final Entity entity, final Column column) {
		return get(TranslationKey.ENTITY.toString() + "." + entity.getEntityName().toLowerCase(Locale.ENGLISH)
				+ (column == null ? "" : "." + column.getName().toLowerCase(Locale.ENGLISH)));
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.ITranslator#getField(org.clubrockisen.entities.Entity,
	 * org.clubrockisen.entities.Column)
	 */
	@Override
	public String getField (final Entity entity, final Column column) {
		return get(entity, column) + get(TranslationKey.MISC.fieldValueSeparator());
	}
}
