package org.clubrockisen.entities.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Status} class.
 * @author Alex
 */
public class StatusTest {
	
	/** The list containing all the status available */
	private List<Status>	status;
	/** The member status */
	private Status			member;
	/** The helper member status */
	private Status			helper;
	/** The office member status */
	private Status			office;
	/** The veteran status */
	private Status			veteran;
	
	/**
	 * Build the list with the status.
	 */
	@Before
	public void setUp () {
		status = Arrays.asList(Status.values());
		member = Status.MEMBER;
		helper = Status.HELPER_MEMBER;
		office = Status.OFFICE_MEMBER;
		veteran = Status.VETERAN;
	}
	
	/**
	 * Assert that the status name are different.
	 */
	@Test
	public void testEnumerations () {
		for (int index = 0; index < status.size(); ++index) {
			final Status currentStatus =  status.get(index);
			for (int otherIndex = index + 1; otherIndex < status.size(); ++otherIndex) {
				if (currentStatus.getName().equalsIgnoreCase(status.get(otherIndex).getName())) {
					fail("Two status with name " + currentStatus.getName() + " found (" +
							currentStatus + " and " + status.get(otherIndex) + ").");
				}
			}
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.enums.Status#getName()}.
	 */
	@Test
	public void testGetName () {
		assertEquals(member.getName(), "member");
		assertEquals(helper.getName(), "helper member");
		assertEquals(office.getName(), "office member");
		assertEquals(veteran.getName(), "veteran");
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.enums.Status#fromValue(java.lang.String)}.
	 */
	@Test
	public void testFromValue () {
		assertEquals(member, Status.fromValue(member.getName()));
		assertEquals(member, Status.fromValue("MEMBER"));
		assertEquals(helper, Status.fromValue(helper.getName()));
		assertEquals(helper, Status.fromValue("HELPER_MEMBER"));
		assertEquals(office, Status.fromValue(office.getName()));
		assertEquals(office, Status.fromValue("OFFICE_MEMBER"));
		assertEquals(veteran, Status.fromValue(veteran.getName()));
		assertEquals(veteran, Status.fromValue("VETERAN"));
		try {
			Status.fromValue("isen");
			fail();
		} catch (final IllegalArgumentException e) {
			assertTrue(true);
		}
		
	}
}
