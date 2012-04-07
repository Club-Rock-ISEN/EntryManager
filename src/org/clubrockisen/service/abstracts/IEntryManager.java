package org.clubrockisen.service.abstracts;

import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;

/**
 * Interface for the entry managers.<br />
 * @author Alex
 */
public interface IEntryManager {
	
	/**
	 * Add an entry for the specified member for the party specified.
	 * @param member
	 *        the member to enter.
	 * @param party
	 *        the party concerned.
	 * @return <code>true</code> in case of success.
	 */
	boolean entry (Member member, Party party);
	
	/**
	 * Remove the entry of the member for the party specified.
	 * @param member
	 *        the member to remove.
	 * @param party
	 *        the party concerned.
	 * @return <code>true</code> in case of success.
	 */
	boolean cancelEntry (Member member, Party party);
	
	/**
	 * Add an entry for the specified member for the party of the current day.
	 * @param member
	 *        the member to enter.
	 * @return <code>true</code> in case of success.
	 */
	boolean entry (Member member);
	
	/**
	 * Remove an entry for the specified member for the party of the current day.
	 * @param member
	 *        the member to remove.
	 * @return <code>true</code> in case of success.
	 */
	boolean cancelEntry (Member member);
	
}
