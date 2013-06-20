package org.clubrockisen.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.controller.abstracts.PartyController;
import org.clubrockisen.entities.Party.PartyColumn;

import com.alexrnl.commons.mvc.AbstractController;

/**
 * Basic implementation of a party controller.<br />
 * Avoid to duplicate code between controller which use parties.
 * @author Alex
 */
public class PartyControllerImp implements PartyController {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(PartyControllerImp.class.getName());
	
	/** The actual controller used */
	private final AbstractController	controller;
	
	/**
	 * Constructor #1.<br />
	 * @param controller
	 *        the controller to be update on a model's change.
	 */
	public PartyControllerImp (final AbstractController controller) {
		super();
		this.controller = controller;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeDate(long)
	 */
	@Override
	public void changeDate (final long newDate) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party date to " + newDate + " in controller");
		}
		controller.setModelProperty(PartyColumn.DATE.getPropertyName(), newDate);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesTotal(int)
	 */
	@Override
	public void changeEntriesTotal (final int newEntriesTotal) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party entries total to " + newEntriesTotal + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_TOTAL.getPropertyName(), newEntriesTotal);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesFirstPart(int)
	 */
	@Override
	public void changeEntriesFirstPart (final int newEntriesFirstPart) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party entries first part to " + newEntriesFirstPart + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_FIRST_PART.getPropertyName(), newEntriesFirstPart);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesSecondPart(int)
	 */
	@Override
	public void changeEntriesSecondPart (final int newEntriesSecondPart) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party entries second part to " + newEntriesSecondPart + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_SECOND_PART.getPropertyName(), newEntriesSecondPart);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesNewMembers(int)
	 */
	@Override
	public void changeEntriesNewMembers (final int newEntriesNewMembers) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party entries for new members to " + newEntriesNewMembers + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_NEW_MEMBER.getPropertyName(), newEntriesNewMembers);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesFree(int)
	 */
	@Override
	public void changeEntriesFree (final int newEntriesFree) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party free entries to " + newEntriesFree + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_FREE.getPropertyName(), newEntriesFree);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesMale(int)
	 */
	@Override
	public void changeEntriesMale (final int newEntriesMale) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party male entries to " + newEntriesMale + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_MALE.getPropertyName(), newEntriesMale);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesFemale(int)
	 */
	@Override
	public void changeEntriesFemale (final int newEntriesFemale) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party female entries to " + newEntriesFemale + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_FEMALE.getPropertyName(), newEntriesFemale);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeRevenue(double)
	 */
	@Override
	public void changeRevenue (final double newRevenue) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party revenue to " + newRevenue + " in controller");
		}
		controller.setModelProperty(PartyColumn.REVENUE.getPropertyName(), newRevenue);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeProfit(double)
	 */
	@Override
	public void changeProfit (final double newProfit) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party profit to " + newProfit + " in controller");
		}
		controller.setModelProperty(PartyColumn.PROFIT.getPropertyName(), newProfit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#persist()
	 */
	@Override
	public boolean persist () {
		return controller.persist();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#reload()
	 */
	@Override
	public void reload () {
		controller.reload();
	}
}
