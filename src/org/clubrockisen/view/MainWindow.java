package org.clubrockisen.view;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
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

import org.clubrockisen.common.ConfigurationKey;
import org.clubrockisen.common.Constants;
import org.clubrockisen.controller.MainWindowController;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.service.Translator;
import org.clubrockisen.service.abstracts.Format;
import org.clubrockisen.service.format.OldDataFiles;
import org.clubrockisen.view.abstracts.AbstractFrame;
import org.clubrockisen.view.components.MemberPanel;

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
	private JButton						enterButton, updateButton, deleteButton;
	private JLabel						nameField, genderField, statusField, entryNumberField,
	nextFreeField, entryPartyTotalNumberField, entryPartyFirstPartNumberField,
	entryPartySecondPartNumberField, newMemberNumberField, freeMemberNumberField,
	maleNumberField, femaleNumberField, deltaField, revenueField, profitField;
	
	/** Model for the result list (TODO move out) */
	private DefaultListModel<Member>	resultListModel;
	
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
		this.setTitle(getTranslator().get(Translator.Key.GUI.title()));
		try {
			this.setIconImage(ImageIO.read(new File(getConfig().get(ConfigurationKey.KEY.iconFile()))));
		} catch (final IOException e) {
			lg.warning("Could not load icon for main window.");
		}
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		buildMenus();
		buildMainContainer();
	}
	
	/**
	 * Create the menu bar and its items.
	 */
	private void buildMenus () {
		final JMenuBar menuBar = new JMenuBar();
		// File menu creation
		final JMenu fileMenu = new JMenu(getTranslator().get(Translator.Key.GUI.menu().file().toString()));
		final JMenuItem profitItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().file().profit()));
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
		final JMenuItem parametersItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().file().parameters()));
		parametersItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
		parametersItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showParameters();
			}
		});
		final JMenuItem quitItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().file().quit()));
		quitItem.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
		quitItem.addActionListener(new ActionListener() {
			
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
		final JMenu dataBaseMenu = new JMenu(getTranslator().get(Translator.Key.GUI.menu().database().toString()));
		final JMenuItem seeMembersItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().database().seeMembers()));
		seeMembersItem.setAccelerator(KeyStroke.getKeyStroke("F12"));
		seeMembersItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		final JMenuItem seeAttendeesItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().database().seeAttendees()));
		seeAttendeesItem.setAccelerator(KeyStroke.getKeyStroke("F11"));
		seeAttendeesItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		final JMenuItem exportDataItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().database().exportData()));
		exportDataItem.setAccelerator(KeyStroke.getKeyStroke("F10"));
		exportDataItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		final JMenuItem importDataItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().database().importData()));
		importDataItem.setAccelerator(KeyStroke.getKeyStroke("F9"));
		importDataItem.addActionListener(new ActionListener() {
			
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
		dataBaseMenu.add(seeMembersItem);
		dataBaseMenu.add(seeAttendeesItem);
		dataBaseMenu.addSeparator();
		dataBaseMenu.add(importDataItem);
		dataBaseMenu.add(exportDataItem);
		
		// Member menu creation
		final JMenu memberMenu = new JMenu(getTranslator().get(Translator.Key.GUI.menu().member().toString()));
		final JMenuItem newMemberItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().member().newMember()));
		newMemberItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newMemberItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.createMember();
			}
		});
		final JMenuItem deleteMemberItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().member().deleteMember()));
		deleteMemberItem.setAccelerator(KeyStroke.getKeyStroke("shift DELETE"));
		deleteMemberItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		final JMenuItem updateMemberItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().member().updateMember()));
		updateMemberItem.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		updateMemberItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (resultList.getSelectedValue() == null) {
					lg.info("No member selected, cannot show update member window");
					Utils.showMessageDialog(MainWindow.this, Translator.Key.GUI.dialog()
							.notSelectedMember(), JOptionPane.WARNING_MESSAGE);
				} else {
					// TODO move result list model to controller?
					controller.showMember(resultList.getSelectedValue());
				}
			}
		});
		memberMenu.add(newMemberItem);
		memberMenu.add(deleteMemberItem);
		memberMenu.add(updateMemberItem);
		
		// Help menu creation
		final JMenu helpMenu = new JMenu(getTranslator().get(Translator.Key.GUI.menu().help().toString()));
		final JMenuItem helpItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().help().help()));
		helpItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		helpItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		final JMenuItem aboutItem = new JMenuItem(getTranslator().get(Translator.Key.GUI.menu().help().about()));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F1"));
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				JOptionPane.showMessageDialog(getFrame(),
						getTranslator().get(Translator.Key.GUI.dialog().about().author()) + " Alex Barféty.\n"
								+ getTranslator().get(Translator.Key.GUI.dialog().about().license()),
								getTranslator().get(Translator.Key.GUI.dialog().about().title()),
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
		final JPanel pane = new JPanel(new GridBagLayout());
		pane.setBorder(BorderFactory.createTitledBorder(getTranslator().get(new Party())));
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel entryPartyTotalNumberLabel = new JLabel(getTranslator().get("app.mainWindow.label.entryPartyTotal"));
		pane.add(entryPartyTotalNumberLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel entryPartyFirstPartNumberLabel = new JLabel(getTranslator().get("app.mainWindow.label.entryPartyFirstPart"));
		pane.add(entryPartyFirstPartNumberLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel entryPartySecondPartNumberLabel = new JLabel(getTranslator().get("app.mainWindow.label.entryPartySecondPart"));
		pane.add(entryPartySecondPartNumberLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel newMemberNumberLabel = new JLabel(getTranslator().get("app.mainWindow.label.newMemberNumber"));
		pane.add(newMemberNumberLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel freeMemberNumberLabel = new JLabel(getTranslator().get("app.mainWindow.label.freeMemberNumber"));
		pane.add(freeMemberNumberLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel maleNumberLabel = new JLabel(getTranslator().get("app.mainWindow.label.maleNumber"));
		pane.add(maleNumberLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel femaleNumberLabel = new JLabel(getTranslator().get("app.mainWindow.label.femaleNumber"));
		pane.add(femaleNumberLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel deltaLabel = new JLabel(Constants.DELTA + " :");
		pane.add(deltaLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel revenueLabel = new JLabel(getTranslator().get("app.mainWindow.label.revenue"));
		pane.add(revenueLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		final JLabel profitLabel = new JLabel(getTranslator().get("app.mainWindow.label.profit"));
		pane.add(profitLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		entryPartyTotalNumberField = new JLabel("12");
		pane.add(entryPartyTotalNumberField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		entryPartyFirstPartNumberField = new JLabel("0");
		pane.add(entryPartyFirstPartNumberField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		entryPartySecondPartNumberField = new JLabel("0");
		pane.add(entryPartySecondPartNumberField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		newMemberNumberField = new JLabel("3");
		pane.add(newMemberNumberField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		freeMemberNumberField = new JLabel("1");
		pane.add(freeMemberNumberField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		maleNumberField = new JLabel("4");
		pane.add(maleNumberField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		femaleNumberField = new JLabel("8");
		pane.add(femaleNumberField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		deltaField = new JLabel("4");
		pane.add(deltaField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		revenueField = new JLabel("48 eur");
		pane.add(revenueField, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = Constants.DEFAULT_INSETS;
		profitField = new JLabel("8 eur");
		pane.add(profitField, c);
		
		return pane;
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
		// TODO Auto-generated method stub
		memberComponent.modelPropertyChange(evt);
	}
	
}
