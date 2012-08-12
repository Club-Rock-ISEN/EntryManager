package org.clubrockisen.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link MySQLConnection} class.
 * @author Alex
 */
public class MySQLConnectionTest {
	
	/** The connection to the database */
	private Connection	connection;
	
	/**
	 * Initialize the connection to the database.
	 */
	@Before
	public void setUp () {
		connection = MySQLConnection.getInstance();
	}
	
	/**
	 * Test method for {@link org.clubrockisen.connection.MySQLConnection#getInstance()}.<br />
	 * Check that the database respond within one second.
	 */
	@Test
	public void testGetInstance () {
		try {
			connection.isValid(1);
			assertEquals(connection.isClosed(), false);
		} catch (final SQLException e) {
			fail("Problem while checking database connection: " + e.getMessage());
		}
	}
}
