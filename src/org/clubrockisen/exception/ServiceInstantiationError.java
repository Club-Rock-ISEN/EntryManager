package org.clubrockisen.exception;

/**
 * Error when using a service factory which is not yet implemented.
 * @author Alex
 */
public class ServiceInstantiationError extends TopLevelError {
	/** Serial Version UID */
	private static final long	serialVersionUID	= 6135003499334666620L;
	
	/** The class which provoke the error */
	private final String		serviceFactory;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor. The use of {@link #ServiceInstantiationError(String) other constructor}
	 * is preferred.
	 */
	public ServiceInstantiationError () {
		this(null);
	}
	
	/**
	 * Constructor #2.<br />
	 * Build an error with the service class name that could not be implemented.
	 * @param serviveFactory
	 *        the factory class name which could not be loaded.
	 */
	public ServiceInstantiationError (final String serviveFactory) {
		this(serviveFactory, null);
	}
	
	/**
	 * Constructor #3.<br />
	 * @param serviceFactory
	 *        the factory class name which could not be loaded.
	 * @param cause
	 *        the cause of this exception.
	 */
	public ServiceInstantiationError (final String serviceFactory, final Throwable cause) {
		super("The service factory class " + serviceFactory + " is not implemented.", cause);
		this.serviceFactory = serviceFactory;
	}
	
	/**
	 * Return the attribute serviceFactory.
	 * @return the attribute serviceFactory.
	 */
	public String getServiceFactory () {
		return serviceFactory;
	}
	
}
