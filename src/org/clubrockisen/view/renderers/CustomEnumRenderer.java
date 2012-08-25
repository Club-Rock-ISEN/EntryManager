package org.clubrockisen.view.renderers;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.clubrockisen.service.abstracts.ServiceFactory;

/**
 * List renderer for any enumeration.<br />
 * Provide the translation in the defined locale.
 * @author Alex
 */
public class CustomEnumRenderer implements ListCellRenderer<Enum<?>>  {
	/** Logger */
	private static Logger		lg	= Logger.getLogger(CustomEnumRenderer.class.getName());
	
	/** The first renderer to use (avoid shrinking) */
	private final DefaultListCellRenderer renderer;
	
	/**
	 * Constructor #1.<br />
	 */
	public CustomEnumRenderer () {
		super();
		if (lg.isLoggable(Level.FINE)) {
			lg.fine(this.getClass().getName() + " created.");
		}
		renderer = new DefaultListCellRenderer();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList,
	 * java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent (final JList<? extends Enum<?>> list,
			final Enum<?> value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		final JLabel component = (JLabel) renderer.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);
		
		component.setText(ServiceFactory.getImplementation().getTranslator().get(value));
		
		return component;
	}
}
