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
import java.net.URISyntaxException;
import java.util.Set;

import org.clubrockisen.dao.h2.H2DAOFactory;
import org.clubrockisen.dao.h2.H2DAOFactoryTest;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Parameter.ParameterColumn;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alexrnl.commons.database.structure.Column;

/**
 * Test suite for the {@link MySQLParameterDAO} class.
 * @author Alex
 */
public class MySQLParameterDAOTest {
	/** The factory */
	private static H2DAOFactory		factory;
	/** The DAO for the parameters */
	private MySQLParameterDAO		parameterDAO;
	
	/**
	 * Create the factory.
	 * @throws URISyntaxException
	 *         if the configuration file could not be loaded.
	 */
	@BeforeClass
	public static void setUpBeforeClass () throws URISyntaxException {
		factory = H2DAOFactoryTest.getDAOFactory(H2DAOFactory.class);
	}
	
	/**
	 * Close the resources.
	 * @throws IOException if the factory could not be close.
	 */
	@AfterClass
	public static void tearDownAfterClass () throws IOException {
		if (factory != null) {
			factory.close();
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
	 * Test method for {@link MySQLParameterDAO#getEntitySample()}.
	 */
	@Test
	public void testGetEntitySample () {
		assertNotNull(parameterDAO.getEntitySample());
	}
	
	/**
	 * Test method for {@link MySQLParameterDAO#create(Parameter)}.
	 */
	@Test
	public void testCreate () {
		assertNull(parameterDAO.create(new Parameter()));
		assertNull(parameterDAO.create(null));
	}
	
	/**
	 * Test method for {@link MySQLParameterDAO#find(int)}.
	 */
	@Test
	public void testFindInt () {
		for (int id = 0; id < 100; ++id) {
			assertNull(parameterDAO.find(id));
		}
	}
	
	/**
	 * Test method for {@link MySQLParameterDAO#update(Parameter)}.
	 */
	@Test
	public void testUpdate () {
		final Set<Parameter> parameters = parameterDAO.retrieveAll();
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
	 * Test method for {@link MySQLParameterDAO#delete(Parameter)}.
	 */
	@Test
	public void testDelete () {
		assertEquals(false, parameterDAO.delete(null));
		assertEquals(false, parameterDAO.delete(new Parameter()));
	}
	
	/**
	 * Test method for {@link MySQLParameterDAO#retrieveAll()}.
	 */
	@Test
	public void testRetrieveAll () {
		final Set<Parameter> parameters = parameterDAO.retrieveAll();
		for (final Parameter parameter : parameters) {
			assertNotNull(parameter.getName());
			assertNotNull(parameter.getType());
			assertNotNull(parameter.getValue());
			assertThat(parameter.getName(), not(""));
		}
	}
	
	/**
	 * Test method for {@link MySQLParameterDAO#search(Column, String)}.
	 */
	@Test
	public void testSearch () {
		final Set<Parameter> parameters = parameterDAO.retrieveAll();
		for (final Parameter parameter : parameters) {
			assertEquals(parameter, parameterDAO.search(Parameter.getColumns().get(ParameterColumn.NAME), parameter.getName()).iterator().next());
		}
		assertEquals(parameters, parameterDAO.search(null, null));
	}
}
