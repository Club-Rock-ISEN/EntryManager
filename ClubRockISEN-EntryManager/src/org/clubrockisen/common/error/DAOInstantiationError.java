package org.clubrockisen.common.error;


/**
 * Error when using a DAO Type which is not yet implemented.
 * @author Alex
 */
public class DAOInstantiationError extends TopLevelError {
	/** Serial version UID */
	private static final long	serialVersionUID	= -5713611039948550967L;
	
	/** The DAO type which is not instantiated */
	private final String		daoFactory;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor. The use of {@link #DAOInstantiationError(String) other constructor}
	 * is preferred.
	 */
	public DAOInstantiationError () {
		this(null);
	}
	
	/**
	 * Constructor #1.<br />
	 * Build an error with the type which is not implemented.
	 * @param daoFactory
	 *        the type of DAO which is not implemented.
	 */
	public DAOInstantiationError (final String daoFactory) {
		this(daoFactory, null);
	}
	
	/**
	 * Constructor #2.<br />
	 * @param daoFactory
	 *        the type of DAO which is not implemented.
	 * @param cause
	 *        the exception thrown in the first place.
	 */
	public DAOInstantiationError (final String daoFactory, final Throwable cause) {
		super("The DAO factory class " + daoFactory + " is not implemented.", cause);
		this.daoFactory = daoFactory;
	}
	
	/**
	 * Return the attribute factory class.
	 * @return the factory class.
	 */
	public String getDAOFactory () {
		return daoFactory;
	}
	
}
