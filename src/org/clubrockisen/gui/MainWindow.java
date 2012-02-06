package org.clubrockisen.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.clubrockisen.dao.AbstractDAOFactory;

/**
 * 
 * @author Alex
 */
public class MainWindow extends JFrame {
	private static Logger		lg					= Logger.getLogger(MainWindow.class.getName());
	/**
	 * Serial version UID
	 */
	private static final long	serialVersionUID	= 8512382872996144843L;

	/** Attributes for the controls **/
	private MainWindow			mainWindow;
	private JMenuBar			menuBar;
	private JMenu				fileMenu, dataBaseMenu, memberMenu, helpMenu;
	private JMenuItem			profitItem, parametersItem, quitItem, seeMemberItem,
			seeAttendeesItem, exportDataItem, newMemberItem, deleteMemberItem, updateMemberItem,
			helpItem, aboutItem;

	/**
	 * Constructor #1.<br />
	 * Unique constructor. Build the GUI of the application.
	 * @param daoFactory
	 *            the factory for the DAO.
	 */
	public MainWindow (final AbstractDAOFactory daoFactory) {
		buildGUI();
		lg.info("Building main window.");
	}

	/**
	 * Create the controls, menu, labels, etc.
	 */
	private void buildGUI () {
		this.setTitle("Club Rock ISEN - Gestionnaire des entées");
		this.setSize(900, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		buildMenus();

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
						+ "par Alex Barféty en 2012. Sous licence BSD.", "À propos",
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
}
