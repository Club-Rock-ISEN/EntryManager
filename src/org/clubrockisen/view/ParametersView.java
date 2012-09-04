package org.clubrockisen.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.clubrockisen.controller.ParameterControllerImpl;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.service.Translator;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.view.abstracts.AbstractFrame;

/**
 * View for the parameter manager.<br />
 * @author Alex
 */
public class ParametersView extends AbstractFrame {
	/** Logger */
	private static Logger											lg					= Logger.getLogger(ParametersView.class.getName());
	
	/** Serial Version UID */
	private static final long										serialVersionUID	= -685243639858300613L;
	
	// Miscellaneous
	/** The map with the controllers to warn */
	private transient Map<ParametersEnum, ParameterControllerImpl>	parametersControllers;
	/** The map with the component used to update the parameters */
	private transient Map<ParametersEnum, JComponent>				parametersComponents;
	/** Instance of a parameter to easy entity method call */
	private Parameter												p;
	
	/**
	 * Constructor #1.<br />
	 * @param parametersControllers the map between the parameters and the controllers.
	 */
	public ParametersView (final Map<ParametersEnum, ParameterControllerImpl> parametersControllers) {
		super(parametersControllers);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractFrame#preInit(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void preInit (final Object... parameters) {
		parametersControllers = (Map<ParametersEnum, ParameterControllerImpl>) parameters[0];
		parametersComponents = new EnumMap<>(ParametersEnum.class);
		p = new Parameter();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractFrame#build()
	 */
	@Override
	protected void build () {
		setTitle(translator.get(Translator.Key.GUI.parameters().title()));
		setContentPane(buildParametersPanel());
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
		
		for (final ParametersEnum parameter : ParametersEnum.values()) {
			// Adding label
			pane.add(new JLabel(translator.getField(parameter)), c);
			// Adding edit field
			++xIndex;
			c.gridx = xIndex;
			final JComponent comp = Utils.getParameterComponent(parameter);
			parametersComponents.put(parameter, comp);
			pane.add(comp, c);
			
			// Setting the index for the next parameters
			xIndex = 0;
			++yIndex;
			c.gridx = xIndex;
			c.gridy = yIndex;
		}
		
		return pane;
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
		// TODO Auto-generated method stub
	}
}
