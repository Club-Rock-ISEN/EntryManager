package org.clubrockisen.dao.mysql;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test suite for the {@link MySQLParameterDAO} class.
 * @author Alex
 */
public class MySQLParameterDAOTest {
	/** The factory */
	private static MySQLDAOFactory	factory;
	/** The DAO for the parameters */
	private MySQLParameterDAO		parameterDAO;
	
	/**
	 * Create the factory.
	 */
	@BeforeClass
	public static void setUpBeforeClass () {
		factory = new MySQLDAOFactory();
	}
	
	/**
	 * Close the resources.
	 */
	@AfterClass
	public static void tearDownAfterClass () {
		try {
			factory.close();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Set up the attribute required for testing.
	 */
	@Before
	public void setUp () {
		parameterDAO = (MySQLParameterDAO) factory.getParameterDAO();
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLParameterDAO#getEntitySample()}.
	 */
	@Test
	public void testGetEntitySample () {
		assertNotNull(parameterDAO.getEntitySample());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLParameterDAO#create(org.clubrockisen.entities.Parameter)}.
	 */
	@Test
	public void testCreate () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLParameterDAO#find(int)}.
	 */
	@Test
	public void testFindInt () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLParameterDAO#update(org.clubrockisen.entities.Parameter)}.
	 */
	@Test
	public void testUpdate () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLParameterDAO#delete(org.clubrockisen.entities.Parameter)}.
	 */
	@Test
	public void testDelete () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLDAO#retrieveAll()}.
	 */
	@Test
	public void testRetrieveAll () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLDAO#search(org.clubrockisen.entities.Column, java.lang.String)}.
	 */
	@Test
	public void testSearch () {
		fail("Not yet implemented"); // TODO
	}
}
