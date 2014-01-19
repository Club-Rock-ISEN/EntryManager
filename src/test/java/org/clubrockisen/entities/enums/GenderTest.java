package org.clubrockisen.entities.enums;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Gender} class.
 * @author Alex
 */
public class GenderTest {
	
	/** The male gender */
	private Gender	male;
	/** The female gender */
	private Gender	female;
	/** The wrong abbreviation to test */
	private char	noEnum;
	
	/**
	 * Set the male & female attributes.
	 */
	@Before
	public void setUp () {
		male = Gender.MALE;
		female = Gender.FEMALE;
		noEnum = 'z';
	}
	
	/**
	 * Check that the enumeration abbreviation are different.
	 */
	@Test
	public void testEnumerations () {
		assertThat(male.getAbbreviation(), not(female.getAbbreviation()));
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
	 * Test method for {@link org.clubrockisen.entities.enums.Gender#toString()}.
	 */
	@Test
	public void testToString () {
		assertNotNull(male.toString());
		assertNotNull(female.toString());
		assertThat(male.toString().length(), not(0));
		assertThat(female.toString().length(), not(0));
		assertThat(male.toString(), not(female.toString()));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.enums.Gender#fromValue(char)}.
	 */
	@Test
	public void testFromValue () {
		assertEquals(male, Gender.fromValue(male.getAbbreviation()));
		assertEquals(female, Gender.fromValue(female.getAbbreviation()));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.enums.Gender#fromValue(char)} which checks
	 * that an exception is thrown on wrong value.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFromValueException () {
		Gender.fromValue(noEnum);
	}
	
}
