package org.clubrockisen.dao.mysql;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the package org.clubrockisen.dao.mysql
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ MySQLParameterDAOTest.class, MySQLEntryMemberPartyDAOTest.class })
public class MySQLTest {
}
