package org.clubrockisen.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.Constants;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.service.abstracts.Converter;
import org.clubrockisen.service.abstracts.Format;
import org.clubrockisen.service.abstracts.IFileManager;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.service.format.OldDataFiles;

import com.alexrnl.commons.database.DAO;
import com.alexrnl.commons.utils.object.ReflectUtils;


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
	private final Set<Format>			fileFormats;
	
	/**
	 * Constructor #1.<br />
	 * @param memberDAO
	 *        the DAO for the members.
	 */
	public FileManager (final DAO<Member> memberDAO) {
		super();
		this.memberDAO = memberDAO;
		fileFormats = Collections.unmodifiableSet(createFormat());
	}
	
	/**
	 * Create and load available file format.
	 * @return a set with the available format.
	 */
	private static Set<Format> createFormat () {
		final Set<Format> fileFormats = new HashSet<>();
		fileFormats.add(OldDataFiles.getInstance());
		return fileFormats;
	}
	
	/**
	 * Retrieve and load the charset specified in the parameter by the user.<br />
	 * If the charset is invalid, or not supported, the default charset is loaded (specified in
	 * {@link Constants}).
	 * @return the {@link Charset} to use.
	 */
	private static Charset getCharset () {
		Charset charSet;
		// Retrieve charset to use
		final String userCharset = ServiceFactory.getImplementation().getParameterManager()
				.get(ParametersEnum.FILE_CHARSET).getValue();
		try {
			charSet = Charset.forName(userCharset);
		} catch (final UnsupportedCharsetException | IllegalCharsetNameException e) {
			lg.warning("Could not use charset " + userCharset + " (default will be used): " +
					e.getClass() + "; " + e.getMessage());
			charSet = Constants.DEFAULT_CHARSET;
		}
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Using charset " + charSet.name());
		}
		return charSet;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IFileManager#getAvailableFormat()
	 */
	@Override
	public Set<Format> getAvailableFormat () {
		return fileFormats;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IFileImporter#parseFile(java.nio.file.Path,
	 * org.clubrockisen.service.abstracts.Format)
	 */
	@Override
	public Integer parseFile (final Path file, final Format format) {
		if (file == null || !Files.exists(file)) {
			lg.warning("Cannot parse file " + file + " because it does not exists.");
			return null;
		}
		if (!Files.isReadable(file)) {
			lg.warning("Cannot parse file " + file + " because it cannot be read.");
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
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.IFileManager#exportFile(java.nio.file.Path,
	 * org.clubrockisen.service.abstracts.Format)
	 */
	@Override
	public boolean exportFile (final Path file, final Format format) {
		if (file == null || Files.isDirectory(file)) { // Avoid deleting a lot of data
			lg.warning("Could not export file " + file  + " because it is a directory or the " +
					"file specified was null.");
			return false;
		}
		
		// Deleting file before writing, to be safe
		try {
			if (Files.exists(file)) {
				lg.warning("File " + file + "already exists, it will be deleted.");
				Files.delete(file);
			}
		} catch (final IOException e) {
			lg.warning("Error while deleting file " + file + ", (" + e.getClass() + "; " + e.getMessage());
			return false;
		}
		
		try (BufferedWriter writer = Files.newBufferedWriter(file, getCharset())) {
			for (final Member member : memberDAO.retrieveAll()) {
				final String memberString = createMember(member, format);
				if (memberString != null) {
					writer.write(memberString);
					writer.write(format.getEntitySeparator());
				} else {
					lg.warning("Could not write member " + member);
				}
			}
		} catch (final IOException e) {
			lg.warning("Error while writing members in file " + file + ", " +
					e.getClass() + "; " + e.getMessage());
			return false;
		}
		
		return true;
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
		
		// Read lines in file
		final List<String> lines = Files.readAllLines(file, getCharset());
		for (final String line : lines) {
			final String[] members = line.split(format.getEntitySeparator());
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
	 *        the data to read.
	 * @param format
	 *        the format to use.
	 * @return the new member or <code>null</code> if parsing failed.
	 */
	private static Member createMember (final String data, final Format format) {
		final Member member = new Member();
		
		final String[] fields = data.split(format.getFieldSeparator());
		
		for (int i = 0; i < fields.length; i++) {
			final Converter<MemberColumn> converter = format.getFieldOrder().get(i);
			if (converter == null) {
				continue;
			}
			// Getting the value of the current field and building the setter name
			final Object fieldValue = converter.read(fields[i]);
			final String methodName = ReflectUtils.SETTER_PREFIX + converter.getField().getFieldName();
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
	
	/**
	 * Create the string data that represent the member specified.
	 * @param member
	 *        the member to write as a string, in the format specified.
	 * @param format
	 *        the format to use.
	 * @return the new member or <code>null</code> if parsing failed.
	 */
	private static String createMember (final Member member, final Format format) {
		final StringBuilder strRepresentation = new StringBuilder();
		
		for (final Converter<MemberColumn> converter : format.getFieldOrder()) {
			if (converter != null) {
				final Object data;
				// TODO add a case for boolean with the is prefix
				final String methodName = ReflectUtils.GETTER_PREFIX + converter.getField().getFieldName();
				try {
					final Method method = Member.class.getMethod(methodName);
					data = method.invoke(member);
				} catch (SecurityException | NoSuchMethodException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					lg.warning("Error while calling method " + methodName + " in member class (" +
							e.getClass() + "; " + e.getMessage() + ")");
					return null;
				}
				
				strRepresentation.append(converter.write(data));
			}
			strRepresentation.append(format.getFieldSeparator());
		}
		
		return strRepresentation.toString();
	}
}
