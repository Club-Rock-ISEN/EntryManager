package org.clubrockisen.view;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.clubrockisen.common.Constants;
import org.clubrockisen.controller.MainWindowController;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.model.SearchModel;
import org.clubrockisen.service.Translator.Key;
import org.clubrockisen.service.abstracts.Format;
import org.clubrockisen.view.abstracts.AbstractFrame;
import org.clubrockisen.view.components.MemberPanel;
import org.clubrockisen.view.components.PartyPanel;

/**
 * The main window of the application.<br />
 * @author Alex
 */
public class MainWindow extends AbstractFrame {
	/** Logger */
	private static Logger				lg					= Logger.getLogger(MainWindow.class.getName());
	
	/** Serial version UID */
	private static final long			serialVersionUID	= 8512382872996144843L;
	
	// Attributes for the controls
	/** Text field for searching members */
	private JTextField					searchBox;
	/** Member component */
	private MemberPanel					memberComponent;
	/** The list with the search results */
	private JList<Member>				resultList;
	/** Button to enter the member */
	private JButton						enterButton;
	/** Button to update the member */
	private JButton						updateButton;
	/** Party component */
	private PartyPanel					partyComponent;
	
	// Miscellaneous
	/** Controller of the main window */
	private MainWindowController		controller;
	
	/**
	 * Constructor #1.<br />
	 * Unique constructor. Build the GUI of the application.
	 * @param controller
	 *        the controller of the view.
	 */
	public MainWindow (final MainWindowController controller) {
		super(controller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractFrame#preInit(java.lang.Object[])
	 */
	@Override
	protected void preInit (final Object... parameters) {
		controller = (MainWindowController) parameters[0];
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractFrame#build()
	 */
	@Override
	protected void build () {
		setTitle(getTranslator().get(Key.GUI.title()));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		buildMenus();
		buildMainContainer();
	}
	
	/**
	 * Create the menu bar and its items.
	 */
	private void buildMenus () {
		final JMenuBar menuBar = new JMenuBar();
		// File menu creation
		final JMenu fileMenu = Utils.getMenu(Key.GUI.menu().file().toString());
		final JMenuItem profitItem = Utils.getMenuItem(Key.GUI.menu().file().profit(), new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
				final DAO<Member> daoMember = AbstractDAOFactory.getImplementation().getMemberDAO();
				//lg.info("found: " + daoMember.find(10000));
				final Member m = daoMember.create(new Member(null, "TESTTT", Gender.FEMALE, 4, 2, 0.0, Status.MEMBER));
				
				//final Member tmp = daoMember.search(Member.getColumns().get(MemberColumn.NAME), "Barféty").get(0);
				m.setEntries(m.getEntries()+1);
				m.setName("SUCCESS");
				daoMember.update(m);
				for (final Member member : daoMember.retrieveAll()) {
					lg.info(member.toString());
				}
			}
		});
		final JMenuItem parametersItem = Utils.getMenuItem(Key.GUI.menu().file().parameters(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showParameters();
			}
		});
		final JMenuItem quitItem = Utils.getMenuItem(Key.GUI.menu().file().quit(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				getFrame().dispose();
			}
		});
		fileMenu.add(profitItem);
		fileMenu.add(parametersItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);
		
