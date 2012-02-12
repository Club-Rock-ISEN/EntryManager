package org.clubrockisen.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.clubrockisen.dao.AbstractDAOFactory;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.enums.Gender;
import org.clubrockisen.entities.enums.Status;

/**
 * 
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
	private JMenuItem					profitItem, parametersItem, quitItem, seeMemberItem,
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
			maleNumberLabel, femaleNumberLabel, deltaLabel, entryPartyTotalNumberField,
			entryPartyFirstPartNumberField, entryPartySecondPartNumberField, newMemberNumberField,
			freeMemberNumberField, maleNumberField, femaleNumberField, deltaField;

	private DefaultListModel<Member>	resultListModel;

	/**
	 * Constructor #1.<br />
	 * Unique constructor. Build the GUI of the application.
	 * @param daoFactory
	 *            the factory for the DAO.
	 */
	public MainWindow (final AbstractDAOFactory daoFactory) {
		super();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run () {
				buildGUI();
				lg.info("Building main window.");
			}
		});
	}

	/**
	 * Create the controls, menu, labels, etc.
	 */
	private void buildGUI () {
		this.setTitle("Club Rock ISEN - Gestionnaire des entées");
		this.setSize(900, 600);
		this.setMinimumSize(new Dimension(300, 200));
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
		fileMenu = new JMenu("Fichier");
		profitItem = new JMenuItem("Bénéfices de la soirée");
		profitItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		profitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		parametersItem = new JMenuItem("Réglage des paramètres");
		parametersItem.setAccelerator(KeyStroke.getKeyStroke("F6"));
		parametersItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
				resultListModel.add(0, new Member(0, "Alex Barféty", Gender.MALE, 0, 0.0,
						Status.VETERAN));
			}
		});
		quitItem = new JMenuItem("Quitter");
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
		dataBaseMenu = new JMenu("Base de données");
		seeMemberItem = new JMenuItem("Voir tout les membres");
		seeMemberItem.setAccelerator(KeyStroke.getKeyStroke("F12"));
		seeMemberItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		seeAttendeesItem = new JMenuItem("Voir les personnes présentes");
		seeAttendeesItem.setAccelerator(KeyStroke.getKeyStroke("F11"));
		seeAttendeesItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		exportDataItem = new JMenuItem("Exporter des données");
		exportDataItem.setAccelerator(KeyStroke.getKeyStroke("F10"));
		exportDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		dataBaseMenu.add(seeMemberItem);
		dataBaseMenu.add(seeAttendeesItem);
		dataBaseMenu.addSeparator();
		dataBaseMenu.add(exportDataItem);

		// Member menu creation
		memberMenu = new JMenu("Membre");
		newMemberItem = new JMenuItem("Nouveau membre");
		newMemberItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newMemberItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		deleteMemberItem = new JMenuItem("Supprimer membre");
		deleteMemberItem.setAccelerator(KeyStroke.getKeyStroke("shift DELETE"));
		deleteMemberItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		updateMemberItem = new JMenuItem("Modifier membre");
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
		helpMenu = new JMenu("Aide");
		helpItem = new JMenuItem("Aide");
		helpItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		helpItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		aboutItem = new JMenuItem("À propos");
		aboutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F1"));
		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (final ActionEvent e) {
				JOptionPane.showMessageDialog(mainWindow, "Programme créé pour le Club Rock ISEN"
						+ " par Alex Barféty en 2012.\nSous licence BSD.", "À propos",
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
		c.insets = new Insets(5, 5, 5, 5);
		searchBox = new JTextField("Recherche...");
		searchBox.addKeyListener(new KeyListener() {
			//TODO move key listener (should trigger search event as well)
			@Override
			public void keyTyped (final KeyEvent e) {}
			@Override
			public void keyReleased (final KeyEvent e) {}
			@Override
			public void keyPressed (final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run () {
							lg.fine("Down arrow pressed, passing focus to result list.");
							resultList.setSelectedIndex(0);
							resultList.requestFocusInWindow();
						}
					});
				}
			}
		});
		panel.add(searchBox, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 0.33;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		resultListModel = new DefaultListModel<Member>();
		resultList = new JList<Member>(resultListModel);
		resultList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(resultList, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		enterButton = new JButton("Entrée");
		panel.add(enterButton, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		updateButton = new JButton("Modifier");
		panel.add(updateButton, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.33;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		deleteButton = new JButton("Supprimer");
		panel.add(deleteButton, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 0.33;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = new Insets(5, 5, 5, 5);
		memberPanel = buildMemberPanel();
		panel.add(memberPanel, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 0.33;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = new Insets(5, 5, 5, 5);
		partyPanel = buildPartyPanel();
		panel.add(partyPanel, c);
	}

	/**
	 * Build the panel with the member information.
	 * @return the panel.
	 */
	private JPanel buildMemberPanel () {
		final JPanel pane = new JPanel(new GridBagLayout());
		pane.setBorder(BorderFactory.createTitledBorder("Membre"));

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		nameLabel = new JLabel("Nom :");
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
		c.insets = new Insets(5, 5, 5, 5);
		genderLabel = new JLabel("Sexe :");
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
		c.insets = new Insets(5, 5, 5, 5);
		statusLabel = new JLabel("Statut :");
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
		c.insets = new Insets(5, 5, 5, 5);
		entryNumberLabel = new JLabel("Nombre de visites :");
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
		c.insets = new Insets(5, 5, 5, 5);
		nextFreeLabel = new JLabel("Prochaine entrée gratuite dans :");
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
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
		statusField = new JLabel("membre");
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
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
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
		pane.setBorder(BorderFactory.createTitledBorder("Soirée"));

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		entryPartyTotalNumberLabel = new JLabel("Nombre total d'entrées :");
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
		c.insets = new Insets(5, 5, 5, 5);
		entryPartyFirstPartNumberLabel = new JLabel("Avant 22h :");
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
		c.insets = new Insets(5, 5, 5, 5);
		entryPartySecondPartNumberLabel = new JLabel("Après 22h :");
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
		c.insets = new Insets(5, 5, 5, 5);
		newMemberNumberLabel = new JLabel("Nombre de nouveaux :");
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
		c.insets = new Insets(5, 5, 5, 5);
		freeMemberNumberLabel = new JLabel("Nombre de gratuits :");
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
		c.insets = new Insets(5, 5, 5, 5);
		maleNumberLabel = new JLabel("Nombre d'hommes :");
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
		c.insets = new Insets(5, 5, 5, 5);
		femaleNumberLabel = new JLabel("Nombre de femmes :");
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
		c.insets = new Insets(5, 5, 5, 5);
		deltaLabel = new JLabel(DELTA + " :");
		pane.add(deltaLabel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
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
		c.insets = new Insets(5, 5, 5, 5);
		deltaField = new JLabel("4");
		pane.add(deltaField, c);

		return pane;
	}
}
