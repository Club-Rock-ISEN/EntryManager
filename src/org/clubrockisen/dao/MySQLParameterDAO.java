package org.clubrockisen.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.clubrockisen.entities.Column;
import org.clubrockisen.entities.Parameter;

/**
 * 
 * @author Alex
 */
public class MySQLParameterDAO implements DAO<Parameter> {
	private static Logger	lg	= Logger.getLogger(MySQLParameterDAO.class.getName());
	
	private final Connection	connection;
	private final Map<? extends Enum<?>, Column>	columns;

	/**
	 * Constructor #1.<br />
	 * @param connection the connection to the database.
	 */
	public MySQLParameterDAO (final Connection connection) {
		this.connection = connection;
		lg.fine("New " + this.getClass().getCanonicalName() + ".");
		// Initialize the columns (call to the constructor is required
		// to ensure the columns are created).
		new Parameter();
		columns = Parameter.getColumns();
	}
	
	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#create(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean create (final Parameter obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#find(int)
	 */
	@Override
	public Parameter find (final int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#update(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean update (final Parameter obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#delete(org.clubrockisen.entities.Entity)
	 */
	@Override
	public boolean delete (final Parameter obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#retrieveAll()
	 */
	@Override
	public List<Parameter> retrieveAll () {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.clubrockisen.dao.DAO#search(org.clubrockisen.entities.Column, java.lang.String)
	 */
	@Override
	public List<Parameter> search (final Column field, final String value) {
		// TODO Auto-generated method stub
		return null;
	}
}
