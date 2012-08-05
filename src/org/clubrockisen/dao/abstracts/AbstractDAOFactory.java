package org.clubrockisen.dao.abstracts;

import java.util.logging.Logger;

import org.clubrockisen.dao.MySQLDAOFactory;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;

/**
 * The abstract factory for the {@link DAO}.<br />
 * Define the different DAO object that should be created for a complete access to the data.
 * @author Alex
 */
public abstract class AbstractDAOFactory {
	/** Logger */
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
	 *            the type of DAO required.
	 * @return the factory to use for creating the DAO objects.
	 */
	public static AbstractDAOFactory getFactory (final DAOType type) {
		switch (type) {
			case MYSQL:
				return new MySQLDAOFactory();
		}
		lg.severe("Unimplemented DAO type (" + type + ").");
		throw new Error("DAO of type " + type + " is not implemented.");
	}
}
