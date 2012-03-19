package org.clubrockisen.services;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.dao.AbstractDAOFactory;
import org.clubrockisen.dao.DAO;
import org.clubrockisen.entities.Parameter;

/**
 * Manager for the parameters.<br />
 * ParametersEnum should be used through this class instead of the {@link DAO} class as it provide
 * easier access and handling.
 * @author Alex
 */
public final class ParametersManager {
	private static Logger				lg			= Logger.getLogger(ParametersManager.class
															.getName());

	private static ParametersManager	singleton	= null;

	private final DAO<Parameter>		dao;
	private Map<ParametersEnum, Parameter>		parameters;

	private ParametersManager(final AbstractDAOFactory daoFactory) {
		lg.info("Building singleton for " + this.getClass().getName());
		this.dao = daoFactory.getParameterDAO();
		loadParameters();
	}

	/**
	 * Create the parameter manager.
	 * @param daoFactory the DAO factory to use.
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
	
	/**
	 * Return the specified parameter.
	 * @param parameter the parameter to retrieve.
	 * @return the
	 */
	public Parameter get (final ParametersEnum parameter) {
		return parameters.get(parameter);
	}
	
	/**
	 * Update the value and/or the type of the parameter.<br />
	 * Do not update the name of the parameter.
	 * @param parameter the parameter to update
	 */
	public void set (final Parameter parameter) {
		if (dao.update(parameter)) {
			lg.fine("Parameter " + parameter.getName() + " updated successfully.");
		} else {
			lg.warning("Parameter " + parameter.getName() + " was not updated.");
		}
			
	}
}
