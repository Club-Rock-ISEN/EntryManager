package org.clubrockisen.entities;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.clubrockisen.entities.EntryMemberParty.EntryColumn;
import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.database.Column;

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
			assertEquals(entry.getEntityColumns(), EntryMemberParty.getColumns());
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
	 * Test method for {@link org.clubrockisen.entities.Entity#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		for (final EntryMemberParty entry : entries) {
			// Checking that all hash codes are different
			for (final EntryMemberParty other : entries) {
				if (entry != other) {
					assertThat(entry.hashCode(), not(other.hashCode()));
				}
			}
			try {
				final EntryMemberParty clone = entry.clone();
				assertEquals(entry.hashCode(), clone.hashCode());
				clone.setIdEntryMemberParty(entry.getIdEntryMemberParty() + 1);
				assertThat(entry.hashCode(), not(clone.hashCode()));
				clone.setIdEntryMemberParty(entry.getIdEntryMemberParty());
				clone.setIdMember(entry.getIdMember() + 1);
				assertThat(entry.hashCode(), not(clone.hashCode()));
				clone.setIdMember(entry.getIdMember());
				clone.setIdParty(entry.getIdParty() + 1);
				assertThat(entry.hashCode(), not(clone.hashCode()));
			} catch (final CloneNotSupportedException e) {
				fail(e.getMessage());
			}
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Entity#equals(Object)}.
	 */
	@Test
	public void testEquals () {
		for (final EntryMemberParty entry : entries) {
			// Checking that all hash codes are different
			for (final EntryMemberParty other : entries) {
				if (entry != other) {
					assertThat(entry, not(other));
				} else {
					assertEquals(entry, other);
				}
			}
			// Checking clones
			try {
				final EntryMemberParty clone = entry.clone();
				assertEquals(entry, clone);
				clone.setIdEntryMemberParty(entry.getIdEntryMemberParty() + 1);
				assertThat(entry, not(clone));
				clone.setIdEntryMemberParty(entry.getIdEntryMemberParty());
				clone.setIdMember(entry.getIdMember() + 1);
				assertThat(entry, not(clone));
				clone.setIdMember(entry.getIdMember());
				clone.setIdParty(entry.getIdParty() + 1);
				assertThat(entry, not(clone));
			} catch (final CloneNotSupportedException e) {
				fail(e.getMessage());
			}
			
			// Other cases
			assertThat(null, not(entry));
			assertFalse(entry.equals(entries));
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Parameter#clone()}.
	 */
	@Test
	public void testClone () {
		try {
			for (final EntryMemberParty entry : entries) {
				final EntryMemberParty other = entry.clone();
				assertEquals(entry.getIdEntryMemberParty(), other.getIdEntryMemberParty());
				assertEquals(entry.getIdMember(), other.getIdMember());
				assertEquals(entry.getIdParty(), other.getIdParty());
			}
		} catch (final CloneNotSupportedException e) {
			fail(e.getMessage());
		}
	}
}
