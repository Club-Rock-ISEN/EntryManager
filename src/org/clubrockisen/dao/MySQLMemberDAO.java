package org.clubrockisen.dao;

import java.util.Collection;
import java.util.logging.Logger;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Member;

/**
 * TODO implement.
 * @author Alex
 */
public class MySQLMemberDAO implements DAO<Member> {
	private static Logger	lg	= Logger.getLogger(MySQLMemberDAO.class.getName());

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#create(java.lang.Object)
	 */
	@Override
	public boolean create (final Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#find(int)
	 */
	@Override
	public Member find (final int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#update(java.lang.Object)
	 */
	@Override
	public boolean update (final Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#delete(java.lang.Object)
	 */
	@Override
	public boolean delete (final Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Member> retrieveAll () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Member> search (final Column field, final String value) {
		// TODO Auto-generated method stub
		return null;
	}
}
