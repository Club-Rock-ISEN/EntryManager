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
	private static Logger					lg					= Logger.getLogger(EntryMemberParty.class.getName());
	
	/** Serial Version UID */
	private static final long				serialVersionUID	= -2478086857006225748L;
	
	/** Map between the enumeration for the columns and the actual columns in the database */
	private static Map<EntryColumn, Column>	columns;
	/** Lock for the columns */
	private static Object					lock				= new Object();
	/** Name of the entity */
	private static String					entityName			= "entryMemberParty";
	
	/** The id of the entry member party */
	private Integer							idEntryMemberParty;
	/** The id of the member entering */
	private Integer							idMember;
	/** The id of the party where the member is entering */
	private Integer							idParty;
	
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
	public enum EntryColumn {
		/** The entry's id */
		ID ("IdEntryMemberParty"),
		/** The entry's member id */
		MEMBER_ID ("IdMember"),
		/** The entry's party id */
		PARTY_ID ("IdParty");
		
		/** The name of the property in the class */
		private String	propertyName;
		
		/**
		 * Constructor #1.<br />
		 * Build a new enumeration, based on the name of the attribute in the class.
		 * @param propertyName
		 *        the name of the property.
		 */
		private EntryColumn (final String propertyName) {
			this.propertyName = propertyName;
		}
		
		/**
		 * Return the name of the attribute in the class.
		 * @return the name of the property.
		 */
		public String getPropertyName () {
			return propertyName;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#setEntityColumns()
	 */
	@Override
	protected void setEntityColumns () {
		synchronized (lock) {
			if (columns != null) {
				return;
			}
			
			columns = new EnumMap<>(EntryColumn.class);
			columns.put(EntryColumn.ID, new Column(Integer.class, "idEntryMemberParty", true));
			columns.put(EntryColumn.MEMBER_ID, new Column(Integer.class, "idMember"));
			columns.put(EntryColumn.PARTY_ID, new Column(Integer.class, "idParty"));
		}
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
		return idEntryMemberParty == null ? Integer.valueOf(-1) : idEntryMemberParty;
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
		return idMember == null ? Integer.valueOf(-1) : idMember;
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
		return idParty == null ? Integer.valueOf(-1) : idParty;
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
