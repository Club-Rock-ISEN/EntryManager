package org.clubrockisen.view.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.clubrockisen.common.Constants;
import org.clubrockisen.controller.abstracts.PartyController;
import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.Party.PartyColumn;
import org.clubrockisen.service.Translator.Key;
import org.clubrockisen.service.abstracts.ITranslator;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.view.Utils;
import org.clubrockisen.view.abstracts.AbstractView;

/**
 * This panel display a party with the field labels.<br />
 * This panel installs listeners on the components and update the specified controller.<br />
 * @author Alex
 */
public class PartyPanel extends JPanel implements AbstractView {
	/** Logger */
	private static Logger					lg					= Logger.getLogger(PartyPanel.class.getName());
	
	/** Serial version UID */
	private static final long				serialVersionUID	= 5439969626885070319L;
	
	// Services
	/** Translator */
	private final transient ITranslator		translator			= ServiceFactory.getImplementation().getTranslator();
	
	// Swing elements
	/** Field for the party's date */
	private final JTextField				dateField;
	/** Field for the party's entry number */
	private final JSpinner					entriesTotalField;
	/** Field for the party's first part entry number */
	private final JSpinner					entriesFirstPartField;
	/** Field for the party's second part entry number */
	private final JSpinner					entriesSecondPartField;
	/** Field for the party's new member entry number */
	private final JSpinner					entriesNewMemberField;
	/** Field for the party's free entry number */
	private final JSpinner					entriesFreeField;
	/** Field for the party's male entry number */
	private final JSpinner					entriesMaleField;
	/** Field for the party's female entry number */
	private final JSpinner					entriesFemaleField;
	/** Field of the difference between male and female members */
	private final JSpinner					deltaValue;
	/** Field for the party's revenue */
	private final JSpinner					revenueField;
	/** Field for the party's profit */
	private final JSpinner					profitField;
	
	// Miscellaneous
	/** Formatter for the date */
	private final SimpleDateFormat			dateFormatter;
	
	/**
	 * Constructor #1.<br />
	 * Build a non editable party panel.
	 * @see #PartyPanel(boolean)
	 */
	public PartyPanel () {
		this(false);
	}
	
