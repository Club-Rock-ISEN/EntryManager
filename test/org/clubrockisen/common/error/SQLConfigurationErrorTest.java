package org.clubrockisen.common.error;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;

import org.junit.Test;

/**
 * Test suite for the {@link SQLConfigurationError} class.
 * @author Alex
 */
public class SQLConfigurationErrorTest {
	
	/** Exception with no details */
	private final SQLConfigurationError unknownSQLError = new SQLConfigurationError();
	/** Exception with details */
	private final SQLConfigurationError knownSQLError = new SQLConfigurationError("Bad url");
	/** Exception with details and cause */
	private final SQLConfigurationError knownCauseSQLError = new SQLConfigurationError("Wrong password", new SQLException());
	
	/**
	 * Test method for {@link java.lang.Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertNull(unknownSQLError.getMessage());
		assertEquals("Bad url", knownSQLError.getMessage());
		assertEquals("Wrong password", knownCauseSQLError.getMessage());
	}
	
	
	/**
	 * Test method for {@link java.lang.Throwable#getCause()}.
	 */
	@Test
	public void testGetCause () {
		assertNull(unknownSQLError.getCause());
		assertNull(knownSQLError.getCause());
		assertEquals(SQLException.class, knownCauseSQLError.getCause().getClass());
	}
	
}
