package org.clubrockisen.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represent an abstract entity of the database.<br />
 * @author Alex
 */
public abstract class Entity implements Serializable {
	/** Logger */
	private static Logger								lg					= Logger.getLogger(Entity.class.getName());
	
	/** Serial Version UID */
	private static final long							serialVersionUID	= 1337771320210441402L;
	
	/** Map between the entities and their id columns */
	private static Map<Class<? extends Entity>, Column>	idColumns 			= new HashMap<>();
	
	/**
	 * Constructor #1.<br />
	 * Unique constructor of the class. Ensure to call the {@link #setEntityColumns()} method.
	 */
	protected Entity () {
		if (getEntityColumns() == null) {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Calling the #setColumn() method.");
			}
			setEntityColumns();
		}
		
	}
	
	/**
	 * Return the name of the represented entity.
	 * @return the name of the represented entity.
	 */
	public abstract String getEntityName ();
	
	/**
	 * Method that creates the columns (or fields) of the entity that is represent.
	 */
	protected abstract void setEntityColumns ();
	
	/**
	 * Return the list of the columns of the table.
	 * @return the list of the columns.
	 */
	public abstract Map<? extends Enum<?>, Column> getEntityColumns ();
	
	/**
	 * Return the id field (as a {@link String}) of the entity.
	 * @return the id of the object.
	 */
	public abstract String getID ();
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Entity clone () throws CloneNotSupportedException {
		return (Entity) super.clone();
	}
	
	/**
	 * Return the id {@link Column} of the entity.
	 * @return the {@link Column} which is defined as the unique (and identifying) column of the
	 *         object.
	 * @throws NoIdException
	 *         if no such {@link Column} could be found.
	 */
	public Column getIDColumn () throws NoIdException {
		// If the id column has previously been found
		if (idColumns.containsKey(getClass())) {
			return idColumns.get(getClass());
		}
		// Searching the id column
		for (final Column column : getEntityColumns().values()) {
			if (column.isID()) {
				idColumns.put(getClass(), column);
				return idColumns.get(getClass());
			}
		}
		// If none, throw exception
		throw new NoIdException(this.getClass());
	}
	
	/**
	 * Generates the beginning of the insert query in SQL syntax:<br />
	 * <code>INSERT INTO (<i>column names</i>) VALUES</code>
	 * @return the query ready for the entity specific data.
	 * @see #generateInsertQuerySQL(boolean) Method that allows to specify if the ID column
	 *      should be in the query
	 */
	public String generateInsertQuerySQL () {
		return generateInsertQuerySQL(true);
	}
	
	/**
	 * Generates the beginning of the insert query in SQL syntax:<br />
	 * <code>INSERT INTO (<i>column names</i>) VALUES</code><br />
	 * The {@link Column}s will be inserted in the order of their declaration in the corresponding
	 * enumeration.
	 * @param putID
	 *        <code>true</code> if the {@link Column#isID() ID column} should be present in the
	 *        query.
	 * @return the query ready for the entity specific data.
	 */
	public String generateInsertQuerySQL (final boolean putID) {
		final StringBuilder columnsNames = new StringBuilder();
		
		for (final Column currentColumn : getEntityColumns().values()) {
			if (!putID && currentColumn.isID()) {
				continue;
			}
			// Adding a comma between fields
			if (columnsNames.length() > 0) {
				columnsNames.append(",");
			}
			columnsNames.append("`");
			columnsNames.append(currentColumn.getName());
			columnsNames.append("`");
		}
		
		return "INSERT INTO " + getEntityName() + "(" + columnsNames.toString() + ") VALUES ";
	}
	
	/**
	 * Generates a string with the delete statement for a SQL database.<br />
	 * <code>DELETE FROM entity WHERE idColumn = value</code>
	 * @return the query which delete an object from the table.
	 * @throws NoIdException
	 *         if the query could not be generated.
	 */
	public String generateDeleteQuerySQL () throws NoIdException {
		return "DELETE FROM " + getEntityName() + generateWhereIDQuerySQL(getID());
	}
	
	/**
	 * Generates the beginning of the update query of the entity. <code>UPDATE entity SET </code>
	 * @return the query ready for adding the entity specific data.
	 */
	public String generateUpdateQuerySQL () {
		return "UPDATE " + getEntityName() + " SET ";
	}
	
	/**
	 * Generates the 'WHERE' part of a query which identifies it by its id.<br />
	 * <code> WHERE idColumn = value</code>
	 * @return the WHERE clause of a query.
	 * @throws NoIdException
	 *         if the query could not be generated.
	 */
	public String generateWhereIDQuerySQL () throws NoIdException {
		return generateWhereIDQuerySQL(getID());
	}
	
	/**
	 * Generates the 'WHERE' part of a query which identifies it by its id.<br />
	 * <code> WHERE idColumn = value</code>
	 * @param id
	 *        the id of the object to find.
	 * @return the WHERE clause of a query.
	 * @throws NoIdException
	 *         if the query could not be generated.
	 */
	public String generateWhereIDQuerySQL (final Object id) throws NoIdException {
		String fieldValue = " = ";
		if (getIDColumn().getType().getSuperclass().equals(Number.class)) {
			fieldValue += id.toString();
		} else {
			fieldValue += "'" + id.toString() + "'";
		}
		return " WHERE " + getIDColumn().getName() + fieldValue;
	}
	
	/**
	 * Generates the 'SELECT *' query for the current object.<br />
	 * <code>SELECT * FROM entity</code>
	 * @return the SQL query for retrieving all the rows from an entity.
	 */
	public String generateSearchAllQuerySQL () {
		return "SELECT * FROM " + getEntityName();
	}
}
