package org.clubrockisen.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.clubrockisen.common.ConfigurationKeys;
import org.clubrockisen.common.error.SQLConfigurationError;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.utils.Configuration;

/**
 * Test suite for the {@link MySQLDAOFactory} class.
 * @author Alex
 */
public class MySQLDAOFactoryTest {
	/** The configuration to use */
	private Configuration		config;
	/** The factory */
	private AbstractDAOFactory	factory;
	
	/**
	 * Set up the attribute required by the tests.
	 */
	@Before
	public void setUp () {
		config = new Configuration(Paths.get(ConfigurationKeys.FILE));
		AbstractDAOFactory.createFactory(config);
		factory = AbstractDAOFactory.getImplementation();
	}
	
	/**
	 * 
	 */
	@After
	public void tearDown () {
		try {
			factory.close();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
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
		assertNotNull(factory.getEntryMemberPartyDAO());
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
	 * Test the behavior with a bad configuration file.
	 * @throws SQLException
	 *         expected to happen.
	 */
	@Test(expected = SQLConfigurationError.class)
	public void testConnectionFailed () throws SQLException {
		AbstractDAOFactory.createFactory(new Configuration(Paths.get("test/wrongConf.xml")));
		factory = AbstractDAOFactory.getImplementation();
	}
}
