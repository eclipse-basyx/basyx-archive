package org.eclipse.basyx.vab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Snippet to test set functionality of a IModelProvider.
 * Based on the MapUpdate tests within the SDK, but removes
 * Java-specific local tests.
 * 
 * @author espen
 *
 */
public class CppMapUpdate {
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
		} catch (ResourceNotFoundException e) {
		}
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
}
