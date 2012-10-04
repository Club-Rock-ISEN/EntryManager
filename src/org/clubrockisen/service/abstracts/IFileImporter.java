package org.clubrockisen.service.abstracts;

import java.nio.file.Path;
import java.util.Set;

/**
 * Interface defining a file importer which allows to import old members in the database.
 * @author Alex
 */
public interface IFileImporter {
	
	/**
	 * Parse a given file for members.
	 * @param file
	 *        the file to parse.
	 * @param format
	 *        the format of the file.
	 * @return <code>true</code> if the file was successfully parsed.
	 */
	boolean parseFile (Path file, Format format);
	
	/**
	 * Retrieve the formats supported by the implementation.
	 * @return the formats supported.
	 */
	Set<Format> getAvailableFormat ();
}
