package org.clubrockisen.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.clubrockisen.entities.EntryMemberParty.EntryColumn;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link EntryMemberParty} class.
 * @author Alex
 */
public class EntryMemberPartyTest {
	
	/** Entry with no informations */
	private EntryMemberParty nullEntry;
	/** Entry with informations from ids */
	private EntryMemberParty entryFromIds;
	/** Entry with information from entities */
	private EntryMemberParty entryFromEntities;
	/** List containing the entries to test */
	private List<EntryMemberParty> entries;
	
	/**
	 * Build the entries which will be used during the tests.
	 */
	@Before
	public void setUp () {
		nullEntry = new EntryMemberParty();
		entryFromIds = new EntryMemberParty(1, 28, 8);
		
		final Member member = new Member();
		member.setIdMember(88);
		final Party party = new Party();
		party.setIdParty(48);
		entryFromEntities = new EntryMemberParty(2, member, party);
		
		entries = new ArrayList<>(3);
		entries.add(nullEntry);
		entries.add(entryFromIds);
		entries.add(entryFromEntities);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#getEntityName()}.
	 */
	@Test
	public void testGetEntityName () {
		for (final EntryMemberParty entry : entries) {
			assertEquals("entryMemberParty", entry.getEntityName());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#getEntityColumns()}.
	 */
	@Test
	public void testGetEntityColumns () {
		for (final EntryMemberParty entry : entries) {
			final Map<? extends Enum<?>, Column> colummns = entry.getEntityColumns();
			assertEquals(3, colummns.size());
			assertEquals("idEntryMemberParty", colummns.get(EntryColumn.ID).getName());
			assertEquals(Integer.class, colummns.get(EntryColumn.ID).getType());
			assertEquals(true, colummns.get(EntryColumn.ID).isID());
			
			assertEquals("idMember", colummns.get(EntryColumn.MEMBER_ID).getName());
			assertEquals(Integer.class, colummns.get(EntryColumn.MEMBER_ID).getType());
			assertEquals(false, colummns.get(EntryColumn.MEMBER_ID).isID());
			
			assertEquals("idParty", colummns.get(EntryColumn.PARTY_ID).getName());
			assertEquals(Integer.class, colummns.get(EntryColumn.PARTY_ID).getType());
			assertEquals(false, colummns.get(EntryColumn.PARTY_ID).isID());
		}
		
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#getID()}.
	 */
	@Test
	public void testGetID () {
		for (final EntryMemberParty entry : entries) {
			assertEquals(entry.getIdEntryMemberParty().toString(), entry.getID());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#getColumns()}.
	 */
	@Test
	public void testGetColumns () {
		for (final EntryMemberParty entry : entries) {
			assertEquals(EntryMemberParty.getColumns(), entry.getEntityColumns());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#getIdEntryMemberParty()}.
	 */
	@Test
	public void testGetIdEntryMemberParty () {
		assertEquals((long) -1, (long) nullEntry.getIdEntryMemberParty());
		assertEquals((long) 1, (long) entryFromIds.getIdEntryMemberParty());
		assertEquals((long) 2, (long) entryFromEntities.getIdEntryMemberParty());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#setIdEntryMemberParty(java.lang.Integer)}.
	 */
	@Test
	public void testSetIdEntryMemberParty () {
		nullEntry.setIdEntryMemberParty(-10);
		entryFromIds.setIdEntryMemberParty(24);
		entryFromEntities.setIdEntryMemberParty(128);
		
		assertEquals((long) -10, (long) nullEntry.getIdEntryMemberParty());
		assertEquals((long) 24, (long) entryFromIds.getIdEntryMemberParty());
		assertEquals((long) 128, (long) entryFromEntities.getIdEntryMemberParty());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#getIdMember()}.
	 */
	@Test
	public void testGetIdMember () {
		assertEquals((long) -1, (long) nullEntry.getIdMember());
		assertEquals((long) 28, (long) entryFromIds.getIdMember());
		assertEquals((long) 88, (long) entryFromEntities.getIdMember());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#setIdMember(java.lang.Integer)}.
	 */
	@Test
	public void testSetIdMember () {
		nullEntry.setIdMember(-100);
		entryFromIds.setIdMember(248);
		entryFromEntities.setIdMember(882);
		
		assertEquals((long) -100, (long) nullEntry.getIdMember());
		assertEquals((long) 248, (long) entryFromIds.getIdMember());
		assertEquals((long) 882, (long) entryFromEntities.getIdMember());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#getIdParty()}.
	 */
	@Test
	public void testGetIdParty () {
		assertEquals((long) -1, (long) nullEntry.getIdParty());
		assertEquals((long) 8, (long) entryFromIds.getIdParty());
		assertEquals((long) 48, (long) entryFromEntities.getIdParty());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.EntryMemberParty#setIdParty(java.lang.Integer)}.
	 */
	@Test
	public void testSetIdParty () {
		nullEntry.setIdParty(-128);
		entryFromIds.setIdParty(52);
		entryFromEntities.setIdParty(44);
		
		assertEquals((long) -128, (long) nullEntry.getIdParty());
		assertEquals((long) 52, (long) entryFromIds.getIdParty());
		assertEquals((long) 44, (long) entryFromEntities.getIdParty());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#getIDColumn()}.
	 */
	@Test
	public void testGetIDColumn () {
		try {
			for (final EntryMemberParty entry : entries) {
				final Column column = entry.getIDColumn();
				assertEquals("idEntryMemberParty", column.getName());
				assertEquals(Integer.class, column.getType());
				assertEquals(true, column.isID());
			}
		} catch (final NoIdException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateInsertQuerySQL()}.
	 */
	@Test
	public void testGenerateInsertQuerySQL () {
		for (final EntryMemberParty entry : entries) {
			assertEquals("INSERT INTO entryMemberParty(`idEntryMemberParty`,`idMember`,`idParty`) VALUES ", entry.generateInsertQuerySQL());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateInsertQuerySQL(boolean)}.
	 */
	@Test
	public void testGenerateInsertQuerySQLBoolean () {
		for (final EntryMemberParty entry : entries) {
			assertEquals("INSERT INTO entryMemberParty(`idEntryMemberParty`,`idMember`,`idParty`) VALUES ", entry.generateInsertQuerySQL(true));
			assertEquals("INSERT INTO entryMemberParty(`idMember`,`idParty`) VALUES ", entry.generateInsertQuerySQL(false));
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateDeleteQuerySQL()}.
	 */
	@Test
	public void testGenerateDeleteQuerySQL () {
		try {
			assertEquals("DELETE FROM entryMemberParty WHERE idEntryMemberParty = -1", nullEntry.generateDeleteQuerySQL());
			assertEquals("DELETE FROM entryMemberParty WHERE idEntryMemberParty = 1", entryFromIds.generateDeleteQuerySQL());
			assertEquals("DELETE FROM entryMemberParty WHERE idEntryMemberParty = 2", entryFromEntities.generateDeleteQuerySQL());
		} catch (final NoIdException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateUpdateQuerySQL()}.
	 */
	@Test
	public void testGenerateUpdateQuerySQL () {
		for (final EntryMemberParty entry : entries) {
			assertEquals("UPDATE entryMemberParty SET ", entry.generateUpdateQuerySQL());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateWhereIDQuerySQL()}.
	 */
	@Test
	public void testGenerateWhereIDQuerySQL () {
		try {
			assertEquals(" WHERE idEntryMemberParty = -1", nullEntry.generateWhereIDQuerySQL());
			assertEquals(" WHERE idEntryMemberParty = 1", entryFromIds.generateWhereIDQuerySQL());
			assertEquals(" WHERE idEntryMemberParty = 2", entryFromEntities.generateWhereIDQuerySQL());
		} catch (final NoIdException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateWhereIDQuerySQL(java.lang.Object)}.
	 */
	@Test
	public void testGenerateWhereIDQuerySQLObject () {
		try {
			assertEquals(" WHERE idEntryMemberParty = 12", nullEntry.generateWhereIDQuerySQL(12));
			assertEquals(" WHERE idEntryMemberParty = 28.12", entryFromIds.generateWhereIDQuerySQL(28.12));
			assertEquals(" WHERE idEntryMemberParty = test", entryFromEntities.generateWhereIDQuerySQL("test"));
		} catch (final NoIdException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#generateSearchAllQuerySQL()}.
	 */
	@Test
	public void testGenerateSearchAllQuerySQL () {
		for (final EntryMemberParty entry : entries) {
			assertEquals("SELECT * FROM entryMemberParty", entry.generateSearchAllQuerySQL());
		}
	}
}
