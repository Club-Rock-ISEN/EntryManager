package org.clubrockisen.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link NoIdException} class.
 * @author Alex
 */
public class NoIdExceptionTest {
	
	/** Exception with the entity that generated the problem */
	private NoIdException	memberException;
	/** Exception with no entity information */
	private NoIdException	unknownException;
	
	/**
	 * Build the exception which will be used in the tests.
	 */
	@Before
	public void setUp () {
		memberException = new NoIdException(Member.class);
		unknownException = new NoIdException();
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.NoIdException#getEntityClass()}.
	 */
	@Test
	public void testGetEntityClass () {
		assertEquals(Member.class, memberException.getEntityClass());
		assertNull(unknownException.getEntityClass());
	}
	
	/**
	 * Test method for {@link java.lang.Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertEquals("Could not find id column in entity Member", memberException.getMessage());
		assertEquals("Could not find id column in entity unknown", unknownException.getMessage());
	}
}
