package org.clubrockisen.view.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.clubrockisen.common.Constants;
import org.clubrockisen.common.TranslationKeys;
import org.clubrockisen.view.abstracts.AbstractFrame;

/**
 * Panel with a cancel and a validate button.<br />
 * The action on cancel button will hide the parent frame.
 * @author Alex
 */
public class ValidateCancelPanel extends JPanel implements ActionListener {
	/** Logger */
	private static Logger							lg	= Logger.getLogger(ValidateCancelPanel.class.getName());
	
	/** Serial version UID */
	private static final long						serialVersionUID	= -1500794156358784997L;
	
	// GUI Components
	/** Button for validating changes on a member */
	private final JButton							validateButton;
	/** Button for canceling changes on a member */
	private final JButton							cancelButton;
	
	// Miscellaneous
	/** List of the listener for the panel */
	private final transient List<ActionListener>	actionListeners;
	/** The parent frame to be hidden on cancel */
	private final AbstractFrame						parentFrame;
	
	/**
	 * Constructor #1.<br />
	 * @param frame
	 *        the parent frame to be hidden on cancel.
	 */
	public ValidateCancelPanel (final AbstractFrame frame) {
		super();
		// Initialization
		this.parentFrame = frame;
		actionListeners = new ArrayList<>();
		cancelButton = new JButton(frame.getTranslator().get(TranslationKeys.MISC.cancel()));
		validateButton = new JButton(frame.getTranslator().get(TranslationKeys.MISC.ok()));
		
		// Creating layout, and adding buttons
		final Box hBox = new Box(BoxLayout.LINE_AXIS);
		hBox.add(cancelButton);
		hBox.add(Box.createRigidArea(new Dimension(Constants.DEFAULT_COMPONENT_GAP, 0)));
		hBox.add(validateButton);
		this.add(hBox);
		
		// Adding listener
		cancelButton.addActionListener(this);
	}
	
	/**
	 * Add the listener to the panel.<br />
	 * Note that only the action on the validate button will be forwarded to the listener.
	 * @param listener
	 *        the listener to be added.
	 */
	public void addActionListener (final ActionListener listener) {
		if (actionListeners.isEmpty()) {
			validateButton.addActionListener(this);
		}
		actionListeners.add(listener);
	}
	
	/**
	 * Remove the listener from the panel.
	 * @param listener
	 *        the listener to be removed.
	 */
	public void removeActionListener (final ActionListener listener) {
		actionListeners.remove(listener);
		if (actionListeners.isEmpty()) {
			validateButton.removeActionListener(this);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed (final ActionEvent e) {
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Action on panel: " + e);
		}
		
		if (e.getSource().equals(cancelButton)) {
			parentFrame.setVisible(false);
		} else if (e.getSource().equals(validateButton)) {
			for (final ActionListener listener : actionListeners) {
				listener.actionPerformed(e);
			}
		} else {
			lg.warning("Unknown source of action for the event " + e.getSource());
		}
	}
	
}
