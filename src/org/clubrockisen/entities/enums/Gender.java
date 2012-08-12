package org.clubrockisen.entities.enums;

/**
 * Enumeration for the genders.
 * @author Alex
 */
public enum Gender {
	/** Male */
	MALE('M'),
	
	/** Female */
	FEMALE('F');
	
	/** The character used to represent the gender in the database */
	private char	abbreviation;
	
	/**
	 * Constructor #1.<br />
	 * Unique constructor for the enumeration.
	 * @param abbreviation
	 *        the abbreviation which represent the gender.
	 */
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
	 * Return the default gender to set ({@link #FEMALE})
	 * @return the default gender to set.
	 */
	public static Gender getDefault () {
		return FEMALE;
	}
	
	/**
	 * Return the enumeration matching the character passed as a parameter.
	 * @param abbreviation
	 *        the abbreviation to match.
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
}
