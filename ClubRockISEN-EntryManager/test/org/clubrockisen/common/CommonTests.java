package org.clubrockisen.common;

import org.clubrockisen.common.error.ErrorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the org.clubrockisen.common
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ConfigurationTest.class, ErrorTest.class })
public class CommonTests {
}
