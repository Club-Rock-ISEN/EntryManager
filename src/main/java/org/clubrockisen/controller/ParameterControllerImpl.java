package org.clubrockisen.controller;

import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.controller.abstracts.ParameterController;
import org.clubrockisen.entities.Parameter.ParameterColumn;
import org.clubrockisen.model.ParameterModel;
import org.clubrockisen.service.abstracts.ParametersEnum;

import com.alexrnl.commons.mvc.AbstractController;

/**
 * Controller for a single parameter.<br />
 * Each main controller may only have one model of a defined type. This step allows to control as
 * much parameters as required but the mapping should be done by the parent controller.
 * @author Alex
 */
public class ParameterControllerImpl extends AbstractController implements ParameterController {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(ParameterControllerImpl.class.getName());
	
	/** The model for the parameter */
	private final ParameterModel		parameterModel;
	/** The parent controller to be warned when a model change */
	private final AbstractController	parentController;
	/** The parameter being controller by this object */
	private final ParametersEnum		parameter;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter being managed by the controller.
	 * @param parentController
	 *        the parent controller to warn on a model's update.
	 */
	public ParameterControllerImpl (final ParametersEnum parameter, final AbstractController parentController) {
		super();
		this.parameter = parameter;
		parameterModel = new ParameterModel(parameter);
		addModel(parameterModel);
		this.parentController = parentController;
	}
	
	@Override
	public void changeValue (final String newValue) {
		setModelProperty(ParameterColumn.VALUE.getFieldName(), newValue);
	}
	
	@Override
	public void changeType (final String newType) {
		setModelProperty(ParameterColumn.TYPE.getFieldName(), newType);
	}
	
	@Override
	public void propertyChange (final PropertyChangeEvent evt) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Model has change, warning views in parent controller.");
		}
		// Editing event to add parameter info
		final PropertyChangeEvent newEvt = new PropertyChangeEvent(evt.getSource(),
				parameter.getName() + evt.getPropertyName(),
				evt.getOldValue(), evt.getNewValue());
		parentController.propertyChange(newEvt);
	}
	
	@Override
	public void dispose () {
		removeModel(parameterModel);
	}
	
	@Override
	public boolean persist () {
		return parameterModel.persist();
	}
	
	@Override
	public void reload () {
		parameterModel.reload();
	}
}
