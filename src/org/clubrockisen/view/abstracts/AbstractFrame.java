package org.clubrockisen.view.abstracts;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.clubrockisen.service.abstracts.IParametersManager;
import org.clubrockisen.service.abstracts.ITranslator;
import org.clubrockisen.service.abstracts.ServiceFactory;

import com.alexrnl.commons.mvc.AbstractView;

/**
 * Abstract frame.<br />
 * Other threads may be waiting on the instance being built, the waiters will be notified when
 * the window is up and running.
 * @author Alex
 */
public abstract class AbstractFrame extends JFrame implements AbstractView {
	/** Logger */
	private static Logger							lg					= Logger.getLogger(AbstractFrame.class.getName());
	
	/** Serial Version UID */
	private static final long						serialVersionUID	= -3391832845968248721L;
	
	/** Icon for frames */
	private static Image							icon = null;
	
	// Services
	/** Parameters manager */
	private final transient IParametersManager		paramManager		= ServiceFactory.getImplementation().getParameterManager();
	/** Translator */
	private final transient ITranslator				translator			= ServiceFactory.getImplementation().getTranslator();
	
	// Miscellaneous
	/** <code>true</code> when the window is ready (GUI built) */
	private boolean									ready;
	/** Reference to this */
	private final AbstractFrame						frame;
	
	/**
	 * Load the icon from the specified file.<br />
	 * It will be used for all future
	 * @param iconFile
	 *        the path to the icon file.
	 */
	public static void loadIcon (final Path iconFile) {
		try {
			icon = ImageIO.read(Files.newInputStream(iconFile));
		} catch (final IOException e) {
			lg.warning("Could not load icon, frame will be iconless (" + e.getClass() + "; " +
					e.getMessage() + ")");
		}
	}
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 * 
	 * @param parameters
	 *        the parameters that will be passed to the {@link #preInit(Object...)} method.
	 */
	public AbstractFrame (final Object... parameters) {
		super();
		ready = false;
		frame = this;
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Building abstract view with parameters: " + Arrays.toString(parameters));
		}
		preInit(parameters);
		SwingUtilities.invokeLater(new GuiBuilder());
	}
	
	/**
	 * Execute pre-initialization process.<br />
	 * Typically, set controllers, initialize required attributes.
	 * @param parameters
	 *        the parameters which were passed to the {@link #AbstractFrame(Object...) constructor}.
	 */
	protected abstract void preInit (Object... parameters);
	
	/**
	 * Build all the components of the frame.<br />
	 * Labels, buttons, menus, text fields, etc.
	 */
	protected abstract void build ();
	
	/**
	 * Build the user interface.<br />
	 * Frame will be:
	 * <ul>
	 * <li>With the icon from the configuration file.</li>
	 * <li>Not visible.</li>
	 * <li>Hidden on close.</li>
	 * <li>Minimum size set to current size after packing the frame.</li>
	 * <li>Centered.</li>
	 * </ul>
	 */
	private void buildGUI () {
		this.setIconImage(icon);
		setVisible(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		build();
		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
	}
	
	/**
	 * Check if the frame's GUI is built.
	 * @return <code>true</code> if the frame is ready.
	 */
	public final boolean isReady () {
		return ready;
	}
	
	/**
	 * Runnable class which build the components of the interface.
	 * @author Alex
	 */
	private final class GuiBuilder implements Runnable {
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run () {
			buildGUI();
			
			if (lg.isLoggable(Level.INFO)) {
				lg.info(frame.getTitle() + ": frame built");
			}
			ready = true;
			synchronized (frame) {
				frame.notifyAll();
			}
		}
	}
	
	/**
	 * Return the parameter manager.
	 * @return the parameter manager.
	 */
	public IParametersManager getParamManager () {
		return paramManager;
	}
	
	/**
	 * Return the translator.
	 * @return the translator.
	 */
	public ITranslator getTranslator () {
		return translator;
	}
	
	/**
	 * Return the reference to the current frame.
	 * @return the frame.
	 */
	public AbstractFrame getFrame () {
		return frame;
	}
	
}