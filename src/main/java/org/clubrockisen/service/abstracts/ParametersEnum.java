package org.clubrockisen.service.abstracts;

import java.util.Locale;

/**
 * The enumeration for the parameters.<br />
 * When adding a parameter, add its corresponding enumeration in this class.
 * 
 * @author Alex
 */
public enum ParametersEnum {
	/**
	 * The look and feel of the application.
	 */
	LOOK_AND_FEEL("lookAndFeel"),
	
	/**
	 * The time limit between the two parts of a party.
	 */
	TIME_LIMIT("timeLimit"),
	
	/**
	 * The price for both parts of the party.
	 */
	ENTRY_PRICE_TOTAL("entryPriceTotal"),
	
	/**
	 * The price for the first part.
	 */
	ENTRY_PRICE_FIRST_PART("entryPriceFirstPart"),
	
	/**
	 * The price for the second part.
	 */
	ENTRY_PRICE_SECOND_PART("entryPriceSecondPart"),
	
	/**
	 * The frequency of free entries.
	 */
	FREE_ENTRY_FREQUENCY ("freeEntryFrequency"),
	
	/**
	 * Minimum threshold for member credit.
	 */
	MIN_CREDIT ("minCredit"),
	
	/**
	 * Maximum threshold for member credit.
	 */
	MAX_CREDIT ("maxCredit"),
	
	/**
	 * Cost of a party, used to compute the profit.
	 */
	PARTY_COST ("partyCost"),
	
	/**
	 * Char set of the imported/exported files.
	 */
	FILE_CHARSET ("fileCharset");
	
	/** The name of the parameter */
	private String	name;
	
	/**
	 * Constructor #1.<br />
	 * Private constructor which build the enumeration.
	 * @param name
	 *        the name of the enumeration.
	 */
	private ParametersEnum (final String name) {
		this.name = name;
	}
	
	/**
	 * Return the name of the parameter (in the database).
	 * @return the name.
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * Return the matching enumeration for the name passed.
	 * @param name
	 *        the name to look for.
	 * @return the matching enumeration.
	 */
	public static ParametersEnum fromValue (final String name) {
		try {
			// First, try to use the JDK method
			return Enum.valueOf(ParametersEnum.class, name.toUpperCase(Locale.getDefault()));
		} catch (final IllegalArgumentException e) {
			// If fail, try to compare with the name of the enumeration
			for (final ParametersEnum currentParam : ParametersEnum.values()) {
				if (currentParam.getName().equalsIgnoreCase(name)) {
					return currentParam;
				}
			}
			throw new IllegalArgumentException("No parameter with name '" + name + "'.", e);
		}
	}
	
}
