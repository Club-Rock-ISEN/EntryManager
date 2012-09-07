package org.clubrockisen.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.util.EnumMap;
import java.util.EventObject;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.clubrockisen.controller.ParametersPanelController;
import org.clubrockisen.entities.Parameter.ParameterColumn;
import org.clubrockisen.service.Translator;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.view.abstracts.AbstractFrame;

/**
 * View for the parameter manager.<br />
 * @author Alex
 */
public class ParametersView extends AbstractFrame {
	/** Logger */
	private static Logger								lg					= Logger.getLogger(ParametersView.class.getName());
	
	/** Serial Version UID */
	private static final long							serialVersionUID	= -685243639858300613L;
	
	// Swing GUI elements
	/** The map with the component used to update the parameters */
	private transient Map<ParametersEnum, JComponent>	parametersComponents;
	/** Button for validating changes on a member */
	private JButton										validateButton;
	/** Button for canceling changes on a member */
	private JButton										cancelButton;
	
	// Miscellaneous
	/** The map with the controllers to warn */
	private transient ParametersPanelController			controller;
	
	/**
	 * Constructor #1.<br />
	 * @param parametersPanelController
	 *        the controller of the view.
	 */
	public ParametersView (final ParametersPanelController parametersPanelController) {
		super(parametersPanelController);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractFrame#preInit(java.lang.Object[])
	 */
	@Override
	protected void preInit (final Object... parameters) {
		controller = (ParametersPanelController) parameters[0];
		parametersComponents = new EnumMap<>(ParametersEnum.class);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractFrame#build()
	 */
	@Override
	protected void build () {
		setTitle(translator.get(Translator.Key.GUI.parameters().title()));
		setContentPane(buildParametersPanel());
		setListeners();
	}
	
	/**
	 * Build the panel with the parameters.<br />
	 * @return the panel.
	 */
	private JPanel buildParametersPanel () {
		final JPanel pane = new JPanel(new GridBagLayout());
		
		int xIndex = 0;
		int yIndex = 0;
		final GridBagConstraints c = new GridBagConstraints(xIndex, yIndex, 1, 1, 0.33, 0.16,
				GridBagConstraints.BASELINE_TRAILING, GridBagConstraints.HORIZONTAL,
				Utils.getDefaultInsets(), 0, 0);
		
		// Loop for parameters
		for (final ParametersEnum parameter : ParametersEnum.values()) {
			// Adding label
			pane.add(new JLabel(translator.getField(parameter)), c);
			// Adding edit field
			++xIndex;
			c.gridx = xIndex;
			c.gridwidth = 2;
			final JComponent comp = Utils.getParameterComponent(parameter);
			parametersComponents.put(parameter, comp);
			pane.add(comp, c);
			
			// Setting the index for the next parameters
			xIndex = 0;
			++yIndex;
			c.gridx = xIndex;
			c.gridy = yIndex;
			c.gridwidth = 1;
		}
		
		// Buttons
		c.gridx = ++xIndex;
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
		for (final Entry<ParametersEnum, JComponent> entry : parametersComponents.entrySet()) {
			final ParameterChangeListener listener = new ParameterChangeListener(entry.getKey(), entry.getValue());
			if (entry.getValue() instanceof JComboBox<?>) {
				((JComboBox<?>) entry.getValue()).addActionListener(listener);
			} else if (entry.getValue() instanceof JSpinner) {
				((JSpinner) entry.getValue()).addChangeListener(listener);
			} else {
				entry.getValue().addFocusListener(listener);
			}
		}
		
		validateButton.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (!controller.persist()) {
					Utils.showMessageDialog(frame,
							Translator.Key.GUI.dialog().notPersistedMember(),
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
	
	/**
	 * Listener for multiple types of events on components.<br />
	 * @author Alex
	 */
	public class ParameterChangeListener extends FocusAdapter implements ActionListener, ChangeListener {
		/** The parameter being listened to */
		private final ParametersEnum	parameter;
		/** The component holding the value */
		private final JComponent		component;
		
		/**
		 * Constructor #1.<br />
		 * @param parameter
		 *        the parameter it listens to.
		 * @param component
		 *        the component which holds the value.
		 */
		public ParameterChangeListener (final ParametersEnum parameter, final JComponent component) {
			super();
			this.parameter = parameter;
			this.component = component;
		}
		
		/**
		 * Process the event.<br />
		 * Warn the controller of changes on the view.
		 * @param e
		 *        the event.
		 */
		private void event (final EventObject e) {
			final String value = Utils.getValue(component);
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Fire change in parameter " + parameter + ": " + value);
			}
			controller.changeValue(parameter, value);
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost (final FocusEvent evt) {
			event(evt);
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed (final ActionEvent e) {
			event(e);
		}
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		@Override
		public void stateChanged (final ChangeEvent e) {
			event(e);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.view.abstracts.AbstractView#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange (final PropertyChangeEvent evt) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing view (according to model): " + evt.getPropertyName() + " with value "
					+ evt.getNewValue());
		}
		boolean found = false;
		for (final ParametersEnum parameter : ParametersEnum.values()) {
			// Parameter found, skipping unnecessary processing
			if (found) {
				break;
			}
			if (evt.getPropertyName().startsWith(parameter.getName())) {
				final String propertyName = evt.getPropertyName().substring(parameter.getName().length());
				if (propertyName.equals(ParameterColumn.VALUE.getPropertyName())) {
					found = true;
					Utils.setValue(parametersComponents.get(parameter), evt.getNewValue());
				}
				if (propertyName.equals(ParameterColumn.TYPE.getPropertyName())) {
					found = true;
					if (lg.isLoggable(Level.FINER)) {
						lg.fine("No component displays the type of a parameter, event ignored");
					}
					// Nothing to be done
				}
			}
		}
		
		if (!found && lg.isLoggable(Level.INFO)) {
			lg.info("Property event not managed: " + evt.getPropertyName());
		}
	}
}
