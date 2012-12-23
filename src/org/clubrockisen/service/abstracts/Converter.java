package org.clubrockisen.service.abstracts;

import org.clubrockisen.entities.Property;


/**
 * Class to read and write a String (from the current format) into an object which will be used
 * to fill the member.<br />
 * The default implementation is just returning the string.
 * @author Alex
 * @param <T> The property this converter may convert.
 */
public class Converter<T extends Property> {
	/** The field */
	private final T	field;
	
	/**
	 * Constructor #1.<br />
	 * @param field
	 *        the field of the converter.
	 */
	public Converter (final T field) {
		super();
		this.field = field;
	}
	
	/**
	 * Return the field filled by this data.
	 * @return the field.
	 */
	public final T getField () {
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
	 * @return the string to write.
	 */
	public String write (final Object fieldValue) {
		return fieldValue.toString();
	}
}