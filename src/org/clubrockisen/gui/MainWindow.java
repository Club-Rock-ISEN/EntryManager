package org.clubrockisen.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.clubrockisen.dao.AbstractDAOFactory;
import org.clubrockisen.dao.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;
import org.clubrockisen.tools.ParametersEnum;
import org.clubrockisen.tools.ParametersManager;

/**
 * The main window of the GUI.
 * @author Alex
 */
public class MainWindow extends JFrame {
	private static Logger				lg					= Logger.getLogger(MainWindow.class
																	.getName());
	/**
	 * Serial version UID
	 */
	private static final long			serialVersionUID	= 8512382872996144843L;

	/**
	 * The unicode for delta
	 */
	public static final String			DELTA				= "\u0394";

	/** Attributes for the controls **/
	private MainWindow					mainWindow;
	private JMenuBar					menuBar;
	private JMenu						fileMenu, dataBaseMenu, memberMenu, helpMenu;
	private JMenuItem					profitItem, parametersItem, quitItem, seeMembersItem,
			seeAttendeesItem, exportDataItem, newMemberItem, deleteMemberItem, updateMemberItem,
			helpItem, aboutItem;
	private Container					panel, memberPanel, partyPanel;
	private JTextField					searchBox;
	private JList<Member>				resultList;
	private JButton						enterButton, updateButton, deleteButton;
	private JLabel						nameLabel, genderLabel, statusLabel, entryNumberLabel,
			nextFreeLabel, nameField, genderField, statusField, entryNumberField, nextFreeField,
			entryPartyTotalNumberLabel, entryPartyFirstPartNumberLabel,
			entryPartySecondPartNumberLabel, newMemberNumberLabel, freeMemberNumberLabel,
			maleNumberLabel, femaleNumberLabel, deltaLabel, revenueLabel, profitLabel,
			entryPartyTotalNumberField, entryPartyFirstPartNumberField,
			entryPartySecondPartNumberField, newMemberNumberField, freeMemberNumberField,
			maleNumberField, femaleNumberField, deltaField, revenueField, profitField;

	private DefaultListModel<Member>	resultListModel;
	private final Insets				defaultInsets		= new Insets(5, 5, 5, 5);
	
	private final Properties					translator;
	
	private final DAO<Member>					daoMember;

