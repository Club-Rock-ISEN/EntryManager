package org.clubrockisen.view;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.Translator;
import org.clubrockisen.view.abstracts.AbstractView;
import org.clubrockisen.view.renderers.CustomEnumRenderer;

/**
 * View which displays a panel to update a member.<br />
 * @author Alex
 */
public class MemberView extends JFrame implements AbstractView {
	/** Logger */
	private static Logger		lg					= Logger.getLogger(MemberView.class.getName());
	
	/** Serial Version UID */
	private static final long	serialVersionUID	= 5754628770258165084L;
	
	/** Default inner border */
	private final Insets		defaultInsets		= new Insets(5, 5, 5, 5);
	/** Translator */
	private final Translator	translator			= Translator.getInstance();
	
	// Swing GUI elements
	/** Label for the member's name */
	private JLabel				nameLabel;
	/** Label for the member's {@link Gender} */
	private JLabel				genderLabel;
	/** Label for the member's {@link Status} */
	private JLabel				statusLabel;
	/** Label for the member's entries */
	private JLabel				entryNumberLabel;
	/** Label for the member's next free entry count down */
	private JLabel				nextFreeLabel;
	/** Label for the member's credit */
	private JLabel				creditLabel;
	/** Field for the member's name */
	private JTextField			nameField;
	/** Field for the member's {@link Gender} */
	private JComboBox<Gender>	genderField;
	/** Field for the member's {@link Status} */
	private JComboBox<Status>	statusField;
	/** Field for the member's entries */
	private JSpinner			entryNumberField;
	/** Field for the member's next free entry count down */
	private JSpinner			nextFreeField;
	/** Field for the member's credit */
	private JSpinner			creditField;
	
	/**
	 * Constructor #1.<br />
	 */
	public MemberView () {
		super();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run () {
				buildGUI();
			}
		});
	}
	
	/**
	 * Build the GUI for the member view.
	 */
	private void buildGUI () {
		this.setTitle(translator.get(new Member()));
		this.setVisible(false);
		this.setSize(300, 450);
		this.setLocationRelativeTo(null);
		this.setContentPane(buildMemberPanel());
		this.setListeners();
	}
	
	/**
	 * Build the panel with the member information.
	 * @return the panel.
	 */
	private JPanel buildMemberPanel () {
		final JPanel pane = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		nameLabel = new JLabel(translator.get(new Member(), Member.getColumns().get(MemberColumn.NAME)));
		pane.add(nameLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		genderLabel = new JLabel(translator.get(new Member(), Member.getColumns().get(MemberColumn.GENDER)));
		pane.add(genderLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		statusLabel = new JLabel(translator.get(new Member(), Member.getColumns().get(MemberColumn.STATUS)));
		pane.add(statusLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		entryNumberLabel = new JLabel(translator.get(new Member(), Member.getColumns().get(MemberColumn.ENTRIES)));
		pane.add(entryNumberLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		nextFreeLabel = new JLabel(translator.get("app.mainWindow.label.nextFree"));
		pane.add(nextFreeLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		creditLabel = new JLabel(translator.get(new Member(), Member.getColumns().get(MemberColumn.CREDIT)));
		pane.add(creditLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = defaultInsets;
		nameField = new JTextField(50);
		pane.add(nameField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = defaultInsets;
		genderField = new JComboBox<>(Gender.values());
		genderField.setRenderer(new CustomEnumRenderer());
		pane.add(genderField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = defaultInsets;
		statusField = new JComboBox<>(Status.values());
		statusField.setRenderer(new CustomEnumRenderer());
		pane.add(statusField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = defaultInsets;
		entryNumberField = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		pane.add(entryNumberField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = defaultInsets;
		nextFreeField = new JSpinner(new SpinnerNumberModel(0, 0, 6, 1));
		nextFreeField.setEnabled(false);
		pane.add(nextFreeField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = defaultInsets;
		creditField = new JSpinner(new SpinnerNumberModel(0.0, -10.0, 1000.0, 0.01));
		pane.add(creditField, c);
		
		return pane;
	}
	
	/**
	 * Set the listeners on the components.
	 */
	private void setListeners () {
		
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
			nextFreeField.setValue(6 - Integer.parseInt(evt.getNewValue().toString()) % 6); // TODO make parameter
		} else if (evt.getPropertyName().equals(MemberColumn.CREDIT.getPropertyName())) {
			creditField.setValue(evt.getNewValue());
		} else if (evt.getPropertyName().equals(MemberColumn.STATUS.getPropertyName())) {
			statusField.setSelectedItem(evt.getNewValue());
		} else if (lg.isLoggable(Level.INFO)) {
			lg.info("Property event not managed: " + evt.getPropertyName());
		}
	}
}
