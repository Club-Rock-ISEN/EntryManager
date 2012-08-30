package org.clubrockisen.dao.mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;
import org.clubrockisen.exception.SQLConfigurationError;
import org.clubrockisen.service.Configuration;
import org.clubrockisen.service.ConfigurationKey;

/**
 * The factory for the MySQL DAO classes.<br />
 * A factory is only handling one DAO of each type and thus will not instantiate more DAOs than
 * needed. However, if the factory is load several times,
 * @author Alex
 */
public class MySQLDAOFactory extends AbstractDAOFactory {
	/** Logger */
	private static Logger				lg			= Logger.getLogger(AbstractDAOFactory.class.getName());
	
	/** Access to the configuration */
	private final Configuration			config	= Configuration.getInstance();
	/** Access to the key structure of the configuration */
	private final ConfigurationKey		keys	= ConfigurationKey.KEY;
	/** The connection to the database */
	private final Connection			connection;
	/** The URL for connecting the database */
	private String						url;
	/** The user name of the schema */
	private String						user;
	/** The password associated to the user */
	private String						password;
	
	/** The member DAO */
	private final DAO<Member>			memberDao;
	/** The parameter DAO */
	private final DAO<Parameter>		parameterDao;
	/** The party DAO */
	private final DAO<Party>			partyDao;
	/** The party DAO */
	private final DAO<EntryMemberParty>	entryMemberPartyDao;
	
	/**
	 * Constructor #1.<br />
	 */
	public MySQLDAOFactory () {
		super();
		loadParameters();
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (final SQLException e) {
			lg.severe("Failed to create the connection to the database " + e.getMessage());
			throw new SQLConfigurationError("Failed to connect to MySQL database: "
					+ e.getMessage(), e);
		}
		if (lg.isLoggable(Level.INFO)) {
			lg.info("Successfully connected to database " + url);
		}
		
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Creating DAOs...");
		}
		// Instantiating all DAOs once to avoid multiple DAOs
		memberDao = new MySQLMemberDAO(connection);
		parameterDao = new MySQLParameterDAO(connection);
		partyDao = new MySQLPartyDAO(connection);
		entryMemberPartyDao = new MySQLEntryMemberPartyDAO(connection);
	}
	
	/**
	 * Load the parameters from the configuration file.
	 */
	private void loadParameters () {
		url = config.get(keys.db().url());
		user = config.get(keys.db().username());
		password = config.get(keys.db().password());
	}
	
	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close () throws IOException {
		try {
			connection.close();
		} catch (final SQLException e) {
			lg.warning("Error while closing MySQL connection (" + e.getMessage() + ")");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getMemberDAO()
	 */
	@Override
	public DAO<Member> getMemberDAO () {
		return memberDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getParameterDAO()
	 */
	@Override
	public DAO<Parameter> getParameterDAO () {
		return parameterDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getPartyDAO()
	 */
	@Override
	public DAO<Party> getPartyDAO () {
		return partyDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.clubrockisen.dao.AbstractDAOFactory#getEntryMemberPartyDAO()
	 */
	@Override
	public DAO<EntryMemberParty> getEntryMemberPartyDAO () {
		return entryMemberPartyDao;
	}
}
