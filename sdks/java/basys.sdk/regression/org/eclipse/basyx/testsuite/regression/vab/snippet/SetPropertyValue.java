package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Snippet to test set functionality of a IModelProvider
 * 
 * @author kuhn, schnicke
 *
 */
public class SetPropertyValue {
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Set and reread property value
		connVABElement.updateElementValue("property1/property1.1", 12);
		connVABElement.updateElementValue("property1/Property1.1", 100);
		Object value3 = connVABElement.readElementValue("property1/property1.1");
		Object value4 = connVABElement.readElementValue("property1/Property1.1");

		// Check test case results
		assertTrue(value3 instanceof Integer);
		assertEquals(12, value3);
		assertTrue(value4 instanceof Integer);
		assertEquals(100, value4);

		// Change values back
		connVABElement.updateElementValue("property1/property1.1", 7);
		connVABElement.deleteElement("property1/Property1.1");
		Object value5 = connVABElement.readElementValue("property1/property1.1");
		Object value6 = connVABElement.readElementValue("property1/Property1.1");

		// Check test case results
		assertTrue(value5 instanceof Integer);
		assertEquals(7, value5);
		assertEquals(null, value6);
	}
}
