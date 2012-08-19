package org.clubrockisen.entities.enums;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
	/** A status name which does not exists */
	private String			wrongStatusName;
	
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
		wrongStatusName = "isen";
	}
	
	/**
	 * Assert that the status name are different.
	 */
	@Test
	public void testEnumerations () {
		for (int index = 0; index < status.size(); ++index) {
			final Status currentStatus = status.get(index);
			for (int otherIndex = index + 1; otherIndex < status.size(); ++otherIndex) {
				assertThat(currentStatus.getName().toLowerCase(), not(status.get(otherIndex)
						.getName().toLowerCase()));
			}
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.enums.Status#getName()}.
	 */
	@Test
	public void testGetName () {
		assertEquals("member", member.getName());
		assertEquals("helper member", helper.getName());
		assertEquals("office member", office.getName());
		assertEquals("veteran", veteran.getName());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.enums.Gender#toString()}.
	 */
	@Test
	public void testToString () {
		for (final Status currentStatus : status) {
			assertNotNull(currentStatus.toString());
			assertThat(status.toString().length(), not(0));
			for (final Status otherStatus : status) {
				if (currentStatus.equals(otherStatus)) {
					continue;
				}
				assertThat(currentStatus.toString(), not(otherStatus.toString()));
			}
		}
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
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.enums.Status#fromValue(java.lang.String)}
	 * which checks if it throws an exception for an unknown status name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFromValueException () {
		Status.fromValue(wrongStatusName);
	}
}
