package org.clubrockisen.common;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Annotation} which allows to tag a method as 'comparable' and thus be used in the generic
 * comparator.
 * @author Alex
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Comparable {
	/**
	 * <code>true</code> if the return value of the method should be used for hash code computation.
	 */
	boolean useForHashCode() default false;
}
