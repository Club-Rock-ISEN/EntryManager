package org.clubrockisen.common;

import static com.alexrnl.commons.translation.Translator.HIERARCHY_SEPARATOR;

import com.alexrnl.commons.translation.GUIElement;
import com.alexrnl.commons.translation.StandardDialog;

/**
 * The Translation file key structure.
 * @author Alex
 */
public final class TranslationKeys {
	/**
	 * Constructor #1.<br />
	 * Build the key structure.
	 */
	private TranslationKeys () {
		super();
	}
	
	/**
	 * The translations for the entities.
	 * @author Alex
	 */
	public static final class Entity {
		/** The key to the entity structure */
		private final String	entityKey	= "entity";
		
		/**
		 * Constructor #1.<br />
		 * Build the entity structure.
		 */
		private Entity () {
			super();
		}
		
		@Override
		public String toString () {
			return entityKey;
		}
		
		/**
		 * The translations for the member entity.
		 * @author Alex
		 */
		public static final class Member {
			/** The key to the member structure */
			private final String	memberKey;
			
			/**
			 * Constructor #1.<br />
			 * Build the member structure.
			 * @param parentKey
			 *        the key from the parent category.
			 */
			private Member (final String parentKey) {
				super();
				this.memberKey = parentKey + HIERARCHY_SEPARATOR + "member";
			}
			
			@Override
			public String toString () {
				return memberKey;
			}
			
			/**
			 * The member name.
			 * @return The translation for the name field.
			 */
			public String name () {
				return memberKey + HIERARCHY_SEPARATOR + "name";
			}
			
			/**
			 * The member gender.
			 * @return The translation for the gender field.
			 */
			public String gender () {
				return memberKey + HIERARCHY_SEPARATOR + "gender";
			}
			
			/**
			 * The member entry number.
			 * @return The translation for the entry field.
			 */
			public String entries () {
				return memberKey + HIERARCHY_SEPARATOR + "entries";
			}
			
			/**
			 * The member credit.
			 * @return The translation for the credit field.
			 */
			public String credit () {
				return memberKey + HIERARCHY_SEPARATOR + "credit";
			}
			
			/**
			 * The member status.
			 * @return The translation for the status field.
			 */
			public String status () {
				return memberKey + HIERARCHY_SEPARATOR + "status";
			}
		}
		
		/**
		 * Access to the member translations.
		 * @return the member translations.
		 */
		public Member member () {
			return new Member(entityKey);
		}
		
		/**
		 * The translations for the party entity.
		 * @author Alex
		 */
		public static final class Party {
			/** The key to the party structure */
			private final String	partyKey;
			
			/**
			 * Constructor #1.<br />
			 * Build the party structure.
			 * @param parentKey
			 *        the key from the parent category.
			 */
			private Party (final String parentKey) {
				super();
				this.partyKey = parentKey + HIERARCHY_SEPARATOR + "party";
			}
			
			@Override
			public String toString () {
				return partyKey;
			}
			
			/**
			 * The party date.
			 * @return the translation for the date field.
			 */
			public String date () {
				return partyKey + HIERARCHY_SEPARATOR + "date";
			}
			
			/**
			 * The total number of entries.
			 * @return the translation for the total entries.
			 */
			public String entriesTotal () {
				return partyKey + HIERARCHY_SEPARATOR + "entriestotal";
			}
			
			/**
			 * The number of entries for the first part.
			 * @return the translation for the first part entries.
			 */
			public String entriesFirstPart () {
				return partyKey + HIERARCHY_SEPARATOR + "entriesfirstpart";
			}
			
			/**
			 * The number of entries for the second part.
			 * @return the translation for the second part entries.
			 */
			public String entriesSecondPart () {
				return partyKey + HIERARCHY_SEPARATOR + "entriessecondpart";
			}
			
			/**
			 * The number of entries for the new member.
			 * @return the translation for the entries of new members.
			 */
			public String entriesNewMember () {
				return partyKey + HIERARCHY_SEPARATOR + "entriesnewmember";
			}
			
			/**
			 * The number of free entries.
			 * @return the translation for the free entries.
			 */
			public String entriesFree () {
				return partyKey + HIERARCHY_SEPARATOR + "entriesfree";
			}
			
			/**
			 * The number of entries from males.
			 * @return the translation for the male entries.
			 */
			public String entriesMale () {
				return partyKey + HIERARCHY_SEPARATOR + "entriesmale";
			}
			
