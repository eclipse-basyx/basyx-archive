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
 * @author kuhn, schnicke
 *
 */
public class GetPropertyValue {
	public static void test(VABConnectionManager connManager) {

		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Get property value
		Object value1 = connVABElement.readElementValue("property1");
		Object value2 = connVABElement.readElementValue("property1/property1.1");

		// Check test case results
		assertTrue(value1 instanceof HashMap);
		assertEquals(4, ((Map<?, ?>) value1).size());
		assertTrue(value2 instanceof Integer);
		assertEquals(7, value2);
	}

}
