package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Snippet to test get functionality of a IModelProvider
 * 
 * @author kuhn, schnicke, espen
 *
 */
public class GetPropertyValue {
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Get property value
		Object rootValue = connVABElement.readElementValue("");
		Object value1 = connVABElement.readElementValue("property1");
		Object value2 = connVABElement.readElementValue("property1/property1.1");
		Object value4 = connVABElement.readElementValue("property1/propertyMap/Test");
		Object value5 = connVABElement.readElementValue("property1/propertyMap/test");
		Object value6 = connVABElement.readElementValue("unknown");
		Object value7 = connVABElement.readElementValue("/property1/property1.1");
		Object value8 = connVABElement.readElementValue("property1/property1.1/");
		Object value9 = connVABElement.readElementValue("/property1/property1.1/");

		// Check test case results
		assertTrue(rootValue instanceof HashMap);
		assertEquals(2, ((Map<?, ?>) rootValue).size());
		assertTrue(value1 instanceof HashMap);
		assertEquals(4, ((Map<?, ?>) value1).size());
		assertTrue(value2 instanceof Integer);
		assertEquals(7, value2);
		assertTrue(value4 instanceof Integer);
		assertEquals(321, value4);
		assertTrue(value5 instanceof Integer);
		assertEquals(123, value5);
		assertEquals(null, value6);
		assertTrue(value7 instanceof Integer);
		assertEquals(7, value7);
		assertTrue(value8 instanceof Integer);
		assertEquals(7, value8);
		assertTrue(value9 instanceof Integer);
		assertEquals(7, value9);

		// parent element unknown
		assertEquals(null, connVABElement.readElementValue("unknown/x"));
	}
}