			/**
			 * The number of entries from female.
			 * @return the translation for the female entries.
			 */
			public String entriesFemale () {
				return partyKey + HIERARCHY_SEPARATOR + "entriesfemale";
			}
			
			/**
			 * The revenue of the party.
			 * @return the translation for the revenue of the party.
			 */
			public String revenue () {
				return partyKey + HIERARCHY_SEPARATOR + "revenue";
			}
			
			/**
			 * The profit of the party.
			 * @return the translation for the profit of the party.
			 */
			public String profit () {
				return partyKey + HIERARCHY_SEPARATOR + "profit";
			}
		}
		
		/**
		 * Access to the party translations.
		 * @return the party translations.
		 */
		public Party party () {
			return new Party(entityKey);
		}
		
	}
	
	/** Access to the entity translations */
	public static final TranslationKeys.Entity	ENTITY	= new Entity();
	
	/**
	 * The translations for the enumerations.
	 * @author Alex
	 */
	public static final class Enum {
		/** The key to the enum structure */
		private final String	enumKey	= "enum";
		
		/**
		 * Constructor #1.<br />
		 * Build the enum structure.
		 */
		private Enum () {
			super();
		}
		
		@Override
		public String toString () {
			return enumKey;
		}
		
		/**
		 * The translations for the genders.
		 * @author Alex
		 */
		public static final class Gender {
			/** The key to the gender structure */
			private final String	genderKey;
			
			/**
			 * Constructor #1.<br />
			 * @param parentKey
			 *        the key from the parent category.
			 */
			private Gender (final String parentKey) {
				super();
				this.genderKey = parentKey + HIERARCHY_SEPARATOR + "gender";
			}
			
			@Override
			public String toString () {
				return genderKey;
			}
			
			/**
			 * The male gender.
			 * @return the translation for the male gender.
			 */
			public String male () {
				return genderKey + HIERARCHY_SEPARATOR + "male";
			}
			
			/**
			 * The female gender.
			 * @return the translation for the female gender.
			 */
			public String female () {
				return genderKey + HIERARCHY_SEPARATOR + "female";
			}
			
		}
		
		/**
		 * Access to the gender translations.
		 * @return the gender translations.
		 */
		public Enum.Gender gender () {
			return new Gender(enumKey);
		}
		
		/**
		 * The translations for the status.
		 * @author Alex
		 */
		public static final class Status {
			/** The key to the gender structure */
			private final String	statusKey;
			
			/**
			 * Constructor #1.<br />
			 * @param parentKey
			 *        the key from the parent category.
			 */
			private Status (final String parentKey) {
				super();
				this.statusKey = parentKey + HIERARCHY_SEPARATOR + "status";
			}
			
			@Override
			public String toString () {
				return statusKey;
			}
			
			/**
			 * The member status.
			 * @return the translation for the member status.
			 */
			public String member () {
				return statusKey + HIERARCHY_SEPARATOR + "member";
			}
			
			/**
			 * The helper member status.
			 * @return the translation for the helper member status.
			 */
			public String helperMember () {
				return statusKey + HIERARCHY_SEPARATOR + "helperMember";
			}
			
			/**
			 * The office member status.
			 * @return the translation for the office member status.
			 */
			public String officeMember () {
				return statusKey + HIERARCHY_SEPARATOR + "officeMember";
			}
			
			/**
			 * The veteran status.
			 * @return the translation for the veteran status.
			 */
			public String veteran () {
				return statusKey + HIERARCHY_SEPARATOR + "veteran";
			}
			
		}
		
		/**
		 * Access to the gender translations.
		 * @return the gender translations.
		 */
		public Enum.Status status () {
			return new Status(enumKey);
		}
		
		/**
		 * The translations for the parameters.
		 * @author Alex
		 */
		public static final class Parameter {
			/** The key to the gender structure */
			private final String	parameterKey;
			
			/**
			 * Constructor #1.<br />
			 * @param parentKey
			 *        the key from the parent category.
			 */
			private Parameter (final String parentKey) {
				super();
				this.parameterKey = parentKey + HIERARCHY_SEPARATOR + "parameter";
			}
			
			@Override
			public String toString () {
				return parameterKey;
			}
			
			/**
			 * The look and feel parameter.
			 * @return the translation for the look and feel parameter.
			 */
			public String lookAndFeel () {
				return parameterKey + HIERARCHY_SEPARATOR + "look_and_feel";
			}
			
