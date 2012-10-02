package org.clubrockisen.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.controller.abstracts.AbstractController;
import org.clubrockisen.controller.abstracts.MemberController;
import org.clubrockisen.controller.abstracts.PartyController;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.model.MemberModel;
import org.clubrockisen.model.PartyModel;

/**
 * Controller for the main window of the applications.
 * @author Alex
 */
public class MainWindowController extends AbstractController implements MemberController, PartyController {
	/** Logger */
	private static Logger			lg	= Logger.getLogger(MainWindowController.class.getName());
	
	/** The model for the member being displayed */
	private final MemberModel		memberModel;
	/** The model for the party being displayed */
	private final PartyModel		partyModel;
	/** The controller to delegate member's update */
	private final MemberController	memberController;
	/** The controller to delegate party's update */
	private final PartyController	partyController;
	
	/**
	 * Constructor #1.<br />
	 */
	public MainWindowController () {
		super();
		// mainWindow = new MainWindow(this);
		memberModel = new MemberModel();
		partyModel = new PartyModel();
		memberController = new MemberControllerImpl(this);
		partyController = new PartyControllerImp(this);
		addModel(memberModel);
		addModel(partyModel);
		// addView(mainWindow);
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Main window controller initialized");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeDate(long)
	 */
	@Override
	public void changeDate (final long newDate) {
		partyController.changeDate(newDate);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesTotal(int)
	 */
	@Override
	public void changeEntriesTotal (final int newEntriesTotal) {
		partyController.changeEntriesTotal(newEntriesTotal);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesFirstPart(int)
	 */
	@Override
	public void changeEntriesFirstPart (final int newEntriesFirstPart) {
		partyController.changeEntriesFirstPart(newEntriesFirstPart);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesSecondPart(int)
	 */
	@Override
	public void changeEntriesSecondPart (final int newEntriesSecondPart) {
		partyController.changeEntriesSecondPart(newEntriesSecondPart);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesNewMembers(int)
	 */
	@Override
	public void changeEntriesNewMembers (final int newEntriesNewMembers) {
		partyController.changeEntriesNewMembers(newEntriesNewMembers);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesFree(int)
	 */
	@Override
	public void changeEntriesFree (final int newEntriesFree) {
		partyController.changeEntriesFree(newEntriesFree);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesMale(int)
	 */
	@Override
	public void changeEntriesMale (final int newEntriesMale) {
		partyController.changeEntriesMale(newEntriesMale);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeEntriesFemale(int)
	 */
	@Override
	public void changeEntriesFemale (final int newEntriesFemale) {
		partyController.changeEntriesFemale(newEntriesFemale);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeRevenue(double)
	 */
	@Override
	public void changeRevenue (final double newRevenue) {
		partyController.changeRevenue(newRevenue);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.PartyController#changeProfit(double)
	 */
	@Override
	public void changeProfit (final double newProfit) {
		partyController.changeProfit(newProfit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.MemberController#changeName(java.lang.String)
	 */
	@Override
	public void changeName (final String newName) {
		memberController.changeName(newName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.controller.abstracts.MemberController#changeGender(org.clubrockisen.entities
	 * .enums.Gender)
	 */
	@Override
	public void changeGender (final Gender newGender) {
		memberController.changeGender(newGender);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.MemberController#changeEntries(int)
	 */
	@Override
	public void changeEntries (final int newEntries) {
		memberController.changeEntries(newEntries);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.MemberController#changeNextFree(int)
	 */
	@Override
	public void changeNextFree (final int newNextFree) {
		memberController.changeNextFree(newNextFree);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.MemberController#changeCredit(double)
	 */
	@Override
	public void changeCredit (final double newCredit) {
		memberController.changeCredit(newCredit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.controller.abstracts.MemberController#changeStatus(org.clubrockisen.entities
	 * .enums.Status)
	 */
	@Override
	public void changeStatus (final Status newStatus) {
		memberController.changeStatus(newStatus);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#dispose()
	 */
	@Override
	public void dispose () {
		// mainWindow.dispose();
		removeModel(memberModel);
		removeModel(partyModel);
		// removeView(mainWindow);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#persist()
	 */
	@Override
	public boolean persist () {
		final boolean result = memberModel.persist();
		// Reverse the comparison to execute both methods even if the first fails.
		return partyModel.persist() && result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#reload()
	 */
	@Override
	public void reload () {
		memberModel.reload();
		partyModel.reload();
	}
}
