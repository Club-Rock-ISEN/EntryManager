package org.clubrockisen.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.common.Constants;

import com.alexrnl.commons.utils.object.AutoCompare;
import com.alexrnl.commons.utils.object.AutoHashCode;
import com.alexrnl.commons.utils.object.Field;

/**
 * Class representing a party.
 * @author Alex
 */
public class Party extends Entity implements Cloneable, java.lang.Comparable<Party> {
	/** Logger */
	private static Logger					lg					= Logger.getLogger(Party.class.getName());
	
	/** Serial Version UID */
	private static final long				serialVersionUID	= 8268834443986169942L;
	/** The date formatter for logging */
	private final SimpleDateFormat			dateFormat			= new SimpleDateFormat(Constants.LOG_DATE_FORMAT);
	
	/** Map between the enumeration and the actual columns in the database */
	private static Map<PartyColumn, Column>	columns;
	/** Lock for the columns */
	private static Object					lock				= new Object();
	/** Name of the entity */
	private static String					entityName			= "party";
	
	/** The id of the party */
	private Integer							idParty;
	/** The date of the party */
	private Long							date;
	/** The total entries of the party */
	private Integer							entriesTotal;
	/** The number of entries during the first part */
	private Integer							entriesFirstPart;
	/** The number of entries during the second part */
	private Integer							entriesSecondPart;
	/** The number of new member entries */
	private Integer							entriesNewMembers;
	/** The number of free entries */
	private Integer							entriesFree;
	/** The number of male during the party */
	private Integer							entriesMale;
	/** The number of female during the party */
	private Integer							entriesFemale;
	/** The revenue for the party */
	private Double							revenue;
	/** The profit for the party */
	private Double							profit;
	
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
	public enum PartyColumn implements Property {
		/** The party's id */
		ID ("IdParty"),
		/** The party's date */
		DATE ("Date"),
		/** The party's entry number */
		ENTRIES_TOTAL ("EntriesTotal"),
		/** The party's  entry number for the first part */
		ENTRIES_FIRST_PART ("EntriesFirstPart"),
		/** The party's  entry number for the second part */
		ENTRIES_SECOND_PART ("EntriesSecondPart"),
		/** The party's number of new member */
		ENTRIES_NEW_MEMBER ("EntriesNewMember"),
		/** The party's number of free entries */
		ENTRIES_FREE ("EntriesFreeMember"),
		/** The party's number of male member */
		ENTRIES_MALE ("EntriesMale"),
		/** The party's number of female member */
		ENTRIES_FEMALE ("EntriesFemale"),
		/** The party's revenue */
		REVENUE ("Revenue"),
		/** The party's profit */
		PROFIT ("Profit");
		
		/** The name of the property in the class */
		private String	propertyName;
		
		/**
		 * Constructor #1.<br />
		 * Build a new enumeration, based on the name of the attribute in the class.
		 * @param propertyName
		 *        the name of the property.
		 */
		private PartyColumn (final String propertyName) {
			this.propertyName = propertyName;
		}
		
		@Override
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
			columns = new EnumMap<>(PartyColumn.class);
			columns.put(PartyColumn.ID, new Column(Integer.class, "idParty", true));
			columns.put(PartyColumn.DATE, new Column(java.sql.Date.class, "date"));
			columns.put(PartyColumn.ENTRIES_TOTAL, new Column(Integer.class, "entriesTotal"));
			columns.put(PartyColumn.ENTRIES_FIRST_PART, new Column(Integer.class, "entriesFirstPart"));
			columns.put(PartyColumn.ENTRIES_SECOND_PART, new Column(Integer.class, "entriesSecondPart"));
			columns.put(PartyColumn.ENTRIES_NEW_MEMBER, new Column(Integer.class, "entriesNewMembers"));
			columns.put(PartyColumn.ENTRIES_FREE, new Column(Integer.class, "entriesFree"));
			columns.put(PartyColumn.ENTRIES_MALE, new Column(Integer.class, "entriesMale"));
			columns.put(PartyColumn.ENTRIES_FEMALE, new Column(Integer.class, "entriesFemale"));
			columns.put(PartyColumn.REVENUE, new Column(Double.class, "revenue"));
			columns.put(PartyColumn.PROFIT, new Column(Double.class, "profit"));
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
	 * Return the list of the columns of the table party.
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
		return getIdParty().toString();
	}
	
