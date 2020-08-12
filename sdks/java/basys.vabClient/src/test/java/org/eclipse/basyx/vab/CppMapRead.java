package org.eclipse.basyx.vab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Snippet to test get functionality of a IModelProvider.
 * Based on the MapRead tests within the SDK, but removes
 * Java-specific local tests.
 * 
 * @author espen
 *
 */
public class CppMapRead {
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		
		// Test path access
		Object slashA = connVABElement.getModelPropertyValue("/primitives/integer");
		Object slashB = connVABElement.getModelPropertyValue("primitives/integer/");
		Object slashC = connVABElement.getModelPropertyValue("/primitives/integer/");
		Object slashD = connVABElement.getModelPropertyValue("/primitives/integer/");
		assertEquals(slashA, 123);
		assertEquals(slashB, 123);
		assertEquals(slashC, 123);
		assertEquals(slashD, 123);
		
		// Test reading different data types
		Object map = connVABElement.getModelPropertyValue("primitives");
		Object doubleValue = connVABElement.getModelPropertyValue("primitives/double");
		Object string = connVABElement.getModelPropertyValue("primitives/string");
		assertEquals(3, ((Map<?, ?>) map).size());
		assertEquals(3.14d, doubleValue);
		assertEquals("TestValue", string);
		
		// Test case sensitivity
		Object caseSensitiveA = connVABElement.getModelPropertyValue("special/casesensitivity");
		Object caseSensitiveB = connVABElement.getModelPropertyValue("special/caseSensitivity");
		assertEquals(true, caseSensitiveA);
		assertEquals(false, caseSensitiveB);
		
		// Test reading null value
		Object nullValue = connVABElement.getModelPropertyValue("special/null");
		assertNull(nullValue);
		
		// Non-existing parent element
		try {
			connVABElement.getModelPropertyValue("unknown/x");
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Non-existing target element
		try {
			connVABElement.getModelPropertyValue("primitives/unkown");
			fail();
		} catch (ResourceNotFoundException e) {}
		try {
			connVABElement.getModelPropertyValue("unkown");
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Nested access
		assertEquals(100, connVABElement.getModelPropertyValue("special/nested/nested/value"));
		
		// Empty path
		Object rootValueA = connVABElement.getModelPropertyValue("");
		Object rootValueB = connVABElement.getModelPropertyValue("/");
		assertEquals(4, ((Map<?, ?>) rootValueA).size());
		assertEquals(4, ((Map<?, ?>) rootValueB).size());
		
		// Null path - should throw exception
		try {
			connVABElement.getModelPropertyValue(null);
			fail();
		} catch (MalformedRequestException e) {}
	}
}
