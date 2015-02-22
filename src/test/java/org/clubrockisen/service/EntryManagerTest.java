package org.clubrockisen.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.clubrockisen.dao.abstracts.EntryManagerAbstractDAOFactory;
import org.clubrockisen.dao.h2.H2DAOFactoryTest;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.abstracts.IEntryManager;
import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alexrnl.commons.time.Time;
import com.alexrnl.commons.utils.Configuration;

/**
 * Test suite for the {@link EntryManager} class.
 * @author Alex
 */
public class EntryManagerTest {
	/** The DAO factory used */
	private static EntryManagerAbstractDAOFactory	factory;
	/** The parameter manager */
	private static IParametersManager				pm;
	/** The time limit previously set */
	private static String							timeLimit;
	
	/** The entry manager to test */
	private IEntryManager							entryManager;

	/**
	 * Save the value of the time limit parameter.
	 * @throws URISyntaxException
	 *         if the configuration file could not be loaded.
	 */
	@BeforeClass
	public static void setUpBeforeClass () throws URISyntaxException {
		factory = H2DAOFactoryTest.getDAOFactory(EntryManagerAbstractDAOFactory.class);
		ServiceFactory.createFactory(new Configuration(Paths.get(H2DAOFactoryTest.class.getResource("/configuration.xml").toURI())), factory);
		pm = ServiceFactory.getImplementation().getParameterManager();
		timeLimit = pm.get(ParametersEnum.TIME_LIMIT).getValue();
	}
	
	/**
	 * Restore the value of the time limit parameter.
	 * @throws IOException
	 *         if the factory could not be close.
	 */
	@AfterClass
	public static void tearDownAfterClass () throws IOException {
		final Parameter timeLimitParameter = pm.get(ParametersEnum.TIME_LIMIT);
		timeLimitParameter.setValue(timeLimit);
		pm.set(timeLimitParameter);
		factory.close();
	}
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		entryManager = ServiceFactory.getImplementation().getEntryManager();
	}
	
	/**
	 * Test method for {@link org.clubrockisen.service.EntryManager#getPrice(org.clubrockisen.entities.Member)}.
	 */
	@Test
	public void testGetPrice () {
		final Parameter timeLimitParameter = pm.get(ParametersEnum.TIME_LIMIT);
		timeLimitParameter.setValue(Time.getCurrent().add(Time.get("00:10")).toString());
		pm.set(timeLimitParameter);
		
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 0, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 1, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 2, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 3, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 4, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 5, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 6, 0, 0.0, Status.MEMBER)), 0.01);
		
		timeLimitParameter.setValue(Time.getCurrent().sub(Time.get("00:10")).toString());
		pm.set(timeLimitParameter);
		assertEquals(3.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 0, 0, 0.0, Status.MEMBER)), 0.01);
		
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 0, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 1, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 2, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 3, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 4, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 5, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 6, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
	}
}
