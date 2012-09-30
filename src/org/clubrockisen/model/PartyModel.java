package org.clubrockisen.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.Party.PartyColumn;
import org.clubrockisen.model.abstracts.AbstractModel;

/**
 * Model for the {@link Party} entity.
 * @author Alex
 */
public class PartyModel extends AbstractModel {
	/** Logger */
	private static Logger		lg	= Logger.getLogger(PartyModel.class.getName());
	
	/** The party represented by the model */
	private Party				party;
	/** The DAO for the parties */
	private final DAO<Party>	daoParty;
	/** Flag which indicates if the model has already been used */
	private boolean		newFlag;
	
	/**
	 * Constructor #1.<br />
	 */
	public PartyModel () {
		super();
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Building new " + this.getClass().getSimpleName());
		}
		this.party = new Party();
		this.daoParty = AbstractDAOFactory.getImplementation().getPartyDAO();
		this.newFlag = true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#persist()
	 */
	@Override
	public boolean persist () {
		boolean success = false;
		if (party.getIdParty() != null) {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Updating party " + party);
			}
			success = daoParty.update(party);
		} else {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Creating party " + party);
			}
			party = daoParty.create(party);
			success = party != null;
		}
		
		return success;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.model.abstracts.AbstractModel#reload()
	 */
	@Override
	public void reload () {
		final int id = party.getIdParty();
		newFlag = true;
		if (id > 0) {
			final Party p = daoParty.find(id);
			initParty(p);
		} else {
			initDefault();
		}
	}
	
	/**
	 * Initialize model with default values.
	 */
	public void initDefault () {
		initParty(new Party());
	}
	
	/**
	 * Initialize the model with the provided party.
	 * @param partyToUse
	 *        the party to use.
	 */
	public void initParty (final Party partyToUse) {
		party.setIdParty(partyToUse.getIdParty());
		setDate(partyToUse.getDate());
		setEntriesTotal(partyToUse.getEntriesTotal());
		setEntriesFirstPart(partyToUse.getEntriesFirstPart());
		setEntriesSecondPart(partyToUse.getEntriesSecondPart());
		setEntriesNewMembers(partyToUse.getEntriesNewMembers());
		setEntriesFree(partyToUse.getEntriesFree());
		setEntriesMale(partyToUse.getEntriesMale());
		setEntriesFemale(partyToUse.getEntriesFemale());
		setRevenue(partyToUse.getRevenue());
		setProfit(partyToUse.getProfit());
		newFlag = false;
	}
	
	/**
	 * Return the date.
	 * @return the date.
	 */
	public Long getDate () {
		return newFlag ? null : party.getDate();
	}
	
	/**
	 * Set the date.
	 * @param date the date to set.
	 */
	public void setDate (final Long date) {
		final Long oldDate = getDate();
		party.setDate(date);
		fireModelChange(PartyColumn.DATE.getPropertyName(), oldDate, date);
	}
	
	/**
	 * Return the entriesTotal.
	 * @return the entriesTotal.
	 */
	public Integer getEntriesTotal () {
		return newFlag ? null : party.getEntriesTotal();
	}
	
	/**
	 * Set the entriesTotal.
	 * @param entriesTotal the entriesTotal to set.
	 */
	public void setEntriesTotal (final Integer entriesTotal) {
		final Integer oldEntriesTotal = getEntriesTotal();
		party.setEntriesTotal(entriesTotal);
		fireModelChange(PartyColumn.ENTRIES_TOTAL.getPropertyName(), oldEntriesTotal, entriesTotal);
	}
	
	/**
	 * Return the entriesFirstPart.
	 * @return the entriesFirstPart.
	 */
	public Integer getEntriesFirstPart () {
		return newFlag ? null : party.getEntriesFirstPart();
	}
	
	/**
	 * Set the entriesFirstPart.
	 * @param entriesFirstPart the entriesFirstPart to set.
	 */
	public void setEntriesFirstPart (final Integer entriesFirstPart) {
		final Integer oldEntriesFirstPart = getEntriesFirstPart();
		party.setEntriesFirstPart(entriesFirstPart);
		fireModelChange(PartyColumn.ENTRIES_FIRST_PART.getPropertyName(), oldEntriesFirstPart, entriesFirstPart);
	}
	
	/**
	 * Return the entriesSecondPart.
	 * @return the entriesSecondPart.
	 */
	public Integer getEntriesSecondPart () {
		return newFlag ? null : party.getEntriesSecondPart();
	}
	
	/**
	 * Set the entriesSecondPart.
	 * @param entriesSecondPart the entriesSecondPart to set.
	 */
	public void setEntriesSecondPart (final Integer entriesSecondPart) {
		final Integer oldEntriesSecondPart = getEntriesSecondPart();
		party.setEntriesSecondPart(entriesSecondPart);
		fireModelChange(PartyColumn.ENTRIES_SECOND_PART.getPropertyName(), oldEntriesSecondPart, entriesSecondPart);
	}
	
	/**
	 * Return the entriesNewMembers.
	 * @return the entriesNewMembers.
	 */
	public Integer getEntriesNewMembers () {
		return newFlag ? null : party.getEntriesNewMembers();
	}
	
	/**
	 * Set the entriesNewMembers.
	 * @param entriesNewMembers the entriesNewMembers to set.
	 */
	public void setEntriesNewMembers (final Integer entriesNewMembers) {
		final Integer oldEntriesNewMembers = getEntriesNewMembers();
		party.setEntriesNewMembers(entriesNewMembers);
		fireModelChange(PartyColumn.ENTRIES_NEW_MEMBER.getPropertyName(), oldEntriesNewMembers, entriesNewMembers);
	}
	
	/**
	 * Return the entriesFree.
	 * @return the entriesFree.
	 */
	public Integer getEntriesFree () {
		return newFlag ? null : party.getEntriesFree();
	}
	
	/**
	 * Set the entriesFree.
	 * @param entriesFree the entriesFree to set.
	 */
	public void setEntriesFree (final Integer entriesFree) {
		final Integer oldEntriesFree = getEntriesFree();
		party.setEntriesFree(entriesFree);
		fireModelChange(PartyColumn.ENTRIES_FREE.getPropertyName(), oldEntriesFree, entriesFree);
	}
	
	/**
	 * Return the entriesMale.
	 * @return the entriesMale.
	 */
	public Integer getEntriesMale () {
		return newFlag ? null : party.getEntriesMale();
	}
	
	/**
	 * Set the entriesMale.
	 * @param entriesMale the entriesMale to set.
	 */
	public void setEntriesMale (final Integer entriesMale) {
		final Integer oldEntriesMale = getEntriesMale();
		party.setEntriesMale(entriesMale);
		fireModelChange(PartyColumn.ENTRIES_MALE.getPropertyName(), oldEntriesMale, entriesMale);
	}
	
	/**
	 * Return the entriesFemale.
	 * @return the entriesFemale.
	 */
	public Integer getEntriesFemale () {
		return newFlag ? null : party.getEntriesFemale();
	}
	
	/**
	 * Set the entriesFemale.
	 * @param entriesFemale the entriesFemale to set.
	 */
	public void setEntriesFemale (final Integer entriesFemale) {
		final Integer oldEntriesFemale = getEntriesFemale();
		party.setEntriesFemale(entriesFemale);
		fireModelChange(PartyColumn.ENTRIES_FEMALE.getPropertyName(), oldEntriesFemale, entriesFemale);
	}
	
	/**
	 * Return the revenue.
	 * @return the revenue.
	 */
	public Double getRevenue () {
		return newFlag ? null : party.getRevenue();
	}
	
	/**
	 * Set the revenue.
	 * @param revenue the revenue to set.
	 */
	public void setRevenue (final Double revenue) {
		final Double oldRevenue = getRevenue();
		party.setRevenue(revenue);
		fireModelChange(PartyColumn.REVENUE.getPropertyName(), oldRevenue, revenue);
	}
	
	/**
	 * Return the profit.
	 * @return the profit.
	 */
	public Double getProfit () {
		return newFlag ? null : party.getProfit();
	}
	
	/**
	 * Set the profit.
	 * @param profit the profit to set.
	 */
	public void setProfit (final Double profit) {
		final Double oldProfit = getProfit();
		party.setProfit(profit);
		fireModelChange(PartyColumn.PROFIT.getPropertyName(), oldProfit, profit);
	}
}
