package org.clubrockisen.entities.enums;

/**
 * Enumeration for the status of the member.
 * @author Alex
 */
public enum Status {
	/** A regular member */
	MEMBER ("member"),
	
	/** An helper member (generally, used for ISEN students) */
	HELPER_MEMBER ("helper member"),
	
	/** A member from the office of the club rock */
	OFFICE_MEMBER ("office member"),
	
	/** A member who used to be an office member */
	VETERAN ("veteran");
	
	/** The name of the status */
	private String	name;
	
	/**
	 * Constructor #1.<br />
	 * Unique constructor for the enumeration.
	 * @param name
	 *            the name of the status.
	 */
	private Status (final String name) {
		this.name = name;
	}
	
	/**
	 * Return the name of the status.
	 * @return the name of the status.
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * Return the default status to set.
	 * @return the default status to set.
	 */
	public static Status getDefault () {
		return MEMBER;
	}
	
	/**
	 * Return the matching enumeration for the name passed.
	 * @param name
	 *            the name to look for.
	 * @return the matching enumeration.
	 */
	public static Status fromValue (final String name) {
		try {
			// First, use the default JDK method
			return Enum.valueOf(Status.class, name.toUpperCase());
		} catch (final IllegalArgumentException e) {
			// If failed, try to match the name of an enumeration
			for (final Status currentStatus : Status.values()) {
				if (currentStatus.getName().equalsIgnoreCase(name)) {
					return currentStatus;
				}
			}
			throw new IllegalArgumentException("No status with name '" + name + "'.", e);
		}
	}
	
}
