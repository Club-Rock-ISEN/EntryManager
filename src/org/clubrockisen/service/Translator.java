package org.clubrockisen.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.Configuration;
import org.clubrockisen.common.ConfigurationKey;
import org.clubrockisen.common.Constants;
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
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#has(java.lang.String)
	 */
	@Override
	public boolean has (final String key) {
		return translations.containsKey(key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.lang.String)
	 */
	@Override
	public String get (final String key) {
		if (!has(key)) {
			if (lg.isLoggable(Level.INFO)) {
				lg.info("Cannot find translation for key " + key);
			}
			return key;
		}
		
		return translations.getProperty(key);
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.service.abstracts.ITranslator#get(java.lang.String, java.lang.Object[])
	 */
	@Override
	public String get (final String key, final Object... parameters) {
		String translation = get(key);
		// Case with no parameters or no translation found
		if (key.equals(translation) || parameters == null || parameters.length == 0) {
			return translation;
		}
		
		for (int indexParameter = 0; indexParameter < parameters.length; ++indexParameter) {
			final Object parameter = parameters[indexParameter];
			final String strToReplace = "" + Constants.PARAMETER_PREFIX + indexParameter;
			if (translation.indexOf(strToReplace) == -1) {
				lg.warning("Parameter '" + parameter + "' cannot be put into the translation, '" +
						strToReplace + "' was not found.");
				continue;
			}
			translation = translation.replace(strToReplace, parameter.toString());
		}
		
		return translation;
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
			public Parameter parameter () {
				return new Parameter(enumKey);
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
			 * Define an element that may also
			 * @author Alex
			 */
			public static final class GUIElement {
				/** The key to the property */
				private final String propertyKey;
				
				/**
				 * Constructor #.<br />
				 * @param propertyKey the key of the property.
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
					 * The profit item.
					 * @return the translation for the profit item.
					 */
					public GUIElement profit () {
						return new GUIElement(fileKey + "." + "profit");
					}
					
					/**
					 * The parameters item.
					 * @return the translation for the parameters item.
					 */
					public GUIElement parameters () {
						return new GUIElement(fileKey + "." + "parameters");
					}
					
					/**
					 * The quit item.
					 * @return the translation for the quit item.
					 */
					public GUIElement quit () {
						return new GUIElement(fileKey + "." + "quit");
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
					public GUIElement seeMembers () {
						return new GUIElement(databaseKey + "." + "seeMembers");
					}
					
					/**
					 * The see attendees item.
					 * @return the translation for the see attendees item.
					 */
					public GUIElement seeAttendees () {
						return new GUIElement(databaseKey + "." + "seeAttendees");
					}
					
					/**
					 * The import data item.
					 * @return the translation for the import data item.
					 */
					public GUIElement importData () {
						return new GUIElement(databaseKey + "." + "importData");
					}
					
					/**
					 * The export data item.
					 * @return the translation for the export data item.
					 */
					public GUIElement exportData () {
						return new GUIElement(databaseKey + "." + "exportData");
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
					public GUIElement newMember () {
						return new GUIElement(memberKey + "." + "newMember");
					}
					
					/**
					 * The delete member item.
					 * @return the translation for the delete member item.
					 */
					public GUIElement deleteMember () {
						return new GUIElement(memberKey + "." + "deleteMember");
					}
					
					/**
					 * The update member item.
					 * @return the translation for the update member item.
					 */
					public GUIElement updateMember () {
						return new GUIElement(memberKey + "." + "updateMember");
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
					public GUIElement help () {
						return new GUIElement(helpKey + "." + "help");
					}
					
					/**
					 * The about item.
					 * @return the translation for the about item.
					 */
					public GUIElement about () {
						return new GUIElement(helpKey + "." + "about");
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
				public static final class About extends AbstractDialog implements Parametrable {
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
						return parameters;
					}
					
				}
				
				/**
				 * Access to the translations for the about dialog.
				 * @param author
				 *        the author of the application.
				 * @return the about dialog translations.
				 */
				public About about (final String author) {
					return new About(dialogKey, author);
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
				
				/**
				 * The dialog to warn when a parameter is not persisted.
				 * @author Alex
				 */
				public static final class NotPersistedParameter extends AbstractDialog {
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
				public NotPersistedParameter notPersistedParameter () {
					return new NotPersistedParameter(dialogKey);
				}
				
				/**
				 * The dialog to warn when a unparsable date is entered.
				 * @author Alex
				 */
				public static final class UnparsableDate extends AbstractDialog implements Parametrable {
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
						return parameters;
					}
					
				}
				
				/**
				 * Access to the translations for the unparsable date dialog.
				 * @param date
				 *        the date which could not be parsed.
				 * @return the unparsable date dialog translations.
				 */
				public UnparsableDate unparsableDate (final String date) {
					return new UnparsableDate(dialogKey, date);
				}
				
				/**
				 * The dialog to warn that the help could not be displayed.
				 * @author Alex
				 */
				public static final class HelpNotDisplayable extends AbstractDialog {
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
				public HelpNotDisplayable helpNotDisplayable () {
					return new HelpNotDisplayable(dialogKey);
				}
				
				/**
				 * The dialog to ask confirmation for member deletion.
				 * @author Alex
				 */
				public static final class DeleteMember extends AbstractDialog implements Parametrable {
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
						return parameters;
					}
					
				}
				
				/**
				 * Access to the translations for the confirm dialog on member deletion.
				 * @param memberName
				 *        the name of the member to delete.
				 * @return the delete member dialog translations.
				 */
				public DeleteMember deleteMember (final String memberName) {
					return new DeleteMember(dialogKey, memberName);
				}
				
				/**
				 * The dialog to confirm the success of file import.
				 * @author Alex
				 */
				public static final class FileImportSuccessful extends AbstractDialog implements Parametrable {
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
						return parameters;
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
				public FileImportSuccessful fileImportSuccessful (final String fileName, final int memberImported) {
					return new FileImportSuccessful(dialogKey, fileName, memberImported);
				}
				
				/**
				 * The dialog of file import failed.
				 * @author Alex
				 */
				public static final class FileImportFailed extends AbstractDialog implements Parametrable {
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
						return parameters;
					}
				}
				
				/**
				 * Access to the file import failed dialog.
				 * @param fileName
				 *        the name of the file imported.
				 * @return the file import failed dialog.
				 */
				public FileImportFailed fileImportFailed (final String fileName) {
					return new FileImportFailed(dialogKey, fileName);
				}
				
				/**
				 * Structure of the choose format dialog.
				 * @author Alex
				 */
				public static final class ChooseFormat extends AbstractDialog {
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
				public ChooseFormat chooseFormat () {
					return new ChooseFormat(dialogKey);
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
