package org.clubrockisen.service.abstracts;

import java.util.List;


/**
 * Interface defining a file format for storing members.
 * @author Alex
 */
public interface Format {
	
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
