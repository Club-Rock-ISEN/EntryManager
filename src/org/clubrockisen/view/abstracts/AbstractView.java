package org.clubrockisen.view.abstracts;

import java.beans.PropertyChangeEvent;

import org.clubrockisen.controller.abstracts.AbstractController;

/**
 * Abstract class for views.<br />
 * This allows any views (which extends this class) to be controlled by an
 * {@link AbstractController}.
 * @author Alex
 */
public interface AbstractView {
	
	/**
	 * Called by the controller when it needs to pass along a property change
	 * from a model.
	 * 
	 * @param evt
	 *        The property change event from the model
	 */
	void modelPropertyChange (PropertyChangeEvent evt);
}
