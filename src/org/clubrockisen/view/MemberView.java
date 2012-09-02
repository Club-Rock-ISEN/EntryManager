package org.clubrockisen.view;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.clubrockisen.controller.abstracts.MemberController;
import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.Translator;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.view.abstracts.AbstractFrame;
import org.clubrockisen.view.renderers.CustomEnumRenderer;

/**
 * View which displays a panel to update a member.<br />
 * @author Alex
 */
public class MemberView extends AbstractFrame {
	/** Logger */
	private static Logger						lg					= Logger.getLogger(MemberView.class.getName());
	
	/** Serial Version UID */
	private static final long					serialVersionUID	= 5754628770258165084L;
	
	// Swing GUI elements
	/** Field for the member's name */
	private JTextField							nameField;
	/** Field for the member's {@link Gender} */
	private JComboBox<Gender>					genderField;
	/** Field for the member's {@link Status} */
	private JComboBox<Status>					statusField;
	/** Field for the member's entries */
	private JSpinner							entryNumberField;
	/** Field for the member's next free entry count down */
	private JSpinner							nextFreeField;
	/** Field for the member's credit */
	private JSpinner							creditField;
	/** Button for validating changes on a member */
	private JButton								validateButton;
	/** Button for canceling changes on a member */
	private JButton								cancelButton;
	
	// Miscellaneous
	/** Controller to use when view changes */
	private transient MemberController	controller;
	/** Instance of a member to easy entity method call */
	private Member								m;
	
	/**
	 * Constructor #1.<br />
	 * @param controller
	 *        the controller to warn when the changes are applied.
	 */
	public MemberView (final MemberController controller) {
		super(controller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractFrame#preInit(java.lang.Object[])
	 */
	@Override
	protected void preInit (final Object ... parameters) {
		m = new Member();
		controller = (MemberController) parameters[0];
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractFrame#build()
	 */
	@Override
	protected void build () {
		setTitle(translator.getField(m));
		setContentPane(buildMemberPanel());
		setListeners();
	}
	
	/**
	 * Build the panel with the member information.
	 * @return the panel.
	 */
	private JPanel buildMemberPanel () {
		final JPanel pane = new JPanel(new GridBagLayout());
		final Map<? extends Enum<?>, Column> col = Member.getColumns();
		int xIndex = 0;
		int yIndex = 0;
		final GridBagConstraints c = new GridBagConstraints(xIndex, yIndex, 1, 1, 0.33, 0.16,
				GridBagConstraints.BASELINE_TRAILING, GridBagConstraints.HORIZONTAL,
				Utils.getDefaultInsets(), 0, 0);
		
		pane.add(new JLabel(translator.getField(m, col.get(MemberColumn.NAME))), c);
		
		c.gridy = ++yIndex;
		pane.add(new JLabel(translator.getField(m, col.get(MemberColumn.GENDER))), c);
		
		c.gridy = ++yIndex;
		pane.add(new JLabel(translator.getField(m, col.get(MemberColumn.STATUS))), c);
		
		c.gridy = ++yIndex;
		pane.add(new JLabel(translator.getField(m, col.get(MemberColumn.ENTRIES))), c);
		
		c.gridy = ++yIndex;
		pane.add(new JLabel(translator.getField(m, col.get(MemberColumn.NEXT_FREE))), c);
		
		c.gridy = ++yIndex;
		pane.add(new JLabel(translator.getField(m, col.get(MemberColumn.CREDIT))), c);
		
		yIndex = 0;
		c.gridx = ++xIndex;
		c.gridy = yIndex;
		c.gridwidth = 2;
		nameField = new JTextField();
		pane.add(nameField, c);
		
		c.gridy = ++yIndex;
		genderField = new JComboBox<>(Gender.values());
		genderField.setRenderer(new CustomEnumRenderer());
		pane.add(genderField, c);
		
		c.gridy = ++yIndex;
		statusField = new JComboBox<>(Status.values());
		statusField.setRenderer(new CustomEnumRenderer());
		pane.add(statusField, c);
		
		c.gridy = ++yIndex;
		entryNumberField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		pane.add(entryNumberField, c);
		
		c.gridy = ++yIndex;
		nextFreeField = new JSpinner(new SpinnerNumberModel(0, 0,
				Integer.parseInt(paramManager.get(ParametersEnum.FREE_ENTRY_FREQUENCY).getValue()), 1));
		pane.add(nextFreeField, c);
		
		c.gridy = ++yIndex;
		creditField = new JSpinner(new SpinnerNumberModel(0.0,
				Double.parseDouble(paramManager.get(ParametersEnum.MIN_CREDIT).getValue()),
				Double.parseDouble(paramManager.get(ParametersEnum.MAX_CREDIT).getValue()),
				0.01)); // TODO make constant?
		pane.add(creditField, c);
		
		c.gridy = ++yIndex;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		cancelButton = new JButton(translator.get(Translator.Key.MISC.cancel()));
		pane.add(cancelButton, c);
		
		c.gridx = ++xIndex;
		validateButton = new JButton(translator.get(Translator.Key.MISC.ok()));
		pane.add(validateButton, c);
		
		return pane;
	}
	
	/**
	 * Set the listeners on the components.
	 */
	private void setListeners () {
		nameField.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusLost (final FocusEvent e) {
				controller.changeName(nameField.getText());
			}
		});
		
		genderField.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.changeGender((Gender) genderField.getSelectedItem());
			}
		});
		
		statusField.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.changeStatus((Status) statusField.getSelectedItem());
			}
		});
		
		entryNumberField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeEntries((int) entryNumberField.getValue());
			}
		});
		
		nextFreeField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeNextFree((int) nextFreeField.getValue());
			}
		});
		
		creditField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeCredit((double) creditField.getValue());
			}
		});
		
		validateButton.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (!controller.persist()) {
					Utils.showMessageDialog(frame, Translator.Key.GUI.dialog().notPersistedMember(),
							JOptionPane.ERROR_MESSAGE);
				} else {
					frame.setVisible(false);
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				frame.setVisible(false);
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractView#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange (final PropertyChangeEvent evt) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing view (according to model): " + evt.getPropertyName() + " with value "
					+ evt.getNewValue());
		}
		if (evt.getPropertyName().equals(MemberColumn.NAME.getPropertyName())) {
			nameField.setText(evt.getNewValue().toString());
		} else if (evt.getPropertyName().equals(MemberColumn.GENDER.getPropertyName())) {
			genderField.setSelectedItem(evt.getNewValue());
		} else if (evt.getPropertyName().equals(MemberColumn.ENTRIES.getPropertyName())) {
			entryNumberField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(MemberColumn.NEXT_FREE.getPropertyName())) {
			nextFreeField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(MemberColumn.CREDIT.getPropertyName())) {
			creditField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(MemberColumn.STATUS.getPropertyName())) {
			statusField.setSelectedItem(evt.getNewValue());
		} else if (lg.isLoggable(Level.INFO)) {
			lg.info("Property event not managed: " + evt.getPropertyName());
		}
	}
}
