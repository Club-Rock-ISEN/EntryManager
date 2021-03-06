package org.clubrockisen.service.abstracts;

import java.util.logging.Logger;

import org.clubrockisen.common.ConfigurationKeys;
import org.clubrockisen.common.error.ServiceInstantiationError;
import org.clubrockisen.dao.abstracts.EntryManagerAbstractDAOFactory;
import org.clubrockisen.service.Translator;

import com.alexrnl.commons.utils.Configuration;

/**
 * Factory for the services of the application.<br />
 * @author Alex
 */
public abstract class ServiceFactory {
	/** Logger */
	private static Logger							lg	= Logger.getLogger(ServiceFactory.class.getName());
	
	/** Implementation of the factory to be used */
	private static ServiceFactory					implementation;
	/** The daoFactory used to communicate with the database */
	private static EntryManagerAbstractDAOFactory	daoFactory;
	/** The translation file to use */
	private static String							translationFile;
	
	/**
	 * Return the implementation of the factory to be used.
	 * @return the concrete implementation
	 */
	public static ServiceFactory getImplementation () {
		return implementation;
	}
	
	/**
	 * Static method used to load the implementation to use in the application.
	 * @param config
	 *        the configuration of the program.
	 * @param emDAOFactory
	 *        the DAO factory to use.
	 */
	public static void createFactory (final Configuration config, final EntryManagerAbstractDAOFactory emDAOFactory) {
		final String serviceFactory = config.get(ConfigurationKeys.KEY.serviceFactory());
		translationFile = config.get(ConfigurationKeys.KEY.translationFile());
		ServiceFactory.daoFactory = emDAOFactory;
		try {
			implementation = Class.forName(serviceFactory).asSubclass(ServiceFactory.class).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			lg.severe("Cannot instantiate service factory class (" + config + "). "
					+ e.getClass() + ", details: " + e.getMessage());
			throw new ServiceInstantiationError(serviceFactory, e);
		}
	}
	
	/**
	 * Get the translation file set during the creation of the implementation.
	 * @return the translation file to use.
	 */
	protected String getTranslationFile () {
		return translationFile;
	}
	
	/**
	 * Return the attribute daoFactory.
	 * @return the attribute daoFactory.
	 */
	public static EntryManagerAbstractDAOFactory getDaoFactory () {
		return daoFactory;
	}

	/**
	 * Return the parameter manager to use.
	 * @return the parameter manager.
	 */
	public abstract IParametersManager getParameterManager ();
	
	/**
	 * Return the translator to use.
	 * @return the translator.
	 */
	public abstract Translator getTranslator ();
	
	/**
	 * Return the entry manager to use.
	 * @return the entry manager.
	 */
	public abstract IEntryManager getEntryManager ();
	
	/**
	 * Return the file manager to use
	 * @return the file manager.
	 */
	public abstract IFileManager getFileManager ();
}
