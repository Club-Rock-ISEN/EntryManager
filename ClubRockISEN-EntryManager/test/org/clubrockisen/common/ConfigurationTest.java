package org.clubrockisen.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Configuration} class.
 * @author Alex
 */
public class ConfigurationTest {
	/** The instance of the configuration */
	private Configuration	config;
	
	/**
	 * Put back the original configuration file.
	 */
	@AfterClass
	public static void tearDownAfterClass () {
		Configuration.setFile(ConfigurationKey.FILE);
	}
	
	/**
	 * Set up class attributes for tests.
	 */
	@Before
	public void setUp () {
		Configuration.setFile(ConfigurationKey.FILE);
		config = Configuration.getInstance();
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Configuration#setFile(java.lang.String)}.
	 */
	@Test
	public void testSetFile () {
		Configuration.setFile("test/wrongConf.xml");
		assertNotNull(config.get(ConfigurationKey.KEY.daoFactory()));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Configuration#setFile(java.lang.String)}
	 * with a null file.
	 */
	@Test
	public void testSetNullFile () {
		Configuration.setFile(null);
		assertNotNull(config.get(ConfigurationKey.KEY.daoFactory()));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Configuration#setFile(java.lang.String)}
	 * with a null file.
	 */
	@Test
	public void testSetFakeFile () {
		Configuration.setFile("fesffms,m");
		assertNull(config.get(ConfigurationKey.KEY.daoFactory()));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Configuration#get(java.lang.String)}.
	 */
	@Test
	public void testGet () {
		// Test all configurations
		assertNotNull(config.get(ConfigurationKey.KEY.daoFactory()));
		assertNotNull(config.get(ConfigurationKey.KEY.serviceFactory()));
		assertNotNull(config.get(ConfigurationKey.KEY.translationFile()));
		assertNotNull(config.get(ConfigurationKey.KEY.db().url()));
		assertNotNull(config.get(ConfigurationKey.KEY.db().username()));
		assertNotNull(config.get(ConfigurationKey.KEY.db().password()));
		
		// Test a fake key
		assertNull(config.get(ConfigurationKey.FILE));
	}
}
