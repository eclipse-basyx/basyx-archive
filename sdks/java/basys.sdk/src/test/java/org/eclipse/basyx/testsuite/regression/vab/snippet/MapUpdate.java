package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

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
		connVABElement.updateElementValue("primitives/integer", 12);
		connVABElement.updateElementValue("primitives/double", 1.2d);
		connVABElement.updateElementValue("primitives/string", "updated");
		// Read back
		Object integer = connVABElement.readElementValue("primitives/integer");
		Object doubleValue = connVABElement.readElementValue("primitives/double");
		Object string = connVABElement.readElementValue("primitives/string");
		// Test
		assertTrue(integer instanceof Integer);
		assertEquals(12, integer);
		assertTrue(doubleValue instanceof Double);
		assertEquals(1.2d, doubleValue);
		assertTrue(string instanceof String);
		assertEquals("updated", string);
		// Revert
		connVABElement.updateElementValue("primitives/integer", 123);
		connVABElement.updateElementValue("primitives/double", 3.14d);
		connVABElement.updateElementValue("primitives/string", "TestValue");

		// Update serializable function
		connVABElement.updateElementValue("operations/serializable",
				(Function<Object[], Object> & Serializable) (param) -> {
					return (int) param[0] - (int) param[1];
				});
		// Read back
		Object serializableFunction = connVABElement.readElementValue("operations/serializable");
		// Test
		Function<Object[], Object> testFunction = (Function<Object[], Object>) serializableFunction;
		assertEquals(-1, testFunction.apply(new Object[] { 2, 3 }));
		// Revert
		connVABElement.updateElementValue("functions/serializable",
				(Function<Object[], Object> & Serializable) (param) -> {
					return (int) param[0] + (int) param[1];
				});

		// Test non-existing parent element
		connVABElement.createElement("unkown/newElement", 5);
		Object nonExisting = connVABElement.readElementValue("unknown/newElement");
		assertNull(nonExisting);

		// Test updating a non-existing element
		connVABElement.updateElementValue("newElement", 10);
		nonExisting = connVABElement.readElementValue("newElement");
		assertEquals(null, nonExisting);

		// Null path - should throw exception
		try {
			connVABElement.updateElementValue(null, "");
			fail();
		} catch (ServerException e) {
		}
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
			connVABElement.updateElementValue(null, newMap);
			fail();
		} catch (ServerException e) {
		}

		// Push whole map via ""-Path
		// - create object
		HashMap<String, Object> newMap2 = new HashMap<>();
		newMap2.put("testKey2", "testValue2");
		// - push
		connVABElement.updateElementValue("", newMap2);
		// - test
		assertEquals("testValue2", connVABElement.readElementValue("testKey2"));
		assertNull(connVABElement.readElementValue("testKey"));
		assertNull(connVABElement.readElementValue("primitives/integer"));
	}
}
