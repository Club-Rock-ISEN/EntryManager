package org.clubrockisen.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.service.abstracts.IEntryManager;
import org.clubrockisen.service.abstracts.IFileImporter;
import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ITranslator;
import org.clubrockisen.service.abstracts.ServiceFactory;

/**
 * Standard implementation of the services.<br />
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
	/** The file importer */
	private final IFileImporter			fileImporter;
	
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
		fileImporter = new FileImporter(AbstractDAOFactory.getImplementation().getMemberDAO());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ServiceFactory#getParameterManager()
	 */
	@Override
	public IParametersManager getParameterManager () {
		return parameterManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ServiceFactory#getTranslator()
	 */
	@Override
	public ITranslator getTranslator () {
		return translator;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ServiceFactory#getEntryManager()
	 */
	@Override
	public IEntryManager getEntryManager () {
		return entryManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ServiceFactory#getFileImporter()
	 */
	@Override
	public IFileImporter getFileImporter () {
		return fileImporter;
	}
	
}