			/**
			 * The time limit parameter.
			 * @return the translation for the time limit parameter.
			 */
			public String timeLimit () {
				return parameterKey + HIERARCHY_SEPARATOR + "time_limit";
			}
			
			/**
			 * The entry price total parameter.
			 * @return the translation for the entry price total parameter.
			 */
			public String entryPriceTotal () {
				return parameterKey + HIERARCHY_SEPARATOR + "entry_price_total";
			}
			
			/**
			 * The entry price first price parameter.
			 * @return the translation for the entry price first price parameter.
			 */
			public String entryPriceFirstPart () {
				return parameterKey + HIERARCHY_SEPARATOR + "entry_price_first_part";
			}
			
			/**
			 * The entry price second price parameter.
			 * @return the translation for the entry price second price parameter.
			 */
			public String entryPriceSecondPart () {
				return parameterKey + HIERARCHY_SEPARATOR + "entry_price_second_part";
			}
			
			/**
			 * The free entry frequency parameter.
			 * @return the translation for the free entry frequency parameter.
			 */
			public String freeEntryFrequency () {
				return parameterKey + HIERARCHY_SEPARATOR + "free_entry_frequency";
			}
			
			/**
			 * The minimum credit parameter.
			 * @return the translation for the minimum credit parameter.
			 */
			public String minCredit () {
				return parameterKey + HIERARCHY_SEPARATOR + "min_credit";
			}
			
			/**
			 * The maximum credit parameter.
			 * @return the translation for the maximum credit parameter.
			 */
			public String maxCredit () {
				return parameterKey + HIERARCHY_SEPARATOR + "max_credit";
			}
			
		}
		
		/**
		 * Access to the parameters translations.
		 * @return the parameters translations.
		 */
		public Enum.Parameter parameter () {
			return new Parameter(enumKey);
		}
		
	}
	
	/** Access to the enumeration translations */
	public static final TranslationKeys.Enum	ENUM	= new Enum();
	
	/**
	 * The formats translation
	 * @author Alex
	 */
	public static final class Formats {
		/** The key to the format structure */
		private final String	formatsKey	= "formats";
		
		/**
		 * Constructor #1.<br />
		 * Build the formats structure.
		 */
		private Formats () {
			super();
		}
		
		/**
		 * The old file format.
		 * @return the translation for the old file format.
		 */
		public String oldFileFormat () {
			return formatsKey + HIERARCHY_SEPARATOR + "oldFileFormat";
		}
	}
	
	/** Access to the formats translations */
	public static final TranslationKeys.Formats	FORMATS	= new Formats();
	
	/**
	 * The GUI related translations
	 * @author Alex
	 */
	public static final class Gui {
		/** The key to the GUI structure */
		private final String	guiKey	= "gui";
		
		/**
		 * Constructor #1.<br />
		 * Build the GUI structure.
		 */
		private Gui () {
			super();
		}
		
		@Override
		public String toString () {
			return guiKey;
		}
		
		/**
		 * The title of the application.
		 * @return the translation for the application's title.
		 */
		public String title () {
			return guiKey + HIERARCHY_SEPARATOR + "title";
		}
		
		/**
		 * The menu translation.
		 * @author Alex
		 */
		public static final class Menu {
			/** The key to the menu structure */
			private final String	menuKey;
			
			/**
			 * Constructor #1.<br />
			 * Build the menu structure.
			 * @param parentKey
			 *        the key from the parent category.
			 */
			private Menu (final String parentKey) {
				super();
				menuKey = parentKey + HIERARCHY_SEPARATOR + "menu";
			}
			
			@Override
			public String toString () {
				return menuKey;
			}
			
			/**
			 * The file menu translation.
			 * @author Alex
			 */
			public static final class File {
				/** The key to the file menu */
				private final String	fileKey;
				
				/**
				 * Constructor #1.<br />
				 * Build the file menu structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				private File (final String parentKey) {
					super();
					fileKey = parentKey + HIERARCHY_SEPARATOR + "file";
				}
				
				@Override
				public String toString () {
					return fileKey;
				}
				
				/**
				 * The parameters item.
				 * @return the translation for the parameters item.
				 */
				public GUIElement parameters () {
					return new GUIElement(fileKey + HIERARCHY_SEPARATOR + "parameters");
				}
				