	/**
	 * Constructor #1.<br />
	 * Unique constructor. Build the GUI of the application.
	 * @param translationFile
	 *            the file with the text to load for the GUI
	 * @param daoFactory
	 *            the factory for the DAO.
	 */
	public MainWindow (final String translationFile, final AbstractDAOFactory daoFactory) {
		super();
		daoMember = daoFactory.getMemberDAO();
		
		translator = new Properties();
		try {
			translator.loadFromXML(new FileInputStream(translationFile));
		} catch (final IOException e) {
			lg.severe("Could not load translation file: " + translationFile + " (" + e.getMessage() + ")");
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run () {
				buildGUI();
				lg.info("Building main window.");
			}
		});
	}
	
	/**
	 * Sets the look and feel of the application
	 */
	public static void setLookAndFeel () {
		final String lookAndFeelName = ParametersManager.getInstance().get(ParametersEnum.LOOK_AND_FEEL).getValue();
		boolean lookAndFeelFound = false;
		for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			lg.fine(laf.getName());
			if (laf.getName().equals(lookAndFeelName)) {
				try {
					UIManager.setLookAndFeel(laf.getClassName());
					lg.fine("Look and feel properly setted (" + laf.getName() + ").");
					lookAndFeelFound = true;
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					lg.warning("Could not set the look and feel " + laf.getName());
					lookAndFeelFound = false;
				}
			}
		}
		if (!lookAndFeelFound) {
			lg.warning("Could not find (or set) the look and feel " + lookAndFeelName
					+ ". Using default look and feel.");
		}
	}

	/**
	 * Create the controls, menu, labels, etc.
	 */
	private void buildGUI () {
		this.setTitle(translator.getProperty("app.title"));
		this.setSize(900, 600);
		try {
			this.setIconImage(ImageIO.read(new File("./data/images/icon.png")));
		} catch (final IOException e) {
			lg.warning("Could not load icon for main window.");
		}
		this.setMinimumSize(new Dimension(850, 425));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		buildMenus();
		buildMainContainer();
		mainWindow = this;
	}

	/**
	 * Create the menu bar and its items.
	 */
	private void buildMenus () {
		menuBar = new JMenuBar();
		// File menu creation
		fileMenu = new JMenu(translator.getProperty("app.menu.file"));
		profitItem = new JMenuItem(translator.getProperty("app.menu.file.profit"));
		profitItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		profitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		parametersItem = new JMenuItem(translator.getProperty("app.menu.file.parameters"));
		parametersItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
		parametersItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
				daoMember.create(new Member(null, "TESTTT", Gender.FEMALE, 4, 0.0, Status.MEMBER));
				final Member tmp = daoMember.find(1);
				tmp.setEntries(tmp.getEntries()+1);
				tmp.setName("SUCCESS");
				daoMember.update(tmp);
				final Parameter laf = ParametersManager.getInstance().get(ParametersEnum.LOOK_AND_FEEL);
				laf.setValue("Nimbus");
				ParametersManager.getInstance().set(laf);
			}
		});
		quitItem = new JMenuItem(translator.getProperty("app.menu.file.quit"));
		quitItem.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
		quitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				lg.info("Exit program.");
				System.exit(0);
			}
		});
		fileMenu.add(profitItem);
		fileMenu.add(parametersItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);

		// Database menu creation
		dataBaseMenu = new JMenu(translator.getProperty("app.menu.database"));
		seeMembersItem = new JMenuItem(translator.getProperty("app.menu.database.seeMembers"));
		seeMembersItem.setAccelerator(KeyStroke.getKeyStroke("F12"));
		seeMembersItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		seeAttendeesItem = new JMenuItem(translator.getProperty("app.menu.database.seeAttendees"));
		seeAttendeesItem.setAccelerator(KeyStroke.getKeyStroke("F11"));
		seeAttendeesItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		exportDataItem = new JMenuItem(translator.getProperty("app.menu.database.exportData"));
		exportDataItem.setAccelerator(KeyStroke.getKeyStroke("F10"));
		exportDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		dataBaseMenu.add(seeMembersItem);
		dataBaseMenu.add(seeAttendeesItem);
		dataBaseMenu.addSeparator();
		dataBaseMenu.add(exportDataItem);

		// Member menu creation
		memberMenu = new JMenu(translator.getProperty("app.menu.member"));
		newMemberItem = new JMenuItem(translator.getProperty("app.menu.newMember"));
		newMemberItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newMemberItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		deleteMemberItem = new JMenuItem(translator.getProperty("app.menu.deleteMember"));
		deleteMemberItem.setAccelerator(KeyStroke.getKeyStroke("shift DELETE"));
		deleteMemberItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		updateMemberItem = new JMenuItem(translator.getProperty("app.menu.updateMember"));
		updateMemberItem.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		updateMemberItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		memberMenu.add(newMemberItem);
		memberMenu.add(deleteMemberItem);
		memberMenu.add(updateMemberItem);

		// Help menu creation
		helpMenu = new JMenu(translator.getProperty("app.menu.help"));
		helpItem = new JMenuItem(translator.getProperty("app.menu.help"));
		helpItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		helpItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		aboutItem = new JMenuItem(translator.getProperty("app.menu.help.about"));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F1"));
		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				JOptionPane.showMessageDialog(mainWindow,
						translator.getProperty("app.dialog.about.author") + " Alex Barféty.\n"
								+ translator.getProperty("app.dialog.about.licence"),
						translator.getProperty("app.menu.help.about"),
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
		this.setJMenuBar(menuBar);
	}

	/**
	 * Builds the containers of the main window
	 */
	private void buildMainContainer () {
		panel = getContentPane();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = defaultInsets;
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
		c.insets = defaultInsets;
		resultListModel = new DefaultListModel<Member>();
		resultList = new JList<Member>(resultListModel);
		final JScrollPane scrollPane = new JScrollPane(resultList);
		scrollPane.setBorder(BorderFactory.createTitledBorder(translator
				.getProperty("app.mainWindow.groupBox.searchResult")));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(scrollPane, c);
		
		searchBox.addKeyListener(new SearchBoxKeyListener(searchBox, resultList, resultListModel,
				daoMember));

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		enterButton = new JButton(translator.getProperty("app.mainWindow.button.enter"));
		panel.add(enterButton, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		updateButton = new JButton(translator.getProperty("app.mainWindow.button.update"));
		panel.add(updateButton, c);

		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		deleteButton = new JButton(translator.getProperty("app.mainWindow.button.delete"));
		panel.add(deleteButton, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 3;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = defaultInsets;
		memberPanel = buildMemberPanel();
		panel.add(memberPanel, c);

		c = new GridBagConstraints();
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 0.33;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = defaultInsets;
		partyPanel = buildPartyPanel();
		panel.add(partyPanel, c);
	}

	/**
	 * Build the panel with the member information.
	 * @return the panel.
	 */
	private JPanel buildMemberPanel () {
		final JPanel pane = new JPanel(new GridBagLayout());
		pane.setBorder(BorderFactory.createTitledBorder(translator
				.getProperty("app.mainWindow.groupBox.member")));

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		nameLabel = new JLabel(translator.getProperty("app.mainWindow.label.name"));
		pane.add(nameLabel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		genderLabel = new JLabel(translator.getProperty("app.mainWindow.label.gender"));
		pane.add(genderLabel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		statusLabel = new JLabel(translator.getProperty("app.mainWindow.label.status"));
		pane.add(statusLabel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		entryNumberLabel = new JLabel(translator.getProperty("app.mainWindow.label.entryNumber"));
		pane.add(entryNumberLabel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		nextFreeLabel = new JLabel(translator.getProperty("app.mainWindow.label.nextFree"));
		pane.add(nextFreeLabel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		nameField = new JLabel("Alex Barféty");
		pane.add(nameField, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		genderField = new JLabel("homme");
		pane.add(genderField, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		statusField = new JLabel("membre d'honneur");
		pane.add(statusField, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		entryNumberField = new JLabel("13");
		pane.add(entryNumberField, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		nextFreeField = new JLabel("5");
		pane.add(nextFreeField, c);

		return pane;
	}

	/**
	 * Builds the panel with the party information.
	 * @return the panel.
	 */
	private JPanel buildPartyPanel () {
		final JPanel pane = new JPanel(new GridBagLayout());
		pane.setBorder(BorderFactory.createTitledBorder(translator.getProperty("app.mainWindow.groupBox.party")));

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = defaultInsets;
		entryPartyTotalNumberLabel = new JLabel(translator.getProperty("app.mainWindow.label.entryPartyTotal"));
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
		c.insets = defaultInsets;
		entryPartyFirstPartNumberLabel = new JLabel(translator.getProperty("app.mainWindow.label.entryPartyFirstPart"));
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
		c.insets = defaultInsets;
		entryPartySecondPartNumberLabel = new JLabel(translator.getProperty("app.mainWindow.label.entryPartySecondPart"));
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
		c.insets = defaultInsets;
		newMemberNumberLabel = new JLabel(translator.getProperty("app.mainWindow.label.newMemberNumber"));
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
		c.insets = defaultInsets;
		freeMemberNumberLabel = new JLabel(translator.getProperty("app.mainWindow.label.freeMemberNumber"));
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
		c.insets = defaultInsets;
		maleNumberLabel = new JLabel(translator.getProperty("app.mainWindow.label.maleNumber"));
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
		c.insets = defaultInsets;
		femaleNumberLabel = new JLabel(translator.getProperty("app.mainWindow.label.femaleNumber"));
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
		c.insets = defaultInsets;
		deltaLabel = new JLabel(DELTA + " :");
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
		c.insets = defaultInsets;
		revenueLabel = new JLabel(translator.getProperty("app.mainWindow.label.revenue"));
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
		c.insets = defaultInsets;
		profitLabel = new JLabel(translator.getProperty("app.mainWindow.label.profit"));
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
		c.insets = defaultInsets;
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
		c.insets = defaultInsets;
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
		c.insets = defaultInsets;
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
		c.insets = defaultInsets;
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
		c.insets = defaultInsets;
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
		c.insets = defaultInsets;
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
		c.insets = defaultInsets;
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
		c.insets = defaultInsets;
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
		c.insets = defaultInsets;
		revenueField = new JLabel("48 €");
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
		c.insets = defaultInsets;
		profitField = new JLabel("8 €");
		pane.add(profitField, c);
		
		return pane;
	}

	
}
