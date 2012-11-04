package org.clubrockisen.common;

import java.awt.Insets;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.swing.text.html.HTML.Tag;

/**
 * Various constant being used in the application.<br />
 * The constant contained in this file are development constant, for constants defined at a user
 * level (or integration level) use the database or the configuration file.
 * @author Alex
 */
public final class Constants {
	
	/**
	 * Constructor #1.<br />
	 * Hide default constructor.
	 */
	private Constants () {
		super();
	}
	
	// GUI constants
	/** The string for displaying the Greek delta (Δ) */
	public static final char	DELTA					= '\u0394';
	
	/** Maximum length for a string in a dialog */
	public static final int		LINE_MAX_LENGTH			= 65;
	
	/** Default gap between components */
	public static final int		DEFAULT_COMPONENT_GAP	= 5;
	
	/** Default insets to be used for GUI building */
	public static final Insets	DEFAULT_INSETS			= new Insets(5, 5, 5, 5);
	
	/** The separator between hours and minutes */
	public static final String	TIME_SEPARATOR			= ":";
	
	/** Character used for mnemonics in menu bar */
	public static final char	MNEMONIC_MARK			= '#';
	
	/** Character used for parameters in translations */
	public static final char	PARAMETER_PREFIX		= '%';
	
	/** The date format used in the GUI */
	public static final String	DISPLAYED_DATE_FORMAT	= "EEEE d MMMM yyyy";
	
	// String and text related constants
	/** The HTML tag for a new HTML document */
	public static final String	HTML_HTML_START			= "<" + Tag.HTML + ">";
	
	/** The HTML tag for the end of an HTML document */
	public static final String	HTML_HTML_END			= "</" + Tag.HTML + ">";
	
	/** The HTML tag for new line */
	public static final String	HTML_NEW_LINE			= "<" + Tag.BR + " />";
	
	/** The new line character */
	public static final char	NEW_LINE				= '\n';
	
	/** The space character */
	public static final char	SPACE					= ' ';
	
	/** The default char set to use */
	public static final Charset	DEFAULT_CHARSET			= StandardCharsets.UTF_8;
	
	// Spinner bounds and step constants
	/** The step between two possible values for the money */
	public static final double	MONEY_STEP_SPINNER		= 0.01;
	
	/** Minimum value for time spinner */
	public static final Time	TIME_MIN_SPINNER		= new Time();
	
	/** Maximum value for time spinner */
	public static final Time	TIME_MAX_SPINNER		= new Time(23, 59);
	
	/** Step for time spinner */
	public static final Time	TIME_STEP_SPINNER		= new Time(0, 1);
	
	// Developper's constants
	/** The date format used in log display */
	public static final String	LOG_DATE_FORMAT			= DISPLAYED_DATE_FORMAT;
	
	/** The name of the author to display */
	public static final String	AUTHOR_NAME				= "Alex Barféty";
	
	/** The date format use to persist dates in a SQL data base */
	public static final String	SQL_DATE_FORMAT			= "yyyy-MM-dd";
	
	// Miscellaneous constants
	/** The file prefix used in URIs */
	public static final String	FILE_URI_PREFIX			= "file:";
	
	/** Default prime number to use for hash code computation */
	public static final int		PRIME_FOR_HASHCODE		= 31;
	
	/** The regex pattern that matches any non decimal character */
	public static final String	NON_DECIMAL_CHARACTER	= "[^0-9]";
	
	/** The common prefix for attribute setters in classes */
	public static final String	SETTER_PREFIX			= "set";
	
}
