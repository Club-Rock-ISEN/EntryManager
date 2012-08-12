package org.clubrockisen.model;

import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.model.abstracts.AbstractModel;

/**
 * TODO
 * @author Alex
 */
public class MemberModel extends AbstractModel {
	private static Logger		lg	= Logger.getLogger(MemberModel.class.getName());
	
	private Member				member;
	private final DAO<Member>	daoMember;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 * @param daoMember
	 *        the {@link DAO} for the members.
	 */
	public MemberModel (final DAO<Member> daoMember) {
		lg.fine("Building new " + this.getClass().getSimpleName());
		this.member = new Member();
		this.daoMember = daoMember;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#fireModelChange(java.lang.String,
	 * java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void fireModelChange (final String propertyName, final Object oldValue,
			final Object newValue) {
		// Persist changes
		if (member.getIdMember() != null) {
			daoMember.update(member);
		} else {
			member = daoMember.create(member);
		}
		super.fireModelChange(propertyName, oldValue, newValue);
	}
	
	/**
	 * Initialize model with default values.
	 */
	public void initDefault () {
		member = new Member();
	}
	
	/**
	 * Initialize model with the member.
	 * @param memberToUse
	 *        the member to use.
	 */
	public void initMember (final Member memberToUse) {
		this.member = memberToUse;
	}
	
	/**
	 * Return the name.
	 * @return the name
	 */
	public String getName () {
		return member.getName();
	}
	
	/**
	 * Set the name.
	 * @param name
	 *        the name to set
	 */
	public void setName (final String name) {
		final String oldName = getName();
		member.setName(name);
		fireModelChange("", oldName, name);
	}
	
	/**
	 * Return the gender.
	 * @return the gender
	 */
	public Gender getGender () {
		return member.getGender();
	}
	
	/**
	 * Set the gender.
	 * @param gender
	 *        the gender to set
	 */
	public void setGender (final Gender gender) {
		final Gender oldGender = getGender();
		member.setGender(gender);
		fireModelChange("", oldGender, gender);
	}
	
	/**
	 * Return the entries.
	 * @return the entries
	 */
	public int getEntries () {
		return member.getEntries();
	}
	
	/**
	 * Set the entries.
	 * @param entries
	 *        the entries to set
	 */
	public void setEntries (final int entries) {
		final int oldEntries = getEntries();
		member.setEntries(entries);
		fireModelChange("", oldEntries, entries);
	}
	
	/**
	 * Return the credit.
	 * @return the credit
	 */
	public double getCredit () {
		return member.getCredit();
	}
	
	/**
	 * Set the credit.
	 * @param credit
	 *        the credit to set
	 */
	public void setCredit (final double credit) {
		final double oldCredit = getCredit();
		member.setCredit(credit);
		fireModelChange("", oldCredit, credit);
	}
	
	/**
	 * Return the status.
	 * @return the status
	 */
	public Status getStatus () {
		return member.getStatus();
	}
	
	/**
	 * Set the status.
	 * @param status
	 *        the status to set
	 */
	public void setStatus (final Status status) {
		final Status oldStatus = getStatus();
		member.setStatus(status);
		fireModelChange("", oldStatus, status);
	}
}
