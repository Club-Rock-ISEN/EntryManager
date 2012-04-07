package org.clubrockisen.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

/**
 * Abstract class for the model of the data to display.<br />
 * {@link PropertyChangeListener Listener} may subscribe to the model using the
 * {@link #addModelChangeListener(PropertyChangeListener)} method.
 * @author Alex
 */
public abstract class AbstractModel {
	private static Logger			lg	= Logger.getLogger(AbstractModel.class.getName());
	
	/**
	 * The observable object.
	 */
	protected PropertyChangeSupport	observable;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public AbstractModel () {
		observable = new PropertyChangeSupport(this);
		lg.fine("Created a new " + this.getClass().getSimpleName());
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
	 * @param propertyName
	 *        the name of the property.
	 * @param oldValue
	 *        its old value.
	 * @param newValue
	 *        its new value.
	 */
	protected void fireModelChange (final String propertyName, final Object oldValue,
			final Object newValue) {
		observable.firePropertyChange(propertyName, oldValue, newValue);
	}
}
