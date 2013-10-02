package org.clubrockisen;

import org.clubrockisen.common.CommonTests;
import org.clubrockisen.entities.EntitiesTests;
import org.clubrockisen.service.ServiceTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All test suite for the Club Rock ISEN application.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({CommonTests.class, EntitiesTests.class, ServiceTests.class})
public class AllTests {
}
