package org.clubrockisen.view.parameter;

import javax.swing.JComponent;

import org.clubrockisen.service.abstracts.ParametersEnum;

/**
 * Abstract class for a component displaying a parameter.
 * @author Alex
 */
public abstract class ParameterComponent {
	
	/** The parameter rendered by the component */
	private final ParametersEnum	parameter;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter the parameter
	 */
	public ParameterComponent (final ParametersEnum parameter) {
		super();
		this.parameter = parameter;
	}
	
	/**
	 * Return the parameter represented by this component.
	 * @return the parameter enumeration .
	 */
	protected ParametersEnum getParameter () {
		return parameter;
	}
	
	/**
	 * Build and return the component which will be used to display the value of the parameter.
	 * @return the component to display.
	 */
	public abstract JComponent getComponent ();
	
	/**
	 * Install an appropriate listener on the component.<br />
	 * The listener forward the event to the listener specified.
	 * @param listener
	 *        the listener to notify on the component changes.
	 */
	public abstract void installListener (ParameterChangeListener listener);
	
	/**
	 * Get the value of the parameter on the component.
	 * @return the value.
	 */
	public abstract String getValue ();
	
	/**
	 * Set the value on the component.
	 * @param value
	 *        the new value to set.
	 */
	public abstract void setValue (Object value);
	
}
