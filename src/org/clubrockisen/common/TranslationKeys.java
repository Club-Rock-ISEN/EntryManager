package org.clubrockisen.common;

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
	 * Interface for configurable translations keys.
	 * @author Alex
	 */
	public interface Parametrable {
		/**
		 * Return the parameters to use when building the translation of the key.
		 * @return the parameters to use.
		 */
		Object[] getParameters ();
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
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
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
				this.memberKey = parentKey + "." + "member";
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString () {
				return memberKey;
			}
			
			/**
			 * The member name.
			 * @return The translation for the name field.
			 */
			public String name () {
				return memberKey + "." + "name";
			}
			
			/**
			 * The member gender.
			 * @return The translation for the gender field.
			 */
			public String gender () {
				return memberKey + "." + "gender";
			}
			
			/**
			 * The member entry number.
			 * @return The translation for the entry field.
			 */
			public String entries () {
				return memberKey + "." + "entries";
			}
			
			/**
			 * The member credit.
			 * @return The translation for the credit field.
			 */
			public String credit () {
				return memberKey + "." + "credit";
			}
			
			/**
			 * The member status.
			 * @return The translation for the status field.
			 */
			public String status () {
				return memberKey + "." + "status";
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
				this.partyKey = parentKey + "." + "party";
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString () {
				return partyKey;
			}
			
			/**
			 * The party date.
			 * @return the translation for the date field.
			 */
			public String date () {
				return partyKey + "." + "date";
			}
			
			/**
			 * The total number of entries.
			 * @return the translation for the total entries.
			 */
			public String entriesTotal () {
				return partyKey + "." + "entriestotal";
			}
			
			/**
			 * The number of entries for the first part.
			 * @return the translation for the first part entries.
			 */
			public String entriesFirstPart () {
				return partyKey + "." + "entriesfirstpart";
			}
			
			/**
			 * The number of entries for the second part.
			 * @return the translation for the second part entries.
			 */
			public String entriesSecondPart () {
				return partyKey + "." + "entriessecondpart";
			}
			
			/**
			 * The number of entries for the new member.
			 * @return the translation for the entries of new members.
			 */
			public String entriesNewMember () {
				return partyKey + "." + "entriesnewmember";
			}
			
			/**
			 * The number of free entries.
			 * @return the translation for the free entries.
			 */
			public String entriesFree () {
				return partyKey + "." + "entriesfree";
			}
			
			/**
			 * The number of entries from males.
			 * @return the translation for the male entries.
			 */
			public String entriesMale () {
				return partyKey + "." + "entriesmale";
			}
			
			/**
			 * The number of entries from female.
			 * @return the translation for the female entries.
			 */
			public String entriesFemale () {
				return partyKey + "." + "entriesfemale";
			}
			
			/**
			 * The revenue of the party.
			 * @return the translation for the revenue of the party.
			 */
			public String revenue () {
				return partyKey + "." + "revenue";
			}
			
			/**
			 * The profit of the party.
			 * @return the translation for the profit of the party.
			 */
			public String profit () {
				return partyKey + "." + "profit";
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
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
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
				this.genderKey = parentKey + "." + "gender";
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString () {
				return genderKey;
			}
			
			/**
			 * The male gender.
			 * @return the translation for the male gender.
			 */
			public String male () {
				return genderKey + "." + "male";
			}
			
			/**
			 * The female gender.
			 * @return the translation for the female gender.
			 */
			public String female () {
				return genderKey + "." + "female";
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
				this.statusKey = parentKey + "." + "status";
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString () {
				return statusKey;
			}
			
			/**
			 * The member status.
			 * @return the translation for the member status.
			 */
			public String member () {
				return statusKey + "." + "member";
			}
			
			/**
			 * The helper member status.
			 * @return the translation for the helper member status.
			 */
			public String helperMember () {
				return statusKey + "." + "helperMember";
			}
			
			/**
			 * The office member status.
			 * @return the translation for the office member status.
			 */
			public String officeMember () {
				return statusKey + "." + "officeMember";
			}
			
			/**
			 * The veteran status.
			 * @return the translation for the veteran status.
			 */
			public String veteran () {
				return statusKey + "." + "veteran";
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
				this.parameterKey = parentKey + "." + "parameter";
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString () {
				return parameterKey;
			}
			
			/**
			 * The look and feel parameter.
			 * @return the translation for the look and feel parameter.
			 */
			public String lookAndFeel () {
				return parameterKey + "." + "look_and_feel";
			}
			
			/**
			 * The time limit parameter.
			 * @return the translation for the time limit parameter.
			 */
			public String timeLimit () {
				return parameterKey + "." + "time_limit";
			}
			
			/**
			 * The entry price total parameter.
			 * @return the translation for the entry price total parameter.
			 */
			public String entryPriceTotal () {
				return parameterKey + "." + "entry_price_total";
			}
			
			/**
			 * The entry price first price parameter.
			 * @return the translation for the entry price first price parameter.
			 */
			public String entryPriceFirstPart () {
				return parameterKey + "." + "entry_price_first_part";
			}
			
			/**
			 * The entry price second price parameter.
			 * @return the translation for the entry price second price parameter.
			 */
			public String entryPriceSecondPart () {
				return parameterKey + "." + "entry_price_second_part";
			}
			
			/**
			 * The free entry frequency parameter.
			 * @return the translation for the free entry frequency parameter.
			 */
			public String freeEntryFrequency () {
				return parameterKey + "." + "free_entry_frequency";
			}
			
			/**
			 * The minimum credit parameter.
			 * @return the translation for the minimum credit parameter.
			 */
			public String minCredit () {
				return parameterKey + "." + "min_credit";
			}
			
			/**
			 * The maximum credit parameter.
			 * @return the translation for the maximum credit parameter.
			 */
			public String maxCredit () {
				return parameterKey + "." + "max_credit";
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
			return formatsKey + "." + "oldFileFormat";
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
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString () {
			return guiKey;
		}
		
		/**
		 * The title of the application.
		 * @return the translation for the application's title.
		 */
		public String title () {
			return guiKey + "." + "title";
		}
		
		/**
		 * Define an element that may also
		 * @author Alex
		 */
		public static final class GUIElement {
			/** The key to the property */
			private final String propertyKey;
			
			/**
			 * Constructor #1.<br />
			 * @param propertyKey
			 *        the key of the property.
			 */
			public GUIElement (final String propertyKey) {
				super();
				this.propertyKey = propertyKey;
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString () {
				return propertyKey;
			}
			
			/**
			 * The text of the element.
			 * @return the translation for the main text of the GUI element.
			 */
			public String getText () {
				return toString();
			}
			
			/**
			 * The shortcut to use for quick call to the element.
			 * @return the shortcut to use.
			 */
			public String getShortcut () {
				return propertyKey + "." + "shortcut";
			}
			
			/**
			 * The tool tip to show on the element.
			 * @return the tool tip to show on the element.
			 */
			public String getToolTip () {
				return propertyKey + "." + "tooltip";
			}
			
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
				menuKey = parentKey + "." + "menu";
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
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
					fileKey = parentKey + "." + "file";
				}
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Object#toString()
				 */
				@Override
				public String toString () {
					return fileKey;
				}
				
				/**
				 * The parameters item.
				 * @return the translation for the parameters item.
				 */
				public Gui.GUIElement parameters () {
					return new GUIElement(fileKey + "." + "parameters");
				}
				
				/**
				 * The quit item.
				 * @return the translation for the quit item.
				 */
				public Gui.GUIElement quit () {
					return new GUIElement(fileKey + "." + "quit");
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
					databaseKey = parentKey + "." + "database";
				}
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Object#toString()
				 */
				@Override
				public String toString () {
					return databaseKey;
				}
				
				/**
				 * The see members item.
				 * @return the translation for the see members item.
				 */
				public Gui.GUIElement seeMembers () {
					return new GUIElement(databaseKey + "." + "seeMembers");
				}
				
				/**
				 * The see attendees item.
				 * @return the translation for the see attendees item.
				 */
				public Gui.GUIElement seeAttendees () {
					return new GUIElement(databaseKey + "." + "seeAttendees");
				}
				
				/**
				 * The import data item.
				 * @return the translation for the import data item.
				 */
				public Gui.GUIElement importData () {
					return new GUIElement(databaseKey + "." + "importData");
				}
				
				/**
				 * The export data item.
				 * @return the translation for the export data item.
				 */
				public Gui.GUIElement exportData () {
					return new GUIElement(databaseKey + "." + "exportData");
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
					memberKey = parentKey + "." + "member";
				}
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Object#toString()
				 */
				@Override
				public String toString () {
					return memberKey;
				}
				
				/**
				 * The new member item.
				 * @return the translation for the new member item.
				 */
				public Gui.GUIElement newMember () {
					return new GUIElement(memberKey + "." + "newMember");
				}
				
				/**
				 * The delete member item.
				 * @return the translation for the delete member item.
				 */
				public Gui.GUIElement deleteMember () {
					return new GUIElement(memberKey + "." + "deleteMember");
				}
				
				/**
				 * The update member item.
				 * @return the translation for the update member item.
				 */
				public Gui.GUIElement updateMember () {
					return new GUIElement(memberKey + "." + "updateMember");
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
					helpKey = parentKey + "." + "help";
				}
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Object#toString()
				 */
				@Override
				public String toString () {
					return helpKey;
				}
				
				/**
				 * The help item.
				 * @return the translation for the help item.
				 */
				public Gui.GUIElement help () {
					return new GUIElement(helpKey + "." + "help");
				}
				
				/**
				 * The about item.
				 * @return the translation for the about item.
				 */
				public Gui.GUIElement about () {
					return new GUIElement(helpKey + "." + "about");
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
		public Gui.Menu menu () {
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
				parametersKey = parentKey + "." + "parameters";
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString () {
				return parametersKey;
			}
			
			/**
			 * The title of the panel.
			 * @return the translation for the panel.
			 */
			public String title () {
				return parametersKey + "." + "title";
			}
			
		}
		
		/**
		 * Access to the parameters manager translations.
		 * @return the parameters manager translations.
		 */
		public Gui.Parameters parameters () {
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
				buttonsKey = parentKey + "." + "buttons";
			}
			
			/**
			 * The enter button.
			 * @return the text of the enter button.
			 */
			public String enter () {
				return buttonsKey + "." + "enter";
			}
			
			/**
			 * The update button.
			 * @return the text of the update button.
			 */
			public String update () {
				return buttonsKey + "." + "update";
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString () {
				return buttonsKey;
			}
			
		}
		
		/**
		 * Access to the translations for the buttons.
		 * @return the buttons translation.
		 */
		public Gui.Buttons buttons () {
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
				dialogKey = parentKey + "." + "dialog";
			}
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString () {
				return dialogKey;
			}
			
			/**
			 * Abstract class to ease dialog translation.
			 * @author Alex
			 */
			public abstract static class AbstractDialog {
				
				/**
				 * Provide the key to the dialog, which allow to define the {@link #title()} and
				 * {@link #message()} method at this level.
				 */
				@Override
				public abstract String toString ();
				
				/**
				 * The title of the dialog.
				 * @return the translation of the dialog's title.
				 */
				public String title () {
					return toString() + "." + "title";
				}
				
				/**
				 * The message of the dialog.
				 * @return the translation of the dialog's message.
				 */
				public String message () {
					return toString() + "." + "message";
				}
			}
			
			/**
			 * The about dialog translations.
			 * @author Alex
			 */
			public static final class About extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the about dialog */
				private final String	aboutKey;
				/** Array with the parameters to use */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * Build the about dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 * @param author
				 *        the author of the application.
				 */
				private About (final String parentKey, final String author) {
					super();
					aboutKey = parentKey + "." + "about";
					parameters = new Object[1];
					parameters[0] = author;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return aboutKey;
				}
				
				/* (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
				
			}
			
			/**
			 * Access to the translations for the about dialog.
			 * @param author
			 *        the author of the application.
			 * @return the about dialog translations.
			 */
			public Dialog.About about (final String author) {
				return new About(dialogKey, author);
			}
			
			/**
			 * The dialog to warn when a member is not selected.
			 * @author Alex
			 */
			public static final class NotSelectedMember extends Dialog.AbstractDialog {
				/** The key to the not selected member dialog */
				private final String	notSelectedMemberKey;
				
				/**
				 * Constructor #1.<br />
				 * Build the not selected member dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				private NotSelectedMember (final String parentKey) {
					super();
					notSelectedMemberKey = parentKey + "." + "notSelectedMember";
				}
				
				/*
				 * (non-Javadoc)
				 * @see
				 * org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return notSelectedMemberKey;
				}
				
			}
			
			/**
			 * Access to the translations for the not selected member dialog.
			 * @return the not selected member dialog translations.
			 */
			public Dialog.NotSelectedMember notSelectedMember () {
				return new NotSelectedMember(dialogKey);
			}
			
			/**
			 * The dialog to warn when a member is not persisted.
			 * @author Alex
			 */
			public static final class NotPersistedMember extends Dialog.AbstractDialog {
				/** The key to the not persisted member dialog */
				private final String	notPersistedMember;
				
				/**
				 * Constructor #1.<br />
				 * Build the not persisted member dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				private NotPersistedMember (final String parentKey) {
					super();
					notPersistedMember = parentKey + "." + "notPersistedMember";
				}
				
				/*
				 * (non-Javadoc)
				 * @see
				 * org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return notPersistedMember;
				}
				
			}
			
			/**
			 * Access to the translations for the not persisted member.
			 * @return the not selected member dialog translations.
			 */
			public Dialog.NotPersistedMember notPersistedMember () {
				return new NotPersistedMember(dialogKey);
			}
			
			/**
			 * The dialog to warn when a parameter is not persisted.
			 * @author Alex
			 */
			public static final class NotPersistedParameter extends Dialog.AbstractDialog {
				/** The key to the not persisted parameter dialog */
				private final String	notPersistedParameter;
				
				/**
				 * Constructor #1.<br />
				 * Build the not persisted parameter dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				private NotPersistedParameter (final String parentKey) {
					super();
					notPersistedParameter = parentKey + "." + "notPersistedParameter";
				}
				
				/*
				 * (non-Javadoc)
				 * @see
				 * org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return notPersistedParameter;
				}
				
			}
			
			/**
			 * Access to the translations for the not persisted parameter.
			 * @return the not persisted parameter dialog translations.
			 */
			public Dialog.NotPersistedParameter notPersistedParameter () {
				return new NotPersistedParameter(dialogKey);
			}
			
			/**
			 * The dialog to warn when a unparsable date is entered.
			 * @author Alex
			 */
			public static final class UnparsableDate extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the unparsable date dialog */
				private final String	unparsableDateKey;
				/** Array with the parameters to use */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * Build the unparsable date dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 * @param date
				 *        the date which could not be parsed.
				 */
				private UnparsableDate (final String parentKey, final String date) {
					super();
					unparsableDateKey = parentKey + "." + "unparsableDate";
					parameters = new Object[1];
					parameters[0] = date;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return unparsableDateKey;
				}
				
				/* (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
				
			}
			
			/**
			 * Access to the translations for the unparsable date dialog.
			 * @param date
			 *        the date which could not be parsed.
			 * @return the unparsable date dialog translations.
			 */
			public Dialog.UnparsableDate unparsableDate (final String date) {
				return new UnparsableDate(dialogKey, date);
			}
			
			/**
			 * The dialog to warn that the help could not be displayed.
			 * @author Alex
			 */
			public static final class HelpNotDisplayable extends Dialog.AbstractDialog {
				/** The key to the help not displayable dialog */
				private final String	helpNotDisplayableKey;
				
				/**
				 * Constructor #1.<br />
				 * Build the help not displayable dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				private HelpNotDisplayable (final String parentKey) {
					super();
					helpNotDisplayableKey = parentKey + "." + "helpNotFound";
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return helpNotDisplayableKey;
				}
				
			}
			
			/**
			 * Access to the translations for the help not displayable dialog.
			 * @return the help not displayable dialog translations.
			 */
			public Dialog.HelpNotDisplayable helpNotDisplayable () {
				return new HelpNotDisplayable(dialogKey);
			}
			
			/**
			 * The dialog to ask confirmation for member deletion.
			 * @author Alex
			 */
			public static final class DeleteMember extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the delete member dialog */
				private final String	deleteMemberKey;
				/** The array with the parameters */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * Build the delete member dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 * @param memberName
				 *        the name of the member to delete.
				 */
				private DeleteMember (final String parentKey, final String memberName) {
					super();
					deleteMemberKey = parentKey + "." + "deleteMember";
					parameters = new Object[1];
					parameters[0] = memberName;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return deleteMemberKey;
				}
				
				/* (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
				
			}
			
			/**
			 * Access to the translations for the confirm dialog on member deletion.
			 * @param memberName
			 *        the name of the member to delete.
			 * @return the delete member dialog translations.
			 */
			public Dialog.DeleteMember deleteMember (final String memberName) {
				return new DeleteMember(dialogKey, memberName);
			}
			
			/**
			 * The dialog to confirm the success of file import.
			 * @author Alex
			 */
			public static final class FileImportSuccessful extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the file import successful dialog */
				private final String	fileImportSuccessfulKey;
				/** Array with the parameters */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * Build the file import successful dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 * @param fileName
				 *        the name of the file imported.
				 * @param membersImported
				 *        the number of members correctly imported
				 */
				private FileImportSuccessful (final String parentKey, final String fileName, final int membersImported) {
					super();
					fileImportSuccessfulKey = parentKey + "." + "fileImportSucceed";
					parameters = new Object[2];
					parameters[0] = fileName;
					parameters[1] = membersImported;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return fileImportSuccessfulKey;
				}
				
				/* (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
				
			}
			
			/**
			 * Access to the file import successful dialog.
			 * @param fileName
			 *        the name of the file imported.
			 * @param memberImported
			 *        the number of member successfully imported.
			 * @return the file import successful dialog.
			 */
			public Dialog.FileImportSuccessful fileImportSuccessful (final String fileName, final int memberImported) {
				return new FileImportSuccessful(dialogKey, fileName, memberImported);
			}
			
			/**
			 * The dialog of file import failed.
			 * @author Alex
			 */
			public static final class FileImportFailed extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the file import failed dialog */
				private final String	fileImportFailedKey;
				/** Array with the parameters */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * Build the file import failed dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 * @param fileName
				 *        the name of the file imported.
				 */
				private FileImportFailed (final String parentKey, final String fileName) {
					super();
					fileImportFailedKey = parentKey + "." + "fileImportFailed";
					parameters = new Object[1];
					parameters[0] = fileName;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return fileImportFailedKey;
				}
				
				/* (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
			}
			
			/**
			 * Access to the file import failed dialog.
			 * @param fileName
			 *        the name of the file imported.
			 * @return the file import failed dialog.
			 */
			public Dialog.FileImportFailed fileImportFailed (final String fileName) {
				return new FileImportFailed(dialogKey, fileName);
			}
			
			/**
			 * Structure of the choose format dialog.
			 * @author Alex
			 */
			public static final class ChooseFormat extends Dialog.AbstractDialog {
				/** The key to the choose format dialog */
				private final String	chooseFormaKey;
				
				/**
				 * Constructor #1.<br />
				 * Build the help not displayable dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				private ChooseFormat (final String parentKey) {
					super();
					chooseFormaKey = parentKey + "." + "chooseFormat";
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return chooseFormaKey;
				}
			}
			
			/**
			 * Access to the choose format dialog.
			 * @return the choose format dialog.
			 */
			public Dialog.ChooseFormat chooseFormat () {
				return new ChooseFormat(dialogKey);
			}
			
			
			/**
			 * The dialog to confirm the success of file export.
			 * @author Alex
			 */
			public static final class FileExportSuccessful extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the file export successful dialog */
				private final String	fileExportSuccessfulKey;
				/** Array with the parameters */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * Build the file export successful dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 * @param fileName
				 *        the name of the file exported.
				 */
				private FileExportSuccessful (final String parentKey, final String fileName) {
					super();
					fileExportSuccessfulKey = parentKey + "." + "fileExportSucceed";
					parameters = new Object[1];
					parameters[0] = fileName;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return fileExportSuccessfulKey;
				}
				
				/* (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
				
			}
			
			/**
			 * Access to the file export successful dialog.
			 * @param fileName
			 *        the name of the file imported.
			 * @return the file export successful dialog.
			 */
			public Dialog.FileExportSuccessful fileExportSuccessful (final String fileName) {
				return new FileExportSuccessful(dialogKey, fileName);
			}
			
			/**
			 * The dialog of file export failed.
			 * @author Alex
			 */
			public static final class FileExportFailed extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the file export failed dialog */
				private final String	fileExportFailedKey;
				/** Array with the parameters */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * Build the file export failed dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 * @param fileName
				 *        the name of the file exported.
				 */
				private FileExportFailed (final String parentKey, final String fileName) {
					super();
					fileExportFailedKey = parentKey + "." + "fileExportFailed";
					parameters = new Object[1];
					parameters[0] = fileName;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return fileExportFailedKey;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
			}
			
			/**
			 * Access to the file export failed dialog.
			 * @param fileName
			 *        the name of the file exported.
			 * @return the file export failed dialog.
			 */
			public Dialog.FileExportFailed fileExportFailed (final String fileName) {
				return new FileExportFailed(dialogKey, fileName);
			}
			
			/**
			 * The dialog for free entrances.
			 * @author Alex
			 */
			public static final class FreeEntry extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the free entry dialog */
				private final String	freeEntryKey;
				/** Array with the parameters */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * @param parentKey
				 *        the key from the parent category.
				 * @param memberName
				 *        the name of the member.
				 */
				public FreeEntry (final String parentKey, final String memberName) {
					super();
					freeEntryKey = parentKey + "." + "freeEntry";
					parameters = new Object[1];
					parameters[0] = memberName;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
				
				/*
				 * (non-Javadoc)
				 * @see
				 * org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return freeEntryKey;
				}
				
			}
			
			/**
			 * Access to the free entry dialog.
			 * @param memberName
			 *        the name of the member whose entry is free.
			 * @return the free entry dialog.
			 */
			public Dialog.FreeEntry freeEntry (final String memberName) {
				return new FreeEntry (dialogKey, memberName);
			}
			
			/**
			 * The dialog for entry price.
			 * @author Alex
			 */
			public static final class EntryPrice extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the entry price dialog */
				private final String	entryPriceKey;
				/** Array with the parameters */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * @param parentKey
				 *        the key from the parent category.
				 * @param memberName
				 *        the name of the member.
				 * @param price
				 *        the price the member has to pay.
				 */
				public EntryPrice (final String parentKey, final String memberName, final double price) {
					super();
					entryPriceKey = parentKey + "." + "entryPrice";
					parameters = new Object[2];
					parameters[0] = memberName;
					parameters[1] = price;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
				
				/*
				 * (non-Javadoc)
				 * @see
				 * org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return entryPriceKey;
				}
				
			}
			
			/**
			 * Access to the entry price dialog.
			 * @param memberName
			 *        the name of the member whose entry is free.
			 * @param price
			 *        the price the member has to pay.
			 * @return the entry price dialog.
			 */
			public Dialog.EntryPrice entryPrice (final String memberName, final double price) {
				return new EntryPrice (dialogKey, memberName, price);
			}
			
			/**
			 * The dialog for member entry.
			 * @author Alex
			 */
			public static final class MemberEntry extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the member entry dialog */
				private final String	memberEntryKey;
				/** Array with the parameters */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * @param parentKey
				 *        the key from the parent category.
				 * @param memberName
				 *        the name of the member.
				 */
				public MemberEntry (final String parentKey, final String memberName) {
					super();
					memberEntryKey = parentKey + "." + "memberEntry";
					parameters = new Object[1];
					parameters[0] = memberName;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
				
				/*
				 * (non-Javadoc)
				 * @see
				 * org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return memberEntryKey;
				}
				
			}
			
			/**
			 * Access to the member entry dialog.
			 * @param memberName
			 *        the name of the member whose entry is free.
			 * @return the member entry dialog.
			 */
			public Dialog.MemberEntry memberEntry (final String memberName) {
				return new MemberEntry (dialogKey, memberName);
			}
			
			/**
			 * The dialog for member entry failed.
			 * @author Alex
			 */
			public static final class MemberEntryFailed extends Dialog.AbstractDialog implements Parametrable {
				/** The key to the member entry failed dialog */
				private final String	memberEntryFailedKey;
				/** Array with the parameters */
				private final Object[]	parameters;
				
				/**
				 * Constructor #1.<br />
				 * @param parentKey
				 *        the key from the parent category.
				 * @param memberName
				 *        the name of the member.
				 */
				public MemberEntryFailed (final String parentKey, final String memberName) {
					super();
					memberEntryFailedKey = parentKey + "." + "memberEntryFailed";
					parameters = new Object[1];
					parameters[0] = memberName;
				}
				
				/*
				 * (non-Javadoc)
				 * @see org.clubrockisen.service.Translator.Key.Parametrable#getParameters()
				 */
				@Override
				public Object[] getParameters () {
					return parameters.clone();
				}
				
				/*
				 * (non-Javadoc)
				 * @see
				 * org.clubrockisen.service.Translator.Key.Gui.Dialog.AbstractDialog#toString()
				 */
				@Override
				public String toString () {
					return memberEntryFailedKey;
				}
				
			}
			
			/**
			 * Access to the member entry failed dialog.
			 * @param memberName
			 *        the name of the member whose entry is free.
			 * @return the member entry failed dialog.
			 */
			public Dialog.MemberEntryFailed memberEntryFailed (final String memberName) {
				return new MemberEntryFailed (dialogKey, memberName);
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
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString () {
			return miscKey;
		}
		
		/**
		 * The field / value separator.
		 * @return the translation for the field / value separator.
		 */
		public String fieldValueSeparator () {
			return miscKey + "." + "fieldValueSeparator";
		}
		
		/**
		 * The currency symbol to use.
		 * @return the translation for the currency symbol.
		 */
		public String currencySymbol () {
			return miscKey + "." + "currencySymbol";
		}
		
		/**
		 * The plural letter of the language.
		 * @return the plural letter of the language.
		 */
		public String pluralLetter () {
			return miscKey + "." + "pluralLetter";
		}
		
		/**
		 * The 'OK' text to use.
		 * @return the translation for the OK.
		 */
		public String ok () {
			return miscKey + "." + "ok";
		}
		
		/**
		 * The 'cancel' text to use.
		 * @return the translation for the cancel.
		 */
		public String cancel () {
			return miscKey + "." + "cancel";
		}
		
		/**
		 * The 'apply' text to use.
		 * @return the translation for the apply.
		 */
		public String apply () {
			return miscKey + "." + "apply";
		}
	}
	
	/** Access to the miscellaneous translations. */
	public static final TranslationKeys.Misc	MISC	= new Misc();
}