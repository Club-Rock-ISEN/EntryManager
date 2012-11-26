package org.clubrockisen.service.abstracts;

import java.util.Set;
import java.util.SortedSet;

import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;

/**
 * Interface for the entry managers.<br />
 * @author Alex
 */
public interface IEntryManager {
	
	/**
	 * Retrieve the party for the current day.<br />
	 * If the party does not exist, creates it.
	 * @return the party of the day.
	 */
	Party getCurrentParty ();
	
	/**
	 * Computes the price the member has to pay to enter tonight's party.
	 * @param member
	 *        the member.
	 * @return the fee the member has to pay.
	 */
	double getPrice (Member member);
	
	/**
	 * Add an entry for the specified member for the party specified.
	 * @param member
	 *        the member to enter.
	 * @param party
	 *        the party concerned.
	 * @return <code>true</code> if the entry was successfully added.
	 */
	boolean entry (Member member, Party party);
	
	/**
	 * Add an entry for the specified member for the party specified.
	 * @param memberId
	 *        the id of the member to enter.
	 * @param partyId
	 *        the id of the party concerned.
	 * @return <code>true</code> if the entry was successfully added.
	 */
	boolean entry (int memberId, int partyId);
	
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
	 * Remove the entry of the member for the party specified.
	 * @param memberId
	 *        the id of the member to remove.
	 * @param partyId
	 *        the id of the party concerned.
	 * @return <code>true</code> in case of success.
	 */
	boolean cancelEntry (int memberId, int partyId);
	
	/**
	 * Add an entry for the specified member for the party of the current day.
	 * @param member
	 *        the member to enter.
	 * @return <code>true</code> in case of success.
	 */
	boolean entry (Member member);
	
	/**
	 * Add an entry for the specified member for the party of the current day.
	 * @param memberId
	 *        the id of the member to enter.
	 * @return <code>true</code> in case of success.
	 */
	boolean entry (int memberId);
	
	/**
	 * Remove an entry for the specified member for the party of the current day.
	 * @param member
	 *        the member to remove.
	 * @return <code>true</code> in case of success.
	 */
	boolean cancelEntry (Member member);
	
	/**
	 * Remove an entry for the specified member for the party of the current day.
	 * @param memberId
	 *        the id of the member to remove.
	 * @return <code>true</code> in case of success.
	 */
	boolean cancelEntry (int memberId);
	
	/**
	 * Retrieve the parties which the specified member have attended.
	 * @param member
	 *        the member.
	 * @return a sorted set containing the parties attended by the members.
	 */
	SortedSet<Party> getParties (Member member);
	
	/**
	 * Retrieve the parties which the specified member have attended.
	 * @param memberId
	 *        the id of the member.
	 * @return a sorted set containing the parties attended by the members.
	 */
	SortedSet<Party> getParties (int memberId);
	
	/**
	 * Retrieve the members that attended the given party.
	 * @param party
	 *        the party.
	 * @return a set with the member that were at the party.
	 */
	Set<Member> getMembers (Party party);
	
	/**
	 * Retrieve the members that attended the given party.
	 * @param partyId
	 *        the id of the party.
	 * @return a set with the member that were at the party.
	 */
	Set<Member> getMembers (int partyId);
	
}
