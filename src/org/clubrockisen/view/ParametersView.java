package org.clubrockisen.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.clubrockisen.common.Constants;
import org.clubrockisen.common.TranslationKeys;
import org.clubrockisen.controller.ParametersPanelController;
import org.clubrockisen.entities.Parameter.ParameterColumn;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.view.abstracts.EntryManagerFrame;
import org.clubrockisen.view.components.ValidateCancelPanel;
import org.clubrockisen.view.parameter.ParameterChangeListener;
import org.clubrockisen.view.parameter.ParameterComponent;
import org.clubrockisen.view.parameter.ParameterComponentsManager;

import com.alexrnl.commons.gui.swing.SwingUtils;

/**
 * View for the parameter manager.<br />
 * @author Alex
 */
public class ParametersView extends EntryManagerFrame implements ParameterChangeListener {
	/** Logger */
	private static Logger										lg					= Logger.getLogger(ParametersView.class.getName());
	
	/** Serial Version UID */
	private static final long									serialVersionUID	= -685243639858300613L;
	/** The reference to the parameter components manager */
	private static ParameterComponentsManager					pcm					= ParameterComponentsManager.getInstance();
	
	// Swing GUI elements
	/** The map with the component used to update the parameters */
	private transient Map<ParametersEnum, ParameterComponent>	parametersComponents;
	/** The validate / cancel panel */
	private ValidateCancelPanel									validateCancelPanel;
	
	// Miscellaneous
	/** The map with the controllers to warn */
	private transient ParametersPanelController					controller;
	
	/**
	 * Constructor #1.<br />
	 * @param parametersPanelController
	 *        the controller of the view.
	 */
	public ParametersView (final ParametersPanelController parametersPanelController) {
		super(TranslationKeys.GUI.parameters().title(), parametersPanelController);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.EntryManagerFrame#preInit(java.lang.Object[])
	 */
	@Override
	protected void preInit (final Object... parameters) {
		controller = (ParametersPanelController) parameters[0];
		parametersComponents = new EnumMap<>(ParametersEnum.class);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.EntryManagerFrame#build()
	 */
	@Override
	protected void build () {
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
				Constants.DEFAULT_INSETS, 0, 0);
		
		// Loop for parameters
		for (final ParametersEnum parameter : ParametersEnum.values()) {
			// Adding label
			pane.add(new JLabel(getTranslator().getField(parameter)), c);
			// Adding edit field
			++xIndex;
			c.gridx = xIndex;
			final ParameterComponent comp = pcm.getNewComponent(parameter);
			parametersComponents.put(parameter, comp);
			pane.add(comp.getComponent(), c);
			
			// Setting the index for the next parameters
			xIndex = 0;
			++yIndex;
			c.gridx = xIndex;
			c.gridy = yIndex;
		}
		
		// Buttons
		c.gridx = ++xIndex;
		c.gridy = ++yIndex;
		c.fill = GridBagConstraints.NONE;
		validateCancelPanel = new ValidateCancelPanel(this);
		pane.add(validateCancelPanel, c);
		
		return pane;
	}
	
	/**
	 * Set the listeners on the components.
	 */
	private void setListeners () {
		for (final ParameterComponent component : parametersComponents.values()) {
			component.installListener(this);
		}
		
		validateCancelPanel.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (!controller.persist()) {
					SwingUtils.showMessageDialog(getFrame(), getTranslator(),
							TranslationKeys.GUI.dialog().notPersistedParameter(),
							JOptionPane.ERROR_MESSAGE, Constants.LINE_MAX_LENGTH);
				} else {
					getFrame().setVisible(false);
				}
			}
		});
		
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
				if (propertyName.equals(ParameterColumn.VALUE.getFieldName())) {
					found = true;
					parametersComponents.get(parameter).setValue(evt.getNewValue());
				}
				if (propertyName.equals(ParameterColumn.TYPE.getFieldName())) {
					found = true;
					if (lg.isLoggable(Level.FINER)) {
						lg.fine("No component displays the type of a parameter, event ignored");
					}
					// Nothing to be done
				}
				if (propertyName.equals(ParameterColumn.COMPONENT_CLASS.getFieldName())) {
					found = true;
					if (lg.isLoggable(Level.FINER)) {
						lg.fine("No component displays the component class of a parameter, event ignored");
					}
					// Nothing to be done
				}
			}
		}
		
		if (!found && lg.isLoggable(Level.INFO)) {
			lg.info("Property event not managed: " + evt.getPropertyName());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.view.parameter.ParameterChangeListener#parameterChangeValue(java.lang.String)
	 */
	@Override
	public void parameterChangeValue (final ParametersEnum parameter, final String value) {
		controller.changeValue(parameter, value);
	}
}
