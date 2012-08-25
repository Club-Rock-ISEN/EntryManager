package org.clubrockisen.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Member.MemberColumn;

/**
 * A custom key listener.<br />
 * It searches for a new {@link Member} on each keystroke of the text box and refresh the list which
 * present the results. <br />
 * Also, if the key specified is pressed (down arrow by default), the focus will be passed to the
 * list holding the results.
 * 
 * @author Alex
 */
public class SearchBoxKeyListener implements KeyListener {
	/** Logger */
	private static Logger					lg						= Logger.getLogger(SearchBoxKeyListener.class
			.getName());
	
	/** The text box being listened */
	private final JTextField				textBox;
	/** The list to refresh */
	private final JList<Member>				list;
	/** The model of the list */
	private final DefaultListModel<Member>	listModel;
	/** The DAO for the members */
	private final DAO<Member>				daoMember;
	/** The previous request, to avoid unnecessary query */
	private String							oldRequest				= "";
	/** The key used to change the focus from the search box to the list result */
	private final int						changeFocusKeyTrigger;
	
	/**
	 * Constructor #1.<br />
	 * @param textBox the text box which is being listened to.
	 * @param list the list to pass the focus to, if the down arrow is pressed.
	 * @param listModel the model of the list to update.
	 * @param daoMember the DAO to access the members informations.
	 */
	public SearchBoxKeyListener(final JTextField textBox, final JList<Member> list,
			final DefaultListModel<Member> listModel, final DAO<Member> daoMember) {
		this(textBox, list, listModel, daoMember, KeyEvent.VK_DOWN);
	}
	
	/**
	 * Constructor #1.<br />
	 * @param textBox the text box which is being listened to.
	 * @param list the list to pass the focus to, if the down arrow is pressed.
	 * @param listModel the model of the list to update.
	 * @param daoMember the DAO to access the members informations.
	 * @param changeFocusKeyTrigger the key which should trigger the change of focus, from the text
	 *            box to the result list.
	 */
	public SearchBoxKeyListener(final JTextField textBox, final JList<Member> list,
			final DefaultListModel<Member> listModel, final DAO<Member> daoMember,
			final int changeFocusKeyTrigger) {
		this.textBox = textBox;
		this.list = list;
		this.listModel = listModel;
		this.daoMember = daoMember;
		this.changeFocusKeyTrigger = changeFocusKeyTrigger;
	}
	
	@Override
	public void keyTyped (final KeyEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run () {
				if (e.getSource() instanceof JTextField) {
					updateModel(e);
				}
			}
		});
	}
	
	@Override
	public void keyReleased (final KeyEvent e) {
	}
	
	@Override
	public void keyPressed (final KeyEvent e) {
		if (e.getKeyCode() == changeFocusKeyTrigger) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run () {
					lg.fine("Down arrow pressed, passing focus to list.");
					list.setSelectedIndex(0);
					list.requestFocusInWindow();
				}
			});
		}
	}
	
	/**
	 * Update the list model with the result of the search in the field.
	 * @param event the event to process.
	 */
	private void updateModel (final KeyEvent event) {
		final JTextField source = (JTextField) event.getSource();
		if (source.getText().trim().length() == 0) {
			listModel.clear();
			oldRequest = "";
			return;
		}
		if (!oldRequest.equalsIgnoreCase(textBox.getText().trim())) {
			oldRequest = source.getText().trim();
			final List<Member> searchResult = daoMember.search(
					Member.getColumns().get(MemberColumn.NAME), oldRequest);
			listModel.clear();
			for (final Member currentMember : searchResult) {
				listModel.addElement(currentMember);
			}
		}
	}
}