package org.clubrockisen.entities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.database.Column;

/**
 * Test suite for the {@link Member} class.
 * @author Alex
 */
public class MemberTest {
	
	/** Member with no informations */
	private Member			nullMember;
	/** Member with all informations */
	private Member			fullMember;
	/** List with all the members to test */
	private List<Member>	members;
	
	/**
	 * Build the members which are going to be tested.
	 */
	@Before
	public void setUp () {
		nullMember = new Member();
		fullMember = new Member(8, "crock", Gender.MALE, 4, 2, 2.8, Status.VETERAN);
		
		members = new ArrayList<>(2);
		members.add(nullMember);
		members.add(fullMember);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getEntityName()}.
	 */
	@Test
	public void testGetEntityName () {
		for (final Member member : members) {
			assertEquals("member", member.getEntityName());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getEntityColumns()}.
	 */
	@Test
	public void testGetEntityColumns () {
		for (final Member member : members) {
			final Map<? extends Enum<?>, Column> columns = member.getEntityColumns();
			assertEquals(7, columns.size());
			
			assertEquals("idMember", columns.get(MemberColumn.ID).getName());
			assertEquals(Integer.class, columns.get(MemberColumn.ID).getType());
			assertEquals(true, columns.get(MemberColumn.ID).isID());
			
			assertEquals("name", columns.get(MemberColumn.NAME).getName());
			assertEquals(String.class, columns.get(MemberColumn.NAME).getType());
			assertEquals(false, columns.get(MemberColumn.NAME).isID());
			
			assertEquals("gender", columns.get(MemberColumn.GENDER).getName());
			assertEquals(Gender.class, columns.get(MemberColumn.GENDER).getType());
			assertEquals(false, columns.get(MemberColumn.GENDER).isID());
			
			assertEquals("entries", columns.get(MemberColumn.ENTRIES).getName());
			assertEquals(Integer.class, columns.get(MemberColumn.ENTRIES).getType());
			assertEquals(false, columns.get(MemberColumn.ENTRIES).isID());
			
			assertEquals("nextFree", columns.get(MemberColumn.NEXT_FREE).getName());
			assertEquals(Integer.class, columns.get(MemberColumn.NEXT_FREE).getType());
			assertEquals(false, columns.get(MemberColumn.NEXT_FREE).isID());
			
			assertEquals("credit", columns.get(MemberColumn.CREDIT).getName());
			assertEquals(Double.class, columns.get(MemberColumn.CREDIT).getType());
			assertEquals(false, columns.get(MemberColumn.CREDIT).isID());
			
			assertEquals("status", columns.get(MemberColumn.STATUS).getName());
			assertEquals(Status.class, columns.get(MemberColumn.STATUS).getType());
			assertEquals(false, columns.get(MemberColumn.STATUS).isID());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getID()}.
	 */
	@Test
	public void testGetID () {
		for (final Member member : members) {
			assertEquals(member.getIdMember().toString(), member.getID());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getColumns()}.
	 */
	@Test
	public void testGetColumns () {
		for (final Member member : members) {
			assertEquals(Member.getColumns(), member.getEntityColumns());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getIdMember()}.
	 */
	@Test
	public void testGetIdMember () {
		assertEquals((long) -1, (long) nullMember.getIdMember());
		assertEquals((long) 8, (long) fullMember.getIdMember());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#setIdMember(java.lang.Integer)}.
	 */
	@Test
	public void testSetIdMember () {
		nullMember.setIdMember(2);
		fullMember.setIdMember(14);
		
		assertEquals((long) 2, (long) nullMember.getIdMember());
		assertEquals((long) 14, (long) fullMember.getIdMember());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getName()}.
	 */
	@Test
	public void testGetName () {
		assertEquals("", nullMember.getName());
		assertEquals("crock", fullMember.getName());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName () {
		nullMember.setName("isen");
		fullMember.setName("club rock");
		
		assertEquals("isen", nullMember.getName());
		assertEquals("club rock", fullMember.getName());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getGender()}.
	 */
	@Test
	public void testGetGender () {
		assertEquals(Gender.FEMALE, nullMember.getGender());
		assertEquals(Gender.MALE, fullMember.getGender());
	}
	
	/**
	 * Test method for
	 * {@link org.clubrockisen.entities.Member#setGender(org.clubrockisen.entities.enums.Gender)}.
	 */
	@Test
	public void testSetGender () {
		nullMember.setGender(Gender.MALE);
		fullMember.setGender(Gender.FEMALE);
		
		assertEquals(Gender.MALE, nullMember.getGender());
		assertEquals(Gender.FEMALE, fullMember.getGender());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getEntries()}.
	 */
	@Test
	public void testGetEntries () {
		assertEquals((long) 0, (long) nullMember.getEntries());
		assertEquals((long) 4, (long) fullMember.getEntries());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#setEntries(Integer)}.
	 */
	@Test
	public void testSetEntries () {
		nullMember.setEntries(1);
		fullMember.setEntries(8);
		
		assertEquals((long) 1, (long) nullMember.getEntries());
		assertEquals((long) 8, (long) fullMember.getEntries());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getNextFree()}.
	 */
	@Test
	public void testGetNextFree () {
		assertEquals((long) -1, (long) nullMember.getNextFree());
		assertEquals((long) 2, (long) fullMember.getNextFree());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#setNextFree(Integer)}.
	 */
	@Test
	public void testSetNextFree () {
		nullMember.setNextFree(4);
		fullMember.setNextFree(3);
		
		assertEquals((long) 4, (long) nullMember.getNextFree());
		assertEquals((long) 3, (long) fullMember.getNextFree());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getCredit()}.
	 */
	@Test
	public void testGetCredit () {
		assertEquals(0.0, nullMember.getCredit(), 0.01);
		assertEquals(2.8, fullMember.getCredit(), 0.01);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#setCredit(Double)}.
	 */
	@Test
	public void testSetCredit () {
		nullMember.setCredit(5.0);
		fullMember.setCredit(1.0);
		
		assertEquals(5.0, nullMember.getCredit(), 0.01);
		assertEquals(1.0, fullMember.getCredit(), 0.01);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#getStatus()}.
	 */
	@Test
	public void testGetStatus () {
		assertEquals(Status.MEMBER, nullMember.getStatus());
		assertEquals(Status.VETERAN, fullMember.getStatus());
	}
	
	/**
	 * Test method for
	 * {@link org.clubrockisen.entities.Member#setStatus(org.clubrockisen.entities.enums.Status)}.
	 */
	@Test
	public void testSetStatus () {
		nullMember.setStatus(Status.HELPER_MEMBER);
		fullMember.setStatus(Status.OFFICE_MEMBER);
		
		assertEquals(Status.HELPER_MEMBER, nullMember.getStatus());
		assertEquals(Status.OFFICE_MEMBER, fullMember.getStatus());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Member#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals("", nullMember.toString());
		assertEquals("crock", fullMember.toString());
	}
	
}
