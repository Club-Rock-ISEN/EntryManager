package org.clubrockisen.service.abstracts;

import org.clubrockisen.entities.Member.MemberColumn;

/**
 * Class to read and write a String (from the current format) into an object which will be used
 * to fill the member.<br />
 * The default implementation is just returning the string.
 * @author Alex
 */
public class Converter {
	/** The field */
	private final MemberColumn	field;
	
	/**
	 * Constructor #1.<br />
	 * @param field
	 *        the field of the converter.
	 */
	public Converter (final MemberColumn field) {
		super();
		this.field = field;
	}
	
	/**
	 * Return the field filled by this data.
	 * @return the field.
	 */
	final public MemberColumn getField () {
		return field;
	}
	
	/**
	 * Read the data string and convert it to the field value target.
	 * @param data
	 *        the data parsed.
	 * @return the converted value.
	 */
	public Object read (final String data) {
		return data;
	}
	
	/**
	 * Write the current field and write its textual representation in a string.
	 * @param fieldValue
	 *        the field of the object.
	 * @return the string to write
	 */
	public String write (final Object fieldValue) {
		return fieldValue.toString();
	}
}