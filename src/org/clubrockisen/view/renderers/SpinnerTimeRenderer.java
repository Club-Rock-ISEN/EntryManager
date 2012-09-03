package org.clubrockisen.view.renderers;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;

import org.clubrockisen.common.Time;

/**
 * Renderer for {@link Time}'s Spinner.<br />
 * FIXME not usable.
 * @author Alex
 */
public class SpinnerTimeRenderer extends DefaultEditor {
	/** Logger */
	private static Logger						lg					= Logger.getLogger(SpinnerTimeRenderer.class.getName());
	
	/** Serial Version UID */
	private static final long					serialVersionUID	= 8882515327265286267L;
	
	/**
	 * Constructor #1.<br />
	 * @param spinner
	 *        the spinner to render.
	 */
	public SpinnerTimeRenderer (final JSpinner spinner) {
		super(spinner);
		getTextField().setEditable(true);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Create editor for time spinner");
		}
	}
	
	
	
}
