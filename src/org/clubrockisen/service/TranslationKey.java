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
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Creating the translation key structure.");
		}
	}
	
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
