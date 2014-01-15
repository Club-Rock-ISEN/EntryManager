package org.clubrockisen.service.abstracts;

import java.nio.file.Path;
import java.util.Set;

/**
 * Interface defining a file manager which allows to import and export old members
 * in the database.
 * @author Alex
 */
public interface IFileManager {
	
	/**
	 * Parse a given file for members.
	 * @param file
	 *        the file to parse.
	 * @param format
	 *        the format of the file.
	 * @return the number of members imported or <code>null</code> if the file could not be parsed.
	 */
	Integer parseFile (Path file, Format format);
	
	/**
	 * Retrieve the formats supported by the implementation.
	 * @return the formats supported.
	 */
	Set<Format> getAvailableFormat ();
	
	/**
	 * Export the members in a file.
	 * @param file
	 *        the target file which will be created and contain the members.
	 * @param format
	 *        the format of the file to create.
	 * @return <code>true</code> if the file was successfully created.
	 */
	boolean exportFile (Path file, Format format);
}
