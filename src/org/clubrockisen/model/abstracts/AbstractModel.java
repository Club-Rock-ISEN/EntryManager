package org.clubrockisen.model.abstracts;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class for the model of the data to display.<br />
 * {@link PropertyChangeListener Listener} may subscribe to the model using the
 * {@link #addModelChangeListener(PropertyChangeListener)} method.
 * @author Alex
 */
public abstract class AbstractModel {
	/** Logger */
	private static Logger			lg	= Logger.getLogger(AbstractModel.class.getName());
	
	/** The observable object */
	private final PropertyChangeSupport	observable;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public AbstractModel () {
		observable = new PropertyChangeSupport(this);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Created a new " + this.getClass().getSimpleName());
		}
	}
	
	/**
	 * Adds a listener to the model.
	 * @param listener
	 *        the object to notify when a property is changed.
	 */
	public void addModelChangeListener (final PropertyChangeListener listener) {
		observable.addPropertyChangeListener(listener);
	}
	
	/**
	 * Removes a listener from the model.
	 * @param listener
	 *        the listener to remove.
	 */
	public void removeModelListener (final PropertyChangeListener listener) {
		observable.removePropertyChangeListener(listener);
	}
	
	/**
	 * Notify listener that a property has been updated.
	 * @param <T> the type of the property (avoid providing two different types to the method).
	 * @param propertyName
	 *        the name of the property.
	 * @param oldValue
	 *        its old value.
	 * @param newValue
	 *        its new value.
	 */
	protected <T> void fireModelChange (final String propertyName, final T oldValue,
			final T newValue) {
		observable.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	/**
	 * Persists the current model in the database.<br />
	 * Create or update operation, depending on the state of the entity in the database.
	 * @return <code>true</code> if the operation has succeeded.
	 */
	public abstract boolean persist ();
	
	/**
	 * Reload the model with the information from the database and fire refresh messages.<br />
	 */
	public abstract void reload ();
}
