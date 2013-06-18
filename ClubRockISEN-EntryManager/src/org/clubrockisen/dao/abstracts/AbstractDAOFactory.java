package org.clubrockisen.dao.abstracts;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

import org.clubrockisen.common.ConfigurationKeys;
import org.clubrockisen.dao.Utils.DBConnectionInfo;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;

import com.alexrnl.commons.database.DAOInstantiationError;
import com.alexrnl.commons.utils.Configuration;

/**
 * The abstract factory for the {@link DAO}s.<br />
 * Define the different DAO objects that should be created for a complete access to the database.
 * @author Alex
 */
public abstract class AbstractDAOFactory implements Closeable {
	/** Logger */
	private static Logger					lg		= Logger.getLogger(AbstractDAOFactory.class.getName());
	
	/** The configuration keys */
	private static final ConfigurationKeys	KEYS	= ConfigurationKeys.KEY;
	
	/** Implementation of the factory to be used */
	private static AbstractDAOFactory		implementation;
	/** The connection info to be used by the implementations */
	private static DBConnectionInfo			dbConnectionInfo;
	/** The creation file for the database */
	private static String					dbCreationFile;

	/**
	 * Return the implementation of the factory to be used.
	 * @return the concrete implementation
	 */
	public static AbstractDAOFactory getImplementation () {
		return implementation;
	}
	
	/**
	 * Retrieve and create the appropriate factory.
	 * @param config
	 *        the type of DAO required.
	 */
	public static synchronized void createFactory (final Configuration config) {
		final String daoClass = config.get(KEYS.daoFactory());
		dbConnectionInfo = new DBConnectionInfo(config.get(KEYS.db().url()),
				config.get(KEYS.db().username()),
				config.get(KEYS.db().password()));
		dbCreationFile = config.get(KEYS.db().creationFile());
		try {
			if (implementation != null) {
				try {
					implementation.close();
				} catch (final IOException e) {
					lg.warning("Could not close previous implementation (" + e.getMessage() + ")");
				}
			}
			implementation = Class.forName(daoClass).asSubclass(AbstractDAOFactory.class).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
				| ClassCastException e) {
			lg.severe("Cannot instantiate DAO factory class (" + config + "). "
					+ e.getClass() + ", details: " + e.getMessage());
			throw new DAOInstantiationError(daoClass, e);
		}
	}
	
	/**
	 * Return the database connection informations.
	 * @return the connection informations.
	 */
	protected DBConnectionInfo getDBConnectionInfo () {
		return dbConnectionInfo;
	}
	
	/**
	 * The DB creation file.
	 * @return the creation file for the database, in case the db does not exists.
	 */
	protected String getCreationFile () {
		return dbCreationFile;
	}
	
	/**
	 * Retrieve a {@link DAO} for the {@link Parameter} class.
	 * @return the DAO for the parameter class.
	 */
	public abstract DAO<Parameter> getParameterDAO ();
	
	/**
	 * Retrieve a {@link DAO} for the {@link Member} class.
	 * @return the DAO for the member class.
	 */
	public abstract DAO<Member> getMemberDAO ();
	
	/**
	 * Retrieve a {@link DAO} for the {@link Party} class.
	 * @return the DAO for the party class.
	 */
	public abstract DAO<Party> getPartyDAO ();
	
	/**
	 * Retrieve a {@link DAO} for the {@link EntryMemberParty} class.
	 * @return the DAO for the entries class.
	 */
	public abstract DAO<EntryMemberParty> getEntryMemberPartyDAO ();
}
