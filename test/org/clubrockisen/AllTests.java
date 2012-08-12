package org.clubrockisen;

import org.clubrockisen.connection.ConnectionTests;
import org.clubrockisen.entities.EntitiesTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All test suite for the Club Rock ISEN application.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ConnectionTests.class, EntitiesTests.class})
public class AllTests {
}
