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
	/** Logger */
	private static Logger						lg					= Logger.getLogger(Member.class.getName());
	
	/** Serial Version UID */
	private static final long					serialVersionUID	= 7811625399175984654L;
	
	/** Map between the column enumeration and the actual columns from the database */
	private static Map<MemberColumn, Column>	columns;
	/** Lock for the columns */
	private static Object						lock				= new Object();
	/** Name of the entity */
	private static String						entityName			= "member";
	
	// The properties of the member
	/** The id of the member */
	private Integer								idMember;
	/** The name of the member */
	private String								name;
	/** The gender of the member */
	private Gender								gender;
	/** The number of entries of this member */
	private Integer								entries;
	/** The number of remaining entry before a free entry */
	private Integer								nextFree;
	/** The credit on the member's account */
	private Double								credit;
	/** The status of the member */
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
	public enum MemberColumn {
		/** The member's id */
		ID ("Id"),
		/** The member's name */
		NAME ("Name"),
		/** The member's gender */
		GENDER ("Gender"),
		/** The member's entries */
		ENTRIES ("Entries"),
		/** The member's next free entry counter */
		NEXT_FREE ("NextFree"),
		/** The member's credit */
		CREDIT ("Credit"),
		/** The member's status */
		STATUS ("Status");
		
		/** The name of the property in the class */
		private String	propertyName;
		
		/**
		 * Constructor #1.<br />
		 * Build a new enumeration, based on the name of the attribute in the class.
		 * @param propertyName
		 *        the name of the property.
		 */
		private MemberColumn (final String propertyName) {
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
	
	@Override
	protected void setEntityColumns () {
		synchronized (lock) {
			if (columns != null) {
				return;
			}
			columns = new EnumMap<>(MemberColumn.class);
			columns.put(MemberColumn.ID, new Column(Integer.class, "idMember", true));
			columns.put(MemberColumn.NAME, new Column(String.class, "name"));
			columns.put(MemberColumn.GENDER, new Column(Gender.class, "gender"));
			columns.put(MemberColumn.ENTRIES, new Column(Integer.class, "entries"));
			columns.put(MemberColumn.NEXT_FREE, new Column(Integer.class, "nextFree"));
			columns.put(MemberColumn.CREDIT, new Column(Double.class, "credit"));
			columns.put(MemberColumn.STATUS, new Column(Status.class, "status"));
		}
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
	 *        the id of the member.
	 * @param name
	 *        the name of the member.
	 * @param gender
	 *        the gender of the member.
	 * @param entries
	 *        the number of entries of the member
	 * @param nextFree
	 *        the number of entries until the next free.
	 * @param credit
	 *        the credit of the member.
	 * @param status
	 *        the status of the member.
	 */
	public Member (final Integer idMember, final String name, final Gender gender,
			final Integer entries, final Integer nextFree, final Double credit, final Status status) {
		super();
		this.idMember = idMember;
		this.name = name;
		this.gender = gender;
		this.entries = entries;
		this.nextFree = nextFree;
		this.credit = credit;
		this.status = status;
		lg.fine("New " + this.getClass().getCanonicalName() + ": " + this.name + ", " + this.gender);
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor
	 */
	public Member () {
		this(null, null, Gender.getDefault(), null, null, null, Status.getDefault());
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
		return idMember == null ? Integer.valueOf(-1) : idMember;
	}
	
	/**
	 * Set the idMember.
	 * @param idMember
	 *        the idMember to set
	 */
	public void setIdMember (final Integer idMember) {
		this.idMember = idMember;
	}
	
	/**
	 * Return the name.
	 * @return the name
	 */
	public String getName () {
		return name == null ? "" : name;
	}
	
	/**
	 * Set the name.
	 * @param name
	 *        the name to set
	 */
	public void setName (final String name) {
		this.name = name;
	}
	
	/**
	 * Return the gender.
	 * @return the gender
	 */
	public Gender getGender () {
		return gender == null ? Gender.getDefault() : gender;
	}
	
	/**
	 * Set the gender.
	 * @param gender
	 *        the gender to set
	 */
	public void setGender (final Gender gender) {
		this.gender = gender;
	}
	
	/**
	 * Return the entries.
	 * @return the entries
	 */
	public Integer getEntries () {
		return entries == null ? Integer.valueOf(0) : entries;
	}
	
	/**
	 * Set the entries.
	 * @param entries
	 *        the entries to set
	 */
	public void setEntries (final int entries) {
		this.entries = entries;
	}
	
	/**
	 * Return the attribute nextFree.
	 * @return the attribute nextFree.
	 */
	public Integer getNextFree () {
		return nextFree == null ? Integer.valueOf(-1) : nextFree;
	}
	
	/**
	 * Set the attribute nextFree.
	 * @param nextFree the attribute nextFree.
	 */
	public void setNextFree (final Integer nextFree) {
		this.nextFree = nextFree;
	}
	
	/**
	 * Return the credit.
	 * @return the credit
	 */
	public Double getCredit () {
		return credit == null ? Double.valueOf(0.0) : credit;
	}
	
	/**
	 * Set the credit.
	 * @param credit
	 *        the credit to set
	 */
	public void setCredit (final double credit) {
		this.credit = credit;
	}
	
	/**
	 * Return the status.
	 * @return the status
	 */
	public Status getStatus () {
		return status == null ? Status.getDefault() : status;
	}
	
	/**
	 * Set the status.
	 * @param status
	 *        the status to set
	 */
	public void setStatus (final Status status) {
		this.status = status;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return name == null ? "" : name;
	}
	
}
