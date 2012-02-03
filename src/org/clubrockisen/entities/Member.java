package org.clubrockisen.entities;

import java.util.logging.Logger;

import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

/**
 * @author Alex
 * 
 */
public class Member {
	private static Logger	T	= Logger.getLogger(Member.class.getName());

	private int				idMember;
	private String			name;
	private Gender			gender;
	private int				entries;
	private double			credit;
	private Status			status;

	/**
	 * Constructor #1.<br />
	 * Constructor using all the fields.
	 * 
	 * @param idMember
	 *            the id of the member.
	 * @param name
	 *            the name of the member.
	 * @param gender
	 *            the gender of the member.
	 * @param entries
	 *            the number of entries of the member
	 * @param credit
	 *            the credit of the member.
	 * @param status
	 *            the status of the member.
	 */
	public Member (final int idMember, final String name, final Gender gender, final int entries,
			final double credit, final Status status) {
		this.idMember = idMember;
		this.name = name;
		this.gender = gender;
		this.entries = entries;
		this.credit = credit;
		this.status = status;
	}

	/**
	 * Constructor #2.<br />
	 * Default constructor
	 */
	public Member () {

	}

	/**
	 * Returns the idMember.
	 * @return the idMember
	 */
	public int getIdMember () {
		return idMember;
	}

	/**
	 * Sets the idMember.
	 * @param idMember
	 *            the idMember to set
	 */
	public void setIdMember (final int idMember) {
		this.idMember = idMember;
	}

	/**
	 * Returns the name.
	 * @return the name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Sets the name.
	 * @param name
	 *            the name to set
	 */
	public void setName (final String name) {
		this.name = name;
	}

	/**
	 * Returns the gender.
	 * @return the gender
	 */
	public Gender getGender () {
		return gender;
	}

	/**
	 * Sets the gender.
	 * @param gender
	 *            the gender to set
	 */
	public void setGender (final Gender gender) {
		this.gender = gender;
	}

	/**
	 * Returns the entries.
	 * @return the entries
	 */
	public int getEntries () {
		return entries;
	}

	/**
	 * Sets the entries.
	 * @param entries
	 *            the entries to set
	 */
	public void setEntries (final int entries) {
		this.entries = entries;
	}

	/**
	 * Returns the credit.
	 * @return the credit
	 */
	public double getCredit () {
		return credit;
	}

	/**
	 * Sets the credit.
	 * @param credit
	 *            the credit to set
	 */
	public void setCredit (final double credit) {
		this.credit = credit;
	}

	/**
	 * Returns the status.
	 * @return the status
	 */
	public Status getStatus () {
		return status;
	}

	/**
	 * Sets the status.
	 * @param status
	 *            the status to set
	 */
	public void setStatus (final Status status) {
		this.status = status;
	}
}
