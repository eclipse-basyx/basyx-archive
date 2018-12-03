package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Snippet to test create and delete functionality of a IModelProvider
 * 
 * @author kuhn, schnicke
 *
 */
public class CreateDelete {
	@SuppressWarnings("unchecked")
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Get property value
		Object value1 = connVABElement.readElementValue("property1");
		Object value2 = connVABElement.readElementValue("property1/property1.1");

		// Check test case results
		assertTrue(value1 instanceof HashMap);
		assertTrue(value2 instanceof Integer);
		assertEquals(7, value2);

		// Create new properties
		// - Create property directly in VAB element
		connVABElement.createElement("property2", 21);
		// - Create property in contained hashmap
		connVABElement.createElement("property1/property1.4", 22);
		// - Create property in collection in contained hashmap
		connVABElement.createElement("property1/property1.2", 23);

		// Read values back
		Object value3 = connVABElement.readElementValue("property2");
		// - Check test case results
		assertTrue(value3 instanceof Integer);
		assertEquals(21, value3);

		// Read values back
		Object value4 = connVABElement.readElementValue("property1/property1.4");
		// - Check test case results
		assertTrue(value4 instanceof Integer);
		assertEquals(22, value4);

		// Read values back
		Object value5 = connVABElement.readElementValue("property1/property1.2");
		// - Check test case results
		assertTrue(value5 instanceof Collection);
		Collection<Object> value5Collection = (Collection<Object>) ((Collection<?>) value5);
		assertEquals(3, value5Collection.size());

		// Delete properties
		connVABElement.deleteElement("property2");
		connVABElement.deleteElement("property1/property1.4");
		connVABElement.deleteElement("property1/property1.2", 23);

		// Read values back
		Object value6 = connVABElement.readElementValue("property2");
		// - Check test case results
		assertEquals(null, value6);

		// Read values back
		Object value7 = connVABElement.readElementValue("property1/property1.4");
		// - Check test case results
		assertEquals(null, value7);

		// Read values back
		Object value8 = connVABElement.readElementValue("property1/property1.2");
		// - Check test case results
		assertTrue(value8 instanceof Collection);
		assertEquals(2, ((Collection<?>) value8).size());
	}
}