	/**
	 * Constructor #.<br />
	 * @param editable
	 *        <code>true</code> if the fields should be editable.
	 */
	public PartyPanel (final boolean editable) {
		super(new GridBagLayout());
		dateFormatter = new SimpleDateFormat(Constants.DISPLAYED_DATE_FORMAT);
		
		final Map<? extends Enum<?>, Column> col = Party.getColumns();
		final Party p = new Party();
		int xIndex = 0;
		int yIndex = 0;
		final GridBagConstraints c = new GridBagConstraints(xIndex, yIndex, 1, 1, 0.5, 0.1,
				GridBagConstraints.BASELINE_TRAILING, GridBagConstraints.BOTH,
				Constants.DEFAULT_INSETS, 0, 0);
		
		add(new JLabel(translator.getField(p, col.get(PartyColumn.DATE))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(p, col.get(PartyColumn.ENTRIES_TOTAL))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(p, col.get(PartyColumn.ENTRIES_FIRST_PART))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(p, col.get(PartyColumn.ENTRIES_SECOND_PART))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(p, col.get(PartyColumn.ENTRIES_NEW_MEMBER))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(p, col.get(PartyColumn.ENTRIES_FREE))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(p, col.get(PartyColumn.ENTRIES_MALE))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(p, col.get(PartyColumn.ENTRIES_FEMALE))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(Constants.DELTA + translator.get(Key.MISC.fieldValueSeparator())), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(p, col.get(PartyColumn.REVENUE))), c);
		
		c.gridy = ++yIndex;
		add(new JLabel(translator.getField(p, col.get(PartyColumn.PROFIT))), c);
		
		yIndex = 0;
		c.gridx = ++xIndex;
		c.gridy = yIndex;
		dateField = new JTextField();
		add(dateField, c);
		
		c.gridy = ++yIndex;
		entriesTotalField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		add(entriesTotalField, c);
		
		c.gridy = ++yIndex;
		entriesFirstPartField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		add(entriesFirstPartField, c);
		
		c.gridy = ++yIndex;
		entriesSecondPartField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		add(entriesSecondPartField, c);
		
		c.gridy = ++yIndex;
		entriesNewMemberField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		add(entriesNewMemberField, c);
		
		c.gridy = ++yIndex;
		entriesFreeField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		add(entriesFreeField, c);
		
		c.gridy = ++yIndex;
		entriesMaleField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		add(entriesMaleField, c);
		
		c.gridy = ++yIndex;
		entriesFemaleField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		add(entriesFemaleField, c);
		
		c.gridy = ++yIndex;
		deltaValue = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		deltaValue.setEnabled(false);
		add(deltaValue, c);
		
		c.gridy = ++yIndex;
		// Note: revenue can only be positive, this is the raw income.
		revenueField = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Integer.MAX_VALUE, Constants.MONEY_STEP_SPINNER));
		add(revenueField, c);
		
		c.gridy = ++yIndex;
		// Note: profit may be negative, this is the revenue minus the charges.
		profitField = new JSpinner(new SpinnerNumberModel(0.0, Integer.MIN_VALUE, Integer.MAX_VALUE, Constants.MONEY_STEP_SPINNER));
		add(profitField, c);
		
		if (!editable) {
			dateField.setEditable(false);
			entriesTotalField.setEnabled(false);
			entriesFirstPartField.setEnabled(false);
			entriesSecondPartField.setEnabled(false);
			entriesNewMemberField.setEnabled(false);
			entriesFreeField.setEnabled(false);
			entriesMaleField.setEnabled(false);
			entriesFemaleField.setEnabled(false);
			revenueField.setEnabled(false);
			profitField.setEnabled(false);
		}
	}
	
	/**
	 * Add listeners to the components.<br />
	 * The listener will notify the specified controller on a property change.
	 * @param controller
	 *        the controller to notify.
	 */
	public void addListeners (final PartyController controller) {
		dateField.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusLost (final FocusEvent evt) {
				final String oldValue = dateField.getText();
				try {
					controller.changeDate(dateFormatter.parse(dateField.getText()).getTime());
				} catch (final ParseException e) {
					lg.warning("Invalid date format, revert changes (" + e.getClass() + "; "
							+ e.getMessage() + ")");
					Utils.showMessageDialog(PartyPanel.this,
							Key.GUI.dialog().unparsableDate(dateField.getText()),
							JOptionPane.WARNING_MESSAGE);
					dateField.setText(oldValue);
				}
			}
		});
		
		entriesTotalField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeEntriesTotal((int) entriesTotalField.getValue());
			}
		});
		
		entriesFirstPartField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeEntriesFirstPart((int) entriesFirstPartField.getValue());
			}
		});
		
		entriesSecondPartField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeEntriesSecondPart((int) entriesSecondPartField.getValue());
			}
		});
		
		entriesNewMemberField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeEntriesNewMembers((int) entriesNewMemberField.getValue());
			}
		});
		
		entriesFreeField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeEntriesFree((int) entriesFreeField.getValue());
			}
		});
		
		entriesMaleField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeEntriesFree((int) entriesMaleField.getValue());
			}
		});
		
		entriesFemaleField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeEntriesFree((int) entriesFemaleField.getValue());
			}
		});
		
		revenueField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeRevenue((double) revenueField.getValue());
			}
		});
		
		profitField.addChangeListener(new ChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged (final ChangeEvent e) {
				controller.changeProfit((double) revenueField.getValue());
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
		
		if (evt.getPropertyName().equals(PartyColumn.DATE.getPropertyName())) {
			dateField.setText(dateFormatter.format(new Date((Long)evt.getNewValue())));
		} else if (evt.getPropertyName().equals(PartyColumn.ENTRIES_TOTAL.getPropertyName())) {
			entriesTotalField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(PartyColumn.ENTRIES_FIRST_PART.getPropertyName())) {
			entriesFirstPartField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(PartyColumn.ENTRIES_SECOND_PART.getPropertyName())) {
			entriesSecondPartField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(PartyColumn.ENTRIES_NEW_MEMBER.getPropertyName())) {
			entriesNewMemberField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(PartyColumn.ENTRIES_FREE.getPropertyName())) {
			entriesFreeField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(PartyColumn.ENTRIES_MALE.getPropertyName())) {
			entriesMaleField.setValue(evt.getNewValue());
			updateDelta();
		} else if (evt.getPropertyName().equals(PartyColumn.ENTRIES_FEMALE.getPropertyName())) {
			entriesFemaleField.setValue(evt.getNewValue());
			updateDelta();
		} else if (evt.getPropertyName().equals(PartyColumn.REVENUE.getPropertyName())) {
			revenueField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(PartyColumn.PROFIT.getPropertyName())) {
			profitField.setValue(evt.getNewValue());
		} else if (lg.isLoggable(Level.INFO)) {
			lg.info("Property event not managed: " + evt.getPropertyName());
		}
	}
	
	/**
	 * Update the delta field using the value of male and female entries.
	 */
	private void updateDelta () {
		final int male = (int) entriesMaleField.getValue();
		final int female = (int) entriesFemaleField.getValue();
		
		deltaValue.setValue(female - male);
	}
}
