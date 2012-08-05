package org.clubrockisen.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class representing a party.
 * @author Alex
 */
public class Party extends Entity {
	private static Logger					lg			= Logger.getLogger(Party.class.getName());
	
	private static Map<PartyColumn, Column>	columns;
	private static String					entityName	= "party";
	
	private Integer							idParty;
	private Date							date;
	private Integer							entriesTotal;
	private Integer							entriesFirstPart;
	private Integer							entriesSecondPart;
	private Integer							entriesNewMembers;
	private Integer							entriesFree;
	private Integer							entriesMale;
	private Integer							entriesFemale;
	private Double							revenue;
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
	@SuppressWarnings("javadoc")
	public enum PartyColumn {
		ID, DATE, ENTRIES_TOTAL, ENTRIES_FIRST_PART, ENTRIES_SECOND_PART, ENTRIES_NEW_MEMBER,
		ENTRIES_FREE, ENTRIES_MALE, ENTRIES_FEMALE, REVENUE, PROFIT;
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
	public Party(final Integer idParty, final Date date, final Integer entriesTotal,
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
	public Party() {
		this(null, null, null, null, null, null, null, null, null, null, null);
	}
	
	/**
	 * Return the idParty.
	 * @return the idParty.
	 */
	public Integer getIdParty () {
		return idParty;
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
	public Date getDate () {
		return date;
	}
	
	/**
	 * Set the date.
	 * @param date the date to set.
	 */
	public void setDate (final Date date) {
		this.date = date;
	}
	
	/**
	 * Return the entriesTotal.
	 * @return the entriesTotal.
	 */
	public Integer getEntriesTotal () {
		return entriesTotal;
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
	public Integer getEntriesFirstPart () {
		return entriesFirstPart;
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
	public Integer getEntriesSecondPart () {
		return entriesSecondPart;
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
	public Integer getEntriesNewMembers () {
		return entriesNewMembers;
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
	public Integer getEntriesFree () {
		return entriesFree;
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
	public Integer getEntriesMale () {
		return entriesMale;
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
	public Integer getEntriesFemale () {
		return entriesFemale;
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
	public Double getRevenue () {
		return revenue;
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
	public Double getProfit () {
		return profit;
	}
	
	/**
	 * Set the profit.
	 * @param profit the profit to set.
	 */
	public void setProfit (final Double profit) {
		this.profit = profit;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return new SimpleDateFormat("EEEE d MMMM yyyy").format(getDate());
	}
	
}
