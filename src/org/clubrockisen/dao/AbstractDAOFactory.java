package org.clubrockisen.dao;

import java.util.logging.Logger;

import org.clubrockisen.entities.Member;

/**
 * The abstract factory for the DAO.
 * @author Alex
 */
public abstract class AbstractDAOFactory {
	private static Logger	lg	= Logger.getLogger(AbstractDAOFactory.class.getName());

	/**
	 * The enumeration for the DAO types.
	 * @author Alex
	 */
	public enum DAOType {
		/**
		 * Access to the database through a MySQL connection.
		 */
		MYSQL
	}

	/**
	 * Retrieve a DAO for the member class.
	 * @return the DAO for the member class.
	 */
	public abstract DAO<Member> getMemberDAO ();

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
