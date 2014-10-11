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
import org.clubrockisen.common.TranslationKeys;
import org.clubrockisen.controller.MainWindowController;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Party;
import org.clubrockisen.model.SearchModel;
import org.clubrockisen.service.abstracts.Format;
import org.clubrockisen.view.abstracts.EntryManagerFrame;
import org.clubrockisen.view.components.MemberPanel;
import org.clubrockisen.view.components.PartyPanel;

import com.alexrnl.commons.gui.swing.SwingUtils;
import com.alexrnl.commons.translation.AbstractDialog;

/**
 * The main window of the application.<br />
 * @author Alex
 */
public class MainWindow extends EntryManagerFrame {
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
		super(TranslationKeys.GUI.title(), controller);
	}
	
	
	@Override
	protected void preInit (final Object... parameters) {
		controller = (MainWindowController) parameters[0];
	}
	
	
	@Override
	protected void build () {
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
		final JMenu fileMenu = SwingUtils.getMenu(getTranslator(), TranslationKeys.GUI.menu().file().toString());
		final JMenuItem parametersItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().file().parameters(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showParameters();
			}
		});
		final JMenuItem quitItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().file().quit(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				getFrame().dispose();
			}
		});
		fileMenu.add(parametersItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);
		
		// Database menu creation
		final JMenu dataBaseMenu = SwingUtils.getMenu(getTranslator(), TranslationKeys.GUI.menu().database().toString());
		final JMenuItem seeMembersItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().database().seeMembers(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showAllMembers();
			}
		});
		final JMenuItem seeAttendeesItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().database().seeAttendees(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showAttendees();
			}
		});
		final JMenuItem importDataItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().database().importData(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				Format format;
				Path file;
				if ((format = getFileFormat()) != null && (file = getFilePath()) != null) {
					final Integer importedMember = controller.importFile(file, format);
					if (importedMember != null) {
						SwingUtils.showMessageDialog(getFrame(), getTranslator(),
								TranslationKeys.GUI.dialog().fileImportSuccessful(file.toString(), importedMember),
								JOptionPane.INFORMATION_MESSAGE, Constants.LINE_MAX_LENGTH);
					} else {
						SwingUtils.showMessageDialog(getFrame(), getTranslator(),
								TranslationKeys.GUI.dialog().fileImportFailed(file.toString()),
								JOptionPane.ERROR_MESSAGE, Constants.LINE_MAX_LENGTH);
					}
				}
			}
		});
		final JMenuItem exportDataItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().database().exportData(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				Format format;
				Path file;
				if ((format = getFileFormat()) != null && (file = getFilePath()) != null) {
					if (controller.exportFile(file, format)) {
						SwingUtils.showMessageDialog(getFrame(), getTranslator(),
								TranslationKeys.GUI.dialog().fileExportSuccessful(file.toString()),
								JOptionPane.INFORMATION_MESSAGE, Constants.LINE_MAX_LENGTH);
					} else {
						SwingUtils.showMessageDialog(getFrame(), getTranslator(),
								TranslationKeys.GUI.dialog().fileExportFailed(file.toString()),
								JOptionPane.ERROR_MESSAGE, Constants.LINE_MAX_LENGTH);
					}
				}
			}
		});
		dataBaseMenu.add(seeMembersItem);
		dataBaseMenu.add(seeAttendeesItem);
		dataBaseMenu.addSeparator();
		dataBaseMenu.add(importDataItem);
		dataBaseMenu.add(exportDataItem);
		
		// Member menu creation
		final JMenu memberMenu = SwingUtils.getMenu(getTranslator(), TranslationKeys.GUI.menu().member().toString());
		final JMenuItem newMemberItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().member().newMember(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.createMember();
			}
		});
		final JMenuItem deleteMemberItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().member().deleteMember(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (SwingUtils.askConfirmation(getFrame(), getTranslator(),
						TranslationKeys.GUI.dialog().deleteMember(getSelectedMember().getName()), Constants.LINE_MAX_LENGTH)) {
					controller.deleteMember(getSelectedMember());
				}
			}
		});
		final JMenuItem updateMemberItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().member().updateMember(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showMember(getSelectedMember());
			}
		});
		memberMenu.add(newMemberItem);
		memberMenu.add(deleteMemberItem);
		memberMenu.add(updateMemberItem);
		
		// Help menu creation
		final JMenu helpMenu = SwingUtils.getMenu(getTranslator(), TranslationKeys.GUI.menu().help().toString());
		final JMenuItem helpItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().help().help(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				if (!controller.showHelp()) {
					SwingUtils.showMessageDialog(getFrame(), getTranslator(),
							TranslationKeys.GUI.dialog().helpNotDisplayable(),
							JOptionPane.WARNING_MESSAGE, Constants.LINE_MAX_LENGTH);
				}
			}
		});
		final JMenuItem aboutItem = SwingUtils.getMenuItem(getTranslator(), TranslationKeys.GUI.menu().help().about(), new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				SwingUtils.showMessageDialog(getFrame(), getTranslator(), TranslationKeys.GUI.dialog().about(Constants.AUTHOR_NAME),
						JOptionPane.INFORMATION_MESSAGE, Constants.LINE_MAX_LENGTH);
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
		scrollPane.setBorder(BorderFactory.createTitledBorder(getTranslator().get("app.mainWindow.groupBox.searchResult")));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged (final ListSelectionEvent e) {
				controller.initMember(resultList.getSelectedValue() == null ? new Member() : resultList.getSelectedValue());
			}
		});
		resultList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed (final KeyEvent e) {
				switch (e.getKeyCode()) {
					case Constants.ENTER_MEMBER_KEY_TRIGGER:
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run () {
								enterButton.doClick();
							}
						});
						break;
					case Constants.LIST_TO_SEARCH_KEY_TRIGGER:
						if (resultList.getSelectedIndex() == 0) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run () {
									resultList.clearSelection();
									searchBox.requestFocusInWindow();
								}
							});
						}
						break;
					default:
						// Do nothing
						break;
				}
			}
			
		});
		panel.add(scrollPane, c);
		
		yIndex = 0;
		c.gridx = ++xIndex;
		c.gridy = yIndex;
		c.fill = GridBagConstraints.NONE;
		enterButton = new JButton(getTranslator().get(TranslationKeys.GUI.buttons().enter()));
		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				final Member member = getSelectedMember();
				if (member == null) {
					if (lg.isLoggable(Level.INFO)) {
						lg.info("No member selected.");
					}
					return;
				}
				
				if (!controller.canEnter(member)) {
					if (lg.isLoggable(Level.INFO)) {
						lg.info("Member is already in the party, canceling current entry.");
					}
					// TODO add dialog
					return;
				}
				
				final double price = controller.getPrice(member);
				AbstractDialog dialog;
				if (price == 0.0) {
					dialog = TranslationKeys.GUI.dialog().freeEntry(member.getName());
				} else {
					dialog = TranslationKeys.GUI.dialog().entryPrice(member.getName(), price);
				}
				
				// Entry canceled
				if (!SwingUtils.askConfirmation(getFrame(), getTranslator(), dialog, Constants.LINE_MAX_LENGTH)) {
					if (lg.isLoggable(Level.INFO)) {
						lg.info("User canceled member entry.");
					}
					return;
				}
				
				if (controller.enter(member)) {
					SwingUtils.showMessageDialog(getFrame(), getTranslator(), TranslationKeys.GUI.dialog().memberEntry(member.getName()),
							JOptionPane.INFORMATION_MESSAGE, Constants.LINE_MAX_LENGTH);
				} else {
					SwingUtils.showMessageDialog(getFrame(), getTranslator(), TranslationKeys.GUI.dialog().memberEntryFailed(member.getName()),
							JOptionPane.ERROR_MESSAGE, Constants.LINE_MAX_LENGTH);
				}
				
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run () {
						searchBox.requestFocusInWindow();
					}
				});
			}
		});
		panel.add(enterButton, c);
		
		c.gridx = ++xIndex;
		updateButton = new JButton(getTranslator().get(TranslationKeys.GUI.buttons().update()));
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (final ActionEvent e) {
				controller.showMember(getSelectedMember());
			}
		});
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
			SwingUtils.showMessageDialog(getFrame(), getTranslator(),
					TranslationKeys.GUI.dialog().notSelectedMember(),
					JOptionPane.WARNING_MESSAGE, Constants.LINE_MAX_LENGTH);
		}
		return member;
	}
	
	/**
	 * Ask the user for a file format and returns it.<br />
	 * <code>null</code> if the user canceled the action.
	 * @return the {@link Format} selected.
	 */
	private Format getFileFormat () {
		return SwingUtils.askChoice(getFrame(), getTranslator(), TranslationKeys.GUI.dialog().chooseFormat(), controller.getAvailableFormat(), Constants.LINE_MAX_LENGTH);
	}
	
	/**
	 * Ask the user for a file and return its {@link Path}.<br />
	 * <code>null</code> if the user canceled the action.
	 * @return the path of the file selected.
	 */
	private Path getFilePath () {
		final JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(getFrame()) != JFileChooser.APPROVE_OPTION) {
			// Cancel action
			return null;
		}
		return chooser.getSelectedFile().toPath();
	}
	
	
	@Override
	public void dispose () {
		super.dispose();
		controller.dispose();
	}
	
	
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
		@Override
		public void keyTyped (final KeyEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run () {
					controller.changeSearch(searchBox.getText());
				}
			});
		}
		
		@Override
		public void keyPressed (final KeyEvent e) {
			switch (e.getKeyCode()) {
				case Constants.SEARCH_TO_LIST_KEY_TRIGGER:
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run () {
							if (lg.isLoggable(Level.FINE)) {
								lg.fine("Passing focus to list.");
							}
							resultList.setSelectedIndex(0);
							resultList.requestFocusInWindow();
						}
					});
					break;
				case Constants.ENTER_MEMBER_KEY_TRIGGER:
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run () {
							if (resultList.getModel().getSize() > 0) {
								resultList.setSelectedIndex(0);
								enterButton.doClick();
							} else {
								controller.createMember(searchBox.getText());
							}
						}
					});
					break;
				default:
					// Do nothing
					break;
			}
			
		}
	}
}
