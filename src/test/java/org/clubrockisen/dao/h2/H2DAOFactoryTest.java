package org.clubrockisen.dao.h2;

import static org.clubrockisen.common.ConfigurationKeys.KEY;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

import org.clubrockisen.dao.abstracts.EntryManagerAbstractDAOFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.database.dao.AbstractDAOFactory;
import com.alexrnl.commons.database.dao.DataSourceConfiguration;
import com.alexrnl.commons.utils.Configuration;


/**
 * Test suite for the {@link H2DAOFactory} class.
 * @author Alex
 */
public class H2DAOFactoryTest {
	/** The factory to test */
	private H2DAOFactory				factory;
	/** The counter for the database instance unicity */
	private static final AtomicInteger	DB_COUNTER	= new AtomicInteger(0);
	
	/**
	 * Create a new DAO factory using an in-memory database.
	 * @param parentClass
	 *        the parent class of the DAO factory.
	 * @return the factory to use.
	 * @throws URISyntaxException
	 *         if the configuration file could not be loaded.
	 */
	public static <T extends EntryManagerAbstractDAOFactory> T getDAOFactory (final Class<T> parentClass) throws URISyntaxException {
		final Path configurationPath = Paths.get(H2DAOFactoryTest.class.getResource("/configuration.xml").toURI());
		final Configuration config = new Configuration(configurationPath);
		
		final DataSourceConfiguration dbInfos = new DataSourceConfiguration(String.format(config.get(KEY.db().url()), DB_COUNTER.incrementAndGet()),
				config.get(KEY.db().username()), config.get(KEY.db().password()),
				Paths.get(configurationPath.getParent().toString(), config.get(KEY.db().creationFile())));
		return AbstractDAOFactory.buildFactory(config.get(KEY.daoFactory()), dbInfos, parentClass);
	}
	
	/**
	 * Set up the attribute required by the tests.
	 * @throws URISyntaxException
	 *         if the configuration file could not be loaded.
	 */
	@Before
	public void setUp () throws URISyntaxException {
		factory = getDAOFactory(H2DAOFactory.class);
	}
	
	/**
	 * Close the DAO factory.
	 * @throws IOException
	 *         if there was an exception when closing the factory.
	 */
	@After
	public void tearDown () throws IOException {
		if (factory != null) {
			factory.close();
		}
	}
	
	/**
	 * Test method for {@link H2DAOFactory#getParameterDAO()}.
	 * Test method for {@link H2DAOFactory#getMemberDAO()}.
	 * Test method for {@link H2DAOFactory#getPartyDAO()}.
	 * Test method for {@link H2DAOFactory#getEntryMemberPartyDAO()}.
	 */
	@Test
	public void testGetDAOs () {
		assertNotNull(factory.getParameterDAO());
		assertNotNull(factory.getMemberDAO());
		assertNotNull(factory.getPartyDAO());
		assertNotNull(factory.getEntryMemberPartyDAO());
	}
	
}
