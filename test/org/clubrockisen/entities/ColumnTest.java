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
		assertEquals(idColumn.getType(), Integer.class);
		assertEquals(otherColumn.getType(), String.class);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#setType(java.lang.Class)}.
	 */
	@Test
	public void testSetType () {
		idColumn.setType(Double.class);
		otherColumn.setType(Date.class);
		assertEquals(idColumn.getType(), Double.class);
		assertEquals(otherColumn.getType(), Date.class);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#getName()}.
	 */
	@Test
	public void testGetName () {
		assertEquals(idColumn.getName(), "id");
		assertEquals(otherColumn.getName(), "name");
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName () {
		idColumn.setName("price");
		otherColumn.setName("date");
		assertEquals(idColumn.getName(), "price");
		assertEquals(otherColumn.getName(), "date");
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#isID()}.
	 */
	@Test
	public void testIsID () {
		assertEquals(idColumn.isID(), true);
		assertEquals(otherColumn.isID(), false);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#setID(boolean)}.
	 */
	@Test
	public void testSetID () {
		idColumn.setID(false);
		otherColumn.setID(true);
		assertEquals(idColumn.isID(), false);
		assertEquals(otherColumn.isID(), true);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals(idColumn.toString(), "name: id, type: Integer, is id: true");
		assertEquals(otherColumn.toString(), "name: name, type: String, is id: false");
	}
}
