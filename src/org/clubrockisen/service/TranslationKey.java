package org.clubrockisen.service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Translation file key structure.<br />
 * 
 * @author Alex
 */
public final class TranslationKey {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(TranslationKey.class.getName());
	
	/**
	 * Constructor #1.<br />
	 * Unique default and private constructor. Allow access to the configuration key only through
	 * TODO
	 */
	private TranslationKey () {
		super();
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Creating the translation key structure.");
		}
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
		 * The translations
		 * @author Alex
		 */
		public static final class Member {
			/** The key to the member structure */
			private final String	memberKey;
			
			/**
			 * Constructor #1.<br />
			 * Build the menu structure.
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
		
	}
	
	/** Access to the entity translations */
	public static final Entity	ENTITY	= new Entity();
	
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
		public Gender gender () {
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
		public Status status () {
			return new Status(enumKey);
		}
		
	}
	
	/** Access to the enumeration translations */
	public static final Enum	ENUM	= new Enum();
	
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
				 * The profit item.
				 * @return the translation for the profit item.
				 */
				public String profit () {
					return fileKey + "." + "profit";
				}
				
				/**
				 * The parameters item.
				 * @return the translation for the parameters item.
				 */
				public String parameters () {
					return fileKey + "." + "parameters";
				}
				
				/**
				 * The quit item.
				 * @return the translation for the quit item.
				 */
				public String quit () {
					return fileKey + "." + "quit";
				}
				
			}
			
			/**
			 * Access to the file menu translations.
			 * @return the file menu translations.
			 */
			public File file () {
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
				public String seeMembers () {
					return databaseKey + "." + "seeMembers";
				}
				
				/**
				 * The see attendees item.
				 * @return the translation for the see attendees item.
				 */
				public String seeAttendees () {
					return databaseKey + "." + "seeAttendees";
				}
				
				/**
				 * The export data item.
				 * @return the translation for the export data item.
				 */
				public String exportData () {
					return databaseKey + "." + "exportData";
				}
				
			}
			
			/**
			 * Access to the database menu translations.
			 * @return the database menu translations.
			 */
			public Database database () {
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
				public String newMember () {
					return memberKey + "." + "newMember";
				}
				
				/**
				 * The delete member item.
				 * @return the translation for the delete member item.
				 */
				public String deleteMember () {
					return memberKey + "." + "deleteMember";
				}
				
				/**
				 * The update member item.
				 * @return the translation for the update member item.
				 */
				public String updateMember () {
					return memberKey + "." + "updateMember";
				}
				
			}
			
			/**
			 * Access to the member menu translations.
			 * @return the member menu translations.
			 */
			public Member member () {
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
				public String help () {
					return helpKey + "." + "help";
				}
				
				/**
				 * The about item.
				 * @return the translation for the about item.
				 */
				public String about () {
					return helpKey + "." + "about";
				}
				
			}
			
			/**
			 * Access to the help menu translations.
			 * @return the help menu translations.
			 */
			public Help help () {
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
			 * The about dialog translations.
			 * @author Alex
			 */
			public static final class About {
				/** The key to the about dialog */
				private final String	aboutKey;
				
				/**
				 * Constructor #1.<br />
				 * Build the about dialog structure.
				 * @param parentKey
				 *        the key from the parent category.
				 */
				private About (final String parentKey) {
					super();
					aboutKey = parentKey + "." + "about";
				}
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Object#toString()
				 */
				@Override
				public String toString () {
					return aboutKey;
				}
				
				/**
				 * The title of the dialog.
				 * @return the translation of the title of the dialog.
				 */
				public String title () {
					return aboutKey + "." + "title";
				}
				
				/**
				 * The author of the application.
				 * @return the translation of the author introduction string.
				 */
				public String author () {
					return aboutKey + "." + "author";
				}
				
				/**
				 * The license of the application.
				 * @return the translation of the license applicable to the application.
				 */
				public String license () {
					return aboutKey + "." + "license";
				}
			}
			
			/**
			 * Access to the translations for the about dialog.
			 * @return the about dialog translations.
			 */
			public About about () {
				return new About(dialogKey);
			}
			
		}
		
		/**
		 * Access to the translations for the dialog.
		 * @return the dialog translations.
		 */
		public Dialog dialog () {
			return new Dialog(guiKey);
		}
	}
	
	/** Access to the GUI related translations. */
	public static final Gui	GUI	= new Gui();
	
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
		 * The currency symbol to use.
		 * @return the translation for the currency symbol.
		 */
		public String pluralLetter () {
			return miscKey + "." + "pluralLetter";
		}
	}
	
	/** Access to the miscellaneous translations. */
	public static final Misc	MISC	= new Misc();
}
