package org.clubrockisen.entities;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

/**
 * Class representing a member of the association.
 * @author Alex
 */
public class Member extends Entity {
	private static Logger	lg	= Logger.getLogger(Member.class.getName());

	private Integer			idMember;
	private String			name;
	private Gender			gender;
	private Integer			entries;
	private Double			credit;
	private Status			status;

	@Override
	protected void setColumns () {
		if (columns != null) {
			return;
		}
		columns = new ArrayList<Column>();
		columns.add(new Column(Integer.class, "idMember", true));
		columns.add(new Column(String.class, "name"));
		columns.add(new Column(Gender.class, "gender"));
		columns.add(new Column(Integer.class, "entries"));
		columns.add(new Column(Double.class, "credit"));
		columns.add(new Column(Status.class, "status"));
	}

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
	public Member (final Integer idMember, final String name, final Gender gender,
			final int entries, final double credit, final Status status) {
		super();
		this.idMember = idMember;
		this.name = name;
		this.gender = gender;
		this.entries = entries;
		this.credit = credit;
		this.status = status;
		lg.info("New member: " + this.name + ", " + this.gender);
	}

	/**
	 * Constructor #2.<br />
	 * Default constructor
	 */
	public Member () {
		this(null, null, null, 0, 0.0, Status.getDefault());
	}

	/**
	 * Return the idMember.
	 * @return the idMember
	 */
	public Integer getIdMember () {
		return idMember;
	}

	/**
	 * Set the idMember.
	 * @param idMember
	 *            the idMember to set
	 */
	public void setIdMember (final Integer idMember) {
		this.idMember = idMember;
	}

	/**
	 * Return the name.
	 * @return the name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the name.
	 * @param name
	 *            the name to set
	 */
	public void setName (final String name) {
		this.name = name;
	}

	/**
	 * Return the gender.
	 * @return the gender
	 */
	public Gender getGender () {
		return gender;
	}

	/**
	 * Set the gender.
	 * @param gender
	 *            the gender to set
	 */
	public void setGender (final Gender gender) {
		this.gender = gender;
	}

	/**
	 * Return the entries.
	 * @return the entries
	 */
	public int getEntries () {
		return entries;
	}

	/**
	 * Set the entries.
	 * @param entries
	 *            the entries to set
	 */
	public void setEntries (final int entries) {
		this.entries = entries;
	}

	/**
	 * Return the credit.
	 * @return the credit
	 */
	public double getCredit () {
		return credit;
	}

	/**
	 * Set the credit.
	 * @param credit
	 *            the credit to set
	 */
	public void setCredit (final double credit) {
		this.credit = credit;
	}

	/**
	 * Return the status.
	 * @return the status
	 */
	public Status getStatus () {
		return status;
	}

	/**
	 * Set the status.
	 * @param status
	 *            the status to set
	 */
	public void setStatus (final Status status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return name;
	}
	
}
