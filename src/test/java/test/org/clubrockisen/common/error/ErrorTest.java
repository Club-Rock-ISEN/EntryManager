package org.clubrockisen.common.error;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the org.clubrockisen.common.error
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ServiceInstantiationErrorTest.class, SQLConfigurationErrorTest.class })
public class ErrorTest {
}
