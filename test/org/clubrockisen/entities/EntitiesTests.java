package org.clubrockisen.entities;

import org.clubrockisen.entities.enums.EnumsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the package org.clubrockisen.entities
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ EnumsTests.class, ColumnTest.class, NoIdExceptionTest.class,
	EntryMemberPartyTest.class, ParameterTest.class, MemberTest.class, PartyTest.class })
public class EntitiesTests {
}
