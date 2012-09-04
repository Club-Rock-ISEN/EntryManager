package org.clubrockisen.common;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Time} class.
 * @author Alex
 */
public class TimeTest {
	
	/** The time at midnight */
	private Time		timeMidnight;
	/** The time in the morning */
	private Time		timeMorning;
	/** The time at noon */
	private Time		timeNoon;
	/** The time in the afternoon */
	private Time		timeAfterNoon;
	/** The time in the night */
	private Time		timeNight;
	/** Array with all the times */
	private List<Time>	times;
	
	/**
	 * Set up the attributes required for the tests.
	 */
	@Before
	public void setUp () {
		timeMidnight = new Time();
		timeMorning = new Time(8, 28);
		timeNoon = new Time(12);
		timeAfterNoon = new Time(15, 68);
		timeNight = new Time(23, -66);
		
		times = new ArrayList<>(5);
		times.add(timeMidnight);
		times.add(timeMorning);
		times.add(timeNoon);
		times.add(timeAfterNoon);
		times.add(timeNight);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		for (final Time time : times) {
			// Checking other times
			for (final Time other : times) {
				if (time != other) {
					assertThat(other.hashCode(), not(time.hashCode()));
				}
			}
			
			// Other checks
			final Time otherM = new Time(time.getHours(), time.getMinutes() + 1);
			final Time otherH = new Time(time.getHours() + 1, time.getMinutes());
			assertThat(otherM.hashCode(), not(time.hashCode()));
			assertThat(otherH.hashCode(), not(time.hashCode()));
			assertEquals(time.hashCode(), new Time(time.getHours(), time.getMinutes()).hashCode());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#get(java.lang.String)}.
	 */
	@Test
	public void testGet () {
		assertEquals(timeMidnight, Time.get("0   0"));
		assertEquals(timeMorning, Time.get("8:28"));
		assertEquals(timeNoon, Time.get("12"));
		assertEquals(timeAfterNoon, Time.get("16h08m"));
		assertEquals(timeNight, Time.get("21.54"));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#getHours()}.
	 */
	@Test
	public void testGetHours () {
		assertEquals(0, timeMidnight.getHours());
		assertEquals(8, timeMorning.getHours());
		assertEquals(12, timeNoon.getHours());
		assertEquals(16, timeAfterNoon.getHours());
		assertEquals(21, timeNight.getHours());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#getMinutes()}.
	 */
	@Test
	public void testGetMinutes () {
		assertEquals(0, timeMidnight.getMinutes());
		assertEquals(28, timeMorning.getMinutes());
		assertEquals(0, timeNoon.getMinutes());
		assertEquals(8, timeAfterNoon.getMinutes());
		assertEquals(54, timeNight.getMinutes());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#add(org.clubrockisen.common.Time)}.
	 */
	@Test
	public void testAdd () {
		// Basic check for midnight time
		for (final Time time : times) {
			assertEquals(time, timeMidnight.add(time));
		}
		assertEquals(timeAfterNoon, timeMorning.add(Time.get("7h40")));
		assertEquals(timeNight, timeNoon.add(new Time(9, 54)));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#sub(org.clubrockisen.common.Time)}.
	 */
	@Test
	public void testSub () {
		// Basic check for midnight time
		for (final Time time : times) {
			assertEquals(time, time.sub(timeMidnight));
		}
		assertEquals(timeMorning, timeNight.sub(Time.get("13h26")));
		assertEquals(timeNoon, timeAfterNoon.sub(new Time(4, 8)));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#compareTo(org.clubrockisen.common.Time)}.
	 */
	@Test
	public void testCompareTo () {
		try {
			for (final Time time : times) {
				assertEquals(0, time.compareTo((Time) time.clone()));
				assertTrue(time.compareTo(null) > 0);
			}
		} catch (final CloneNotSupportedException e) {
			fail(e.getMessage());
		}
		
		// The list is ordered so testing that is possible
		for (int index = 0; index < times.size() - 1; ++index) {
			assertTrue(times.get(index + 1).compareTo(times.get(index)) > 0);
			assertTrue(times.get(index).compareTo(times.get(index + 1)) < 0);
		}
		assertTrue(Time.get("3h01").compareTo(Time.get("3h02")) < 0);
		assertTrue(Time.get("21h03").compareTo(Time.get("21h02")) > 0);
		
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#after(org.clubrockisen.common.Time)}.
	 */
	@Test
	public void testAfter () {
		for (int index = 0; index < times.size() - 1; ++index) {
			assertTrue(times.get(index + 1).after(times.get(index)));
			assertTrue(!times.get(index).after(times.get(index + 1)));
		}
		assertTrue(Time.get("16h1").after(Time.get("4h02")));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#before(org.clubrockisen.common.Time)}.
	 */
	@Test
	public void testBefore () {
		for (int index = 0; index < times.size() - 1; ++index) {
			assertTrue(times.get(index).before(times.get(index + 1)));
			assertTrue(!times.get(index + 1).before(times.get(index)));
		}
		assertTrue(Time.get("2h08m").before(Time.get("4h02")));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#clone()}.
	 */
	@Test
	public void testClone () {
		try {
			for (final Time time : times) {
				final Time clone = (Time) time.clone();
				assertNotSame(time, clone);
				assertEquals(time.getHours(), clone.getHours());
				assertEquals(time.getMinutes(), clone.getMinutes());
			}
		} catch (final CloneNotSupportedException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals("00:00", timeMidnight.toString());
		assertEquals("08:28", timeMorning.toString());
		assertEquals("12:00", timeNoon.toString());
		assertEquals("16:08", timeAfterNoon.toString());
		assertEquals("21:54", timeNight.toString());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.Time#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject () {
		for (final Time time : times) {
			// Check all other times are different
			for (final Time other : times) {
				if (time != other) {
					assertThat(time, not(other));
				}
			}
			
			// Other checks
			assertThat(new Time(time.getHours(), time.getMinutes() + 1), not(time));
			assertThat(new Time(time.getHours() + 1, time.getMinutes()), not(time));
			assertEquals(time, new Time(time.getHours(), time.getMinutes()));
			assertEquals(time, time);
			assertTrue(!time.equals(null));
			assertTrue(!time.equals(new Object()));
		}
	}
}
