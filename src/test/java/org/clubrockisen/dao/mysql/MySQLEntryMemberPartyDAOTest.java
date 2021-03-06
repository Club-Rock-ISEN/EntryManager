package org.clubrockisen.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;

import org.clubrockisen.dao.h2.H2DAOFactory;
import org.clubrockisen.dao.h2.H2DAOFactoryTest;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.alexrnl.commons.database.structure.Column;

/**
 * Test suite for the {@link MySQLEntryMemberPartyDAO} class.
 * @author Alex
 */
public class MySQLEntryMemberPartyDAOTest {
	/** The factory */
	private static H2DAOFactory			factory;
	/** The DAO for the entries */
	private MySQLEntryMemberPartyDAO	entriesDAO;
	
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
	 * @throws IOException
	 *         if the factory could not be close.
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
		entriesDAO = (MySQLEntryMemberPartyDAO) factory.getEntryMemberPartyDAO();
	}
	
	/**
	 * Test method for {@link MySQLEntryMemberPartyDAO#getEntitySample()}.
	 */
	@Test
	public void testGetEntitySample () {
		assertNotNull(entriesDAO.getEntitySample());
	}
	
	/**
	 * Test method for {@link MySQLEntryMemberPartyDAO#create(EntryMemberParty)}.
	 */
	@Test
	public void testCreate () {
		// Null case
		assertNull(entriesDAO.create(null));
		
		// Regular cases
		// Prepare reference member
		boolean deleteM = false;
		Member member = factory.getMemberDAO().find(1);
		if (member == null) {
			member = new Member();
			member.setIdMember(1);
			member = factory.getMemberDAO().create(member);
			deleteM = true;
		}
		// Prepare reference party
		boolean deleteP = false;
		Party party = factory.getPartyDAO().find(1);
		if (party == null) {
			party = new Party();
			party.setIdParty(1);
			party = factory.getPartyDAO().create(party);
			deleteP = true;
		}
		
		final EntryMemberParty entry = new EntryMemberParty(-1, member.getIdMember(), party.getIdParty());
		final EntryMemberParty found = entriesDAO.create(entry);
		if (found == null) {
			fail("Failed to create entry, check error log");
			return;
		}
		assertEquals(member.getIdMember(), found.getIdMember());
		assertEquals(party.getIdParty(), found.getIdParty());
		
		assertTrue(entriesDAO.delete(found));
		if (deleteM) {
			assertTrue(factory.getMemberDAO().delete(member));
		}
		if (deleteP) {
			assertTrue(factory.getPartyDAO().delete(party));
		}
	}
	
	/**
	 * Test method for {@link MySQLEntryMemberPartyDAO#update(EntryMemberParty)}.
	 */
	@Ignore
	@Test
	public void testUpdate () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link MySQLEntryMemberPartyDAO#find(int)}.
	 */
	@Ignore
	@Test
	public void testFind () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link MySQLEntryMemberPartyDAO#delete(EntryMemberParty)}.
	 */
	@Ignore
	@Test
	public void testDelete () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link MySQLEntryMemberPartyDAO#retrieveAll()}.
	 */
	@Ignore
	@Test
	public void testRetrieveAll () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link MySQLEntryMemberPartyDAO#search(Column, String)}.
	 */
	@Ignore
	@Test
	public void testSearch () {
		fail("Not yet implemented"); // TODO
	}
}
