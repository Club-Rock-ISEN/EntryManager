package org.clubrockisen.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.service.abstracts.IParametersManager;

/**
 * Manager for the parameters.<br />
 * ParametersEnum should be used through this class instead of the {@link DAO} class as it provide
 * easier access and handling.
 * @author Alex
 */
public final class ParametersManager implements IParametersManager {
	/** Logger */
	private static Logger					lg			= Logger.getLogger(ParametersManager.class.getName());
	
	/** Unique instance of the manager */
	private static ParametersManager		singleton	= null;
	
	/** The access to the parameters */
	private final DAO<Parameter>			dao;
	/**
	 * The map between the parameters (registered in the {@link ParametersEnum}) and the concrete
	 * parameter from the database
	 */
	private Map<ParametersEnum, Parameter>	parameters;
	
	/**
	 * Constructor #1.<br />
	 * Build the instance of the class.
	 * @param daoFactory
	 *        the factory for the DAO
	 */
	private ParametersManager(final AbstractDAOFactory daoFactory) {
		lg.info("Building singleton for " + this.getClass().getName());
		this.dao = daoFactory.getParameterDAO();
		loadParameters();
	}
	
	/**
	 * Create the parameter manager.
	 * @param daoFactory
	 *        the DAO factory to use.
	 */
	public static void create (final AbstractDAOFactory daoFactory) {
		singleton = new ParametersManager(daoFactory);
	}
	
	/**
	 * Returns the instance of the {@link ParametersManager}.<br />
	 * Ensure to {@link #create(AbstractDAOFactory)} the instance before calling this method.
	 * @return the instance of the parameter manager.
	 */
	public static ParametersManager getInstance () {
		return singleton;
	}
	
	/**
	 * Load the parameters into the map.
	 */
	private void loadParameters () {
		parameters = new EnumMap<>(ParametersEnum.class);
		final List<Parameter> dbParameters = dao.retrieveAll();
		
		for (final Parameter parameter : dbParameters) {
			parameters.put(ParametersEnum.fromValue(parameter.getName()), parameter);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.services.interfaces.IParametersManager#get(org.clubrockisen.services.
	 * ParametersEnum)
	 */
	@Override
	public Parameter get (final ParametersEnum parameter) {
		return parameters.get(parameter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.services.interfaces.IParametersManager#set(org.clubrockisen.entities.Parameter)
	 */
	@Override
	public boolean set (final Parameter parameter) {
		if (dao.update(parameter)) {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Parameter " + parameter.getName() + " updated successfully.");
			}
			return true;
		}
		lg.warning("Parameter " + parameter.getName() + " was not updated.");
		return false;
	}
}
