package org.clubrockisen.dao.abstracts;

import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;

import com.alexrnl.commons.database.AbstractDAOFactory;
import com.alexrnl.commons.database.DAO;

/**
 * The abstract factory for the {@link DAO}s.<br />
 * Define the different DAO objects that should be created for a complete access to the database.
 * @author Alex
 */
public abstract class EntryManagerAbstractDAOFactory extends AbstractDAOFactory {

	/**
	 * Avoid cast in user classes, but this should be re-think to avoid such things, i.e. remove
	 * singleton and use instances.
	 * FIXME remove ugly cast
	 * @return the implementation.
	 */
	public static EntryManagerAbstractDAOFactory getImplementation () {
		return (EntryManagerAbstractDAOFactory) AbstractDAOFactory.getImplementation();
	}
	
	/**
	 * Retrieve a {@link DAO} for the {@link Parameter} class.
	 * @return the DAO for the parameter class.
	 */
	public abstract DAO<Parameter> getParameterDAO ();
	
	/**
	 * Retrieve a {@link DAO} for the {@link Member} class.
	 * @return the DAO for the member class.
	 */
	public abstract DAO<Member> getMemberDAO ();
	
	/**
	 * Retrieve a {@link DAO} for the {@link Party} class.
	 * @return the DAO for the party class.
	 */
	public abstract DAO<Party> getPartyDAO ();
	
	/**
	 * Retrieve a {@link DAO} for the {@link EntryMemberParty} class.
	 * @return the DAO for the entries class.
	 */
	public abstract DAO<EntryMemberParty> getEntryMemberPartyDAO ();
}
