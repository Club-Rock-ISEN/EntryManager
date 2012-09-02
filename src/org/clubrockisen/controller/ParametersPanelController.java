package org.clubrockisen.controller;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.controller.abstracts.AbstractController;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.view.ParametersView;

/**
 * TODO
 * @author Alex
 */
public class ParametersPanelController extends AbstractController {
	/** Logger */
	private static Logger										lg	= Logger.getLogger(ParametersPanelController.class.getName());
	
	/** Map between the parameters and their controllers */
	private final Map<ParametersEnum, ParameterControllerImpl>	parametersControllers;
	/** The parameters panel */
	private final ParametersView								parametersView;
	
	/**
	 * Constructor #1.<br />
	 */
	public ParametersPanelController () {
		super();
		parametersControllers = new EnumMap<>(ParametersEnum.class);
		for (final ParametersEnum parameter : ParametersEnum.values()) {
			parametersControllers.put(parameter, new ParameterControllerImpl(parameter, this));
		}
		parametersView = new ParametersView(parametersControllers);
		addView(parametersView);
		
		if (lg.isLoggable(Level.FINE)) {
			lg.fine(this.getClass().getName() + " created");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#setModelProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setModelProperty (final String propertyName, final Object newValue) {
		ParametersEnum parameterToUpdate = null;
		String fullProperty = null;
		for (final ParametersEnum parameter : ParametersEnum.values()) {
			if (propertyName.startsWith(parameter.getName())) {
				parameterToUpdate = parameter;
				fullProperty = propertyName.substring(parameter.getName().length());
				break;
			}
		}
		if (parameterToUpdate != null && fullProperty != null) {
			parametersControllers.get(parameterToUpdate).setModelProperty(fullProperty, newValue);
		} else {
			lg.warning("Could not parse propertyName " + propertyName + " as a parameter name.");
		}
	}
	
	/**
	 * Show the parameters panel.
	 */
	public void show () {
		for (final ParameterControllerImpl controller : parametersControllers.values()) {
			controller.refreshModel();
		}
		parametersView.setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#dispose()
	 */
	@Override
	public void dispose () {
		parametersView.dispose();
		removeView(parametersView);
		for (final ParameterControllerImpl parameterController : parametersControllers.values()) {
			parameterController.dispose();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#persist()
	 */
	@Override
	public boolean persist () {
		boolean success = true;
		for (final Entry<ParametersEnum, ParameterControllerImpl> entry : parametersControllers.entrySet()) {
			if (!entry.getValue().persist()) {
				lg.warning("Failed to persist parameter " + entry.getKey());
				success = false;
			}
		}
		return success;
	}
	
}
