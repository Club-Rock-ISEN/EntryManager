package org.clubrockisen.controller.abstracts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.Constants;
import org.clubrockisen.model.abstracts.AbstractModel;
import org.clubrockisen.view.abstracts.AbstractView;

/**
 * Abstract class which defines the default behavior for generic controllers.<br />
 * Allows to register several {@link AbstractView views} and {@link AbstractModel models}, the
 * controller is in charge of the communications between the views and the model that they display.
 * @author Alex
 */
public abstract class AbstractController implements PropertyChangeListener {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(AbstractController.class.getName());
	
	/** The models represented by the views */
	private final List<AbstractModel>	registeredModels;
	/** The views to be updated by the models */
	private final List<AbstractView>	registeredViews;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public AbstractController () {
		super();
		registeredModels = new LinkedList<>();
		registeredViews = new LinkedList<>();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange (final PropertyChangeEvent evt) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("property changed in model: " + evt.getPropertyName() + " = " + evt.getNewValue()
					+ " (" + evt.getOldValue() + ")");
		}
		for (final AbstractView view : registeredViews) {
			view.modelPropertyChange(evt);
		}
	}
	
	/**
	 * Add a model and subscribe to its modifications.
	 * @param model
	 *        the model to add to the controller.
	 */
	public void addModel (final AbstractModel model) {
		registeredModels.add(model);
		model.addModelChangeListener(this);
	}
	
	/**
	 * Remove a model and unsubscribe to its modifications.
	 * @param model
	 *        the model to remove.
	 */
	public void removeModel (final AbstractModel model) {
		registeredModels.remove(model);
		model.removeModelListener(this);
	}
	
	/**
	 * Add a view to notify on models changes.
	 * @param view
	 *        the view to notify.
	 */
	public void addView (final AbstractView view) {
		registeredViews.add(view);
	}
	
	/**
	 * Remove a view from the controller and thus will not receive any future property changes.
	 * @param view
	 *        the view to remove from future notifications.
	 */
	public void removeView (final AbstractView view) {
		registeredViews.remove(view);
	}
	
	/**
	 * Utility method for setting property on models
	 * @param propertyName
	 *        the name of the property to update (used in the setter).
	 * @param newValue
	 *        the value to set, of the appropriate class.
	 */
	public void setModelProperty (final String propertyName, final Object newValue) {
		for (final AbstractModel model : registeredModels) {
			try {
				final Method method = model.getClass().getMethod(Constants.SETTER_PREFIX + propertyName,
						new Class<?>[] { newValue.getClass() });
				method.invoke(model, newValue);
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				lg.warning("Error while calling setter in " + model.getClass().getSimpleName()
						+ " for property " + propertyName + " (" + e.getClass() + "; "
						+ e.getMessage() + ")");
			} catch (final NoSuchMethodException e) {
				// This is a "normal" exception (model does not have the property to update)
				lg.info("Model " + model.getClass().getSimpleName() + " has no setter for " +
						"property " + propertyName + " (" + e.getMessage() + ")");
			}
		}
	}
	
	/**
	 * Dispose of the resources used by the controller.<br />
	 * Clear frames, window, unsubscribe models and listeners, etc.
	 */
	public abstract void dispose ();
	
	/**
	 * Persists any changes to the database.<br />
	 * @return <code>true</code> if the operation succeeded.
	 */
	public abstract boolean persist ();
	
	/**
	 * Reload the models registered from the database.<br />
	 */
	public abstract void reload ();
}
