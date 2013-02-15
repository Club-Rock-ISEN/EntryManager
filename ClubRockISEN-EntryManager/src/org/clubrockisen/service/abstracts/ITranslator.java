package org.clubrockisen.service.abstracts;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Entity;

/**
 * Interface for the translator.
 * @author Alex
 */
public interface ITranslator {
	
	/**
	 * Check if the translation is in the file.<br />
	 * Use this method to avoid log useless information.
	 * @param key
	 *        the key to look for.
	 * @return <code>true</code> if the key is loaded.
	 */
	boolean has (String key);
	
	/**
	 * Get the translation of a specific key.
	 * @param key
	 *        the key to translate.
	 * @return the translation of the key from the specified locale file.
	 */
	String get (String key);
	
	/**
	 * Get the translation of a collection of keys.
	 * @param keys
	 *        the collection of keys to translate.
	 * @return the translated collection.
	<T extends Collection<?>> T get (final T keys);
	 */
	
	/**
	 * Get the translation of a specific key.
	 * @param key
	 *        the key to translate.
	 * @param parameters
	 *        the parameters to use to build the translation.
	 * @return the translation of the key from the specified locale file.
	 */
	String get (String key, Object... parameters);
	
	/**
	 * Get the translation of a specific key and append the field value separator.
	 * @param key
	 *        the key to translate.
	 * @return the translation of the key from the specified locale file.
	 */
	String getField (String key);
	
	/**
	 * Get the translation of the enumeration value.
	 * @param key
	 *        the enumeration to translate.
	 * @return the translation of the enumeration in the current locale.
	 */
	String get (Enum<?> key);
	
	/**
	 * Get the translation of the enumeration value and append the field value separator.
	 * @param key
	 *        the enumeration to translate.
	 * @return the translation of the enumeration in the current locale.
	 */
	String getField (Enum<?> key);
	
	/**
	 * Get the translate name of the entity.
	 * @param entity
	 *        the entity to translate.
	 * @return the translation of the entity.
	 */
	String get (Entity entity);
	
	/**
	 * Get the translate name of the entity and append the field value separator.
	 * @param entity
	 *        the entity to translate.
	 * @return the translation of the entity.
	 */
	String getField (Entity entity);
	
	/**
	 * Get the translation of the column from the specified entity.
	 * @param entity
	 *        the entity from which the column is from.
	 * @param column
	 *        the column name to translate.
	 * @return the translation of the entity column.
	 */
	String get (Entity entity, Column column);
	
	/**
	 * Get the translation of the column from the specified entity and append the field value
	 * separator.
	 * @param entity
	 *        the entity from which the column is from.
	 * @param column
	 *        the column name to translate.
	 * @return the translation of the entity column.
	 */
	String getField (Entity entity, Column column);
	
}
