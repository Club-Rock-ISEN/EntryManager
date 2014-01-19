package org.clubrockisen.service.abstracts;

import java.util.List;

import org.clubrockisen.entities.Member.MemberColumn;


/**
 * Interface defining a file format for storing entities.
 * @author Alex
 */
public interface Format {
	
	/**
	 * Retrieve the string which separates two definitions of entities.
	 * @return the separator.
	 */
	String getEntitySeparator ();
	
	/**
	 * Retrieve the string which separates two fields in the definition of an entity.
	 * @return the separator.
	 */
	String getFieldSeparator ();
	
	/**
	 * Retrieve the order of the field of the format.<br />
	 * Use <code>null</code> for unused column in the current file format.
	 * @return the list ordered with the column definition.
	 */
	List<Converter<MemberColumn>> getFieldOrder ();
	
}