		// Database menu creation
		final JMenu dataBaseMenu = Utils.getMenu(Key.GUI.menu().database().toString());
		final JMenuItem seeMembersItem = Utils.getMenuItem(Key.GUI.menu().database().seeMembers(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showAllMembers();
			}
		});
		final JMenuItem seeAttendeesItem = Utils.getMenuItem(Key.GUI.menu().database().seeAttendees(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showAttendees();
			}
		});
		final JMenuItem importDataItem = Utils.getMenuItem(Key.GUI.menu().database().importData(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				final Format format = Utils.askChoice(getFrame(), Key.GUI.dialog().chooseFormat(),
						controller.getAvailableFormat());
				if (format == null) {
					// No format chosen, chancel action
					return;
				}
				final JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(getFrame()) != JFileChooser.APPROVE_OPTION) {
					// Cancel action
					return;
				}
				final Path file = chooser.getSelectedFile().toPath();
				final Integer importedMember = controller.importFile(file, format);
				if (importedMember != null) {
					Utils.showMessageDialog(getFrame(), Key.GUI.dialog().fileImportSuccessful(file.toString(), importedMember),
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					Utils.showMessageDialog(getFrame(), Key.GUI.dialog().fileImportFailed(file.toString()),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		final JMenuItem exportDataItem = Utils.getMenuItem(Key.GUI.menu().database().exportData(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				final Format format = Utils.askChoice(getFrame(), Key.GUI.dialog().chooseFormat(),
						controller.getAvailableFormat());
				if (format == null) {
					// No format chosen, chancel action
					return;
				}
				final JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(getFrame()) != JFileChooser.APPROVE_OPTION) {
					// Cancel action
					return;
				}
				final Path file = chooser.getSelectedFile().toPath();
				if (controller.exportFile(file, format)) {
					Utils.showMessageDialog(getFrame(), Key.GUI.dialog().fileExportSuccessful(file.toString()),
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					Utils.showMessageDialog(getFrame(), Key.GUI.dialog().fileExportFailed(file.toString()),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		dataBaseMenu.add(seeMembersItem);
		dataBaseMenu.add(seeAttendeesItem);
		dataBaseMenu.addSeparator();
		dataBaseMenu.add(importDataItem);
		dataBaseMenu.add(exportDataItem);
		
		// Member menu creation
		final JMenu memberMenu = Utils.getMenu(Key.GUI.menu().member().toString());
		final JMenuItem newMemberItem = Utils.getMenuItem(Key.GUI.menu().member().newMember(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.createMember();
			}
		});
		final JMenuItem deleteMemberItem = Utils.getMenuItem(Key.GUI.menu().member().deleteMember(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (Utils.askConfirmation(getFrame(), Key.GUI.dialog().deleteMember(getSelectedMember().getName()))) {
					controller.deleteMember(getSelectedMember());
				}
			}
		});
		final JMenuItem updateMemberItem = Utils.getMenuItem(Key.GUI.menu().member().updateMember(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showMember(getSelectedMember());
			}
		});
		memberMenu.add(newMemberItem);
		memberMenu.add(deleteMemberItem);
		memberMenu.add(updateMemberItem);
		
		// Help menu creation
		final JMenu helpMenu = Utils.getMenu(Key.GUI.menu().help().toString());
		final JMenuItem helpItem = Utils.getMenuItem(Key.GUI.menu().help().help(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (!controller.showHelp()) {
					Utils.showMessageDialog(getFrame(),
							Key.GUI.dialog().helpNotDisplayable(),
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		final JMenuItem aboutItem = Utils.getMenuItem(Key.GUI.menu().help().about(), new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				Utils.showMessageDialog(getFrame(), Key.GUI.dialog().about(Constants.AUTHOR_NAME),
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(helpItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutItem);
		
		// Adding menus to menu bar
		menuBar.add(fileMenu);
		menuBar.add(dataBaseMenu);
		menuBar.add(memberMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
	}
	
	/**
	 * Builds the containers of the main window
	 */
	private void buildMainContainer () {
		final Container panel = getContentPane();
		panel.setLayout(new GridBagLayout());
		
		int xIndex = 0;
		int yIndex = 0;
		final GridBagConstraints c = new GridBagConstraints(xIndex, yIndex, 1, 1, 0.33, 0.0,
				GridBagConstraints.BASELINE_TRAILING, GridBagConstraints.HORIZONTAL,
				Constants.DEFAULT_INSETS, 0, 0);
		searchBox = new JTextField();
		searchBox.addKeyListener(new SearchKeyListener());
		panel.add(searchBox, c);
		
		c.gridy = ++yIndex;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		resultList = new JList<>();
		final JScrollPane scrollPane = new JScrollPane(resultList);
		scrollPane.setBorder(BorderFactory.createTitledBorder(getTranslator()
				.get("app.mainWindow.groupBox.searchResult")));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.addListSelectionListener(new ListSelectionListener() {
			/*
			 * (non-Javadoc)
			 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
			 */
			@Override
			public void valueChanged (final ListSelectionEvent e) {
				controller.initMember(resultList.getSelectedValue());
			}
		});
		panel.add(scrollPane, c);
		
		yIndex = 0;
		c.gridx = ++xIndex;
		c.gridy = yIndex;
		c.fill = GridBagConstraints.NONE;
		enterButton = new JButton(getTranslator().get(Key.GUI.buttons().enter()));
		panel.add(enterButton, c);
		
		c.gridx = ++xIndex;
		updateButton = new JButton(getTranslator().get(Key.GUI.buttons().update()));
		panel.add(updateButton, c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 3;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.VERTICAL;
		final JPanel memberPanel = buildMemberPanel();
		panel.add(memberPanel, c);
		
		c.gridx = 4;
		c.gridy = 0;
		final JPanel partyPanel = buildPartyPanel();
		panel.add(partyPanel, c);
	}
	
	/**
	 * Build the panel with the member information.
	 * @return the panel.
	 */
	private JPanel buildMemberPanel () {
		final JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createTitledBorder(getTranslator().get(new Member())));
		memberComponent = new MemberPanel();
		pane.add(memberComponent);
		
		return pane;
	}
	
	/**
	 * Builds the panel with the party information.
	 * @return the panel.
	 */
	private JPanel buildPartyPanel () {
		final JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createTitledBorder(getTranslator().get(new Party())));
		partyComponent = new PartyPanel();
		pane.add(partyComponent);
		
		return pane;
	}
	
	/**
	 * Return the selected member in the list.<br />
	 * Show a warning dialog if none is selected.
	 * @return the selected member or <code>null</code> if none is selected.
	 */
	private Member getSelectedMember () {
		final Member member = resultList.getSelectedValue();
		if (member == null) {
			lg.info("No member selected.");
			Utils.showMessageDialog(getFrame(),
					Key.GUI.dialog().notSelectedMember(),
					JOptionPane.WARNING_MESSAGE);
		}
		return member;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose () {
		super.dispose();
		controller.dispose();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.view.abstracts.AbstractView#modelPropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void modelPropertyChange (final PropertyChangeEvent evt) {
		memberComponent.modelPropertyChange(evt);
		partyComponent.modelPropertyChange(evt);
		if (evt.getPropertyName().equals(SearchModel.SEARCH)) {
			searchBox.setText(evt.getNewValue().toString());
			controller.updateSearchResult();
		}
		if (evt.getPropertyName().equals(MainWindowController.MEMBER_LIST)) {
			resultList.setModel((ListModel<Member>) evt.getNewValue());
		}
	}
	
	/**
	 * Key listener to be used on the search field.<br />
	 * Updates the search term, and change the focus to the list when the key specified is set.
	 * @author Alex
	 */
	private final class SearchKeyListener extends KeyAdapter {
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.KeyAdapter#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped (final KeyEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run () {
					controller.changeSearch(searchBox.getText());
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed (final KeyEvent e) {
			if (e.getKeyCode() == Constants.CHANGE_FOCUS_KEY_TRIGGER) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run () {
						if (lg.isLoggable(Level.FINE)) {
							lg.fine("Down arrow pressed, passing focus to list.");
						}
						resultList.setSelectedIndex(0);
						resultList.requestFocusInWindow();
					}
				});
			}
		}
	}
	
}
