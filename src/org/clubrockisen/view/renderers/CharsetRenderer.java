package org.clubrockisen.view.renderers;

import java.awt.Component;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JList;

import org.clubrockisen.common.Constants;

/**
 * This renderer highlight a defined set of strings.<br />
 * 
 * @author Alex
 */
public class CharsetRenderer extends CustomTextRenderer<String> {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(CharsetRenderer.class.getName());
	
	/** The collection of strings to highlight */
	private final Collection<String>	toHighlight;
	
	/**
	 * Constructor #1.<br />
	 * @param toHighlight
	 *        the collection of strings to highlight.
	 */
	public CharsetRenderer (final Collection<String> toHighlight) {
		super();
		this.toHighlight = toHighlight;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.view.renderers.CustomTextRenderer#getListCellRendererComponent(javax.swing
	 * .JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent (final JList<? extends String> list,
			final String value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		final JLabel component = (JLabel) super.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);
		
		if (toHighlight.contains(component.getText()) && !isSelected) {
			component.setBackground(Constants.HIGHLIGHT_COLOR);
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Highlighting " + component.getText());
			}
		}
		
		return component;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.renderers.CustomTextRenderer#getText(java.lang.Object)
	 */
	@Override
	protected String getText (final String value) {
		return value;
	}
}
