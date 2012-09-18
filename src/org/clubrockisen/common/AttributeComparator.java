package org.clubrockisen.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	 * Compare two object using the return value of the methods marked as {@link Comparable}.
	 * @param left
	 *        the first object to compare.
	 * @param right
	 *        the right object to compare.
	 * @param <T>
	 *        the type of the objects to be compared
	 * @return <code>true</code> if all the attributes marked {@link Comparable} are equals.
	 */
	public static <T> boolean autoCompare (final T left, final T right) {
		// Basic cases
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (!left.getClass().equals(right.getClass())) {
			return false;
		}
		
		// Comparing attributes
		final List<Object> leftValues = new ArrayList<>();
		final List<Object> rightValues = new ArrayList<>();
		
		final Method[] methods = left.getClass().getMethods();
		for (final Method method : methods) {
			final Annotation annotation = method.getAnnotation(Comparable.class);
			if (annotation != null && annotation instanceof Comparable) {
				try { // TODO use map to remember methods for a given class Map<Class, Set<Method>>
					if (lg.isLoggable(Level.FINE)) {
						lg.fine("method: " + method.getName());
					}
					leftValues.add(method.invoke(left, (Object[]) null));
					rightValues.add(method.invoke(right, (Object[]) null));
				} catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					lg.warning("Could not add the value of the method " + method.getName() + " because "
							+ e.getClass() + "; " + e.getMessage());
				}
			}
		}
		
		for (int index = 0; index < leftValues.size(); ++index) {
			if (!Attributes.areEquals(leftValues.get(index), rightValues.get(index))) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Compute the hash code of an object using the values of the methods marked {@link Comparable}
	 * with the use for hash code parameter set at <code>true</code>.
	 * @param obj
	 *        the object used to compute the hash code.
	 * @return the hash code for the specified object.
	 */
	public static int autoHashCode (final Object obj) {
		int hashCode = 1;
		final Method[] methods = obj.getClass().getMethods();
		for (final Method method : methods) {
			final Annotation annotation = method.getAnnotation(Comparable.class);
			if (annotation != null && annotation instanceof Comparable) {
				if (((Comparable) annotation).useForHashCode()) {
					try { // TODO use map to remember methods for a given class Map<Class, Set<Method>>
						if (lg.isLoggable(Level.FINE)) {
							lg.fine("method: " + method.getName());
						}
						final Object res = method.invoke(obj, (Object[]) null);
						hashCode = Constants.PRIME_FOR_HASHCODE * hashCode + (res == null ? 0 : res.hashCode());
					} catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						lg.warning("Could not add the value of the method " + method.getName() + " because "
								+ e.getClass() + "; " + e.getMessage());
					}
				}
			}
		}
		return hashCode;
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
