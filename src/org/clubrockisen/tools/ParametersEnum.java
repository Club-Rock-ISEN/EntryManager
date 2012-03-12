package org.clubrockisen.tools;


/**
 * The enumeration for the parameters.<br />
 * When adding a parameter, add its corresponding enumeration in this class.
 * @author Alex
 */
public enum ParametersEnum {
	/**
	 * The look and feel of the application.
	 */
	LOOK_AND_FEEL ("lookAndFeel"),
	
	/**
	 * The time limit between the two parts of a party.
	 */
	TIME_LIMIT ("timeLimit"),
	
	/**
	 * The price for both parts of the party.
	 */
	ENTRY_PRICE_TOTAL ("entryPriceTotal"),
	
	/**
	 * The price for the first part.
	 */
	ENTRY_PRICE_FIRST_PART ("entryPriceFirstPart"),
	
	/**
	 * The price for the second part.
	 */
	ENTRY_PRICE_SECOND_PART ("entryPriceSecondPart");
	
	private String name;
	
	private ParametersEnum(final String name) {
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
	 *            the name to look for.
	 * @return the matching enumeration.
	 */
	public static ParametersEnum fromValue (final String name) {
		try {
			return Enum.valueOf(ParametersEnum.class, name.toUpperCase());
		} catch (final IllegalArgumentException e) {
			for (final ParametersEnum currentParam : ParametersEnum.values()) {
				if (currentParam.getName().equalsIgnoreCase(name)) {
					return currentParam;
				}
			}
			throw new IllegalArgumentException("No parameter with name '" + name + "'.", e);
		}
	}

}
