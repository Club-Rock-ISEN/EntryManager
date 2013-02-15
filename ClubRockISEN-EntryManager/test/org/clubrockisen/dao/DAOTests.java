package org.clubrockisen.dao;

import org.clubrockisen.dao.mysql.MySQLTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the package org.clubrockisen.dao
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({MySQLTest.class})
public class DAOTests {
}
