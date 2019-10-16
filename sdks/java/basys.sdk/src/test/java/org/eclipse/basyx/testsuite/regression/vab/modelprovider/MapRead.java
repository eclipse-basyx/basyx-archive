package org.eclipse.basyx.testsuite.regression.vab.modelprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Snippet to test get functionality of a IModelProvider
 * 
 * @author kuhn, schnicke, espen
 *
 */
public class MapRead {
	@SuppressWarnings("unchecked")
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

		// Test reading serializable functions
		Object serializableFunction = connVABElement.getModelPropertyValue("operations/serializable");
		Function<Object[], Object> testFunction = (Function<Object[], Object>) serializableFunction;
		assertEquals(3, testFunction.apply(new Object[] { 1, 2 }));

		// Non-existing parent element
		assertNull(connVABElement.getModelPropertyValue("unknown/x"));

		// Non-existing target element
		assertNull(connVABElement.getModelPropertyValue("primitives/unkown"));
		assertNull(connVABElement.getModelPropertyValue("unkown"));

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
		} catch (ServerException e) {
		}
	}
}
