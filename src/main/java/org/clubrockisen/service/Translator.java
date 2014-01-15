package org.clubrockisen.service;

import java.nio.file.Path;
import java.util.Locale;

import org.clubrockisen.common.TranslationKeys;

import com.alexrnl.commons.database.structure.Column;
import com.alexrnl.commons.database.structure.Entity;

/**
 * Implementation of the translator.<br />
 * Class which provide utilities method to translate key to the locale set in the configuration
 * file.
 * @author Alex
 */
public final class Translator extends com.alexrnl.commons.translation.Translator {
	
	/**
	 * Constructor #1.<br />
	 * @param translationFile
	 *        the translation file to load.
	 */
	public Translator (final Path translationFile) {
		super(translationFile);
	}
	
	/**
	 * Get the translation of a specific key and append the field value separator.
	 * @param key
	 *        the key to translate.
	 * @return the translation of the key from the specified locale file.
	 */
	public String getField (final String key) {
		return get(key) + get(TranslationKeys.MISC.fieldValueSeparator());
	}
	
	/**
	 * Get the translation of the enumeration value.
	 * @param key
	 *        the enumeration to translate.
	 * @return the translation of the enumeration in the current locale.
	 */
	public String get (final Enum<?> key) {
		return get(TranslationKeys.ENUM.toString() + "."
				+ key.getClass().getSimpleName().toLowerCase(Locale.ENGLISH) + "."
				+ key.name().toLowerCase(Locale.ENGLISH));
	}
	
	/**
	 * Get the translation of the enumeration value and append the field value separator.
	 * @param key
	 *        the enumeration to translate.
	 * @return the translation of the enumeration in the current locale.
	 */
	public String getField (final Enum<?> key) {
		return get(key) + get(TranslationKeys.MISC.fieldValueSeparator());
	}

	/**
	 * Get the translate name of the entity.
	 * @param entity
	 *        the entity to translate.
	 * @return the translation of the entity.
	 */
	public String get (final Entity entity) {
		return get(entity, null);
	}
	
	/**
	 * Get the translate name of the entity and append the field value separator.
	 * @param entity
	 *        the entity to translate.
	 * @return the translation of the entity.
	 */
	public String getField (final Entity entity) {
		return getField(entity, null);
	}
	
	/**
	 * Get the translation of the column from the specified entity.
	 * @param entity
	 *        the entity from which the column is from.
	 * @param column
	 *        the column name to translate.
	 * @return the translation of the entity column.
	 */
	public String get (final Entity entity, final Column column) {
		return get(TranslationKeys.ENTITY.toString() + "." + entity.getEntityName().toLowerCase(Locale.ENGLISH)
				+ (column == null ? "" : "." + column.getName().toLowerCase(Locale.ENGLISH)));
	}
	
	/**
	 * Get the translation of the column from the specified entity and append the field value
	 * separator.
	 * @param entity
	 *        the entity from which the column is from.
	 * @param column
	 *        the column name to translate.
	 * @return the translation of the entity column.
	 */
	public String getField (final Entity entity, final Column column) {
		return get(entity, column) + get(TranslationKeys.MISC.fieldValueSeparator());
	}
}
