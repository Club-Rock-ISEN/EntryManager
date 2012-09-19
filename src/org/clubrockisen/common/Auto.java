package org.clubrockisen.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO
 * @author Alex
 */
public final class Auto {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(Auto.class.getName());
	
	/** Unique instance of the class */
	private static Auto singleton = new Auto();
	
	/** Method for equals per class */
	private final Map<Class<?>, Set<Method>> equalsMethods;
	/** Method for hash code per class */
	private final Map<Class<?>, Set<Method>> hashCodeMethods;
	
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private Auto () {
		super();
		equalsMethods = new HashMap<>();
		hashCodeMethods = new HashMap<>();
	}
	
	/**
	 * Return the unique instance of the class.
	 * @return the singleton.
	 */
	public static Auto getInstance () {
		return singleton;
	}
	
	/**
	 * Retrieve the methods used to compare objects.
	 * @param objClass
	 *        the class of the objects.
	 * @return the set with the equals methods.
	 */
	private Set<Method> getEqualsMethods (final Class<?> objClass) {
		if (!equalsMethods.containsKey(objClass)) {
			retrieveMethods(objClass);
		}
		return equalsMethods.get(objClass);
	}
	
	/**
	 * Retrieve the methods used to compare objects.
	 * @param objClass
	 *        the class of the objects.
	 * @return the set with the equals methods.
	 */
	private Set<Method> getHashCodeMethods (final Class<?> objClass) {
		if (!hashCodeMethods.containsKey(objClass)) {
			retrieveMethods(objClass);
		}
		return hashCodeMethods.get(objClass);
	}
	
	/**
	 * Retrieve the equals and hash code methods for the class specified and put it in the map.<br />
	 * Override value already present in the map.
	 * @param objClass
	 *        the class to parse.
	 */
	private void retrieveMethods (final Class<?> objClass) {
		final Set<Method> equals = new HashSet<>();
		final Set<Method> hashCode = new HashSet<>();
		for (final Method method : objClass.getMethods()) {
			final Annotation annotation = method.getAnnotation(Comparable.class);
			if (annotation instanceof Comparable) {
				if (lg.isLoggable(Level.FINE)) {
					lg.fine("method: " + method.getName());
				}
				equals.add(method);
				if (((Comparable) annotation).useForHashCode()) {
					hashCode.add(method);
				}
			}
		}
		equalsMethods.put(objClass, equals);
		hashCodeMethods.put(objClass, hashCode);
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
	public <T> boolean compare (final T left, final T right) {
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
		
		// Retrieving attributes
		final AttributeComparator comparator = new AttributeComparator();
		for (final Method method : getEqualsMethods(left.getClass())) {
			try {
				comparator.add(method.invoke(left, (Object[]) null), method.invoke(right, (Object[]) null));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				lg.warning("Could not add the value of the method " + method.getName() + " because "
						+ e.getClass() + "; " + e.getMessage());
			}
		}
		
		return comparator.areEquals();
	}
	
	/**
	 * Compute the hash code of an object using the values of the methods marked {@link Comparable}
	 * with the use for hash code parameter set at <code>true</code>.
	 * @param obj
	 *        the object used to compute the hash code.
	 * @return the hash code for the specified object.
	 */
	public int hashCode (final Object obj) {
		final List<Object> attributes = new ArrayList<>();
		for (final Method method : getHashCodeMethods(obj.getClass())) {
			try {
				if (lg.isLoggable(Level.FINE)) {
					lg.fine("method: " + method.getName());
				}
				attributes.add(method.invoke(obj, (Object[]) null));
			} catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				lg.warning("Could not add the value of the method " + method.getName() + " because "
						+ e.getClass() + "; " + e.getMessage());
			}
		}
		return AttributeComparator.hashCode(attributes);
	}
}
