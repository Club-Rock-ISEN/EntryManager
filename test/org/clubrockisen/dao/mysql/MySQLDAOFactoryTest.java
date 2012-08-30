package org.clubrockisen.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.exception.DAOInstantiationError;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link MySQLDAOFactory} class.
 * @author Alex
 */
public class MySQLDAOFactoryTest {
	/** The factory */
	private AbstractDAOFactory	factory;
	/** A dummy class name which is not a factory */
	private String dummyClass;
	/** A fake class name which is not a class */
	private String fakeClass;
	
	/**
	 * Set up the attribute required by the tests.
	 */
	@Before
	public void setUp () {
		AbstractDAOFactory.createFactory(MySQLDAOFactory.class.getName());
		factory = AbstractDAOFactory.getImplementation();
		dummyClass = String.class.getName();
		fakeClass = "a";
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLDAOFactory#getParameterDAO()}.
	 */
	@Test
	public void testGetParameterDAO () {
		assertNotNull(factory.getParameterDAO());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLDAOFactory#getMemberDAO()}.
	 */
	@Test
	public void testGetMemberDAO () {
		assertNotNull(factory.getMemberDAO());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLDAOFactory#getPartyDAO()}.
	 */
	@Test
	public void testGetPartyDAO () {
		assertNotNull(factory.getPartyDAO());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLDAOFactory#getEntryMemberPartyDAO()}.
	 */
	@Test
	public void testGetEntryMemberPartyDAO () {
		assertNotNull(factory.getParameterDAO());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLDAOFactory#close()}.
	 */
	@Test
	public void testClose () {
		try {
			factory.close();
			assertEquals(0, factory.getParameterDAO().retrieveAll().size());
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.abstracts.AbstractDAOFactory#createFactory(java.lang.String)}.
	 */
	@Test(expected=DAOInstantiationError.class)
	public void testCreateFactoryNotDAO () {
		AbstractDAOFactory.createFactory(dummyClass);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.abstracts.AbstractDAOFactory#createFactory(java.lang.String)}.
	 */
	@Test(expected=DAOInstantiationError.class)
	public void testCreateFactoryNotClass () {
		AbstractDAOFactory.createFactory(fakeClass);
	}
}
