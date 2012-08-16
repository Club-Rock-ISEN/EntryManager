package org.clubrockisen.controller.abstracts;

import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

/**
 * Interface for defining a controller for a member entity.
 * @author Alex
 */
public interface MemberController {
	
	/**
	 * Change the name of the member in the model.
	 * @param newName
	 *        the new name for the member.
	 */
	void changeName (final String newName);
	
	/**
	 * Change the gender of the member in the model.
	 * @param newGender
	 *        the new gender for the member.
	 */
	void changeGender (final Gender newGender);
	
	/**
	 * Change the entries of the member in the model.
	 * @param newEntries
	 *        the new entry number for the member.
	 */
	void changeEntries (final int newEntries);
	
	/**
	 * Change the credit of the member in the model.
	 * @param newCredit
	 *        the new credit for the member.
	 */
	void changeCredit (final double newCredit);
	
	/**
	 * Change the status of the member in the model.
	 * @param newStatus
	 *        the new status for the member.
	 */
	void changeStatus (final Status newStatus);
	
}