package org.clubrockisen.entities;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

/**
 * Class representing a member of the association.
 * @author Alex
 */
public class Member extends Entity {
	private static Logger	lg	= Logger.getLogger(Member.class.getName());

	private static Map<MemberColumn, Column>	columns;
	private static String						entityName	= "member";

	private Integer								idMember;
	private String								name;
	private Gender								gender;
	private Integer								entries;
	private Double								credit;
	private Status								status;

	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#getEntityName()
	 */
	@Override
	public String getEntityName () {
		return entityName;
	}

	/**
	 * Enumeration for the column of the table associated to the type.
	 * @author Alex
	 */
	@SuppressWarnings("javadoc")
	public enum MemberColumn {
		ID, NAME, GENDER, ENTRIES, CREDIT, STATUS
	}

	@Override
	protected void setEntityColumns () {
		if (columns != null) {
			return;
		}
		columns = new EnumMap<MemberColumn, Column>(MemberColumn.class);
		columns.put(MemberColumn.ID, new Column(Integer.class, "idMember", true));
		columns.put(MemberColumn.NAME, new Column(String.class, "name"));
		columns.put(MemberColumn.GENDER, new Column(Gender.class, "gender"));
		columns.put(MemberColumn.ENTRIES, new Column(Integer.class, "entries"));
		columns.put(MemberColumn.CREDIT, new Column(Double.class, "credit"));
		columns.put(MemberColumn.STATUS, new Column(Status.class, "status"));
	}

	@Override
	public Map<? extends Enum<?>, Column> getEntityColumns () {
		return columns;
	}
	
	/**
	 * Return the list of the columns of the table Member.
	 * @return the list of the columns.
	 */
	public static Map<? extends Enum<?>, Column> getColumns () {
		return columns;
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
		lg.fine("New " + this.getClass().getCanonicalName() + ": " + this.name + ", " + this.gender);
	}

	/**
	 * Constructor #2.<br />
	 * Default constructor
	 */
	public Member () {
		this(null, null, Gender.getDefault(), 0, 0.0, Status.getDefault());
	}

	@Override
	public String getID () {
		return getIdMember().toString();
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
