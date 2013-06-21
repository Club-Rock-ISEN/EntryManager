package org.clubrockisen.view.renderers;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SwingConstants;

/**
 * Renderer for a JSpinner.<br />
 * This is a mere extension of the {@link DefaultEditor}, but the field is editable and the current
 * value is aligned at the end of the line.
 * @author Alex
 */
public class SpinnerEditableRenderer extends DefaultEditor {
	/** Logger */
	private static Logger						lg					= Logger.getLogger(SpinnerEditableRenderer.class.getName());
	
	/** Serial Version UID */
	private static final long					serialVersionUID	= 8882515327265286267L;
	
	/**
	 * Constructor #1.<br />
	 * @param spinner
	 *        the spinner to render.
	 */
	public SpinnerEditableRenderer (final JSpinner spinner) {
		super(spinner);
		getTextField().setHorizontalAlignment(SwingConstants.TRAILING);
		getTextField().setEditable(true);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Create editable field for spinner");
		}
	}
	
}
