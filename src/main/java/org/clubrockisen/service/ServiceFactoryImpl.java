package org.clubrockisen.service;

import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.service.abstracts.IEntryManager;
import org.clubrockisen.service.abstracts.IFileManager;
import org.clubrockisen.service.abstracts.IParametersManager;
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
	private final Translator			translator;
	/** The entry manager */
	private final IEntryManager			entryManager;
	/** The file importer */
	private final IFileManager			fileManager;
	
	/**
	 * Constructor #1.<br />
	 * Initialize the services.
	 */
	public ServiceFactoryImpl () {
		super();
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Initialize services.");
		}
		parameterManager = new ParametersManager(getDaoFactory());
		translator = new Translator(Paths.get(getTranslationFile()));
		entryManager = new EntryManager(getDaoFactory());
		fileManager = new FileManager(getDaoFactory().getMemberDAO());
	}
	
	@Override
	public IParametersManager getParameterManager () {
		return parameterManager;
	}
	
	@Override
	public Translator getTranslator () {
		return translator;
	}
	
	@Override
	public IEntryManager getEntryManager () {
		return entryManager;
	}
	
	@Override
	public IFileManager getFileManager () {
		return fileManager;
	}
	
}
