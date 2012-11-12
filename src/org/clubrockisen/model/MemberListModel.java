package org.clubrockisen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.model.abstracts.AbstractModel;

/**
 * Model for the member list (result of the search).
 * @author Alex
 */
public class MemberListModel extends AbstractModel {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(MemberListModel.class.getName());
	
	/** Property name fired when the model is changing */
	public static final String MEMBER_LIST = "MemberList";
	
	/** The list with the member to be displayed in the result box */
	private List<Member>	memberList;
	
	/**
	 * Constructor #1.<br />
	 * Build the model for a result list.
	 */
	public MemberListModel () {
		super();
		memberList = new ArrayList<>(0);
	}
	
	/**
	 * Return the attribute memberList.
	 * @return the attribute memberList.
	 */
	public List<Member> getMemberList () {
		return memberList;
	}
	
	/**
	 * Set the attribute memberList.
	 * @param memberList the attribute memberList.
	 */
	public void setMemberList (final List<Member> memberList) {
		final List<Member> oldMemberList = getMemberList();
		this.memberList = memberList;
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Updating member list old: " + oldMemberList.size() + "; new: " + memberList.size());
		}
		fireModelChange(MEMBER_LIST, oldMemberList, memberList);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#persist()
	 */
	@Override
	public boolean persist () {
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#reload()
	 */
	@Override
	public void reload () {
		final DAO<Member> daoMember = AbstractDAOFactory.getImplementation().getMemberDAO();
		final List<Member> refreshedMembers = new ArrayList<>();
		for (final Member member : memberList) {
			final Member refreshedMember = daoMember.find(member.getIdMember());
			if (refreshedMember == null) {
				lg.warning("Member " + member + " doesn't seem to exist anymore.");
				continue;
			}
			refreshedMembers.add(refreshedMember);
		}
		setMemberList(refreshedMembers);
	}
}
