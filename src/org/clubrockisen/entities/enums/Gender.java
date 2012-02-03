package org.clubrockisen.entities.enums;

/**
 * Enumeration for the genders.
 * 
 * @author Alex
 */
public enum Gender {
	/**
	 * Male
	 */
	MALE ('M'),

	/**
	 * Female
	 */
	FEMALE ('F');

	private char	abbreviation;

	private Gender (final char abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * Returns the abbreviation corresponding to the gender.
	 * @return the abbreviation.
	 */
	public char getAbbreviation () {
		return abbreviation;
	}
}
