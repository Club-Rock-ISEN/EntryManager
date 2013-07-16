package org.clubrockisen.service;

import java.nio.file.Path;
import java.util.Locale;

import org.clubrockisen.common.TranslationKeys;
import org.clubrockisen.service.abstracts.ITranslator;

import com.alexrnl.commons.database.structure.Column;
import com.alexrnl.commons.database.structure.Entity;

/**
 * Implementation of the translator.<br />
 * Class which provide utilities method to translate key to the locale set in the configuration
 * file.
 * @author Alex
 */
public final class Translator extends com.alexrnl.commons.translation.Translator implements ITranslator {
	
	/**
	 * Constructor #1.<br />
	 * @param translationFile
	 *        the translation file to load.
	 */
	public Translator (final Path translationFile) {
		super(translationFile);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#getField(java.lang.String)
	 */
	@Override
	public String getField (final String key) {
		return get(key) + get(TranslationKeys.MISC.fieldValueSeparator());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.lang.Enum)
	 */
	@Override
	public String get (final Enum<?> key) {
		return get(TranslationKeys.ENUM.toString() + "."
				+ key.getClass().getSimpleName().toLowerCase(Locale.ENGLISH) + "."
				+ key.name().toLowerCase(Locale.ENGLISH));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#getField(java.lang.Enum)
	 */
	@Override
	public String getField (final Enum<?> key) {
		return get(key) + get(TranslationKeys.MISC.fieldValueSeparator());
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
		return get(TranslationKeys.ENTITY.toString() + "." + entity.getEntityName().toLowerCase(Locale.ENGLISH)
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
		return get(entity, column) + get(TranslationKeys.MISC.fieldValueSeparator());
	}
}
