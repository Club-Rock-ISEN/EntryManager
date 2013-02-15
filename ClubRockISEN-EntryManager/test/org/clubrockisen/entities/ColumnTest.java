package org.clubrockisen.entities;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		final Column newIdColumn = new Column(idColumn.getType(), idColumn.getName(), idColumn.isID());
		assertNotSame(idColumn, newIdColumn);
		assertEquals(idColumn.hashCode(), newIdColumn.hashCode());
		
		// Check that hash code changes on any field update
		newIdColumn.setID(!idColumn.isID());
		assertThat(idColumn.hashCode(), not(newIdColumn.hashCode()));
		
		newIdColumn.setID(idColumn.isID());
		newIdColumn.setType(Number.class);
		assertThat(idColumn.hashCode(), not(newIdColumn.hashCode()));
		
		newIdColumn.setType(idColumn.getType());
		newIdColumn.setName(idColumn.getName() + "//");
		assertThat(idColumn.hashCode(), not(newIdColumn.hashCode()));
		
		newIdColumn.setName(idColumn.getName());
		newIdColumn.setType(null);
		assertThat(idColumn.hashCode(), not(newIdColumn.hashCode()));
		
		newIdColumn.setType(idColumn.getType());
		newIdColumn.setName(null);
		assertThat(idColumn.hashCode(), not(newIdColumn.hashCode()));
	}
	
	/**
	 * Test method for {@link org.clubrockisen.entities.Column#equals(Object)}.
	 */
	@Test
	public void testEquals () {
		final Column newIdColumn = new Column(idColumn.getType(), idColumn.getName(), idColumn.isID());
		final Column newOtherColumn = new Column(otherColumn.getType(), otherColumn.getName(), otherColumn.isID());
		
		assertFalse(idColumn.equals(null));
		assertTrue(idColumn.equals(idColumn));
		assertFalse(idColumn.equals(new Object()));
		
		assertEquals(idColumn, newIdColumn);
		assertEquals(otherColumn, newOtherColumn);
		
		newIdColumn.setID(!idColumn.isID());
		newOtherColumn.setName(otherColumn.getName() + "./");
		assertThat(idColumn, not(newIdColumn));
		assertThat(otherColumn, not(newIdColumn));
		
		newIdColumn.setID(idColumn.isID());
		newIdColumn.setName(idColumn.getName() + "..");
		newOtherColumn.setName(otherColumn.getName());
		newOtherColumn.setType(null);
		assertThat(idColumn, not(newIdColumn));
		assertThat(otherColumn, not(newOtherColumn));
		
		newIdColumn.setName(idColumn.getName());
		newIdColumn.setType(Object.class);
		newOtherColumn.setType(otherColumn.getType());
		newOtherColumn.setName(null);
		assertThat(idColumn, not(newIdColumn));
		assertThat(otherColumn, not(newOtherColumn));
		
		newOtherColumn.setType(null);
		assertThat(new Column(), not(idColumn));
		assertEquals(new Column(), newOtherColumn);
	}
}
