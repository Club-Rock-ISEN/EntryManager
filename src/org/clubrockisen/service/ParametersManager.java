package org.clubrockisen.service;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.EntryManagerAbstractDAOFactory;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ParametersEnum;

import com.alexrnl.commons.database.DAO;

/**
 * Manager for the parameters.<br />
 * ParametersEnum should be used through this class instead of the {@link DAO} class as it provide
 * easier access and handling.
 * @author Alex
 */
public final class ParametersManager implements IParametersManager {
	/** Logger */
	private static Logger					lg			= Logger.getLogger(ParametersManager.class.getName());
	
	/** The access to the parameters */
	private final DAO<Parameter>			dao;
	/**
	 * The map between the parameters (registered in the {@link ParametersEnum}) and the concrete
	 * parameter from the database
	 */
	private final Map<ParametersEnum, Parameter>	parameters;
	
	/**
	 * Constructor #1.<br />
	 * Build the instance of the class.
	 * @param daoFactory
	 *        the factory for the DAO
	 */
	public ParametersManager(final EntryManagerAbstractDAOFactory daoFactory) {
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Building singleton for " + this.getClass().getName());
		}
		this.dao = daoFactory.getParameterDAO();
		parameters = new EnumMap<>(ParametersEnum.class);
		final Set<Parameter> dbParameters = dao.retrieveAll();
		
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
			// Replacing the value in the map, to be sure
			parameters.put(ParametersEnum.fromValue(parameter.getName()), parameter);
			return true;
		}
		lg.warning("Parameter " + parameter.getName() + " was not updated.");
		return false;
	}
}
