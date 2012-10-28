package org.clubrockisen.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.Constants;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.service.abstracts.Format;
import org.clubrockisen.service.abstracts.Format.Converter;
import org.clubrockisen.service.abstracts.IFileManager;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.service.format.OldDataFiles;


/**
 * Implementation for the file manager.
 * @author Alex
 */
public class FileManager implements IFileManager {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(FileManager.class.getName());
	
	/** The member DAO */
	private final DAO<Member>			memberDAO;
	/** The set with the file format */
	private Set<Format>					fileFormats;
	
	/**
	 * Constructor #1.<br />
	 * @param memberDAO
	 *        the DAO for the members.
	 */
	public FileManager (final DAO<Member> memberDAO) {
		super();
		this.memberDAO = memberDAO;
		createFormat();
	}
	
	/**
	 * Create and load available file format.
	 */
	private void createFormat () {
		this.fileFormats = new HashSet<>();
		fileFormats.add(OldDataFiles.getInstance());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IFileImporter#parseFile(java.nio.file.Path,
	 * org.clubrockisen.service.abstracts.Format)
	 */
	@Override
	public Integer parseFile (final Path file, final Format format) {
		if (file == null || !Files.exists(file) || !Files.isReadable(file)) {
			lg.warning("Cannot parse file " + file + " because it does not exists or cannot be read.");
			return null;
		}
		
		int membersPersisted = 0;
		try {
			// Parse file
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Parsing of file " + file + " (size: " + Files.size(file) + ") in " + format);
			}
			final List<Member> membersToAdd = loadMembers(file, format);
			
			// Persist members
			for (final Member member : membersToAdd) {
				if (memberDAO.create(member) != null) {
					membersPersisted++;
				} else {
					lg.warning("Error while creating member " + member);
				}
			}
			
			// Checking for errors.
			if (membersPersisted != membersToAdd.size()) {
				lg.warning("Error, could not create " + (membersToAdd.size() - membersPersisted)
						+ " members.");
			} else if (lg.isLoggable(Level.INFO)) {
				lg.info("Successfully persisted " + membersPersisted + " members.");
			}
		} catch (final IOException e) {
			lg.warning("Failed to parse file " + file + ", " + e.getClass() + "; " + e.getMessage());
			return null;
		}
		return membersPersisted;
	}
	
	/**
	 * Parse a file and load the members it contains.<br />
	 * @param file
	 *        the file to parse.
	 * @param format
	 *        the format of the file.
	 * @return the list with the members extracted from the file specified.
	 * @throws IOException
	 *         if the file could not be read properly.
	 */
	private static List<Member> loadMembers (final Path file, final Format format)
			throws IOException {
		final List<Member> membersToAdd = new ArrayList<>();
		// Retrieve charset to use
		final String userCharSet = ServiceFactory.getImplementation().getParameterManager().get(ParametersEnum.FILE_CHARSET).getValue();
		Charset charSet = null;
		try {
			charSet = Charset.forName(userCharSet);
		} catch (final UnsupportedCharsetException | IllegalCharsetNameException e) {
			lg.warning("Could not use charset " + userCharSet + ": " + e.getClass() + "; " + e.getMessage());
			charSet = Constants.DEFAULT_CHARSET;
		}
		// Read lines in file
		final List<String> lines = Files.readAllLines(file, charSet);
		for (final String line : lines) {
			final String[] members = line.split(format.getMemberSeparator());
			for (final String member : members) {
				final Member memberToAdd = createMember(member, format);
				if (memberToAdd != null) {
					if (lg.isLoggable(Level.FINE)) {
						lg.fine("Adding member " + memberToAdd.printDetails() + " from " + member);
					}
					membersToAdd.add(memberToAdd);
				}
			}
		}
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Successfully parsed " + membersToAdd.size() + " members.");
		}
		return membersToAdd;
	}
	
	/**
	 * Create a member from the string data provided using the format specified.
	 * @param data
	 *        the data to read
	 * @param format
	 *        the format to use
	 * @return the new member or <code>null</code> if parsing failed.
	 */
	private static Member createMember (final String data, final Format format) {
		final Member member = new Member();
		
		final String[] fields = data.split(format.getFieldSeparator());
		
		for (int i = 0; i < fields.length; i++) {
			final Converter converter = format.getFieldOrder().get(i);
			if (converter == null) {
				continue;
			}
			// Getting the value of the current field and building the setter name
			final Object fieldValue = converter.convert(fields[i]);
			final String methodName = "set" + converter.getField().getPropertyName();
			try {
				final Method method = Member.class.getMethod(methodName, new Class<?>[] { fieldValue.getClass() });
				method.invoke(member, fieldValue);
			} catch (SecurityException | NoSuchMethodException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				lg.warning("Error while calling method " + methodName + " in member class (" +
						e.getClass() + "; " + e.getMessage() + ")");
				return null;
			}
		}
		
		return member;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IFileManager#getAvailableFormat()
	 */
	@Override
	public Set<Format> getAvailableFormat () {
		if (fileFormats == null) {
			createFormat();
		}
		return fileFormats;
	}
}
