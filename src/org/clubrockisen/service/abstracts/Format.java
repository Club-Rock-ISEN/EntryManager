package org.clubrockisen.service.abstracts;

import java.util.List;

import org.clubrockisen.entities.Member.MemberColumn;

/**
 * Interface defining a file format for storing members.
 * @author Alex
 */
public interface Format {
	
	/**
	 * Interface to convert a String (from the current format) into an object which will be used
	 * to fill the member.<br />
	 * The default implementation is just returning the string.
	 * @author Alex
	 */
	class Converter {
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
		public MemberColumn getField () {
			return field;
		}
		
		/**
		 * Convert the data string to the field value target.
		 * @param data
		 *        the data parsed.
		 * @return the converted value.
		 */
		public Object convert (final String data) {
			return data;
		}
	}
	
	/**
	 * Retrieve the string which separates two definitions of members.
	 * @return the separator.
	 */
	String getMemberSeparator ();
	
	/**
	 * Retrieve the string which separates two fields in the definition of a member.
	 * @return the separator.
	 */
	String getFieldSeparator ();
	
	/**
	 * Retrieve the order of the field of the format.<br />
	 * Use <code>null</code> for unused column in the current file format.
	 * @return the list ordered with the column definition.
	 */
	List<Converter> getFieldOrder ();
	
}
