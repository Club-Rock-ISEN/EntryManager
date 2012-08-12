package org.clubrockisen.dao.abstracts;

import org.clubrockisen.dao.DAOType;

/**
 * Error when using a DAO Type which is not yet implemented.
 * @author Alex
 */
public class DAOInstantiationException extends Error {
	/** Serial version UID */
	private static final long	serialVersionUID	= -705590220302237397L;
	
	/** The DAO type which is not instantiated */
	private final DAOType		daoType;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor. The use of {@link #DAOInstantiationException(DAOType) other constructor}
	 * is preferred.
	 */
	public DAOInstantiationException () {
		this(null);
	}
	
	/**
	 * Constructor #2.<br />
	 * Build an error with the type which is not implemented.
	 * @param type
	 *        the type of DAO which is not implemented.
	 */
	public DAOInstantiationException (final DAOType type) {
		this(type, null);
	}
	
	/**
	 * Constructor #.<br />
	 * @param type
	 *        the type of DAO which is not implemented.
	 * @param cause
	 *        the exception thrown in the first place.
	 */
	public DAOInstantiationException (final DAOType type, final Throwable cause) {
		super("The DAO of type " + type + " is not implemented.", cause);
		this.daoType = type;
	}
	
	/**
	 * Return the attribute daoType.
	 * @return the attribute daoType.
	 */
	public DAOType getDaoType () {
		return daoType;
	}
	
}
