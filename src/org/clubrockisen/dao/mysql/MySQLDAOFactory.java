package org.clubrockisen.dao.mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.clubrockisen.common.error.SQLConfigurationError;
import org.clubrockisen.dao.Utils;
import org.clubrockisen.dao.abstracts.EntryManagerAbstractDAOFactory;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;

import com.alexrnl.commons.database.dao.DAO;
import com.alexrnl.commons.database.dao.DataSourceConfiguration;

/**
 * The factory for the MySQL DAO classes.<br />
 * A factory is only handling one DAO of each type and thus will not instantiate more DAOs than
 * needed. However, if the factory is load several times, several DAOs will be created.
 * @author Alex
 */
public class MySQLDAOFactory extends EntryManagerAbstractDAOFactory {
	
	/** The connection to the database */
	private final Connection		connection;
	/** The member DAO */
	private DAO<Member>				memberDao;
	/** The parameter DAO */
	private DAO<Parameter>			parameterDao;
	/** The party DAO */
	private DAO<Party>				partyDao;
	/** The entry DAO */
	private DAO<EntryMemberParty>	entryMemberPartyDao;
	
	/**
	 * Constructor #1.<br />
	 * @param dataSourceConfiguration
	 *        the data source configuration.
	 */
	public MySQLDAOFactory (final DataSourceConfiguration dataSourceConfiguration) {
		super(dataSourceConfiguration);
		connection = Utils.getConnection(getDataSourceConfiguration());
		
		// Instantiating all DAOs once to avoid multiple DAOs
		try {
			memberDao = new MySQLMemberDAO(connection);
			parameterDao = new MySQLParameterDAO(connection);
			partyDao = new MySQLPartyDAO(connection);
			entryMemberPartyDao = new MySQLEntryMemberPartyDAO(connection);
		} catch (final SQLException e) {
			throw new SQLConfigurationError("Error while initializing MySQL DAOs", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close () throws IOException {
		super.close();
		Utils.close(connection);
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
