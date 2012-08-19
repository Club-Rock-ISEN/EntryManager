package org.clubrockisen.entities;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Column} class.
 * @author Alex
 */
public class ColumnTest {
	
	/** A column representing an id column */
	private Column idColumn;
	/** A column which is not and id column */
	private Column otherColumn;
	
	/**
	 * Build the column required for the tests.
	 */
	@Before
	public void setUp () {
		idColumn = new Column(Integer.class, "id", true);
		otherColumn = new Column(String.class, "name");
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#getType()}.
	 */
	@Test
	public void testGetType () {
		assertEquals(Integer.class, idColumn.getType());
		assertEquals(String.class, otherColumn.getType());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#setType(java.lang.Class)}.
	 */
	@Test
	public void testSetType () {
		idColumn.setType(Double.class);
		otherColumn.setType(Date.class);
		assertEquals(Double.class, idColumn.getType());
		assertEquals(Date.class, otherColumn.getType());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#getName()}.
	 */
	@Test
	public void testGetName () {
		assertEquals("id", idColumn.getName());
		assertEquals("name", otherColumn.getName());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName () {
		idColumn.setName("price");
		otherColumn.setName("date");
		assertEquals("price", idColumn.getName());
		assertEquals("date", otherColumn.getName());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#isID()}.
	 */
	@Test
	public void testIsID () {
		assertEquals(true, idColumn.isID());
		assertEquals(false, otherColumn.isID());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#setID(boolean)}.
	 */
	@Test
	public void testSetID () {
		idColumn.setID(false);
		otherColumn.setID(true);
		assertEquals(false, idColumn.isID());
		assertEquals(true, otherColumn.isID());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals("name: id, type: Integer, is id: true", idColumn.toString());
		assertEquals("name: name, type: String, is id: false", otherColumn.toString());
	}
}
