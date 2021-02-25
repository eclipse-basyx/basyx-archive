/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.testsuite.regression.vab.modelprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Function;

import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Snippet to test set functionality of a IModelProvider
 * 
 * @author kuhn, schnicke
 *
 */
public class MapUpdate {
	
	@SuppressWarnings("unchecked")
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		
		// Set primitives
		connVABElement.setModelPropertyValue("primitives/integer", 12);
		connVABElement.setModelPropertyValue("primitives/double", 1.2d);
		connVABElement.setModelPropertyValue("primitives/string", "updated");
		// Read back
		Object integer = connVABElement.getModelPropertyValue("primitives/integer");
		Object doubleValue = connVABElement.getModelPropertyValue("primitives/double");
		Object string = connVABElement.getModelPropertyValue("primitives/string");
		// Test
		assertTrue(integer instanceof Integer);
		assertEquals(12, integer);
		assertTrue(doubleValue instanceof Double);
		assertEquals(1.2d, doubleValue);
		assertTrue(string instanceof String);
		assertEquals("updated", string);
		// Revert
		connVABElement.setModelPropertyValue("primitives/integer", 123);
		connVABElement.setModelPropertyValue("primitives/double", 3.14d);
		connVABElement.setModelPropertyValue("primitives/string", "TestValue");
		
		// Update serializable function
		connVABElement.setModelPropertyValue("operations/serializable",
				(Function<Object[], Object> & Serializable) (param) -> {
					return (int) param[0] - (int) param[1];
				});
		// Read back
		Object serializableFunction = connVABElement.getModelPropertyValue("operations/serializable");
		// Test
		Function<Object[], Object> testFunction = (Function<Object[], Object>) serializableFunction;
		assertEquals(-1, testFunction.apply(new Object[] { 2, 3 }));
		// Revert
		connVABElement.setModelPropertyValue("operations/serializable",
				(Function<Object[], Object> & Serializable) (param) -> {
					return (int) param[0] + (int) param[1];
				});
		
		// Test non-existing parent element
		try {
			connVABElement.createValue("unkown/newElement", 5);
			fail();
		} catch (ResourceNotFoundException e) {}
		try {
			connVABElement.getModelPropertyValue("unknown/newElement");
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Test updating a non-existing element
		try {
			connVABElement.setModelPropertyValue("newElement", 10);
			fail();
		} catch (ResourceNotFoundException e) {}
		try {
			connVABElement.getModelPropertyValue("newElement");
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Test updating an existing null-element
		connVABElement.setModelPropertyValue("special/null", true);
		Object bool = connVABElement.getModelPropertyValue("special/null");
		assertTrue((boolean) bool);

		// Null path - should throw exception
		try {
			connVABElement.setModelPropertyValue(null, "");
			fail();
		} catch (MalformedRequestException e) {}
	}

	public static void testPushAll(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
	
		// Push whole map via null-Path - should throw exception
		// - create object
		HashMap<String, Object> newMap = new HashMap<>();
		newMap.put("testKey", "testValue");
		// - push
		try {
			connVABElement.setModelPropertyValue(null, newMap);
			fail();
		} catch (MalformedRequestException e) {}
	
		// Push whole map via ""-Path
		// - create object
		HashMap<String, Object> newMap2 = new HashMap<>();
		newMap2.put("testKey2", "testValue2");
		// - push
		connVABElement.setModelPropertyValue("", newMap2);
		// - test
		assertEquals("testValue2", connVABElement.getModelPropertyValue("testKey2"));
		try {
			connVABElement.getModelPropertyValue("testKey");
			fail();
		} catch (ResourceNotFoundException e) {}
		try {
			connVABElement.getModelPropertyValue("primitives/integer");
			fail();
		} catch (ResourceNotFoundException e) {}
	}
}
