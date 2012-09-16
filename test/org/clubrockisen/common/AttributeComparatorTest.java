package org.clubrockisen.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.clubrockisen.common.AttributeComparator.Attributes;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link AttributeComparator} class.
 * @author Alex
 */
public class AttributeComparatorTest {
	/** List of left attributes to be compared */
	private final List<Object> left = new ArrayList<>();
	/** List of right attributes to be compared */
	private final List<Object> right = new ArrayList<>();
	
	/**
	 * Set up attributes for test cases.
	 */
	@Before
	public void setUp () {
		left.add(true);
		right.add(true);
		left.add(8);
		right.add(8);
		left.add(null);
		right.add(null);
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.AttributeComparator#add(Object, Object)}.
	 */
	@Test
	public void testAddObjectObject () {
		final AttributeComparator ac = new AttributeComparator();
		assertEquals(0, ac.getAttributeNumber());
		ac.add(left, right);
		assertEquals(1, ac.getAttributeNumber());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.AttributeComparator#add(Attributes)}.
	 */
	@Test
	public void testAddAttributes () {
		final AttributeComparator ac = new AttributeComparator();
		assertEquals(0, ac.getAttributeNumber());
		ac.add(new Attributes<>(left, right));
		assertEquals(1, ac.getAttributeNumber());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.AttributeComparator#clear()}.
	 */
	@Test
	public void testClear () {
		final AttributeComparator ac = new AttributeComparator();
		ac.add(new Attributes<>(left, right));
		assertEquals(1, ac.getAttributeNumber());
		ac.clear();
		assertEquals(0, ac.getAttributeNumber());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.AttributeComparator#areEquals()}.
	 */
	@Test
	public void testAreEquals () {
		final AttributeComparator attributeComparator = new AttributeComparator();
		for (int index = 0; index < left.size(); ++index) {
			attributeComparator.add(left.get(index), right.get(index));
		}
		assertTrue(attributeComparator.areEquals());
		
		attributeComparator.add(null, false);
		assertFalse(attributeComparator.areEquals());
		
		attributeComparator.clear();
		attributeComparator.add(true, false);
		assertFalse(attributeComparator.areEquals());
	}
	
	/**
	 * Test method for {@link org.clubrockisen.common.AttributeComparator#checkInstance(Object, Class)}.
	 */
	@Test
	public void testCheckInstance () {
		assertFalse(AttributeComparator.checkInstance(null, Object.class));
		assertFalse(AttributeComparator.checkInstance(left, Set.class));
		assertTrue(AttributeComparator.checkInstance(left, ArrayList.class));
	}
	
}
