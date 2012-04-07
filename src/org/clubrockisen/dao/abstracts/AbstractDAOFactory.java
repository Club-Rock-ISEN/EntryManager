package org.clubrockisen.dao.abstracts;

import java.util.logging.Logger;

import org.clubrockisen.dao.MySQLDAOFactory;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;

/**
 * The abstract factory for the {@link DAO}.
 * @author Alex
 */
public abstract class AbstractDAOFactory {
	private static Logger	lg	= Logger.getLogger(AbstractDAOFactory.class.getName());
	
	/**
	 * The enumeration for the {@link DAO} types.
	 * @author Alex
	 */
	public enum DAOType {
		/**
		 * Access to the database through a MySQL connection.
		 */
		MYSQL
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
	public abstract DAO<EntryMemberParty> getEntryMemberPartyDAO();
	
	/**
	 * Retrieve and create the appropriate factory.
	 * @param type
	 *            the type of DAO you want.
	 * @return the factory to use for creating the DAO objects.
	 * @throws InstantiationException
	 *             if the type sent has no implementation.
	 */
	public static AbstractDAOFactory getFactory (final DAOType type)
			throws InstantiationException {
		switch (type) {
			case MYSQL:
				return new MySQLDAOFactory();
		}
		lg.severe("Unimplemented DAO type (" + type + ").");
		throw new InstantiationException("Unimplemented DAO type: " + type);
	}
}
