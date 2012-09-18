package org.clubrockisen.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
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
import javax.swing.WindowConstants;

import org.clubrockisen.common.Constants;
import org.clubrockisen.controller.MemberPanelController;
import org.clubrockisen.controller.ParametersPanelController;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.entities.Member;
import org.clubrockisen.service.Translator;
import org.clubrockisen.service.abstracts.ServiceFactory;
import org.clubrockisen.view.abstracts.AbstractView;

/**
 * The main window of the GUI.
 * @author Alex
 */
public class MainWindow extends JFrame implements AbstractView {
	/** Logger */
	private static Logger				lg					= Logger.getLogger(MainWindow.class.getName());
	/** Serial version UID */
	private static final long			serialVersionUID	= 8512382872996144843L;
	
	/** The services */
	private static final ServiceFactory	SERVICES			= ServiceFactory.getImplementation();
	
	// Attributes for the controls
	/** Reference to the main window (easier access to anonymous classes) */
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
	
	/** Indicates the readiness of the view */
	private boolean						ready = false;
	/** The panel for the member modifications */
	private final MemberPanelController memberUpdatePanel;
	/** The panel for the parameters modifications */
	private final ParametersPanelController	parametersPanel;
	
	/**
	 * Constructor #1.<br />
	 * Unique constructor. Build the GUI of the application.
	 */
	public MainWindow () {
		super();
		memberUpdatePanel = new MemberPanelController();
		parametersPanel = new ParametersPanelController();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run () {
				buildGUI();
				if (lg.isLoggable(Level.INFO)) {
					lg.info("Main window built");
				}
				ready = true;
				synchronized (mainWindow) {
					mainWindow.notifyAll();
				}
			}
		});
	}
	
	/**
	 * Return the readiness of the window.
	 * @return <code>true</code> if the form is ready.
	 */
	public boolean isReady () {
		return ready;
	}
	
	/**
	 * Create the controls, menu, labels, etc.
	 */
	private void buildGUI () {
		this.setTitle(SERVICES.getTranslator().get(Translator.Key.GUI.title()));
		this.setSize(900, 600);
		try {
			this.setIconImage(ImageIO.read(new File("./data/images/icon.png")));
		} catch (final IOException e) {
			lg.warning("Could not load icon for main window.");
		}
		this.setMinimumSize(new Dimension(850, 425));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
		fileMenu = new JMenu(SERVICES.getTranslator().get(Translator.Key.GUI.menu().file().toString()));
		profitItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().file().profit()));
		profitItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		profitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
				//final DAO<Member> daoMember = AbstractDAOFactory.getImplementation().getMemberDAO();
				//lg.info("found: " + daoMember.find(10000));
				//final Member m = daoMember.create(new Member(null, "TESTTT", Gender.FEMALE, 4, 2, 0.0, Status.MEMBER));
				//daoMember.delete(m);
				//final Member tmp = daoMember.search(Member.getColumns().get(MemberColumn.NAME), "Barf�ty").get(0);
				//tmp.setEntries(tmp.getEntries()+1);
				//tmp.setName("SUCCESS");
				//daoMember.update(tmp);
				//final Parameter laf = ParametersManager.getInstance().get(ParametersEnum.LOOK_AND_FEEL);
				//laf.setValue("Nimbus");
				//ParametersManager.getInstance().set(laf);
			}
		});
		parametersItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().file().parameters()));
		parametersItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
		parametersItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				parametersPanel.show();
			}
		});
		quitItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().file().quit()));
		quitItem.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
		quitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				mainWindow.dispose();
			}
		});
		fileMenu.add(profitItem);
		fileMenu.add(parametersItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);
		
		// Database menu creation
		dataBaseMenu = new JMenu(SERVICES.getTranslator().get(Translator.Key.GUI.menu().database().toString()));
		seeMembersItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().database().seeMembers()));
		seeMembersItem.setAccelerator(KeyStroke.getKeyStroke("F12"));
		seeMembersItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		seeAttendeesItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().database().seeAttendees()));
		seeAttendeesItem.setAccelerator(KeyStroke.getKeyStroke("F11"));
		seeAttendeesItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		exportDataItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().database().exportData()));
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
		memberMenu = new JMenu(SERVICES.getTranslator().get(Translator.Key.GUI.menu().member().toString()));
		newMemberItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().member().newMember()));
		newMemberItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newMemberItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		deleteMemberItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().member().deleteMember()));
		deleteMemberItem.setAccelerator(KeyStroke.getKeyStroke("shift DELETE"));
		deleteMemberItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		updateMemberItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().member().updateMember()));
		updateMemberItem.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		updateMemberItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (resultList.getSelectedValue() == null) {
					lg.info("No member selected, cannot show update member window");
					Utils.showMessageDialog(MainWindow.this, Translator.Key.GUI.dialog()
							.notSelectedMember(), JOptionPane.WARNING_MESSAGE);
				} else {
					memberUpdatePanel.showMember(resultList.getSelectedValue());
				}
			}
		});
		memberMenu.add(newMemberItem);
		memberMenu.add(deleteMemberItem);
		memberMenu.add(updateMemberItem);
		
		// Help menu creation
		helpMenu = new JMenu(SERVICES.getTranslator().get(Translator.Key.GUI.menu().help().toString()));
		helpItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().help().help()));
		helpItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		helpItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		aboutItem = new JMenuItem(SERVICES.getTranslator().get(Translator.Key.GUI.menu().help().about()));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F1"));
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (final ActionEvent e) {
				JOptionPane.showMessageDialog(mainWindow,
						SERVICES.getTranslator().get(Translator.Key.GUI.dialog().about().author()) + " Alex Barf�ty.\n"
								+ SERVICES.getTranslator().get(Translator.Key.GUI.dialog().about().license()),
								SERVICES.getTranslator().get(Translator.Key.GUI.dialog().about().title()),
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
		resultListModel = new DefaultListModel<>();
		resultList = new JList<>(resultListModel);
		final JScrollPane scrollPane = new JScrollPane(resultList);
		scrollPane.setBorder(BorderFactory.createTitledBorder(SERVICES.getTranslator()
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
		c.insets = defaultInsets;
		enterButton = new JButton(SERVICES.getTranslator().get("app.mainWindow.button.enter"));
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
		updateButton = new JButton(SERVICES.getTranslator().get("app.mainWindow.button.update"));
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
		deleteButton = new JButton(SERVICES.getTranslator().get("app.mainWindow.button.delete"));
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
		pane.setBorder(BorderFactory.createTitledBorder(SERVICES.getTranslator()
				.get("app.mainWindow.groupBox.member")));
		
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
		nameLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.name"));
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
		genderLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.gender"));
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
		statusLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.status"));
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
		entryNumberLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.entryNumber"));
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
		nextFreeLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.nextFree"));
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
		nameField = new JLabel("Alex Barf�ty");
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
		pane.setBorder(BorderFactory.createTitledBorder(SERVICES.getTranslator().get("app.mainWindow.groupBox.party")));
		
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
		entryPartyTotalNumberLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.entryPartyTotal"));
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
		entryPartyFirstPartNumberLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.entryPartyFirstPart"));
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
		entryPartySecondPartNumberLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.entryPartySecondPart"));
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
		newMemberNumberLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.newMemberNumber"));
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
		freeMemberNumberLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.freeMemberNumber"));
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
		maleNumberLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.maleNumber"));
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
		femaleNumberLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.femaleNumber"));
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
		deltaLabel = new JLabel(Constants.DELTA + " :");
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
		revenueLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.revenue"));
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
		profitLabel = new JLabel(SERVICES.getTranslator().get("app.mainWindow.label.profit"));
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
		c.insets = defaultInsets;
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
		lg.info("Exit program.");
		super.dispose();
		memberUpdatePanel.dispose();
		parametersPanel.dispose();
		try {
			AbstractDAOFactory.getImplementation().close();
		} catch (final IOException ex) {
			lg.warning("Error while closing DAO connection (" + ex.getMessage() + ")");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.view.abstracts.AbstractView#modelPropertyChange(java.beans.PropertyChangeEvent
	 * )
	 */
	@Override
	public void modelPropertyChange (final PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
}
