package org.clubrockisen.service;

import static org.clubrockisen.common.ConfigurationKeys.KEY;
import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.clubrockisen.common.ConfigurationKeys;
import org.clubrockisen.dao.abstracts.EntryManagerAbstractDAOFactory;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.abstracts.IEntryManager;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.database.dao.AbstractDAOFactory;
import com.alexrnl.commons.database.dao.DataSourceConfiguration;
import com.alexrnl.commons.utils.Configuration;

/**
 * Test suite for the {@link EntryManager} class.
 * @author Alex
 */
public class EntryManagerTest {
	/** The entry manager to test */
	private IEntryManager entryManager;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		final Configuration config = new Configuration(Paths.get(ConfigurationKeys.FILE));
		final DataSourceConfiguration dbInfos = new DataSourceConfiguration(config.get(KEY.db().url()),
				config.get(KEY.db().username()), config.get(KEY.db().password()),
				Paths.get(config.get(KEY.db().creationFile())));
		final EntryManagerAbstractDAOFactory factory = AbstractDAOFactory.buildFactory(config.get(KEY.daoFactory()),
				dbInfos, EntryManagerAbstractDAOFactory.class);
		ServiceFactory.createFactory(config, factory);
		entryManager = ServiceFactory.getImplementation().getEntryManager();
	}
	
	/**
	 * Test method for {@link org.clubrockisen.service.EntryManager#getPrice(org.clubrockisen.entities.Member)}.
	 */
	@Test
	public void testGetPrice () {
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 0, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 1, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 2, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 3, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 4, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 5, 0, 0.0, Status.MEMBER)), 0.01);
		assertEquals(4.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 6, 0, 0.0, Status.MEMBER)), 0.01);
		
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 0, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 1, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 2, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 3, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 4, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 5, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
		assertEquals(0.0, entryManager.getPrice(new Member(0, "Alex", Gender.MALE, 6, 0, 0.0, Status.HELPER_MEMBER)), 0.01);
	}
}
