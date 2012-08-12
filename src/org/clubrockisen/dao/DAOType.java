package org.clubrockisen.dao;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.mysql.MySQLDAOFactory;

/**
 * The enumeration for the DAO types which can be used.
 * @author Alex
 */
public enum DAOType {
	/** Access to the database through a MySQL connection */
	MYSQL("MySQL", MySQLDAOFactory.class);
	
	/** The name of the DAO type */
	private final String								name;
	/** The factory class of the DAO Type */
	private final Class<? extends AbstractDAOFactory>	factory;
	
	/**
	 * Constructor #1.<br />
	 * @param name
	 *        the name of the DAO type.
	 * @param factory
	 *        the factory class of the DAO type.
	 */
	private DAOType (final String name, final Class<? extends AbstractDAOFactory> factory) {
		this.name = name;
		this.factory = factory;
	}
	
	/**
	 * Return the attribute name.
	 * @return the attribute name.
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * Return the attribute factory.
	 * @return the attribute factory.
	 */
	public Class<? extends AbstractDAOFactory> getFactory () {
		return factory;
	}
	
	/**
	 * Return the matching DAO for the name passed.
	 * @param name
	 *        the name to look for.
	 * @return the matching DAO type.
	 */
	public static DAOType fromValue (final String name) {
		try {
			// First, use the default JDK method
			return Enum.valueOf(DAOType.class, name);
		} catch (final IllegalArgumentException e) {
			// If failed, try to match the name of an enumeration
			for (final DAOType currentDAO : DAOType.values()) {
				if (currentDAO.getName().equalsIgnoreCase(name)) {
					return currentDAO;
				}
			}
			throw new IllegalArgumentException("No DAO Type with name '" + name + "'.", e);
		}
	}
}