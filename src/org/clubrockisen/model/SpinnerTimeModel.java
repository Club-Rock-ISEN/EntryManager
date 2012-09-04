package org.clubrockisen.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractSpinnerModel;
import javax.swing.JSpinner;

import org.clubrockisen.common.Time;

/**
 * Model for a {@link JSpinner} on {@link Time}.<br />
 * Allow to set a value using a {@link Time} or a {@link String} which can be formatted using
 * {@link Time#get(String)}.
 * @author Alex
 */
public class SpinnerTimeModel extends AbstractSpinnerModel {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(SpinnerTimeModel.class.getName());
	
	/** Serial version UID */
	private static final long	serialVersionUID	= 5142588648167228978L;
	
	// Times
	/** The value of the model */
	private Time value;
	/** The step between two values */
	private final Time step;
	/** The maximum value reachable */
	private final Time maxValue;
	/** The minimum value reachable */
	private final Time minValue;
	
	/**
	 * Constructor #1.<br />
	 * @param value
	 *        the value of the spinner.
	 * @param step
	 *        the step.
	 * @param maxValue
	 *        the maximum time reachable.
	 * @param minValue
	 *        the minimum time reachable.
	 */
	public SpinnerTimeModel (final Time value, final Time step, final Time maxValue, final Time minValue) {
		super();
		this.value = value;
		this.step = step;
		this.maxValue = maxValue;
		this.minValue = minValue;
		if (lg.isLoggable(Level.INFO)) {
			lg.info(this.getClass().getName() + " created");
		}
	}
	
	/**
	 * Constructor #2.<br />
	 * @param value
	 *        the value of the spinner.
	 * @param maxValue
	 *        the maximum time reachable.
	 * @param minValue
	 *        the minimum time reachable.
	 */
	public SpinnerTimeModel (final Time value, final Time maxValue, final Time minValue) {
		this(value, new Time(0, 1), maxValue, minValue);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.SpinnerModel#getValue()
	 */
	@Override
	public Object getValue () {
		return value;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.SpinnerModel#setValue(java.lang.Object)
	 */
	@Override
	public void setValue (final Object value) {
		if ((value == null) || (!(value instanceof Time) && !(value instanceof String))) {
			throw new IllegalArgumentException("Illegal value for time " + value);
		}
		if (!value.equals(this.value)) {
			if (value instanceof Time) {
				this.value = (Time) value;
			} else {
				this.value = Time.get((String) value);
			}
			fireStateChanged();
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.SpinnerModel#getNextValue()
	 */
	@Override
	public Object getNextValue () {
		return incrTime(1);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.SpinnerModel#getPreviousValue()
	 */
	@Override
	public Object getPreviousValue () {
		return incrTime(-1);
	}
	
	/**
	 * Increment the current Time by one step in the direction specified.
	 * @param dir
	 *        direction of the step.
	 * @return the new time to be set.
	 */
	private Time incrTime (final int dir) {
		Time newValue = null;
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("incrTime in dir " + dir + " (value: " + value + ")");
		}
		
		// Changing value
		if (dir > 0) {
			newValue = value.add(step);
		} else if (dir < 0) {
			newValue = value.sub(step);
		} else {
			throw new IllegalArgumentException("Direction value " + dir + " is not valid.");
		}
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("newValue: " + newValue);
		}
		
		// Bound checking
		if ((maxValue != null) && (maxValue.compareTo(newValue) < 0)) {
			return null;
		}
		if ((minValue != null) && (minValue.compareTo(newValue) > 0)) {
			return null;
		}
		
		return newValue;
	}
	
}