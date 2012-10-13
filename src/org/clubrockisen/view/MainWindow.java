package org.clubrockisen.view;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
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
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import org.clubrockisen.common.Constants;
import org.clubrockisen.controller.MainWindowController;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.Translator.Key;
import org.clubrockisen.service.abstracts.Format;
import org.clubrockisen.service.format.OldDataFiles;
import org.clubrockisen.view.abstracts.AbstractFrame;
import org.clubrockisen.view.components.MemberPanel;
import org.clubrockisen.view.components.PartyPanel;

/**
 * The main window of the GUI.
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
	/** Button to delete the member */
	private JButton						deleteButton;
	/** Party component */
	private PartyPanel					partyComponent;
	
	/** Model for the result list (TODO move out) */
	private DefaultListModel<Member>	resultListModel;
	
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
		final JMenu fileMenu = new JMenu(getTranslator().get(Key.GUI.menu().file().toString()));
		final JMenuItem profitItem = new JMenuItem(getTranslator().get(Key.GUI.menu().file().profit()));
		profitItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		profitItem.addActionListener(new ActionListener() {
			
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
				//final Parameter laf = ParametersManager.getInstance().get(ParametersEnum.LOOK_AND_FEEL);
				//laf.setValue("Nimbus");
				//ParametersManager.getInstance().set(laf);
			}
		});
		final JMenuItem parametersItem = new JMenuItem(getTranslator().get(Key.GUI.menu().file().parameters()));
		parametersItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
		parametersItem.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showParameters();
			}
		});
		final JMenuItem quitItem = new JMenuItem(getTranslator().get(Key.GUI.menu().file().quit()));
		quitItem.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
		quitItem.addActionListener(new ActionListener() {
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
		final JMenu dataBaseMenu = new JMenu(getTranslator().get(Key.GUI.menu().database().toString()));
		final JMenuItem seeMembersItem = new JMenuItem(getTranslator().get(Key.GUI.menu().database().seeMembers()));
		seeMembersItem.setAccelerator(KeyStroke.getKeyStroke("F12"));
		seeMembersItem.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showAllMembers();
			}
		});
		final JMenuItem seeAttendeesItem = new JMenuItem(getTranslator().get(Key.GUI.menu().database().seeAttendees()));
		seeAttendeesItem.setAccelerator(KeyStroke.getKeyStroke("F11"));
		seeAttendeesItem.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showAttendees();
			}
		});
		final JMenuItem importDataItem = new JMenuItem(getTranslator().get(Key.GUI.menu().database().importData()));
		importDataItem.setAccelerator(KeyStroke.getKeyStroke("F9"));
		importDataItem.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(getFrame()) != JFileChooser.APPROVE_OPTION) {
					// Cancel action
					return;
				}
				final Format format = OldDataFiles.getInstance(); // TODO update to allow format selection
				if (controller.importFile(chooser.getSelectedFile().toPath(), format)) {
					JOptionPane.showConfirmDialog(getFrame(), "Success!", "File parsed", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(getFrame(), "Failed!", "File parsed", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		final JMenuItem exportDataItem = new JMenuItem(getTranslator().get(Key.GUI.menu().database().exportData()));
		exportDataItem.setAccelerator(KeyStroke.getKeyStroke("F10"));
		exportDataItem.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.exportFile(null, null);
			}
		});
		dataBaseMenu.add(seeMembersItem);
		dataBaseMenu.add(seeAttendeesItem);
		dataBaseMenu.addSeparator();
		dataBaseMenu.add(importDataItem);
		dataBaseMenu.add(exportDataItem);
		
		// Member menu creation
		final JMenu memberMenu = new JMenu(getTranslator().get(Key.GUI.menu().member().toString()));
		final JMenuItem newMemberItem = new JMenuItem(getTranslator().get(Key.GUI.menu().member().newMember()));
		newMemberItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newMemberItem.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.createMember();
			}
		});
		final JMenuItem deleteMemberItem = new JMenuItem(getTranslator().get(Key.GUI.menu().member().deleteMember()));
		deleteMemberItem.setAccelerator(KeyStroke.getKeyStroke("shift DELETE"));
		deleteMemberItem.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (Utils.askConfirmation(getFrame(), Key.GUI.dialog().deleteMember())) {
					controller.deleteMember(getSelectedMember());
				}
			}
		});
		final JMenuItem updateMemberItem = new JMenuItem(getTranslator().get(Key.GUI.menu().member().updateMember()));
		updateMemberItem.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		updateMemberItem.addActionListener(new ActionListener() {
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
		final JMenu helpMenu = new JMenu(getTranslator().get(Key.GUI.menu().help().toString()));
		final JMenuItem helpItem = new JMenuItem(getTranslator().get(Key.GUI.menu().help().help()));
		helpItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		helpItem.addActionListener(new ActionListener() {
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
		final JMenuItem aboutItem = new JMenuItem(getTranslator().get(Key.GUI.menu().help().about()));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F1"));
		aboutItem.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed (final ActionEvent e) {
				JOptionPane.showMessageDialog(getFrame(),
						getTranslator().get(Key.GUI.dialog().about().author()) + " Alex Barféty.\n"
								+ getTranslator().get(Key.GUI.dialog().about().license()),
								getTranslator().get(Key.GUI.dialog().about().title()),
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
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = Constants.DEFAULT_INSETS;
		searchBox = new JTextField();
		panel.add(searchBox, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 0.33;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = Constants.DEFAULT_INSETS;
		resultListModel = new DefaultListModel<>();
		resultList = new JList<>(resultListModel);
		final JScrollPane scrollPane = new JScrollPane(resultList);
		scrollPane.setBorder(BorderFactory.createTitledBorder(getTranslator()
				.get("app.mainWindow.groupBox.searchResult")));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(scrollPane, c);
		
		searchBox.addKeyListener(new SearchBoxKeyListener(searchBox, resultList, resultListModel,
				AbstractDAOFactory.getImplementation().getMemberDAO()));
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		enterButton = new JButton(getTranslator().get("app.mainWindow.button.enter"));
		panel.add(enterButton, c);
		
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		updateButton = new JButton(getTranslator().get("app.mainWindow.button.update"));
		panel.add(updateButton, c);
		
		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		deleteButton = new JButton(getTranslator().get("app.mainWindow.button.delete"));
		panel.add(deleteButton, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 3;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = Constants.DEFAULT_INSETS;
		final JPanel memberPanel = buildMemberPanel();
		panel.add(memberPanel, c);
		
		c = new GridBagConstraints();
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 0.33;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = Constants.DEFAULT_INSETS;
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
		
	}
	
}
