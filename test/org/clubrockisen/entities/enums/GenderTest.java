package org.clubrockisen.entities.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Gender} class.
 * @author Alex
 */
public class GenderTest {
	
	/** The male gender */
	private Gender male;
	/** The female gender */
	private Gender female;
	
	/**
	 * Set the male & female attributes.
	 */
	@Before
	public void setUp () {
		male = Gender.MALE;
		female = Gender.FEMALE;
	}
	
	/**
	 * Check that the enumeration abbreviation are different.
	 */
	@Test
	public void testEnumerations () {
		assertNotSame(male.getAbbreviation(), female.getAbbreviation());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.enums.Gender#getAbbreviation()}.
	 */
	@Test
	public void testGetAbbreviation () {
		assertEquals(male.getAbbreviation(), 'M');
		assertEquals(female.getAbbreviation(), 'F');
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.enums.Gender#fromValue(char)}.
	 */
	@Test
	public void testFromValue () {
		assertEquals(male, Gender.fromValue(male.getAbbreviation()));
		assertEquals(female, Gender.fromValue(female.getAbbreviation()));
		try {
			Gender.fromValue('z');
			fail();
		} catch (final IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}
