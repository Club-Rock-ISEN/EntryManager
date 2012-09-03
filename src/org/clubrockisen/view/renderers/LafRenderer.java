package org.clubrockisen.view.renderers;

import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Renderer for Look and Feel combo box.<br />
 * @author Alex
 */
public class LafRenderer extends CustomTextRenderer<LookAndFeelInfo> {
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.view.renderers.CustomTextRenderer#getText(java.lang.Object)
	 */
	@Override
	protected String getText (final LookAndFeelInfo value) {
		return value.getName();
	}
	
}
