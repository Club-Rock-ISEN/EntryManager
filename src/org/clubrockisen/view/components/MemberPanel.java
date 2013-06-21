package org.clubrockisen.view.components;

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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.clubrockisen.common.Constants;
import org.clubrockisen.controller.abstracts.MemberController;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ITranslator;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.view.renderers.CustomEnumRenderer;

import com.alexrnl.commons.database.Column;
import com.alexrnl.commons.mvc.AbstractView;

/**
 * This panel display a member with the field labels.<br />
 * This panel installs listeners on the components and update the specified controller.<br />
 * @author Alex
 */
public class MemberPanel extends JPanel implements AbstractView {
	/** Logger */
	private static Logger						lg					= Logger.getLogger(MemberPanel.class.getName());
	
	/** Serial Version UID */
	private static final long					serialVersionUID	= 4695909526754944832L;
	
	// Services
	/** Translator */
	private final transient ITranslator			translator			= ServiceFactory.getImplementation().getTranslator();
	/** Parameter manager */
	private final transient IParametersManager	paramManager		= ServiceFactory.getImplementation().getParameterManager();
	
	// Swing elements
	/** Field for the member's name */
	private final JTextField					nameField;
	/** Field for the member's {@link Gender} */
	private final JComboBox<Gender>				genderField;
	/** Field for the member's {@link Status} */
	private final JComboBox<Status>				statusField;
	/** Field for the member's entries */
	private final JSpinner						entryNumberField;
	/** Field for the member's next free entry count down */
	private final JSpinner						nextFreeField;
	/** Field for the member's credit */
	private final JSpinner						creditField;
	
	/**
	 * Constructor #1.<br />
	 * Build a non-editable member panel.
	 * @see #MemberPanel(boolean)
	 */
	public MemberPanel () {
		this(false);
	}
	
	/**
	 * Constructor #2.<br />
	 * @param editable
	 *        <code>true</code> if the fields should be editable.
	 */
	public MemberPanel (final boolean editable) {
		super(new GridBagLayout());
		
		final Map<? extends Enum<?>, Column> col = Member.getColumns();
		final Member m = new Member();
		int xIndex = 0;
		int yIndex = 0;
		final GridBagConstraints c = new GridBagConstraints(xIndex, yIndex, 1, 1, 0.5, 0.16,
				GridBagConstraints.BASELINE_TRAILING, GridBagConstraints.BOTH,
				Constants.DEFAULT_INSETS, 0, 0);
		
		add(new JLabel(translator.getField(m, col.get(MemberColumn.NAME))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(m, col.get(MemberColumn.GENDER))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(m, col.get(MemberColumn.STATUS))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(m, col.get(MemberColumn.ENTRIES))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(m, col.get(MemberColumn.NEXT_FREE))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(m, col.get(MemberColumn.CREDIT))), c);
		
		yIndex = 0;
		c.gridx = ++xIndex;
		c.gridy = yIndex;
		nameField = new JTextField();
		add(nameField, c);
		
		c.gridy = ++yIndex;
		genderField = new JComboBox<>(Gender.values());
		genderField.setRenderer(new CustomEnumRenderer());
		add(genderField, c);
		
		c.gridy = ++yIndex;
		statusField = new JComboBox<>(Status.values());
		statusField.setRenderer(new CustomEnumRenderer());
		add(statusField, c);
		
		c.gridy = ++yIndex;
		entryNumberField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		add(entryNumberField, c);
		
		c.gridy = ++yIndex;
		nextFreeField = new JSpinner(new SpinnerNumberModel(0, 0,
				Integer.parseInt(paramManager.get(ParametersEnum.FREE_ENTRY_FREQUENCY).getValue()), 1));
		add(nextFreeField, c);
		
		c.gridy = ++yIndex;
		creditField = new JSpinner(new SpinnerNumberModel(0.0,
				Double.parseDouble(paramManager.get(ParametersEnum.MIN_CREDIT).getValue()),
				Double.parseDouble(paramManager.get(ParametersEnum.MAX_CREDIT).getValue()),
				Constants.MONEY_STEP_SPINNER));
		add(creditField, c);
		
		if (!editable) {
			nameField.setEditable(false);
			genderField.setEnabled(false);
			statusField.setEnabled(false);
			entryNumberField.setEnabled(false);
			nextFreeField.setEnabled(false);
			creditField.setEnabled(false);
		}
	}
	
	
	/**
	 * Add listeners to the components.<br />
	 * The listener will notify the specified controller on a property change.
	 * @param controller
	 *        the controller to notify.
	 */
	public void addListeners (final MemberController controller) {
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
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractView#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange (final PropertyChangeEvent evt) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing view: " + evt.getPropertyName() + " with value " + evt.getNewValue());
		}
		
		if (evt.getPropertyName().equals(MemberColumn.NAME.getFieldName())) {
			nameField.setText(evt.getNewValue().toString());
		} else if (evt.getPropertyName().equals(MemberColumn.GENDER.getFieldName())) {
			genderField.setSelectedItem(evt.getNewValue());
		} else if (evt.getPropertyName().equals(MemberColumn.ENTRIES.getFieldName())) {
			entryNumberField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(MemberColumn.NEXT_FREE.getFieldName())) {
			nextFreeField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(MemberColumn.CREDIT.getFieldName())) {
			creditField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(MemberColumn.STATUS.getFieldName())) {
			statusField.setSelectedItem(evt.getNewValue());
		} else if (lg.isLoggable(Level.FINE)) {
			lg.fine("Property event not managed: " + evt.getPropertyName());
		}
	}
}
