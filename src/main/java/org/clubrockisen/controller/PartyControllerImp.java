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
	
	@Override
	public void changeDate (final long newDate) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party date to " + newDate + " in controller");
		}
		controller.setModelProperty(PartyColumn.DATE.getFieldName(), newDate);
	}
	
	@Override
	public void changeEntriesTotal (final int newEntriesTotal) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party entries total to " + newEntriesTotal + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_TOTAL.getFieldName(), newEntriesTotal);
	}
	
	@Override
	public void changeEntriesFirstPart (final int newEntriesFirstPart) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party entries first part to " + newEntriesFirstPart + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_FIRST_PART.getFieldName(), newEntriesFirstPart);
	}
	
	@Override
	public void changeEntriesSecondPart (final int newEntriesSecondPart) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party entries second part to " + newEntriesSecondPart + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_SECOND_PART.getFieldName(), newEntriesSecondPart);
	}
	
	@Override
	public void changeEntriesNewMembers (final int newEntriesNewMembers) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party entries for new members to " + newEntriesNewMembers + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_NEW_MEMBER.getFieldName(), newEntriesNewMembers);
	}
	
	@Override
	public void changeEntriesFree (final int newEntriesFree) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party free entries to " + newEntriesFree + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_FREE.getFieldName(), newEntriesFree);
	}
	
	@Override
	public void changeEntriesMale (final int newEntriesMale) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party male entries to " + newEntriesMale + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_MALE.getFieldName(), newEntriesMale);
	}
	
	@Override
	public void changeEntriesFemale (final int newEntriesFemale) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party female entries to " + newEntriesFemale + " in controller");
		}
		controller.setModelProperty(PartyColumn.ENTRIES_FEMALE.getFieldName(), newEntriesFemale);
	}
	
	@Override
	public void changeRevenue (final double newRevenue) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party revenue to " + newRevenue + " in controller");
		}
		controller.setModelProperty(PartyColumn.REVENUE.getFieldName(), newRevenue);
	}
	
	@Override
	public void changeProfit (final double newProfit) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing party profit to " + newProfit + " in controller");
		}
		controller.setModelProperty(PartyColumn.PROFIT.getFieldName(), newProfit);
	}
	
	@Override
	public boolean persist () {
		return controller.persist();
	}
	
	@Override
	public void reload () {
		controller.reload();
	}
}
