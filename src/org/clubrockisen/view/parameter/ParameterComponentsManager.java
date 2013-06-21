package org.clubrockisen.view.parameter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ParametersEnum;
import org.clubrockisen.service.abstracts.ServiceFactory;

/**
 * Manager for the component manager.<br />
 * The {@link #getNewComponent(ParametersEnum)} method allow to build a new component
 * @author Alex
 */
public final class ParameterComponentsManager {
	/** Logger */
	private static Logger						lg					= Logger.getLogger(ParameterComponentsManager.class.getName());
	
	/** Instance of the class */
	private static ParameterComponentsManager	singleton			= new ParameterComponentsManager();
	
	/** Reference to the parameters manager */
	private final IParametersManager			parametersManager;
	/** Map between the parameters and their component classes */
	private final Map<ParametersEnum, Class<? extends ParameterComponent>>	components;
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private ParameterComponentsManager () {
		super();
		components = new EnumMap<>(ParametersEnum.class);
		parametersManager = ServiceFactory.getImplementation().getParameterManager();
		loadClasses();
	}
	
	/**
	 * Return the unique instance of the class.
	 * @return the singleton.
	 */
	public static ParameterComponentsManager getInstance () {
		return singleton;
	}
	
	/**
	 * Load the parameter component classes.
	 */
	private void loadClasses () {
		components.clear();
		for (final ParametersEnum parameter : ParametersEnum.values()) {
			try {
				final String className = this.getClass().getPackage().getName() + "." +
						parametersManager.get(parameter).getComponentClass();
				components.put(parameter, Class.forName(className).asSubclass(ParameterComponent.class));
			} catch (final ClassCastException e) {
				lg.warning("Class specified for parameter " + parameter.getName() + " is not a " +
						"subclass of " + ParameterComponent.class + " (" + e.getClass() + "; " +
						e.getMessage() + ")");
			} catch (final ClassNotFoundException e) {
				lg.warning("Parameter class not found for parameter " + parameter.getName() +
						" (" + e.getClass() + "; " + e.getMessage() + ")");
			}
		}
	}
	
	/**
	 * Return a new instance of a component for the given parameter.
	 * @param parameter
	 *        the parameter.
	 * @return the component to display the parameter.
	 */
	public ParameterComponent getNewComponent (final ParametersEnum parameter) {
		try {
			final Constructor<? extends ParameterComponent> cotr = components.get(parameter).getConstructor(ParametersEnum.class);
			return cotr.newInstance(parameter);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException | InvocationTargetException e) {
			lg.warning("Could not instantiate parameter component (" + e.getClass() + "; "
					+ e.getMessage() + ")");
			// Default component modifier
			return new TextField(parameter);
		}
	}
	
}
