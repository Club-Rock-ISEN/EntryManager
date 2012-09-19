package org.clubrockisen.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which provides an easy way to compare attributes of a same object.<br />
 * This class also contains a utility method to compute the hash code of a list of objects.
 * @author Alex
 */
public class AttributeComparator {
	/** Logger */
	private static Logger		lg					= Logger.getLogger(AttributeComparator.class.getName());
	
	
	/**
	 * Represent a couple of attributes of the same type.
	 * @author Alex
	 * @param <T>
	 *        the type of the attributes compared.
	 */
	public static class Attributes<T> {
		/** The 'left' attribute */
		private final T	left;
		/** The 'right' attribute */
		private final T	right;
		
		/**
		 * Constructor #.<br />
		 * @param left
		 *        the left attribute.
		 * @param right
		 *        the right attribute.
		 */
		public Attributes (final T left, final T right) {
			super();
			this.left = left;
			this.right = right;
		}
		
		/**
		 * Compare both attributes and returns <code>true</code> if the attributes are equals.
		 * Uses the {@link Object#equals(Object)} method to compare the objects.
		 * @return <code>true</code> if both objects are equals.
		 */
		public boolean areEquals () {
			return areEquals(left, right);
		}
		
		/**
		 * Compare both attributes and returns <code>true</code> if the attributes are equals.
		 * @param left
		 *        the left attribute.
		 * @param right
		 *        the right attribute.
		 * @return <code>true</code> if both objects are equals.
		 */
		public static <T> boolean areEquals(final T left, final T right) {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Comparing " + left + " and " + right);
			}
			// Null cases
			if (left == null) {
				if (right != null) {
					return false;
				}
				// Both are null
			} else if (!left.equals(right)) { // Regular cases
				return false;
			}
			return true;
		}
	}
	
	/** List of the attributes to be compared */
	private final List<Attributes<?>> attributes;
	
	/**
	 * Constructor #1.<br />
	 */
	public AttributeComparator () {
		super();
		attributes = new ArrayList<>();
	}
	
	/**
	 * Add attributes to be compared.
	 * @param left
	 *        the left attribute.
	 * @param right
	 *        the right attribute.
	 */
	public <T> void add (final T left, final T right) {
		add(new Attributes<>(left, right));
	}
	
	/**
	 * Add a new attribute to be compared.
	 * @param attribute
	 *        the attribute to add.
	 */
	public <T> void add (final Attributes<T> attribute) {
		attributes.add(attribute);
	}
	
	/**
	 * Clear the list of attributes.<br />
	 * Allow to use the comparator for other purposes.
	 */
	public void clear () {
		attributes.clear();
	}
	
	/**
	 * Return the current number of attributes to be compared.
	 * @return the number of attributes.
	 */
	public int getAttributeNumber () {
		return attributes.size();
	}
	
	/**
	 * Compare the attributes provided.
	 * @return <code>true</code> if all the attributes are equals.
	 */
	public boolean areEquals () {
		for (final Attributes<?> attribute : attributes) {
			if (!attribute.areEquals()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param nullValue
	 *        the value to use when the attribute is <code>null</code>.
	 * @param prime
	 *        the prime value to use for the hash code.
	 * @return the hash code for the given set of attributes.
	 */
	public static int hashCode (final int nullValue, final int prime, final Iterable<Object> attributes) {
		int result = 1;
		for (final Object object : attributes) {
			result = prime * result + (object == null ? nullValue : object.hashCode());
		}
		return result;
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param nullValue
	 *        the value to use when the attribute is <code>null</code>.
	 * @param prime
	 *        the prime value to use for the hash code.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final int nullValue, final int prime, final Object[] attributes) {
		return hashCode(nullValue, prime, Arrays.asList(attributes));
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param nullValue
	 *        the value to use when the attribute is <code>null</code>.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final int nullValue, final Iterable<Object> attributes) {
		return hashCode(nullValue, Constants.PRIME_FOR_HASHCODE, attributes);
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param nullValue
	 *        the value to use when the attribute is <code>null</code>.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final int nullValue, final Object[] attributes) {
		return hashCode(nullValue, Arrays.asList(attributes));
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final Iterable<Object> attributes) {
		return hashCode(0, attributes);
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final Object[] attributes) {
		return hashCode(Arrays.asList(attributes));
	}
	
	/**
	 * Check if the object specified is an instance of the specified class.
	 * @param obj
	 *        the object to check.
	 * @param objClass
	 *        the class the object is suppose to be an instance of.
	 * @return <code>true</code> if the object is an instance of the class specified.
	 */
	public static boolean checkInstance (final Object obj, final Class<?> objClass) {
		return objClass.isInstance(obj);
	}
	
}
