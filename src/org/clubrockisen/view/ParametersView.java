package org.clubrockisen.view;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.clubrockisen.view.abstracts.AbstractView;

/**
 * TODO
 * @author Alex
 */
public class ParametersView extends JFrame implements AbstractView {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(ParametersView.class.getName());
	
	/** Serial Version UID */
	private static final long	serialVersionUID	= -685243639858300613L;
	
	/**
	 * Constructor #1.<br />
	 */
	public ParametersView () {
		super();
		
	}
	/* (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractView#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange (final PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		lg.info("Property changed: " + evt);
	}
}
