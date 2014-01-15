package org.clubrockisen.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Parameter.ParameterColumn;
import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;

import com.alexrnl.commons.mvc.AbstractModel;

/**
 * Model for a {@link Parameter} entity.
 * @author Alex
 */
public class ParameterModel extends AbstractModel {
	/** Logger */
	private static Logger				lg					= Logger.getLogger(ParameterModel.class.getName());
	
	/** The parameters manager */
	private static IParametersManager	parametersManager	= ServiceFactory.getImplementation().getParameterManager();
	
	/** The parameter represented by this model */
	private final Parameter				parameter;
	/** <code>true</code> if the parameter is on its first refresh. */
	private final boolean				firstRefresh;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter being modeled.
	 */
	public ParameterModel (final ParametersEnum parameter) {
		super();
		this.parameter = new Parameter(parametersManager.get(parameter).getName());
		firstRefresh = false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#persist()
	 */
	@Override
	public boolean persist () {
		return parametersManager.set(parameter);
	}

	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#reload()
	 */
	@Override
	public void reload () {
		final Parameter param = parametersManager.get(ParametersEnum.fromValue(parameter.getName()));
		setComponentClass(param.getComponentClass());
		setType(param.getType());
		setValue(param.getValue());
	}

	/**
	 * Return the name.
	 * @return the name of the parameter.
	 */
	public String getName () {
		return parameter.getName();
	}
	
	/**
	 * Set the name if the parameter.<br />
	 * Should not be used, parameters name may only be modified by updating the database and source
	 * code.
	 * @param name
	 *        the new name.
	 */
	public void setName (final String name) {
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Cannot change name of parameter " + parameter.getName());
		}
	}
	
	/**
	 * Return the value.
	 * @return the value of the parameter.
	 */
	public String getValue () {
		return firstRefresh ? null : parameter.getValue();
	}
	
	/**
	 * Set the value of the parameter.
	 * @param value
	 *        the new value.
	 */
	public void setValue (final String value) {
		final String oldValue = getValue();
		parameter.setValue(value);
		Object evtValue = value;
		if (Integer.class.getSimpleName().equals(parameter.getType())) {
			evtValue = Integer.valueOf(value);
		} else if (Double.class.getSimpleName().equals(parameter.getType())) {
			evtValue = Double.valueOf(value);
		}
		fireModelChange(ParameterColumn.VALUE.getFieldName(), oldValue, evtValue);
	}
	
	/**
	 * Return the type.
	 * @return the type of the parameter.
	 */
	public String getType () {
		return firstRefresh ? null : parameter.getType();
	}
	
	/**
	 * Set the type of the parameter.
	 * @param type
	 *        the new type.
	 */
	public void setType (final String type) {
		final String oldType = parameter.getType();
		parameter.setType(type);
		fireModelChange(ParameterColumn.TYPE.getFieldName(), oldType, type);
	}
	
	/**
	 * Return the component class.
	 * @return the component class of the parameter.
	 */
	public String getComponentClass () {
		return firstRefresh ? null : parameter.getComponentClass();
	}
	
	/**
	 * Set the type of the parameter.
	 * @param componentClass
	 *        the new component class.
	 */
	public void setComponentClass (final String componentClass) {
		final String oldType = parameter.getComponentClass();
		parameter.setComponentClass(componentClass);
		fireModelChange(ParameterColumn.COMPONENT_CLASS.getFieldName(), oldType, componentClass);
	}
}