				/**
				 * The quit item.
				 * @return the translation for the quit item.
				 */
				public GUIElement quit () {
					return new GUIElement(fileKey + HIERARCHY_SEPARATOR + "quit");
				}
				
			}
			
			/**
			 * Access to the file menu translations.
			 * @return the file menu translations.
			 */
			public Menu.File file () {
				return new File(menuKey);
			}
			
			/**
			 * The database menu translation.
			 * @author Alex
			 */
			public static final class Database {
				/** The key to the file menu */
				private final String	databaseKey;
				
				/**
				 * Constructor #1.<br />
				 * Build the database menu structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				private Database (final String parentKey) {
					super();
					databaseKey = parentKey + HIERARCHY_SEPARATOR + "database";
				}
				
				@Override
				public String toString () {
					return databaseKey;
				}
				
				/**
				 * The see members item.
				 * @return the translation for the see members item.
				 */
				public GUIElement seeMembers () {
					return new GUIElement(databaseKey + HIERARCHY_SEPARATOR + "seeMembers");
				}
				
				/**
				 * The see attendees item.
				 * @return the translation for the see attendees item.
				 */
				public GUIElement seeAttendees () {
					return new GUIElement(databaseKey + HIERARCHY_SEPARATOR + "seeAttendees");
				}
				
				/**
				 * The import data item.
				 * @return the translation for the import data item.
				 */
				public GUIElement importData () {
					return new GUIElement(databaseKey + HIERARCHY_SEPARATOR + "importData");
				}
				
				/**
				 * The export data item.
				 * @return the translation for the export data item.
				 */
				public GUIElement exportData () {
					return new GUIElement(databaseKey + HIERARCHY_SEPARATOR + "exportData");
				}
				
			}
			
			/**
			 * Access to the database menu translations.
			 * @return the database menu translations.
			 */
			public Menu.Database database () {
				return new Database(menuKey);
			}
			
			/**
			 * The member menu translation.
			 * @author Alex
			 */
			public static final class Member {
				/** The key to the member menu */
				private final String	memberKey;
				
				/**
				 * Constructor #1.<br />
				 * Build the member menu structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				private Member (final String parentKey) {
					super();
					memberKey = parentKey + HIERARCHY_SEPARATOR + "member";
				}
				
				@Override
				public String toString () {
					return memberKey;
				}
				
				/**
				 * The new member item.
				 * @return the translation for the new member item.
				 */
				public GUIElement newMember () {
					return new GUIElement(memberKey + HIERARCHY_SEPARATOR + "newMember");
				}
				
				/**
				 * The delete member item.
				 * @return the translation for the delete member item.
				 */
				public GUIElement deleteMember () {
					return new GUIElement(memberKey + HIERARCHY_SEPARATOR + "deleteMember");
				}
				
				/**
				 * The update member item.
				 * @return the translation for the update member item.
				 */
				public GUIElement updateMember () {
					return new GUIElement(memberKey + HIERARCHY_SEPARATOR + "updateMember");
				}
				
			}
			
			/**
			 * Access to the member menu translations.
			 * @return the member menu translations.
			 */
			public Menu.Member member () {
				return new Member(menuKey);
			}
			
			/**
			 * The help menu translation.
			 * @author Alex
			 */
			public static final class Help {
				/** The key to the help menu */
				private final String	helpKey;
				
				/**
				 * Constructor #1.<br />
				 * Build the help menu structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				public Help (final String parentKey) {
					super();
					helpKey = parentKey + HIERARCHY_SEPARATOR + "help";
				}
				
				@Override
				public String toString () {
					return helpKey;
				}
				
				/**
				 * The help item.
				 * @return the translation for the help item.
				 */
				public GUIElement help () {
					return new GUIElement(helpKey + HIERARCHY_SEPARATOR + "help");
				}
				
				/**
				 * The about item.
				 * @return the translation for the about item.
				 */
				public GUIElement about () {
					return new GUIElement(helpKey + HIERARCHY_SEPARATOR + "about");
				}
				
			}
			
			/**
			 * Access to the help menu translations.
			 * @return the help menu translations.
			 */
			public Menu.Help help () {
				return new Help(menuKey);
			}
			
		}
		
		/**
		 * Access to the menu translations.
		 * @return the menu translations.
		 */
		public Menu menu () {
			return new Menu(guiKey);
		}
		
		/**
		 * The parameters manager panel.
		 * @author Alex
		 */
		public static final class Parameters {
			/** The key to parameters panel */
			private final String	parametersKey;
			
