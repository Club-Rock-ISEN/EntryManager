package org.clubrockisen.exception;

/**
 * Abstract class to use for declaring severe errors thrown.<br />
 * Will be caught at the higher level of the application.
 * @author Alex
 */
public abstract class TopLevelError extends Error {
	/** Serial Version UID */
	private static final long	serialVersionUID	= -551856567230335889L;
	
	/**
	 * Constructor #1.<br />
	 * @param message
	 *        the details of the error.
	 * @param cause
	 *        the exception that caused the error.
	 */
	public TopLevelError (final String message, final Throwable cause) {
		super(message, cause);
	}
	
}