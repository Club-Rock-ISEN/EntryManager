package org.clubrockisen.controller;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.view.ParametersView;

import com.alexrnl.commons.mvc.AbstractController;

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
		parametersView = new ParametersView(this);
		addView(parametersView);
		
		// Waiting for the complete GUI generation
		synchronized (parametersView) {
			try {
				while (!parametersView.isReady()) {
					parametersView.wait();
				}
			} catch (final InterruptedException e) {
				lg.warning("Main thread interrupted: " + e.getMessage());
			}
		}
		
		if (lg.isLoggable(Level.FINE)) {
			lg.fine(this.getClass().getName() + " created");
		}
	}
	
	/**
	 * Show the parameters panel.
	 */
	public void show () {
		reload();
		parametersView.setVisible(true);
	}
	
	/*
	 * (non-Javadoc)
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
	
	/*
	 * (non-Javadoc)
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
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#reload()
	 */
	@Override
	public void reload () {
		for (final ParameterControllerImpl parameterController : parametersControllers.values()) {
			parameterController.reload();
		}
	}
	
	/**
	 * Dispatch a change of value on the specified parameter.
	 * @param parameter
	 *        the parameter.
	 * @param newValue
	 *        the new value of the parameter.
	 */
	public void changeValue (final ParametersEnum parameter, final String newValue) {
		parametersControllers.get(parameter).changeValue(newValue);
		
	}
	
	/**
	 * Dispatch a change of type on the specified parameter.
	 * @param parameter
	 *        the parameter.
	 * @param newType
	 *        the new type of the parameter.
	 */
	public void changeType (final ParametersEnum parameter, final String newType) {
		parametersControllers.get(parameter).changeType(newType);
	}
	
}
