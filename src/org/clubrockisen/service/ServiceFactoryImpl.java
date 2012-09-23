package org.clubrockisen.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.service.abstracts.IEntryManager;
import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ITranslator;
import org.clubrockisen.service.abstracts.ServiceFactory;

/**
 * TODO
 * @author Alex
 */
public class ServiceFactoryImpl extends ServiceFactory {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(ServiceFactoryImpl.class.getName());
	
	/** The parameter manager */
	private final IParametersManager	parameterManager;
	/** The translator */
	private final ITranslator			translator;
	/** The entry manager */
	private final IEntryManager			entryManager;
	
	/**
	 * Constructor #1.<br />
	 * Initialize the services.
	 */
	public ServiceFactoryImpl () {
		super();
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Initialize services.");
		}
		parameterManager = new ParametersManager(AbstractDAOFactory.getImplementation());
		translator = Translator.getInstance();
		entryManager = new EntryManager(AbstractDAOFactory.getImplementation());
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ServiceFactory#getParameterManager()
	 */
	@Override
	public IParametersManager getParameterManager () {
		return parameterManager;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ServiceFactory#getTranslator()
	 */
	@Override
	public ITranslator getTranslator () {
		return translator;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ServiceFactory#getEntryManager()
	 */
	@Override
	public IEntryManager getEntryManager () {
		return entryManager;
	}
}
