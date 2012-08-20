package org.clubrockisen;

/**
 * Abstract class to use for declaring severe errors thrown.<br />
 * Will be caught at the higher level of the application.
 * @author Alex
 */
public abstract class ClubRockISENError extends Error {
	/** Serial Version UID */
	private static final long	serialVersionUID	= -551856567230335889L;
	
	/**
	 * Constructor #1.<br />
	 * @param message
	 *        the details of the error.
	 * @param cause
	 *        the exception that caused the error.
	 */
	public ClubRockISENError (final String message, final Throwable cause) {
		super(message, cause);
	}
	
}