package org.clubrockisen.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.Configuration;
import org.clubrockisen.common.ConfigurationKey;
import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Entity;
import org.clubrockisen.service.abstracts.ITranslator;

/**
 * Implementation of the translator.<br />
 * Singleton which provide utilities method to translate key to the local set in the configuration
 * file.
 * TODO move keys in common package
 * @author Alex
 */
public final class Translator implements ITranslator {
	/** Logger */
	private static Logger					lg			= Logger.getLogger(Translator.class.getName());
	
	// Configuration
	/** Access to the configuration */
	private final Configuration				config		= Configuration.getInstance();
	/** Access to the key structure of the configuration */
	private static final ConfigurationKey	KEYS		= ConfigurationKey.KEY;
	
	// Translations
	/** Unique instance of the class */
	private static Translator				singleton	= new Translator();
	/** The map between the keys and their translation */
	private final Properties				translations;
	
	/**
	 * Constructor #1.<br />
	 * Private constructor which load the translation file.
	 */
	private Translator () {
		final String translationFile = config.get(KEYS.translationFile());
		translations = new Properties();
		try {
			translations.loadFromXML(new FileInputStream(translationFile));
		} catch (final IOException e) {
			lg.severe("Could not load translation file: " + translationFile + " (" + e.getMessage()
					+ ")");
			translations.clear();
			return;
		}
		
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Language locale file " + translationFile + " successfully loaded ("
					+ translations.size() + " keys loaded)");
		}
	}
	
	/**
	 * Return the translator.
	 * @return the unique instance of the class.
	 */
	public static Translator getInstance () {
		return singleton;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.lang.String)
	 */
	@Override
	public String get (final String key) {
		if (!translations.containsKey(key)) {
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Cannot find translation for key " + key);
			}
			return key;
		}
		
		return translations.getProperty(key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#getField(java.lang.String)
	 */
	@Override
	public String getField (final String key) {
		return get(key) + get(Key.MISC.fieldValueSeparator());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.lang.Enum)
	 */
	@Override
	public String get (final Enum<?> key) {
		return get(Key.ENUM.toString() + "."
				+ key.getClass().getSimpleName().toLowerCase(Locale.ENGLISH) + "."
				+ key.name().toLowerCase(Locale.ENGLISH));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#getField(java.lang.Enum)
	 */
	@Override
	public String getField (final Enum<?> key) {
		return get(key) + get(Key.MISC.fieldValueSeparator());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(org.clubrockisen.entities.Entity)
	 */
	@Override
	public String get (final Entity entity) {
		return get(entity, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.ITranslator#getField(org.clubrockisen.entities.Entity)
	 */
	@Override
	public String getField (final Entity entity) {
		return getField(entity, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(org.clubrockisen.entities.Entity,
	 * org.clubrockisen.entities.Column)
	 */
	@Override
	public String get (final Entity entity, final Column column) {
		return get(Key.ENTITY.toString() + "." + entity.getEntityName().toLowerCase(Locale.ENGLISH)
				+ (column == null ? "" : "." + column.getName().toLowerCase(Locale.ENGLISH)));
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.clubrockisen.service.abstracts.ITranslator#getField(org.clubrockisen.entities.Entity,
	 * org.clubrockisen.entities.Column)
	 */
	@Override
	public String getField (final Entity entity, final Column column) {
		return get(entity, column) + get(Key.MISC.fieldValueSeparator());
	}
	
	/**
	 * The Translation file key structure.
	 * @author Alex
	 */
	public static final class Key {
		/**
		 * Constructor #1.<br />
		 * Build the key structure.
		 */
		private Key () {
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
			public Parameters parameters () {
				return new Parameters(guiKey);
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
					 * Provide the key to the dialog, which allow to define the {@link #title()}
					 * and {@link #message()} method at this level.
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
				
				/**
				 * The dialog to warn when a member is not selected.
				 * @author Alex
				 */
				public static final class NotSelectedMember extends AbstractDialog {
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
				public NotSelectedMember notSelectedMember () {
					return new NotSelectedMember(dialogKey);
				}
				
				/**
				 * The dialog to warn when a member is not persisted.
				 * @author Alex
				 */
				public static final class NotPersistedMember extends AbstractDialog {
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
				public NotPersistedMember notPersistedMember () {
					return new NotPersistedMember(dialogKey);
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
		public static final Misc	MISC	= new Misc();
	}
}
