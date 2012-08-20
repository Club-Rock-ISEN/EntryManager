package org.clubrockisen.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.clubrockisen.service.Configuration;
import org.junit.Test;

/**
 * Test suite for the {@link MySQLConnection} class.
 * @author Alex
 */
public class MySQLConnectionTest {
	
	/** The connection to the database */
	private Connection	connection;
	
	/**
	 * Test method for {@link org.clubrockisen.connection.MySQLConnection#getInstance()}.<br />
	 * Check that the database respond within one second.
	 */
	@Test
	public void testGetInstance () {
		Configuration.setFile(null);
		connection = MySQLConnection.getInstance();
		try {
			assertEquals(true, connection.isValid(1));
			assertEquals(false, connection.isClosed());
		} catch (final SQLException e) {
			fail("Problem while checking database connection: " + e.getMessage());
		}
		MySQLConnection.close();
	}
	
	/**
	 * Test the behavior with a bad configuration file.
	 * @throws SQLException
	 *         expected to happen.
	 */
	@Test(expected = SQLConfigurationException.class)
	public void testConnectionFailed () throws SQLException {
		Configuration.setFile("test/wrongConf.xml");
		connection = MySQLConnection.getInstance();
		connection.isValid(1);
		MySQLConnection.close();
	}
	
	/**
	 * Test method for {@link org.clubrockisen.connection.MySQLConnection#close()}.
	 */
	@Test
	public void testClose () {
		Configuration.setFile(null);
		MySQLConnection.close();
		connection = MySQLConnection.getInstance();
		MySQLConnection.close();
	}
}
