package org.clubrockisen.common.error;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test suite for the {@link DAOInstantiationError} class.
 * @author Alex
 */
public class DAOInstantiationErrorTest {
	
	/** Exception with an unknown DAO */
	private final DAOInstantiationError unknownDAO = new DAOInstantiationError();
	/** Exception with a known DAO class */
	private final DAOInstantiationError knownDAO  = new DAOInstantiationError("org.clubrockisen.dao.sql");
	
	/**
	 * Test method for {@link org.clubrockisen.common.error.DAOInstantiationError#getDAOFactory()}.
	 */
	@Test
	public void testGetDAOFactory () {
		assertNull(unknownDAO.getDAOFactory());
		assertEquals("org.clubrockisen.dao.sql", knownDAO.getDAOFactory());
	}
	
	/**
	 * Test method for {@link java.lang.Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertEquals("The DAO factory class null is not implemented.", unknownDAO.getMessage());
		assertEquals("The DAO factory class org.clubrockisen.dao.sql is not implemented.", knownDAO.getMessage());
	}
}
