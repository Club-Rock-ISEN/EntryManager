package org.clubrockisen.entities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.clubrockisen.entities.Party.PartyColumn;
import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.database.Column;

/**
 * Test suite for the {@link Party} class.
 * @author Alex
 */
public class PartyTest {
	
	/** Party with no information */
	private Party nullParty;
	/** Party with the date */
	private Party partyFromDate;
	/** Party with all the information */
	private Party fullParty;
	/** List with all the parties to test */
	private List<Party> parties;
	
	/**
	 * Build the parties to test.
	 */
	@Before
	public void setUp () {
		nullParty = new Party();
		final Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		partyFromDate = new Party(today.getTimeInMillis());
		final Calendar yesterday = Calendar.getInstance();
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		yesterday.set(Calendar.MILLISECOND, 0);
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		fullParty = new Party(2, yesterday.getTimeInMillis(), 20, 18, 2, 4, 3, 8, 12, 68.0, -15.0);
		
		parties = new ArrayList<>(3);
		parties.add(nullParty);
		parties.add(partyFromDate);
		parties.add(fullParty);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getEntityName()}.
	 */
	@Test
	public void testGetEntityName () {
		for (final Party party : parties) {
			assertEquals("party", party.getEntityName());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getEntityColumns()}.
	 */
	@Test
	public void testGetEntityColumns () {
		for (final Party party : parties) {
			final Map<? extends Enum<?>, Column> columns = party.getEntityColumns();
			
			assertEquals("idParty", columns.get(PartyColumn.ID).getName());
			assertEquals(Integer.class, columns.get(PartyColumn.ID).getType());
			assertEquals(true, columns.get(PartyColumn.ID).isID());
			
			assertEquals("date", columns.get(PartyColumn.DATE).getName());
			assertEquals(java.sql.Date.class, columns.get(PartyColumn.DATE).getType());
			assertEquals(false, columns.get(PartyColumn.DATE).isID());
			
			assertEquals("entriesTotal", columns.get(PartyColumn.ENTRIES_TOTAL).getName());
			assertEquals(Integer.class, columns.get(PartyColumn.ENTRIES_TOTAL).getType());
			assertEquals(false, columns.get(PartyColumn.ENTRIES_TOTAL).isID());
			
			assertEquals("entriesFirstPart", columns.get(PartyColumn.ENTRIES_FIRST_PART).getName());
			assertEquals(Integer.class, columns.get(PartyColumn.ENTRIES_FIRST_PART).getType());
			assertEquals(false, columns.get(PartyColumn.ENTRIES_FIRST_PART).isID());
			
			assertEquals("entriesSecondPart", columns.get(PartyColumn.ENTRIES_SECOND_PART).getName());
			assertEquals(Integer.class, columns.get(PartyColumn.ENTRIES_SECOND_PART).getType());
			assertEquals(false, columns.get(PartyColumn.ENTRIES_SECOND_PART).isID());
			
			assertEquals("entriesNewMembers", columns.get(PartyColumn.ENTRIES_NEW_MEMBER).getName());
			assertEquals(Integer.class, columns.get(PartyColumn.ENTRIES_NEW_MEMBER).getType());
			assertEquals(false, columns.get(PartyColumn.ENTRIES_NEW_MEMBER).isID());
			
			assertEquals("entriesFree", columns.get(PartyColumn.ENTRIES_FREE).getName());
			assertEquals(Integer.class, columns.get(PartyColumn.ENTRIES_FREE).getType());
			assertEquals(false, columns.get(PartyColumn.ENTRIES_FREE).isID());
			
			assertEquals("entriesMale", columns.get(PartyColumn.ENTRIES_MALE).getName());
			assertEquals(Integer.class, columns.get(PartyColumn.ENTRIES_MALE).getType());
			assertEquals(false, columns.get(PartyColumn.ENTRIES_MALE).isID());
			
			assertEquals("entriesFemale", columns.get(PartyColumn.ENTRIES_FEMALE).getName());
			assertEquals(Integer.class, columns.get(PartyColumn.ENTRIES_FEMALE).getType());
			assertEquals(false, columns.get(PartyColumn.ENTRIES_FEMALE).isID());
			
			assertEquals("revenue", columns.get(PartyColumn.REVENUE).getName());
			assertEquals(Double.class, columns.get(PartyColumn.REVENUE).getType());
			assertEquals(false, columns.get(PartyColumn.REVENUE).isID());
			
			assertEquals("profit", columns.get(PartyColumn.PROFIT).getName());
			assertEquals(Double.class, columns.get(PartyColumn.PROFIT).getType());
			assertEquals(false, columns.get(PartyColumn.PROFIT).isID());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getID()}.
	 */
	@Test
	public void testGetID () {
		assertEquals("-1", nullParty.getID());
		assertEquals("-1", partyFromDate.getID());
		assertEquals("2", fullParty.getID());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getColumns()}.
	 */
	@Test
	public void testGetColumns () {
		for (final Party party : parties) {
			assertEquals(party.getEntityColumns(), Party.getColumns());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getIdParty()}.
	 */
	@Test
	public void testGetIdParty () {
		assertEquals((long) -1, (long) nullParty.getIdParty());
		assertEquals((long) -1, (long) partyFromDate.getIdParty());
		assertEquals((long) 2, (long) fullParty.getIdParty());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setIdParty(java.lang.Integer)}.
	 */
	@Test
	public void testSetIdParty () {
		nullParty.setIdParty(1);
		partyFromDate.setIdParty(8);
		fullParty.setIdParty(14);
		
		assertEquals((long) 1, (long) nullParty.getIdParty());
		assertEquals((long) 8, (long) partyFromDate.getIdParty());
		assertEquals((long) 14, (long) fullParty.getIdParty());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getDate()}.
	 */
	@Test
	public void testGetDate () {
		final Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		final Calendar yesterday = Calendar.getInstance();
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		yesterday.set(Calendar.MILLISECOND, 0);
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		
		assertEquals(0L, (long) nullParty.getDate());
		assertEquals(today.getTimeInMillis(), (long) partyFromDate.getDate());
		assertEquals(yesterday.getTimeInMillis(), (long) fullParty.getDate());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setDate(Long)}.
	 */
	@Test
	public void testSetDate () {
		final Calendar beforeYesterday = Calendar.getInstance();
		beforeYesterday.set(Calendar.HOUR_OF_DAY, 0);
		beforeYesterday.set(Calendar.MINUTE, 0);
		beforeYesterday.set(Calendar.SECOND, 0);
		beforeYesterday.set(Calendar.MILLISECOND, 0);
		beforeYesterday.add(Calendar.DAY_OF_MONTH, -2);
		nullParty.setDate(beforeYesterday.getTimeInMillis());
		
		final Calendar tomorrow = Calendar.getInstance();
		tomorrow.set(Calendar.HOUR_OF_DAY, 0);
		tomorrow.set(Calendar.MINUTE, 0);
		tomorrow.set(Calendar.SECOND, 0);
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		tomorrow.set(Calendar.MILLISECOND, 0);
		partyFromDate.setDate(tomorrow.getTimeInMillis());
		final Calendar afterTomorrow = Calendar.getInstance();
		afterTomorrow.set(Calendar.HOUR_OF_DAY, 0);
		afterTomorrow.set(Calendar.MINUTE, 0);
		afterTomorrow.set(Calendar.SECOND, 0);
		afterTomorrow.set(Calendar.MILLISECOND, 0);
		afterTomorrow.add(Calendar.DAY_OF_MONTH, 2);
		fullParty.setDate(afterTomorrow.getTimeInMillis());
		
		assertEquals(beforeYesterday.getTimeInMillis(), (long) nullParty.getDate());
		assertEquals(tomorrow.getTimeInMillis(), (long) partyFromDate.getDate());
		assertEquals(afterTomorrow.getTimeInMillis(), (long) fullParty.getDate());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getEntriesTotal()}.
	 */
	@Test
	public void testGetEntriesTotal () {
		assertEquals((long) 0, (long) nullParty.getEntriesTotal());
		assertEquals((long) 0, (long) partyFromDate.getEntriesTotal());
		assertEquals((long) 20, (long) fullParty.getEntriesTotal());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setEntriesTotal(java.lang.Integer)}.
	 */
	@Test
	public void testSetEntriesTotal () {
		nullParty.setEntriesTotal(1);
		partyFromDate.setEntriesTotal(2);
		fullParty.setEntriesTotal(22);
		
		assertEquals((long) 1, (long) nullParty.getEntriesTotal());
		assertEquals((long) 2, (long) partyFromDate.getEntriesTotal());
		assertEquals((long) 22, (long) fullParty.getEntriesTotal());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getEntriesFirstPart()}.
	 */
	@Test
	public void testGetEntriesFirstPart () {
		assertEquals((long) 0, (long) nullParty.getEntriesFirstPart());
		assertEquals((long) 0, (long) partyFromDate.getEntriesFirstPart());
		assertEquals((long) 18, (long) fullParty.getEntriesFirstPart());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setEntriesFirstPart(java.lang.Integer)}.
	 */
	@Test
	public void testSetEntriesFirstPart () {
		nullParty.setEntriesFirstPart(3);
		partyFromDate.setEntriesFirstPart(4);
		fullParty.setEntriesFirstPart(24);
		
		assertEquals((long) 3, (long) nullParty.getEntriesFirstPart());
		assertEquals((long) 4, (long) partyFromDate.getEntriesFirstPart());
		assertEquals((long) 24, (long) fullParty.getEntriesFirstPart());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getEntriesSecondPart()}.
	 */
	@Test
	public void testGetEntriesSecondPart () {
		assertEquals((long) 0, (long) nullParty.getEntriesSecondPart());
		assertEquals((long) 0, (long) partyFromDate.getEntriesSecondPart());
		assertEquals((long) 2, (long) fullParty.getEntriesSecondPart());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setEntriesSecondPart(java.lang.Integer)}.
	 */
	@Test
	public void testSetEntriesSecondPart () {
		nullParty.setEntriesSecondPart(6);
		partyFromDate.setEntriesSecondPart(8);
		fullParty.setEntriesSecondPart(5);
		
		assertEquals((long) 6, (long) nullParty.getEntriesSecondPart());
		assertEquals((long) 8, (long) partyFromDate.getEntriesSecondPart());
		assertEquals((long) 5, (long) fullParty.getEntriesSecondPart());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getEntriesNewMembers()}.
	 */
	@Test
	public void testGetEntriesNewMembers () {
		assertEquals((long) 0, (long) nullParty.getEntriesNewMembers());
		assertEquals((long) 0, (long) partyFromDate.getEntriesNewMembers());
		assertEquals((long) 4, (long) fullParty.getEntriesNewMembers());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setEntriesNewMembers(java.lang.Integer)}.
	 */
	@Test
	public void testSetEntriesNewMembers () {
		nullParty.setEntriesNewMembers(5);
		partyFromDate.setEntriesNewMembers(2);
		fullParty.setEntriesNewMembers(8);
		
		assertEquals((long) 5, (long) nullParty.getEntriesNewMembers());
		assertEquals((long) 2, (long) partyFromDate.getEntriesNewMembers());
		assertEquals((long) 8, (long) fullParty.getEntriesNewMembers());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getEntriesFree()}.
	 */
	@Test
	public void testGetEntriesFree () {
		assertEquals((long) 0, (long) nullParty.getEntriesFree());
		assertEquals((long) 0, (long) partyFromDate.getEntriesFree());
		assertEquals((long) 3, (long) fullParty.getEntriesFree());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setEntriesFree(java.lang.Integer)}.
	 */
	@Test
	public void testSetEntriesFree () {
		nullParty.setEntriesFree(1);
		partyFromDate.setEntriesFree(3);
		fullParty.setEntriesFree(10);
		
		assertEquals((long) 1, (long) nullParty.getEntriesFree());
		assertEquals((long) 3, (long) partyFromDate.getEntriesFree());
		assertEquals((long) 10, (long) fullParty.getEntriesFree());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getEntriesMale()}.
	 */
	@Test
	public void testGetEntriesMale () {
		assertEquals((long) 0, (long) nullParty.getEntriesMale());
		assertEquals((long) 0, (long) partyFromDate.getEntriesMale());
		assertEquals((long) 8, (long) fullParty.getEntriesMale());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setEntriesMale(java.lang.Integer)}.
	 */
	@Test
	public void testSetEntriesMale () {
		nullParty.setEntriesMale(2);
		partyFromDate.setEntriesMale(7);
		fullParty.setEntriesMale(12);
		
		assertEquals((long) 2, (long) nullParty.getEntriesMale());
		assertEquals((long) 7, (long) partyFromDate.getEntriesMale());
		assertEquals((long) 12, (long) fullParty.getEntriesMale());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getEntriesFemale()}.
	 */
	@Test
	public void testGetEntriesFemale () {
		assertEquals((long) 0, (long) nullParty.getEntriesFemale());
		assertEquals((long) 0, (long) partyFromDate.getEntriesFemale());
		assertEquals((long) 12, (long) fullParty.getEntriesFemale());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setEntriesFemale(java.lang.Integer)}.
	 */
	@Test
	public void testSetEntriesFemale () {
		nullParty.setEntriesFemale(1);
		partyFromDate.setEntriesFemale(9);
		fullParty.setEntriesFemale(14);
		
		assertEquals((long) 1, (long) nullParty.getEntriesFemale());
		assertEquals((long) 9, (long) partyFromDate.getEntriesFemale());
		assertEquals((long) 14, (long) fullParty.getEntriesFemale());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getRevenue()}.
	 */
	@Test
	public void testGetRevenue () {
		assertEquals(0.0, nullParty.getRevenue(), 0.01);
		assertEquals(0.0, partyFromDate.getRevenue(), 0.01);
		assertEquals(68.0, fullParty.getRevenue(), 0.01);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setRevenue(java.lang.Double)}.
	 */
	@Test
	public void testSetRevenue () {
		nullParty.setRevenue(10.0);
		partyFromDate.setRevenue(28.21);
		fullParty.setRevenue(98.46);
		
		assertEquals(10.0, nullParty.getRevenue(), 0.01);
		assertEquals(28.21, partyFromDate.getRevenue(), 0.01);
		assertEquals(98.46, fullParty.getRevenue(), 0.01);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#getProfit()}.
	 */
	@Test
	public void testGetProfit () {
		assertEquals(0.0, nullParty.getProfit(), 0.01);
		assertEquals(0.0, partyFromDate.getProfit(), 0.01);
		assertEquals(-15.0, fullParty.getProfit(), 0.01);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Party#setProfit(java.lang.Double)}.
	 */
	@Test
	public void testSetProfit () {
		nullParty.setProfit(-12.80);
		partyFromDate.setProfit(18.64);
		fullParty.setProfit(8.12);
		
		assertEquals(-12.80, nullParty.getProfit(), 0.01);
		assertEquals(18.64, partyFromDate.getProfit(), 0.01);
		assertEquals(8.12, fullParty.getProfit(), 0.01);
	}
}
