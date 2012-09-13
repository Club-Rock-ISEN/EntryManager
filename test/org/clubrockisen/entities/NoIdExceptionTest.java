package org.clubrockisen.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test suite for the {@link NoIdException} class.
 * @author Alex
 */
public class NoIdExceptionTest {
	
	/** Exception with no entity information */
	private final NoIdException	unknownException = new NoIdException();
	/** Exception with the entity that generated the problem */
	private final NoIdException	memberException = new NoIdException(Member.class);
	
	/**
	 * Test method for {@link org.clubrockisen.entities.NoIdException#getEntityClass()}.
	 */
	@Test
	public void testGetEntityClass () {
		assertEquals(Entity.class, unknownException.getEntityClass());
		assertEquals(Member.class, memberException.getEntityClass());
	}
	
	/**
	 * Test method for {@link java.lang.Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertEquals("Could not find id column in entity Entity", unknownException.getMessage());
		assertEquals("Could not find id column in entity Member", memberException.getMessage());
	}
}
