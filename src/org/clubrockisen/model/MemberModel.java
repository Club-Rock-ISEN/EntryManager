package org.clubrockisen.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.model.abstracts.AbstractModel;

/**
 * Model for the {@link Member} entity.
 * @author Alex
 */
public class MemberModel extends AbstractModel {
	/** Logger */
	private static Logger		lg	= Logger.getLogger(MemberModel.class.getName());
	
	/** The member represented by the member */
	private Member				member;
	/** The DAO for members */
	private final DAO<Member>	daoMember;
	/** Flag which indicates if the model has already been used */
	private boolean				newFlag;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public MemberModel () {
		super();
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Building new " + this.getClass().getSimpleName());
		}
		this.member = new Member();
		this.daoMember = AbstractDAOFactory.getImplementation().getMemberDAO();
		this.newFlag = true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#persist()
	 */
	@Override
	public boolean persist () {
		boolean success = false;
		if (member.getIdMember() != null && member.getIdMember() != -1) {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Updating member " + member);
			}
			success = daoMember.update(member);
		} else {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Creating member " + member);
			}
			member = daoMember.create(member);
			success = member != null;
		}
		
		return success;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#reload()
	 */
	@Override
	public void reload () {
		final int id = member.getIdMember();
		newFlag = true;
		if (id > 0) {
			final Member m = daoMember.find(id);
			initMember(m);
		} else {
			initDefault();
		}
	}
	
	/**
	 * Initialize model with default values.
	 */
	public void initDefault () {
		initMember(new Member());
	}
	
	/**
	 * Initialize model with the member.
	 * @param memberToUse
	 *        the member to use.
	 */
	public void initMember (final Member memberToUse) {
		if (memberToUse == null) {
			initDefault(); // FIXME do something else!
			return;
		}
		member.setIdMember(memberToUse.getIdMember());
		setName(memberToUse.getName());
		setGender(memberToUse.getGender());
		setEntries(memberToUse.getEntries());
		setCredit(memberToUse.getCredit());
		setStatus(memberToUse.getStatus());
		newFlag = false;
	}
	
	/**
	 * Return the name.
	 * @return the name
	 */
	public String getName () {
		return newFlag ? null : member.getName();
	}
	
	/**
	 * Set the name.
	 * @param name
	 *        the name to set
	 */
	public void setName (final String name) {
		final String oldName = getName();
		member.setName(name);
		fireModelChange(MemberColumn.NAME.getPropertyName(), oldName, name);
	}
	
	/**
	 * Return the gender.
	 * @return the gender
	 */
	public Gender getGender () {
		return newFlag ? null : member.getGender();
	}
	
	/**
	 * Set the gender.
	 * @param gender
	 *        the gender to set
	 */
	public void setGender (final Gender gender) {
		final Gender oldGender = getGender();
		member.setGender(gender);
		fireModelChange(MemberColumn.GENDER.getPropertyName(), oldGender, gender);
	}
	
	/**
	 * Return the entries.
	 * @return the entries
	 */
	public Integer getEntries () {
		return newFlag ? null : member.getEntries();
	}
	
	/**
	 * Set the entries.
	 * @param entries
	 *        the entries to set
	 */
	public void setEntries (final Integer entries) {
		final Integer oldEntries = getEntries();
		member.setEntries(entries);
		fireModelChange(MemberColumn.ENTRIES.getPropertyName(), oldEntries, entries);
	}
	
	/**
	 * Return the next free entry.
	 * @return the next free entry
	 */
	public Integer getNextFree () {
		return newFlag ? null : member.getNextFree();
	}
	
	/**
	 * Set the next free.
	 * @param nextFree
	 *        the next free entry to set
	 */
	public void setNextFree (final Integer nextFree) {
		final Integer oldNextFree = getNextFree();
		member.setNextFree(nextFree);
		fireModelChange(MemberColumn.NEXT_FREE.getPropertyName(), oldNextFree, nextFree);
	}
	
	/**
	 * Return the credit.
	 * @return the credit
	 */
	public Double getCredit () {
		return newFlag ? null : member.getCredit();
	}
	
	/**
	 * Set the credit.
	 * @param credit
	 *        the credit to set
	 */
	public void setCredit (final Double credit) {
		final Double oldCredit = getCredit();
		member.setCredit(credit);
		fireModelChange(MemberColumn.CREDIT.getPropertyName(), oldCredit, credit);
	}
	
	/**
	 * Return the status.
	 * @return the status
	 */
	public Status getStatus () {
		return newFlag ? null : member.getStatus();
	}
	
	/**
	 * Set the status.
	 * @param status
	 *        the status to set
	 */
	public void setStatus (final Status status) {
		final Status oldStatus = getStatus();
		member.setStatus(status);
		fireModelChange(MemberColumn.STATUS.getPropertyName(), oldStatus, status);
	}
	
}
