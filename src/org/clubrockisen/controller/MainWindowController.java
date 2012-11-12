package org.clubrockisen.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.clubrockisen.common.Configuration;
import org.clubrockisen.common.ConfigurationKey;
import org.clubrockisen.controller.abstracts.AbstractController;
import org.clubrockisen.controller.abstracts.MemberController;
import org.clubrockisen.controller.abstracts.PartyController;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.model.MemberListModel;
import org.clubrockisen.model.MemberModel;
import org.clubrockisen.model.PartyModel;
import org.clubrockisen.model.SearchModel;
import org.clubrockisen.service.abstracts.Format;
import org.clubrockisen.service.abstracts.IEntryManager;
import org.clubrockisen.service.abstracts.IFileManager;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.view.MainWindow;

/**
 * Controller for the main window of the applications.
 * @author Alex
 */
public class MainWindowController extends AbstractController implements MemberController, PartyController {
	/** Logger */
	private static Logger					lg				= Logger.getLogger(MainWindowController.class.getName());
	
	// View
	/** The main window */
	private final MainWindow				mainWindow;
	
	// Models
	/** The model for the member being displayed */
	private final MemberModel				memberModel;
	/** The model for the party being displayed */
	private final PartyModel				partyModel;
	/** The model for the search field */
	private final SearchModel				searchModel;
	/** The model for the member list */
	private final MemberListModel			memberListModel;
	
	// Delegate controllers
	/** The controller to delegate member's update */
	private final MemberController			memberController;
	/** The controller to delegate party's update */
	private final PartyController			partyController;
	
	// Controllers for other panels
	/** The panel for the member modifications */
	private final MemberPanelController		memberUpdatePanel;
	/** The panel for the parameters modifications */
	private final ParametersPanelController	parametersPanel;
	
	// Reference to required services
	/** The configuration */
	private final Configuration				configuration	= Configuration.getInstance();
	/** The configuration keys */
	private final ConfigurationKey			keys			= ConfigurationKey.KEY;
	/** The file importer */
	private final IFileManager				fileManager		= ServiceFactory.getImplementation().getFileManager();
	/** The entry manager */
	private final IEntryManager				entryManager	= ServiceFactory.getImplementation().getEntryManager();
	
	/**
	 * Constructor #1.<br />
	 */
	public MainWindowController () {
		super();
		// Other panels
		memberUpdatePanel = new MemberPanelController();
		parametersPanel = new ParametersPanelController();
		// MVC initialization
		mainWindow = new MainWindow(this);
		memberModel = new MemberModel();
		partyModel = new PartyModel();
		searchModel = new SearchModel();
		memberListModel = new MemberListModel();
		// TODO partyModel.initParty(entryManager.getCurrentParty());
		memberController = new MemberControllerImpl(this);
		partyController = new PartyControllerImp(this);
		addModel(memberModel);
		addModel(partyModel);
		addModel(searchModel);
		addModel(memberListModel);
		addView(mainWindow);
		
		// Waiting for the complete GUI generation
		synchronized (mainWindow) {
			try {
				while (!mainWindow.isReady()) {
					mainWindow.wait();
				}
			} catch (final InterruptedException e) {
				lg.warning("Main thread interrupted: " + e.getMessage());
			}
		}
		
		if (lg.isLoggable(Level.INFO)) {
			lg.info(this.getClass().getSimpleName() + " initialized");
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
	
	/**
	 * Initialize the model with the given member.<br />
	 * The member will be reloaded from the database.
	 * @param member
	 *        the member to display.
	 */
	public void initMember (final Member member) {
		memberModel.initMember(member);
		memberModel.reload();
	}
	/**
	 * Change the search string in the controller.
	 * @param newSearch
	 *        the new search string.
	 */
	public void changeSearch (final String newSearch) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Changing the search field to '" + newSearch + "'");
		}
		setModelProperty(SearchModel.SEARCH, newSearch);
	}
	
	/**
	 * Update the member list with the result of the search.
	 */
	public void updateSearchResult () {
		SwingUtilities.invokeLater(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run () {
				final DAO<Member> memberDAO = AbstractDAOFactory.getImplementation().getMemberDAO();
				lg.info("searching for '" + searchModel.getSearch() + "'");
				final List<Member> members = new ArrayList<>(memberDAO.search(Member.getColumns().get(MemberColumn.NAME),
						searchModel.getSearch()));
				memberListModel.setMemberList(members);
			}
		});
	}
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.controller.abstracts.AbstractController#dispose()
	 */
	@Override
	public void dispose () {
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Exit program.");
		}
		// No need to call the dispose method on the main window: this is the origin of the call.
		removeModel(memberModel);
		removeModel(partyModel);
		removeView(mainWindow);
		memberUpdatePanel.dispose();
		parametersPanel.dispose();
		try {
			AbstractDAOFactory.getImplementation().close();
		} catch (final IOException ex) {
			lg.warning("Error while closing DAO connection (" + ex.getMessage() + ")");
		}
	}
	
	/**
	 * Show the main window.
	 */
	public void show () {
		mainWindow.setVisible(true);
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
	
	/**
	 * Show the member panel to create a new member.
	 */
	public void createMember () {
		memberUpdatePanel.showMember(new Member());
		memberModel.reload();
	}
	
	/**
	 * Show the parameters panel.
	 */
	public void showParameters () {
		parametersPanel.show();
	}
	
	/**
	 * Retrieve the available file format from the file manager.
	 * @return a collection with the format available.
	 */
	public Collection<Format> getAvailableFormat () {
		return fileManager.getAvailableFormat();
	}
	
	/**
	 * Import a file with member in the database.
	 * @param file
	 *        the file to import.
	 * @param format
	 *        the format of the file.
	 * @return <code>true</code> if the file has correctly been imported.
	 */
	public Integer importFile (final Path file, final Format format) {
		return fileManager.parseFile(file, format);
	}
	
	/**
	 * Export a file with members in the database.
	 * @param file
	 *        the file to export.
	 * @param format
	 *        the format of the file.
	 * @return <code>true</code> if the file has correctly been imported.
	 */
	public boolean exportFile (final Path file, final Format format) {
		// TODO
		return false;
	}
	
	/**
	 * Show the specified member.
	 * @param member
	 *        the member to show.
	 */
	public void showMember (final Member member) {
		if (member != null) {
			memberUpdatePanel.showMember(member);
		}
	}
	
	/**
	 * Show the help of the program.
	 * @return <code>true</code> if the help could be displayed.
	 */
	public boolean showHelp () {
		if (Desktop.isDesktopSupported()) {
			try {
				final Path helpFile = Paths.get(configuration.get(keys.helpFile()));
				Desktop.getDesktop().browse(helpFile.toUri());
			} catch (final IOException e) {
				lg.warning("Could not display help (" + e.getClass() + "; " + e.getMessage() + ")");
				return false;
			}
			return true;
		}
		// No desktop supported
		lg.warning("Cannot show help, desktop not supported");
		return false;
	}
	
	/**
	 * Show all the members in the database.
	 */
	public void showAllMembers () {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Show the attendees of the current party.
	 */
	public void showAttendees () {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Delete the member from the database.
	 * @param member
	 *        the member to delete.
	 */
	public void deleteMember (final Member member) {
		if (member != null) {
			// TODO Auto-generated method stub
		}
	}
}
