package org.clubrockisen.dao;

/**
 * Basic CRUD operations on an abstract object.
 * @author Alex
 * @param <T>
 *            The class of the object to manipulate.
 */
public abstract interface DAO<T> {

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
}
