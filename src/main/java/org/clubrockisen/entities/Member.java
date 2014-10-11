package org.clubrockisen.entities;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

import com.alexrnl.commons.database.structure.Column;
import com.alexrnl.commons.database.structure.Entity;
import com.alexrnl.commons.database.structure.EntityColumn;
import com.alexrnl.commons.utils.object.AutoEquals;
import com.alexrnl.commons.utils.object.AutoHashCode;
import com.alexrnl.commons.utils.object.Field;

/**
 * Class representing a member of the association.
 * @author Alex
 */
public class Member extends Entity implements Cloneable {
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
	
	@Override
	public String getEntityName () {
		return entityName;
	}
	
	/**
	 * Enumeration for the column of the table associated to the type.
	 * @author Alex
	 */
	public enum MemberColumn implements EntityColumn {
		/** The member's id */
		ID ("IdMember"),
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
		
		@Override
		public String getFieldName () {
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
	public Map<? extends Enum<? extends EntityColumn>, Column> getEntityColumns () {
		return columns;
	}
	
	/**
	 * Return the list of the columns of the table Member.
	 * @return the list of the columns.
	 */
	public static Map<? extends Enum<? extends EntityColumn>, Column> getColumns () {
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
	 * Default constructor.
	 */
	public Member () {
		this(null, null, Gender.getDefault(), null, null, null, Status.getDefault());
	}
	
	/**
	 * Constructor #3.<br />
	 * Build a member with a given name.
	 * @param name
	 *        the name of the member.
	 */
	public Member (final String name) {
		this(null, name, Gender.getDefault(), null, null, null, Status.getDefault());
	}
	
	@Override
	public String getID () {
		return getIdMember().toString();
	}
	
	/**
	 * Return the idMember.
	 * @return the idMember
	 */
	@Field
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
	@Field
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
	@Field
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
	@Field
	public Integer getEntries () {
		return entries == null ? Integer.valueOf(0) : entries;
	}
	
	/**
	 * Set the entries.
	 * @param entries
	 *        the entries to set
	 */
	public void setEntries (final Integer entries) {
		this.entries = entries;
	}
	
	/**
	 * Return the attribute nextFree.
	 * @return the attribute nextFree.
	 */
	@Field
	public Integer getNextFree () {
		return nextFree == null ? Integer.valueOf(-1) : nextFree;
	}
	
	/**
	 * Set the attribute nextFree.
	 * @param nextFree the attribute nextFree.
	 */
	@Deprecated
	public void setNextFree (final Integer nextFree) {
		this.nextFree = nextFree;
	}
	
	/**
	 * Return the credit.
	 * @return the credit
	 */
	@Field
	public Double getCredit () {
		return credit == null ? Double.valueOf(0.0) : credit;
	}
	
	/**
	 * Set the credit.
	 * @param credit
	 *        the credit to set
	 */
	public void setCredit (final Double credit) {
		this.credit = credit;
	}
	
	/**
	 * Return the status.
	 * @return the status
	 */
	@Field
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
	
	@Override
	public String toString () {
		return name == null ? "" : name;
	}
	
	@Override
	public int hashCode () {
		return AutoHashCode.getInstance().hashCode(this);
	}
	
	@Override
	public boolean equals (final Object obj) {
		if (!(obj instanceof Member)) {
			return false;
		}
		return AutoEquals.getInstance().compare(this, (Member) obj);
	}
	
	@Override
	public Entity clone () throws CloneNotSupportedException {
		final Member clone =  new Member();
		clone.idMember = idMember;
		clone.name = name;
		clone.gender = gender;
		clone.entries = entries;
		clone.nextFree = nextFree;
		clone.credit = credit;
		clone.status = status;
		return clone;
	}
	
	/**
	 * Print all the fields of the member.<br />
	 * @return a String containing all the field values.
	 */
	public String printDetails () {
		return "name: " + name + "\t"
				+ "gender: " + gender + "\t"
				+ "entries: " + entries + "\t"
				+ "nextFree: " + nextFree + "\t"
				+ "credit: " + credit + "\t"
				+ "status: " + status;
	}
	
}
