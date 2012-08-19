package org.clubrockisen.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private static Logger					lg		= Logger.getLogger(Translator.class.getName());
	
	/** Unique instance of the class */
	private static Translator				singleton;
	
	/** Access to the configuration */
	private final Configuration				config	= Configuration.getInstance();
	/** Access to the key structure of the configuration */
	private static final ConfigurationKey	KEYS	= ConfigurationKey.KEY;
	
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
		if (singleton == null) {
			singleton = new Translator();
		}
		return singleton;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.lang.String)
	 */
	@Override
	public String get (final String key) {
		if (!translations.containsKey(key)) {
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Cannot find translation for key " + key);
			}
			return key;
		}
		
		return translations.getProperty(key);
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
				+ key.getClass().getSimpleName().toLowerCase() + "." + key.name().toLowerCase());
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
		return get(TranslationKey.ENTITY.toString() + "." + entity.getEntityName().toLowerCase()
				+ (column == null ? "" : "." + column.getName().toLowerCase()));
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
