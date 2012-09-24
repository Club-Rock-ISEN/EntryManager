package org.clubrockisen.service;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.service.abstracts.Format;
import org.clubrockisen.service.abstracts.IFileImporter;
import org.clubrockisen.service.format.OldDataFiles;

/**
 * Implementation for the file importer.
 * @author Alex
 */
public class FileImporter implements IFileImporter {
	/** Logger */
	private static Logger		lg	= Logger.getLogger(FileImporter.class.getName());
	
	/** The member DAO */
	private final DAO<Member>	memberDAO;
	/** The set with the file format */
	private HashSet<Format>		fileFormat;
	
	/**
	 * Constructor #1.<br />
	 * @param memberDAO
	 *        the DAO for the members.
	 */
	public FileImporter (final DAO<Member> memberDAO) {
		super();
		this.memberDAO = memberDAO;
		createFormat();
	}
	
	/**
	 * Create and load available file format.
	 */
	private void createFormat () {
		this.fileFormat = new HashSet<>();
		fileFormat.add(OldDataFiles.getInstance());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IFileImporter#parseFile(java.nio.file.Path,
	 * org.clubrockisen.service.abstracts.Format)
	 */
	@Override
	public void parseFile (final Path file, final Format format) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IFileImporter#getAvailableFormat()
	 */
	@Override
	public Set<Format> getAvailableFormat () {
		if (fileFormat == null) {
			createFormat();
		}
		return fileFormat;
	}
}
