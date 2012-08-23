package org.clubrockisen.exception;

/**
 * Exception indicating a problem with the configuration of the database.<br />
 * Probable cause, wrong URL, login or password.
 * @author Alex
 */
public class SQLConfigurationError extends TopLevelError {
	/** Serial Version UID */
	private static final long	serialVersionUID	= 8530177052070875293L;
	
	/**
	 * Constructor #1.<br />
	 * Constructor with no message nor cause.
	 */
	public SQLConfigurationError () {
		this(null);
	}
	
	/**
	 * Constructor #2.<br />
	 * @param message
	 *        the details of the problem.
	 */
	public SQLConfigurationError (final String message) {
		this(message, null);
	}
	
	/**
	 * Constructor #3.<br />
	 * @param message
	 *        the details of the problem.
	 * @param cause
	 *        the exception which caused the issue.
	 */
	public SQLConfigurationError (final String message, final Throwable cause) {
		super(message, cause);
	}
}
