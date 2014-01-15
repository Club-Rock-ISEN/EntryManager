package org.clubrockisen.common.error;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test suite for the {@link ServiceInstantiationError} class.
 * @author Alex
 */
public class ServiceInstantiationErrorTest {
	
	/** Exception with an unknown service */
	private final ServiceInstantiationError unknownService = new ServiceInstantiationError();
	/** Exception with a known service class */
	private final ServiceInstantiationError knownService = new ServiceInstantiationError("org.clubrockisen.service.impl");
	
	/**
	 * Test method for {@link org.clubrockisen.common.error.ServiceInstantiationError#getServiceFactory()}.
	 */
	@Test
	public void testGetServiceFactory () {
		assertNull(unknownService.getServiceFactory());
		assertEquals("org.clubrockisen.service.impl", knownService.getServiceFactory());
	}
	
	/**
	 * Test method for {@link java.lang.Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertEquals("The service factory class null is not implemented.", unknownService.getMessage());
		assertEquals("The service factory class org.clubrockisen.service.impl is not implemented.", knownService.getMessage());
	}
}
