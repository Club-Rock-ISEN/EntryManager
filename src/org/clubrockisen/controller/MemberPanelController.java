package org.clubrockisen.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.controller.abstracts.MemberController;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.model.MemberModel;
import org.clubrockisen.view.MemberView;

import com.alexrnl.commons.mvc.AbstractController;

/**
 * Controller for the member update panel.<br />
 * @author Alex
 */
public class MemberPanelController extends AbstractController implements MemberController {
	/** Logger */
	private static Logger			lg	= Logger.getLogger(MemberPanelController.class.getName());
	
	/** The view showing the member's attributes */
	private final MemberView		memberView;
	/** The member model to be controlled by this and shown by the view */
	private final MemberModel		memberModel;
	/** The controller to delegate the model's updates */
	private final MemberController	memberController;
	
	/**
	 * Constructor #1.<br />
	 */
	public MemberPanelController () {
		super();
		memberView = new MemberView(this);
		memberModel = new MemberModel();
		memberController = new MemberControllerImpl(this);
		addModel(memberModel);
		addView(memberView);
		
		// Waiting for the complete GUI generation
		synchronized (memberView) {
			try {
				while (!memberView.isReady()) {
					memberView.wait();
				}
			} catch (final InterruptedException e) {
				lg.warning("Main thread interrupted: " + e.getMessage());
			}
		}
		
		if (lg.isLoggable(Level.INFO)) {
			lg.info(this.getClass().getSimpleName() + " initialized");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeName(java.lang.String)
	 */
	@Override
	public void changeName (final String newName) {
		memberController.changeName(newName);
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeGender(java.lang.String)
	 */
	@Override
	public void changeGender (final Gender newGender) {
		memberController.changeGender(newGender);
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeEntries(int)
	 */
	@Override
	public void changeEntries (final int newEntries) {
		memberController.changeEntries(newEntries);
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.MemberController#changeNextFree(int)
	 */
	@Override
	public void changeNextFree (final int newNextFree) {
		memberController.changeNextFree(newNextFree);
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeCredit(double)
	 */
	@Override
	public void changeCredit (final double newCredit) {
		memberController.changeCredit(newCredit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.MemberController#changeStatus(double)
	 */
	@Override
	public void changeStatus (final Status newStatus) {
		memberController.changeStatus(newStatus);
	}
	
	/**
	 * Show the panel which allow to edit a member.
	 * @param member
	 *        the member to edit.
	 */
	public void showMember (final Member member) {
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Showing member " + member);
		}
		memberModel.initMember(member);
		memberView.setVisible(true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#persist()
	 */
	@Override
	public boolean persist () {
		return memberModel.persist();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#dispose()
	 */
	@Override
	public void dispose () {
		memberView.dispose();
		removeModel(memberModel);
		removeView(memberView);
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#reload()
	 */
	@Override
	public void reload () {
		memberModel.reload();
	}
	
}