			/**
			 * Constructor #1.<br />
			 * Build the structure for the parameter's panel translations.
			 * @param parentKey
			 *        the key from the parent category.
			 */
			private Parameters (final String parentKey) {
				super();
				parametersKey = parentKey + HIERARCHY_SEPARATOR + "parameters";
			}
			
			@Override
			public String toString () {
				return parametersKey;
			}
			
			/**
			 * The title of the panel.
			 * @return the translation for the panel.
			 */
			public String title () {
				return parametersKey + HIERARCHY_SEPARATOR + "title";
			}
			
		}
		
		/**
		 * Access to the parameters manager translations.
		 * @return the parameters manager translations.
		 */
		public Parameters parameters () {
			return new Parameters(guiKey);
		}
		
		/**
		 * The buttons translations.
		 * @author Alex
		 */
		public static final class Buttons {
			/** The key to the buttons translations */
			private final String	buttonsKey;
			
			/**
			 * Constructor #1.<br />
			 * Build the buttons structure.
			 * @param parentKey
			 *        the key from the parent category.
			 */
			public Buttons (final String parentKey) {
				super();
				buttonsKey = parentKey + HIERARCHY_SEPARATOR + "buttons";
			}
			
			/**
			 * The enter button.
			 * @return the text of the enter button.
			 */
			public String enter () {
				return buttonsKey + HIERARCHY_SEPARATOR + "enter";
			}
			
			/**
			 * The update button.
			 * @return the text of the update button.
			 */
			public String update () {
				return buttonsKey + HIERARCHY_SEPARATOR + "update";
			}
			
			@Override
			public String toString () {
				return buttonsKey;
			}
			
		}
		
		/**
		 * Access to the translations for the buttons.
		 * @return the buttons translation.
		 */
		public Buttons buttons () {
			return new Buttons(guiKey);
		}
		
		/**
		 * The dialog translations.
		 * @author Alex
		 */
		public static final class Dialog {
			/** The key to dialogs */
			private final String	dialogKey;
			
			/**
			 * Constructor #1.<br />
			 * Build the dialog structure.
			 * @param parentKey
			 *        the key from the parent category.
			 */
			private Dialog (final String parentKey) {
				super();
				dialogKey = parentKey + HIERARCHY_SEPARATOR + "dialog";
			}
			
			@Override
			public String toString () {
				return dialogKey;
			}
			
			/**
			 * Access to the translations for the about dialog.
			 * @param author
			 *        the author of the application.
			 * @return the about dialog translations.
			 */
			public StandardDialog about (final String author) {
				return new StandardDialog(dialogKey, author);
			}
			
			/**
			 * Access to the translations for the not selected member dialog.
			 * @return the not selected member dialog translations.
			 */
			public StandardDialog notSelectedMember () {
				return new StandardDialog(dialogKey);
			}
			
			/**
			 * Access to the translations for the not persisted member.
			 * @return the not selected member dialog translations.
			 */
			public StandardDialog notPersistedMember () {
				return new StandardDialog(dialogKey);
			}
			
			/**
			 * Access to the translations for the not persisted parameter.
			 * @return the not persisted parameter dialog translations.
			 */
			public StandardDialog notPersistedParameter () {
				return new StandardDialog(dialogKey);
			}
			
			/**
			 * Access to the translations for the unparsable date dialog.
			 * @param date
			 *        the date which could not be parsed.
			 * @return the unparsable date dialog translations.
			 */
			public StandardDialog unparsableDate (final String date) {
				return new StandardDialog(dialogKey, date);
			}
			
			/**
			 * Access to the translations for the help not displayable dialog.
			 * @return the help not displayable dialog translations.
			 */
			public StandardDialog helpNotDisplayable () {
				return new StandardDialog(dialogKey);
			}
			
			/**
			 * Access to the translations for the confirm dialog on member deletion.
			 * @param memberName
			 *        the name of the member to delete.
			 * @return the delete member dialog translations.
			 */
			public StandardDialog deleteMember (final String memberName) {
				return new StandardDialog(dialogKey, memberName);
			}
			
			/**
			 * Access to the file import successful dialog.
			 * @param fileName
			 *        the name of the file imported.
			 * @param memberImported
			 *        the number of member successfully imported.
			 * @return the file import successful dialog.
			 */
			public StandardDialog fileImportSuccessful (final String fileName, final int memberImported) {
				return new StandardDialog(dialogKey, fileName, memberImported);
			}
			