	/**
	 * Constructor #1.<br />
	 * Constructor setting all the fields.
	 * @param idParty
	 *        the id of the party.
	 * @param date
	 *        the date of the party.
	 * @param entriesTotal
	 *        the total number of entries.
	 * @param entriesFirstPart
	 *        the entries during the first part.
	 * @param entriesSecondPart
	 *        the entries during the second part.
	 * @param entriesNewMembers
	 *        the entries of the new members.
	 * @param entriesFree
	 *        the entries of the free members.
	 * @param entriesMale
	 *        the entries of the male members.
	 * @param entriesFemale
	 *        the entries of the female members.
	 * @param revenue
	 *        the revenue of the party.
	 * @param profit
	 *        the profit of the party.
	 */
	public Party (final Integer idParty, final Long date, final Integer entriesTotal,
			final Integer entriesFirstPart, final Integer entriesSecondPart,
			final Integer entriesNewMembers, final Integer entriesFree, final Integer entriesMale,
			final Integer entriesFemale, final Double revenue, final Double profit) {
		super();
		this.idParty = idParty;
		this.date = date;
		this.entriesTotal = entriesTotal;
		this.entriesFirstPart = entriesFirstPart;
		this.entriesSecondPart = entriesSecondPart;
		this.entriesNewMembers = entriesNewMembers;
		this.entriesFree = entriesFree;
		this.entriesMale = entriesMale;
		this.entriesFemale = entriesFemale;
		this.revenue = revenue;
		this.profit = profit;
		lg.fine("New " + this.getClass().getCanonicalName() + ": " + this.date);
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor.
	 */
	public Party () {
		this(null, null, null, null, null, null, null, null, null, null, null);
	}
	
	/**
	 * Constructor #3.<br />
	 * @param date
	 *        the date of the party.
	 */
	public Party (final Long date) {
		this (null, date, null, null, null, null, null, null, null, null, null);
	}
	
	/**
	 * Return the idParty.
	 * @return the idParty.
	 */
	@Field
	public Integer getIdParty () {
		return idParty == null ? Integer.valueOf(-1) : idParty;
	}
	
	/**
	 * Set the idParty.
	 * @param idParty the idParty to set.
	 */
	public void setIdParty (final Integer idParty) {
		this.idParty = idParty;
	}
	
	/**
	 * Return the date.
	 * @return the date.
	 */
	@Field
	public Long getDate () {
		return date == null ? Long.valueOf(0) : date;
	}
	
	/**
	 * Set the date.
	 * @param date the date to set.
	 */
	public void setDate (final Long date) {
		this.date = date;
	}
	
	/**
	 * Return the entriesTotal.
	 * @return the entriesTotal.
	 */
	@Field
	public Integer getEntriesTotal () {
		return entriesTotal == null ? Integer.valueOf(0) : entriesTotal;
	}
	
	/**
	 * Set the entriesTotal.
	 * @param entriesTotal the entriesTotal to set.
	 */
	public void setEntriesTotal (final Integer entriesTotal) {
		this.entriesTotal = entriesTotal;
	}
	
	/**
	 * Return the entriesFirstPart.
	 * @return the entriesFirstPart.
	 */
	@Field
	public Integer getEntriesFirstPart () {
		return entriesFirstPart == null ? Integer.valueOf(0) : entriesFirstPart;
	}
	
	/**
	 * Set the entriesFirstPart.
	 * @param entriesFirstPart the entriesFirstPart to set.
	 */
	public void setEntriesFirstPart (final Integer entriesFirstPart) {
		this.entriesFirstPart = entriesFirstPart;
	}
	
	/**
	 * Return the entriesSecondPart.
	 * @return the entriesSecondPart.
	 */
	@Field
	public Integer getEntriesSecondPart () {
		return entriesSecondPart == null ? Integer.valueOf(0) : entriesSecondPart;
	}
	
	/**
	 * Set the entriesSecondPart.
	 * @param entriesSecondPart the entriesSecondPart to set.
	 */
	public void setEntriesSecondPart (final Integer entriesSecondPart) {
		this.entriesSecondPart = entriesSecondPart;
	}
	
	/**
	 * Return the entriesNewMembers.
	 * @return the entriesNewMembers.
	 */
	@Field
	public Integer getEntriesNewMembers () {
		return entriesNewMembers == null ? Integer.valueOf(0) : entriesNewMembers;
	}
	
	/**
	 * Set the entriesNewMembers.
	 * @param entriesNewMembers the entriesNewMembers to set.
	 */
	public void setEntriesNewMembers (final Integer entriesNewMembers) {
		this.entriesNewMembers = entriesNewMembers;
	}
	
	/**
	 * Return the entriesFree.
	 * @return the entriesFree.
	 */
	@Field
	public Integer getEntriesFree () {
		return entriesFree == null ? Integer.valueOf(0) : entriesFree;
	}
	
	/**
	 * Set the entriesFree.
	 * @param entriesFree the entriesFree to set.
	 */
	public void setEntriesFree (final Integer entriesFree) {
		this.entriesFree = entriesFree;
	}
	
	/**
	 * Return the entriesMale.
	 * @return the entriesMale.
	 */
	@Field
	public Integer getEntriesMale () {
		return entriesMale == null ? Integer.valueOf(0) : entriesMale;
	}
	
	/**
	 * Set the entriesMale.
	 * @param entriesMale the entriesMale to set.
	 */
	public void setEntriesMale (final Integer entriesMale) {
		this.entriesMale = entriesMale;
	}
	
	/**
	 * Return the entriesFemale.
	 * @return the entriesFemale.
	 */
	@Field
	public Integer getEntriesFemale () {
		return entriesFemale == null ? Integer.valueOf(0) : entriesFemale;
	}
	
	/**
	 * Set the entriesFemale.
	 * @param entriesFemale the entriesFemale to set.
	 */
	public void setEntriesFemale (final Integer entriesFemale) {
		this.entriesFemale = entriesFemale;
	}
	
	/**
	 * Return the revenue.
	 * @return the revenue.
	 */
	@Field
	public Double getRevenue () {
		return revenue == null ? Double.valueOf(0.0) : revenue;
	}
	
	/**
	 * Set the revenue.
	 * @param revenue the revenue to set.
	 */
	public void setRevenue (final Double revenue) {
		this.revenue = revenue;
	}
	
	/**
	 * Return the profit.
	 * @return the profit.
	 */
	@Field
	public Double getProfit () {
		return profit == null ? Double.valueOf(0.0) : profit;
	}
	
	/**
	 * Set the profit.
	 * @param profit the profit to set.
	 */
	public void setProfit (final Double profit) {
		this.profit = profit;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return "Party of " + dateFormat.format(new Date(getDate())) + " (id: " + idParty + ")";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode () {
		return AutoHashCode.getInstance().hashCode(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (final Object obj) {
		if (!(obj instanceof Party)) {
			return false;
		}
		return AutoCompare.getInstance().compare(this, (Party) obj);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.entities.Entity#clone()
	 */
	@Override
	public Party clone () throws CloneNotSupportedException {
		final Party clone = (Party) super.clone();
		clone.idParty = idParty;
		clone.date = date;
		clone.entriesTotal = entriesTotal;
		clone.entriesFirstPart = entriesFirstPart;
		clone.entriesSecondPart = entriesSecondPart;
		clone.entriesNewMembers = entriesNewMembers;
		clone.entriesFree = entriesFree;
		clone.entriesMale = entriesMale;
		clone.entriesFemale = entriesFemale;
		clone.revenue = revenue;
		clone.profit = profit;
		return clone;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo (final Party o) {
		if (o == null || o.date == null) {
			return -1;
		}
		return (int) (date - o.date);
	}
	
}
