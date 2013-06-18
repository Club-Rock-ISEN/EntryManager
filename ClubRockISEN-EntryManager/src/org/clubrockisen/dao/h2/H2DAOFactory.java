package org.clubrockisen.dao.h2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.clubrockisen.common.Constants;
import org.clubrockisen.common.error.SQLConfigurationError;
import org.clubrockisen.dao.NoIdException;
import org.clubrockisen.dao.Utils;
import org.clubrockisen.dao.Utils.DBConnectionInfo;
import org.clubrockisen.dao.abstracts.AbstractDAOFactory;
import org.clubrockisen.dao.abstracts.DAO;
import org.clubrockisen.dao.mysql.MySQLEntryMemberPartyDAO;
import org.clubrockisen.dao.mysql.MySQLMemberDAO;
import org.clubrockisen.dao.mysql.MySQLParameterDAO;
import org.clubrockisen.dao.mysql.MySQLPartyDAO;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;
import org.h2.tools.RunScript;

/**
 * The factory for the H2 DAO classes.<br />
 * A factory is only handling one DAO of each type and thus will not instantiate more DAOs than
 * needed. However, if the factory is load several times, several DAOs will be created.
 * @author Alex
 */
public class H2DAOFactory extends AbstractDAOFactory {
	/** Logger */
	private static Logger				lg	= Logger.getLogger(H2DAOFactory.class.getName());
	
	/** The connection to the database */
	private final Connection			connection;
	/** The member DAO */
	private final DAO<Member>			memberDao;
	/** The parameter DAO */
	private final DAO<Parameter>		parameterDao;
	/** The party DAO */
	private final DAO<Party>			partyDao;
	/** The entry member party DAO */
	private final DAO<EntryMemberParty>	entryMemberPartyDao;
	
	/**
	 * Constructor #1.<br />
	 */
	public H2DAOFactory () {
		super();
		
		final DBConnectionInfo dbInfos = getDBConnectionInfo();
		Path dbFile = getDBFile(dbInfos, true);
		if (!Files.exists(dbFile)) {
			try {
				dbFile = getDBFile(dbInfos, false);
				if (lg.isLoggable(Level.INFO)) {
					lg.info("URL connection: " + (org.h2.engine.Constants.START_URL + dbFile));
				}
				RunScript.execute(org.h2.engine.Constants.START_URL + dbFile, dbInfos.getUsername(), dbInfos.getPassword(),
						getCreationFile(), null, true);
			} catch (final SQLException e) {
				lg.warning("Error while initilization of H2 database (" + e.getClass() + "; " +
						e.getMessage() + ")");
				throw new SQLConfigurationError("Could not create H2 database", e);
			}
			
		}
		
		connection = Utils.getConnection(dbInfos);
		// Instantiating all DAOs once to avoid multiple DAOs
		try {
			memberDao = new MySQLMemberDAO(connection);
			parameterDao = new MySQLParameterDAO(connection);
			partyDao = new MySQLPartyDAO(connection);
			entryMemberPartyDao = new MySQLEntryMemberPartyDAO(connection);
		} catch (SQLException | NoIdException e) {
			throw new SQLConfigurationError("Error while initializing H2 DAOs.", e);
		}
	}

	/**
	 * Check if the database file exists.
	 * @param dbInfos
	 *        the database information to use.
	 * @param suffix
	 *        <code>true</code> if the suffix should be added to the path.
	 * @return <code>true</code>
	 */
	private static Path getDBFile (final DBConnectionInfo dbInfos, final boolean suffix) {
		// Creating database if file does not exists. jdbc:h2:file:data/database/crock.db
		String file = dbInfos.getUrl().substring(org.h2.engine.Constants.START_URL.length());
		if (file.startsWith(Constants.FILE_URI_PREFIX)) {
			file = file.substring(Constants.FILE_URI_PREFIX.length());
		}
		// Removing parameters
		final int delimiter = file.lastIndexOf(';');
		if (delimiter >= 0) {
			file = file.substring(0, delimiter);
		}
		
		// Adding the extension if required
		if (suffix) {
			file += org.h2.engine.Constants.SUFFIX_PAGE_FILE;
		}
		
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("filename: " + file);
		}
		return Paths.get(file);
	}
	
	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close () throws IOException {
		Utils.close(connection);
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.abstracts.AbstractDAOFactory#getParameterDAO()
	 */
	@Override
	public DAO<Parameter> getParameterDAO () {
		return parameterDao;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.abstracts.AbstractDAOFactory#getMemberDAO()
	 */
	@Override
	public DAO<Member> getMemberDAO () {
		return memberDao;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.abstracts.AbstractDAOFactory#getPartyDAO()
	 */
	@Override
	public DAO<Party> getPartyDAO () {
		return partyDao;
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.abstracts.AbstractDAOFactory#getEntryMemberPartyDAO()
	 */
	@Override
	public DAO<EntryMemberParty> getEntryMemberPartyDAO () {
		return entryMemberPartyDao;
	}
}
