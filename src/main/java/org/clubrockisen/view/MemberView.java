package org.clubrockisen.view;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.clubrockisen.common.Constants;
import org.clubrockisen.common.TranslationKeys;
import org.clubrockisen.controller.abstracts.MemberController;
import org.clubrockisen.view.abstracts.EntryManagerFrame;
import org.clubrockisen.view.components.MemberPanel;
import org.clubrockisen.view.components.ValidateCancelPanel;

import com.alexrnl.commons.gui.swing.SwingUtils;

/**
 * View which displays a panel to update a member.<br />
 * @author Alex
 */
public class MemberView extends EntryManagerFrame {
	/** Serial Version UID */
	private static final long			serialVersionUID	= 5754628770258165084L;
	
	// Swing GUI elements
	/** The member panel */
	private MemberPanel					memberPanel;
	/** The validate / cancel panel */
	private ValidateCancelPanel			validateCancelPanel;
	
	// Miscellaneous
	/** Controller to use when view changes */
	private transient MemberController	controller;
	
	/**
	 * Constructor #1.<br />
	 * @param controller
	 *        the controller to warn when the changes are applied.
	 */
	public MemberView (final MemberController controller) {
		super(TranslationKeys.ENTITY.member().toString(), controller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.EntryManagerFrame#preInit(java.lang.Object[])
	 */
	@Override
	protected void preInit (final Object ... parameters) {
		controller = (MemberController) parameters[0];
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.EntryManagerFrame#build()
	 */
	@Override
	protected void build () {
		setContentPane(buildMemberPanel());
		setListeners();
	}
	
	/**
	 * Build the panel with the member information.
	 * @return the panel.
	 */
	private JPanel buildMemberPanel () {
		final JPanel pane = new JPanel(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1, 0.5,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				Constants.DEFAULT_INSETS, 0, 0);
		memberPanel = new MemberPanel(true);
		validateCancelPanel = new ValidateCancelPanel(this);
		
		pane.add(memberPanel, c);
		c.gridy = 1;
		pane.add(validateCancelPanel, c);
		
		return pane;
	}
	
	/**
	 * Set the listeners on the components.
	 */
	private void setListeners () {
		memberPanel.addListeners(controller);
		
		validateCancelPanel.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (!controller.persist()) {
					SwingUtils.showMessageDialog(getFrame(), getTranslator(),
							TranslationKeys.GUI.dialog().notPersistedMember(),
							JOptionPane.ERROR_MESSAGE, Constants.LINE_MAX_LENGTH);
				} else {
					getFrame().setVisible(false);
				}
			}
		});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractView#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange (final PropertyChangeEvent evt) {
		memberPanel.modelPropertyChange(evt);
	}
}
