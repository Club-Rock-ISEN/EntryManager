package org.clubrockisen.dao.h2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.clubrockisen.common.error.SQLConfigurationError;
import org.clubrockisen.dao.Utils;
import org.clubrockisen.dao.abstracts.EntryManagerAbstractDAOFactory;
import org.clubrockisen.dao.mysql.MySQLEntryMemberPartyDAO;
import org.clubrockisen.dao.mysql.MySQLMemberDAO;
import org.clubrockisen.dao.mysql.MySQLParameterDAO;
import org.clubrockisen.dao.mysql.MySQLPartyDAO;
import org.clubrockisen.entities.EntryMemberParty;
import org.clubrockisen.entities.Member;
import org.clubrockisen.entities.Parameter;
import org.clubrockisen.entities.Party;

import com.alexrnl.commons.database.dao.DAO;
import com.alexrnl.commons.database.dao.DataSourceConfiguration;
import com.alexrnl.commons.database.h2.H2Utils;

/**
 * The factory for the H2 DAO classes.<br />
 * A factory is only handling one DAO of each type and thus will not instantiate more DAOs than
 * needed. However, if the factory is load several times, several DAOs will be created.
 * @author Alex
 */
public class H2DAOFactory extends EntryManagerAbstractDAOFactory {
	
	/** The connection to the database */
	private final Connection				connection;
	/** The member DAO */
	private DAO<Member>				memberDao;
	/** The parameter DAO */
	private DAO<Parameter>			parameterDao;
	/** The party DAO */
	private DAO<Party>				partyDao;
	/** The entry member party DAO */
	private DAO<EntryMemberParty>	entryMemberPartyDao;
	
	/**
	 * Constructor #1.<br />
	 * @param dataSourceConfiguration
	 *        the data source configuration.
	 */
	public H2DAOFactory (final DataSourceConfiguration dataSourceConfiguration) {
		super(dataSourceConfiguration);
		H2Utils.initDatabase(getDataSourceConfiguration());
		
		connection = Utils.getConnection(getDataSourceConfiguration());
		// Instantiating all DAOs once to avoid multiple DAOs
		try {
			memberDao = new MySQLMemberDAO(connection);
			parameterDao = new MySQLParameterDAO(connection);
			partyDao = new MySQLPartyDAO(connection);
			entryMemberPartyDao = new MySQLEntryMemberPartyDAO(connection);
		} catch (final SQLException e) {
			throw new SQLConfigurationError("Error while initializing H2 DAOs", e);
		}
		addDAO(Member.class, memberDao);
		addDAO(Parameter.class, parameterDao);
		addDAO(Party.class, partyDao);
		addDAO(EntryMemberParty.class, entryMemberPartyDao);
	}

	@Override
	public void close () throws IOException {
		super.close();
		Utils.close(connection);
	}
	
	@Override
	public DAO<Parameter> getParameterDAO () {
		return parameterDao;
	}
	
	@Override
	public DAO<Member> getMemberDAO () {
		return memberDao;
	}
	
	@Override
	public DAO<Party> getPartyDAO () {
		return partyDao;
	}
	
	@Override
	public DAO<EntryMemberParty> getEntryMemberPartyDAO () {
		return entryMemberPartyDao;
	}
}
