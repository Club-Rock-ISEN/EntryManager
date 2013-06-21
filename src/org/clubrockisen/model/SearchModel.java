package org.clubrockisen.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.mvc.AbstractModel;

/**
 * Model for the search string of the main window.
 * @author Alex
 */
public class SearchModel extends AbstractModel {
	/** Logger */
	private static Logger		lg		= Logger.getLogger(SearchModel.class.getName());
	
	/** The property name which will be fire on the model's change */
	public static final String	SEARCH	= "Search";
	
	/** The search string */
	private String				search;
	
	/**
	 * Constructor #1.<br />
	 * Build the model for a search string.
	 */
	public SearchModel () {
		super();
		search = "";
	}
	
	/**
	 * Return the attribute search.
	 * @return the attribute search.
	 */
	public String getSearch () {
		return search;
	}
	
	/**
	 * Set the attribute search.
	 * @param search the attribute search.
	 */
	public void setSearch (final String search) {
		final String oldSearch = getSearch();
		this.search = search;
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Searching for '" + search + "'");
		}
		fireModelChange(SEARCH, oldSearch, search);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#persist()
	 */
	@Override
	public boolean persist () {
		// Useless for this model
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#reload()
	 */
	@Override
	public void reload () {
		// Nothing to be reloaded from the database
	}
}