			/**
			 * Access to the file import failed dialog.
			 * @param fileName
			 *        the name of the file imported.
			 * @return the file import failed dialog.
			 */
			public StandardDialog fileImportFailed (final String fileName) {
				return new StandardDialog(dialogKey, fileName);
			}
			
			/**
			 * Access to the choose format dialog.
			 * @return the choose format dialog.
			 */
			public StandardDialog chooseFormat () {
				return new StandardDialog(dialogKey);
			}
			
			/**
			 * Access to the file export successful dialog.
			 * @param fileName
			 *        the name of the file imported.
			 * @return the file export successful dialog.
			 */
			public StandardDialog fileExportSuccessful (final String fileName) {
				return new StandardDialog(dialogKey, fileName);
			}
			
			/**
			 * Access to the file export failed dialog.
			 * @param fileName
			 *        the name of the file exported.
			 * @return the file export failed dialog.
			 */
			public StandardDialog fileExportFailed (final String fileName) {
				return new StandardDialog(dialogKey, fileName);
			}
			
			/**
			 * Access to the free entry dialog.
			 * @param memberName
			 *        the name of the member whose entry is free.
			 * @return the free entry dialog.
			 */
			public StandardDialog freeEntry (final String memberName) {
				return new StandardDialog (dialogKey, memberName);
			}
			
			/**
			 * Access to the entry price dialog.
			 * @param memberName
			 *        the name of the member whose entry is free.
			 * @param price
			 *        the price the member has to pay.
			 * @return the entry price dialog.
			 */
			public StandardDialog entryPrice (final String memberName, final double price) {
				return new StandardDialog (dialogKey, memberName, price);
			}
			
			/**
			 * Access to the member entry dialog.
			 * @param memberName
			 *        the name of the member whose entry is free.
			 * @return the member entry dialog.
			 */
			public StandardDialog memberEntry (final String memberName) {
				return new StandardDialog (dialogKey, memberName);
			}
			
			/**
			 * Access to the member entry failed dialog.
			 * @param memberName
			 *        the name of the member whose entry is free.
			 * @return the member entry failed dialog.
			 */
			public StandardDialog memberEntryFailed (final String memberName) {
				return new StandardDialog (dialogKey, memberName);
			}
		}
		
		/**
		 * Access to the translations for the dialog.
		 * @return the dialog translations.
		 */
		public Gui.Dialog dialog () {
			return new Dialog(guiKey);
		}
		
	}
	
	/** Access to the GUI related translations. */
	public static final TranslationKeys.Gui	GUI	= new Gui();
	
	/**
	 * The miscellaneous translations.
	 * @author Alex
	 */
	public static final class Misc {
		/** The key to the miscellaneous */
		private final String	miscKey	= "misc";
		
		/**
		 * Constructor #1.<br />
		 * Build the miscellaneous structure.
		 */
		private Misc () {
			super();
		}
		
		@Override
		public String toString () {
			return miscKey;
		}
		
		/**
		 * The field / value separator.
		 * @return the translation for the field / value separator.
		 */
		public String fieldValueSeparator () {
			return miscKey + HIERARCHY_SEPARATOR + "fieldValueSeparator";
		}
		
		/**
		 * The currency symbol to use.
		 * @return the translation for the currency symbol.
		 */
		public String currencySymbol () {
			return miscKey + HIERARCHY_SEPARATOR + "currencySymbol";
		}
		
		/**
		 * The plural letter of the language.
		 * @return the plural letter of the language.
		 */
		public String pluralLetter () {
			return miscKey + HIERARCHY_SEPARATOR + "pluralLetter";
		}
		
		/**
		 * The 'OK' text to use.
		 * @return the translation for the OK.
		 */
		public String ok () {
			return miscKey + HIERARCHY_SEPARATOR + "ok";
		}
		
		/**
		 * The 'cancel' text to use.
		 * @return the translation for the cancel.
		 */
		public String cancel () {
			return miscKey + HIERARCHY_SEPARATOR + "cancel";
		}
		
		/**
		 * The 'apply' text to use.
		 * @return the translation for the apply.
		 */
		public String apply () {
			return miscKey + HIERARCHY_SEPARATOR + "apply";
		}
	}
	
	/** Access to the miscellaneous translations. */
	public static final TranslationKeys.Misc	MISC	= new Misc();
}