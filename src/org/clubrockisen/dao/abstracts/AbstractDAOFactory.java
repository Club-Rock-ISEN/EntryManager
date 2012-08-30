package org.clubrockisen.dao.abstracts;

import java.io.Closeable;
import java.util.logging.Logger;

import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;
import org.clubrockisen.exception.DAOInstantiationError;

/**
 * The abstract factory for the {@link DAO}s.<br />
 * Define the different DAO objects that should be created for a complete access to the database.
 * @author Alex
 */
public abstract class AbstractDAOFactory implements Closeable {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(AbstractDAOFactory.class.getName());
	
	/** Implementation of the factory to be used */
	private static AbstractDAOFactory	implementation;
	
	/**
	 * Return the implementation of the factory to be used.
	 * @return the concrete implementation
	 */
	public static AbstractDAOFactory getImplementation () {
		return implementation;
	}
	
	/**
	 * Retrieve and create the appropriate factory.
	 * @param factoryClass
	 *        the type of DAO required.
	 */
	public static void createFactory (final String factoryClass) {
		try {
			implementation = (AbstractDAOFactory) Class.forName(factoryClass).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
				| ClassCastException e) {
			lg.severe("Cannot instantiate DAO factory class (" + factoryClass + "). "
					+ e.getClass() + ", details: " + e.getMessage());
			throw new DAOInstantiationError(factoryClass, e);
		}
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
