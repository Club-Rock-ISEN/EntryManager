package org.clubrockisen.entities.enums;

/**
 * Enumeration for the genders.
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
	 * Return the abbreviation corresponding to the gender.
	 * @return the abbreviation.
	 */
	public char getAbbreviation () {
		return abbreviation;
	}

	/**
	 * Return the enumeration matching the character passed as a parameter.
	 * @param abbreviation
	 *            the abbreviation to match.
	 * @return the matching enumeration.
	 */
	public static Gender fromValue (final char abbreviation) {
		if (MALE.getAbbreviation() == abbreviation) {
			return MALE;
		}
		if (FEMALE.getAbbreviation() == abbreviation) {
			return FEMALE;
		}
		throw new IllegalArgumentException("No such gender: '" + abbreviation + "'.");
	}

	/**
	 * Return the default gender to set ({@link #FEMALE})
	 * @return the default gender to set.
	 */
	public static Gender getDefault () {
		return FEMALE;
	}
}
