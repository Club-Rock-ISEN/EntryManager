package org.clubrockisen.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.service.abstracts.Format;
import org.clubrockisen.service.abstracts.Format.Converter;
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
	private Set<Format>			fileFormats;
	
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
		this.fileFormats = new HashSet<>();
		fileFormats.add(OldDataFiles.getInstance());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IFileImporter#parseFile(java.nio.file.Path,
	 * org.clubrockisen.service.abstracts.Format)
	 */
	@Override
	public boolean parseFile (final Path file, final Format format) {
		if (file == null || !Files.exists(file) || !Files.isReadable(file)) {
			lg.warning("Cannot parse file " + file + " because it does not exists or cannot be read.");
			return false;
		}
		try {
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Parsing of file " + file + " (size: " + Files.size(file) + ") in " + format);
			}
			final List<Member> membersToAdd = new ArrayList<>();
			// TODO parametrize charset
			final List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
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
			int membersPersisted = 0;
			for (final Member member : membersToAdd) {
				if (memberDAO.create(member) != null) {
					membersPersisted++;
				} else {
					lg.warning("Error while creating member " + member);
				}
			}
			if (membersPersisted != membersToAdd.size()) {
				lg.warning("Error, could not create " + (membersToAdd.size() - membersPersisted)
						+ " members.");
			} else if (lg.isLoggable(Level.INFO)) {
				lg.info("Successfully persisted " + membersPersisted + " members.");
			}
		} catch (final IOException e) {
			lg.warning("Failed to parse file " + file + ", " + e.getClass() + "; " + e.getMessage());
			return false;
		}
		return true;
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
	 * @see org.clubrockisen.service.abstracts.IFileImporter#getAvailableFormat()
	 */
	@Override
	public Set<Format> getAvailableFormat () {
		if (fileFormats == null) {
			createFormat();
		}
		return fileFormats;
	}
}
