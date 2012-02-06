package org.clubrockisen.dao;

import java.util.Collection;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Entity;

/**
 * Basic CRUD operations on an abstract object.
 * @author Alex
 * @param <T>
 *            The class of the object to manipulate.
 */
public abstract interface DAO<T extends Entity> {

	/**
	 * Create operation.
	 * @param obj
	 *            the object to create.
	 * @return <code>true</code> if the creation of the object has been successful.
	 */
	boolean create (T obj);

	/**
	 * Read operation.
	 * @param id
	 *            the id of the object to retrieve.
	 * @return The object which matches the <code>id</code> or <code>null</code> if no matches had
	 *         been found.
	 */
	T find (int id);

	/**
	 * Update operation.
	 * @param obj
	 *            the object to update.
	 * @return <code>true</code> if the update of the object has been successful.
	 */
	boolean update (T obj);

	/**
	 * Delete operation.
	 * @param obj
	 *            the object to delete.
	 * @return <code>true</code> if the deletion of the object has been successful.
	 */
	boolean delete (T obj);

	/**
	 * Retrieve all the objects available.<br />
	 * <em>Use with caution.</em>
	 * @return A collection with all the objects.
	 */
	Collection<T> retrieveAll();

	/**
	 * Retrieve a collection of object matching
	 * @param field the field to search
	 * @param value the value to test
	 * @return A collection with the object matching the
	 */
	Collection<T> search (Column field, String value);
}
