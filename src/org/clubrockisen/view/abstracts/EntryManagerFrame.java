package org.clubrockisen.view.abstracts;

import java.nio.file.Path;

import org.clubrockisen.service.abstracts.ITranslator;
import org.clubrockisen.service.abstracts.ServiceFactory;

import com.alexrnl.commons.gui.swing.AbstractFrame;

/**
 * Abstract frame.<br />
 * Other threads may be waiting on the instance being built, the waiters will be notified when
 * the window is up and running.
 * @author Alex
 */
public abstract class EntryManagerFrame extends AbstractFrame {
	/** Serial Version UID */
	private static final long							serialVersionUID	= -3391832845968248721L;
	
	/** Translator */
	private static final transient ITranslator			TRANSLATOR			= ServiceFactory.getImplementation().getTranslator();
	/** Icon for frames */
	private static Path									icon = null;
	
	/**
	 * Set the file for the icon of the frames.<br />
	 * @param iconFile
	 *        the path to the icon file.
	 */
	public static void setIcon (final Path iconFile) {
		icon = iconFile;
	}
	
	/**
	 * Constructor #1.<br />
	 * @param title
	 *        the title of the frame.
	 * @param parameters
	 *        the parameters that will be passed to the {@link #preInit(Object...)} method.
	 */
	public EntryManagerFrame (final String title, final Object... parameters) {
		super(TRANSLATOR.get(title), icon, parameters);
	}
	
	/**
	 * Return the translator.
	 * @return the translator.
	 */
	public ITranslator getTranslator () {
		return TRANSLATOR;
	}
	
}
