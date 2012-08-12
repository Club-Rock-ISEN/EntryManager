package org.clubrockisen.entities;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class representing a entry of a {@link Member} to a {@link Party}.
 * @author Alex
 */
public class EntryMemberParty extends Entity {
	/** Logger */
	private static Logger							lg			= Logger.getLogger(EntryMemberParty.class.getName());
	
	/** Map between the enumeration for the columns and the actual columns in the database */
	private static Map<EntryMemberColumn, Column>	columns;
	/** Name of the entity */
	private static String							entityName	= "entryMemberParty";
	
	/** The id of the entry member party */
	private Integer									idEntryMemberParty;
	/** The id of the member entering */
	private Integer									idMember;
	/** The id of the party where the member is entering */
	private Integer									idParty;
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#getEntityName()
	 */
	@Override
	public String getEntityName () {
		return entityName;
	}
	
	/**
	 * Enumeration for the columns of the table associated to the type.
	 * @author Alex
	 */
	@SuppressWarnings("javadoc")
	public enum EntryMemberColumn {
		ID, MEMBER_ID, PARTY_ID;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#setEntityColumns()
	 */
	@Override
	protected void setEntityColumns () {
		if (columns != null) {
			return;
		}
		
		columns = new EnumMap<>(EntryMemberColumn.class);
		columns.put(EntryMemberColumn.ID, new Column(Integer.class, "idEntryMemberParty", true));
		columns.put(EntryMemberColumn.MEMBER_ID, new Column(Integer.class, "idMember"));
		columns.put(EntryMemberColumn.PARTY_ID, new Column(Integer.class, "idparty"));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#getEntityColumns()
	 */
	@Override
	public Map<? extends Enum<?>, Column> getEntityColumns () {
		return columns;
	}
	
	/**
	 * Return the list of the columns of the table entry member party.
	 * @return the columns.
	 */
	public static Map<? extends Enum<?>, Column> getColumns () {
		return columns;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#getID()
	 */
	@Override
	public String getID () {
		return getIdEntryMemberParty().toString();
	}
	
	/**
	 * Constructor #1.<br />
	 * @param idEntryMemberParty
	 *        the id of this entry.
	 * @param idMember
	 *        the id of the member.
	 * @param idParty
	 *        the id of the party.
	 */
	public EntryMemberParty (final Integer idEntryMemberParty, final Integer idMember, final Integer idParty) {
		super();
		this.idEntryMemberParty = idEntryMemberParty;
		this.idMember = idMember;
		this.idParty = idParty;
		lg.fine("New " + this.getClass().getName() + " created: party");
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor.
	 */
	public EntryMemberParty () {
		this(null, (Integer) null, (Integer) null);
	}
	
	/**
	 * Constructor #3.<br />
	 * Build a entry based on a member and on a party.
	 * @param idEntryMemberParty
	 *        the id of this entry.
	 * @param member
	 *        the member.
	 * @param party
	 *        the party.
	 */
	public EntryMemberParty (final Integer idEntryMemberParty, final Member member, final Party party) {
		this(idEntryMemberParty, member.getIdMember(), party.getIdParty());
	}
	
	/**
	 * Return the attribute idEntryMemberParty.
	 * @return the attribute idEntryMemberParty.
	 */
	public Integer getIdEntryMemberParty () {
		return idEntryMemberParty;
	}
	
	/**
	 * Set the attribute idEntryMemberParty.
	 * @param idEntryMemberParty
	 *        the attribute idEntryMemberParty.
	 */
	public void setIdEntryMemberParty (final Integer idEntryMemberParty) {
		this.idEntryMemberParty = idEntryMemberParty;
	}
	
	/**
	 * Return the attribute idMember.
	 * @return the attribute idMember.
	 */
	public Integer getIdMember () {
		return idMember;
	}
	
	/**
	 * Set the attribute idMember.
	 * @param idMember
	 *        the attribute idMember.
	 */
	public void setIdMember (final Integer idMember) {
		this.idMember = idMember;
	}
	
	/**
	 * Return the attribute idParty.
	 * @return the attribute idParty.
	 */
	public Integer getIdParty () {
		return idParty;
	}
	
	/**
	 * Set the attribute idParty.
	 * @param idParty
	 *        the attribute idParty.
	 */
	public void setIdParty (final Integer idParty) {
		this.idParty = idParty;
	}
	
}
