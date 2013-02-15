package org.clubrockisen.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.controller.abstracts.AbstractController;
import org.clubrockisen.controller.abstracts.MemberController;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

/**
 * Basic implementation of a member controller.<br />
 * Avoid to duplicate code between controller which use members.
 * @author Alex
 */
public class MemberControllerImpl implements MemberController {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(MemberControllerImpl.class.getName());
	
	/** The actual controller used */
	private final AbstractController	controller;
	
	/**
	 * Constructor #1.<br />
	 * @param controller
	 *        the controller to be update on a model's change.
	 */
	public MemberControllerImpl (final AbstractController controller) {
		super();
		this.controller = controller;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeName(java.lang.String)
	 */
	@Override
	public void changeName (final String newName) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing member name to " + newName + " in controller");
		}
		controller.setModelProperty(MemberColumn.NAME.getPropertyName(), newName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeGender(java.lang.String)
	 */
	@Override
	public void changeGender (final Gender newGender) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing member gender to " + newGender + " in controller");
		}
		controller.setModelProperty(MemberColumn.GENDER.getPropertyName(), newGender);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeEntries(int)
	 */
	@Override
	public void changeEntries (final int newEntries) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing member entries to " + newEntries + " in controller");
		}
		controller.setModelProperty(MemberColumn.ENTRIES.getPropertyName(), newEntries);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.MemberController#changeNextFree(int)
	 */
	@Override
	public void changeNextFree (final int newNextFree) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing member next free entry to " + newNextFree + " in controller");
		}
		controller.setModelProperty(MemberColumn.NEXT_FREE.getPropertyName(), newNextFree);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeCredit(double)
	 */
	@Override
	public void changeCredit (final double newCredit) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing member credit to " + newCredit + " in controller");
		}
		controller.setModelProperty(MemberColumn.CREDIT.getPropertyName(), newCredit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeStatus(double)
	 */
	@Override
	public void changeStatus (final Status newStatus) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing member status to " + newStatus + " in controller");
		}
		controller.setModelProperty(MemberColumn.STATUS.getPropertyName(), newStatus);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.MemberController#persist()
	 */
	@Override
	public boolean persist () {
		return controller.persist();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.MemberController#reload()
	 */
	@Override
	public void reload () {
		controller.reload();
	}
	
}
