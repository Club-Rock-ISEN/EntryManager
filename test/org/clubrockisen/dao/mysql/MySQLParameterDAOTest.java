package org.clubrockisen.dao.mysql;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Parameter.ParameterColumn;
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
	/** Backup for the parameters */
	private static List<Parameter>	parametersBackup;
	/** The DAO for the parameters */
	private MySQLParameterDAO		parameterDAO;
	
	/**
	 * Create the factory.
	 */
	@BeforeClass
	public static void setUpBeforeClass () {
		factory = new MySQLDAOFactory();
		parametersBackup = ((MySQLParameterDAO) factory.getParameterDAO()).retrieveAll();
	}
	
	/**
	 * Close the resources.
	 */
	@AfterClass
	public static void tearDownAfterClass () {
		try {
			assertEquals(parametersBackup, ((MySQLParameterDAO) factory.getParameterDAO()).retrieveAll());
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
		assertNull(parameterDAO.create(new Parameter()));
		assertNull(parameterDAO.create(null));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLParameterDAO#find(int)}.
	 */
	@Test
	public void testFindInt () {
		for (int id = 0; id < 100; ++id) {
			assertNull(parameterDAO.find(id));
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLParameterDAO#update(org.clubrockisen.entities.Parameter)}.
	 */
	@Test
	public void testUpdate () {
		final List<Parameter> parameters = parameterDAO.retrieveAll();
		try {
			for (final Parameter parameter : parameters) {
				final Parameter backup = parameter.clone();
				parameter.setValue("foo");
				parameter.setType("bar");
				assertTrue(parameterDAO.update(parameter));
				assertEquals("foo", parameter.getValue());
				assertEquals("bar", parameter.getType());
				assertTrue(parameterDAO.update(backup));
			}
		} catch (final CloneNotSupportedException e) {
			fail(e.getMessage());
		}
		assertFalse(parameterDAO.update(null));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLParameterDAO#delete(org.clubrockisen.entities.Parameter)}.
	 */
	@Test
	public void testDelete () {
		assertEquals(false, parameterDAO.delete(null));
		assertEquals(false, parameterDAO.delete(new Parameter()));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLDAO#retrieveAll()}.
	 */
	@Test
	public void testRetrieveAll () {
		final List<Parameter> parameters = parameterDAO.retrieveAll();
		for (final Parameter parameter : parameters) {
			assertNotNull(parameter.getName());
			assertNotNull(parameter.getType());
			assertNotNull(parameter.getValue());
			assertThat(parameter.getName(), not(""));
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.dao.mysql.MySQLDAO#search(org.clubrockisen.entities.Column, java.lang.String)}.
	 */
	@Test
	public void testSearch () {
		final List<Parameter> parameters = parameterDAO.retrieveAll();
		for (final Parameter parameter : parameters) {
			assertEquals(parameter, parameterDAO.search(Parameter.getColumns().get(ParameterColumn.NAME), parameter.getName()).get(0));
		}
		assertEquals(parameters, parameterDAO.search(null, null));
	}
}
