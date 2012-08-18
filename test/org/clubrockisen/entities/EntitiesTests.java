package org.clubrockisen.entities;

import org.clubrockisen.entities.enums.EntitiesEnumsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the package org.clubrockisen.entities
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ EntitiesEnumsTests.class, ColumnTest.class, NoIdExceptionTest.class,
	EntryMemberPartyTest.class })
public class EntitiesTests {
}
