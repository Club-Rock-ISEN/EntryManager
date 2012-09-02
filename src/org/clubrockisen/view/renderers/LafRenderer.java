package org.clubrockisen.view.renderers;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Renderer for Look and Feel combo box.
 * @author Alex
 */
public class LafRenderer implements ListCellRenderer<LookAndFeelInfo> {
	/** Logger */
	private static Logger					lg	= Logger.getLogger(LafRenderer.class.getName());
	
	/** The first renderer to use (avoid shrinking) */
	private final DefaultListCellRenderer	renderer;
	
	/**
	 * Constructor #1.<br />
	 */
	public LafRenderer () {
		super();
		if (lg.isLoggable(Level.FINE)) {
			lg.fine(this.getClass().getName() + " created.");
		}
		renderer = new DefaultListCellRenderer();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList,
	 * java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent (final JList<? extends LookAndFeelInfo> list,
			final LookAndFeelInfo value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		final JLabel comp = (JLabel) renderer.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);
		comp.setText(value.getName());
		return comp;
	}
}
