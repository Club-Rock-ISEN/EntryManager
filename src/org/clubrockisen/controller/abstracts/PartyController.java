package org.clubrockisen.controller.abstracts;

/**
 * Interface for defining a controller for a party entity.
 * @author Alex
 */
public interface PartyController {
	
	/**
	 * Change the date of the party in the model.
	 * @param newDate
	 *        the new date for the party.
	 */
	void changeDate (long newDate);
	
	/**
	 * Change the entries total of the party in the model.
	 * @param newEntriesTotal
	 *        the entries total for the party.
	 */
	void changeEntriesTotal (int newEntriesTotal);
	
	/**
	 * Change the entries first part of the party in the model.
	 * @param newEntriesFirstPart
	 *        the entries first part for the party.
	 */
	void changeEntriesFirstPart (int newEntriesFirstPart);
	
	/**
	 * Change the entries second part of the party in the model.
	 * @param newEntriesSecondPart
	 *        the entries second part for the party.
	 */
	void changeEntriesSecondPart (int newEntriesSecondPart);
	
	/**
	 * Change the entries of new members of the party in the model.
	 * @param newEntriesNewMembers
	 *        the entries of new members for the party.
	 */
	void changeEntriesNewMembers (int newEntriesNewMembers);
	
	/**
	 * Change the free entries of the party in the model.
	 * @param newEntriesFree
	 *        the free entries total for the party.
	 */
	void changeEntriesFree (int newEntriesFree);
	
	/**
	 * Change the male entries of the party in the model.
	 * @param newEntriesMale
	 *        the male entries for the party.
	 */
	void changeEntriesMale (int newEntriesMale);
	
	/**
	 * Change the female entries of the party in the model.
	 * @param newEntriesFemale
	 *        the female entries for the party.
	 */
	void changeEntriesFemale (int newEntriesFemale);
	
	/**
	 * Change the revenue of the party.
	 * @param newRevenue
	 *        the revenue for the party.
	 */
	void changeRevenue (double newRevenue);
	
	/**
	 * Change the profit of the party.
	 * @param newProfit
	 *        the profit for the party.
	 */
	void changeProfit (double newProfit);
	
	/**
	 * Persists any changes to the database.<br />
	 * @return <code>true</code> if the operation succeeded.
	 */
	boolean persist ();
	
	/**
	 * Reload the models registered from the database.<br />
	 */
	void reload ();
	
}
