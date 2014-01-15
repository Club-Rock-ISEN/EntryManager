package org.clubrockisen.view.renderers;

import org.clubrockisen.service.abstracts.ServiceFactory;

import com.alexrnl.commons.gui.swing.renderers.ListCellTextRenderer;

/**
 * List renderer for any enumeration.<br />
 * Provide the translation in the defined locale.
 * @author Alex
 */
public class CustomEnumRenderer extends ListCellTextRenderer<Enum<?>> {
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.renderers.CustomTextRenderer#getText(java.lang.Object)
	 */
	@Override
	protected String getText (final Enum<?> value) {
		return ServiceFactory.getImplementation().getTranslator().get(value);
	}
	
}
