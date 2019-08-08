package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

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
		Object slashA = connVABElement.readElementValue("/primitives/integer");
		Object slashB = connVABElement.readElementValue("primitives/integer/");
		Object slashC = connVABElement.readElementValue("/primitives/integer/");
		Object slashD = connVABElement.readElementValue("/primitives/integer/");
		assertEquals(slashA, 123);
		assertEquals(slashB, 123);
		assertEquals(slashC, 123);
		assertEquals(slashD, 123);

		// Test reading different data types
		Object map = connVABElement.readElementValue("primitives");
		Object doubleValue = connVABElement.readElementValue("primitives/double");
		Object string = connVABElement.readElementValue("primitives/string");
		assertEquals(3, ((Map<?, ?>) map).size());
		assertEquals(3.14d, doubleValue);
		assertEquals("TestValue", string);

		// Test case sensitivity
		Object caseSensitiveA = connVABElement.readElementValue("special/casesensitivity");
		Object caseSensitiveB = connVABElement.readElementValue("special/caseSensitivity");
		assertEquals(true, caseSensitiveA);
		assertEquals(false, caseSensitiveB);

		// Test reading null value
		Object nullValue = connVABElement.readElementValue("special/null");
		assertNull(nullValue);

		// Test reading serializable functions
		Object serializableFunction = connVABElement.readElementValue("operations/serializable");
		Function<Object[], Object> testFunction = (Function<Object[], Object>) serializableFunction;
		assertEquals(3, testFunction.apply(new Object[] { 1, 2 }));

		// Non-existing parent element
		assertNull(connVABElement.readElementValue("unknown/x"));

		// Non-existing target element
		assertNull(connVABElement.readElementValue("primitives/unkown"));
		assertNull(connVABElement.readElementValue("unkown"));

		// Nested access
		assertEquals(100, connVABElement.readElementValue("special/nested/nested/value"));

		// Empty path
		Object rootValueA = connVABElement.readElementValue("");
		Object rootValueB = connVABElement.readElementValue("/");
		assertEquals(4, ((Map<?, ?>) rootValueA).size());
		assertEquals(4, ((Map<?, ?>) rootValueB).size());

		// Null path - should throw exception
		try {
			connVABElement.readElementValue(null);
			fail();
		} catch (ServerException e) {
		}
	}
}
