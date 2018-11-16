package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;

/**
 * Snippet to test get functionality of a IModelProvider
 * 
 * @author kuhn, schnicke
 *
 */
public class GetPropertyValue {
	public static void test(VABConnectionManager connManager) {

		// Connect to VAB element with ID "SimpleVABElement"
		// - Retrieve connected AAS from AAS ID
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Get property value
		Object value1 = connVABElement.readElementValue("property1");
		Object value2 = connVABElement.readElementValue("property1/property1.1");
		
		// Check test case results
		assertTrue(value1 instanceof HashMap);
		assertTrue(value2 instanceof HashMap);
		
		// Can use hashmap provider to test deserialized response
		VABHashmapProvider value1_provider = new VABHashmapProvider((Map<String, Object>) value1);
		Map<?, ?> containedElements = (Map<?, ?>) value1_provider.getModelPropertyValue("entity");
		assertEquals(4, containedElements.size());
		
		// Can use hashmap provider to test deserialized response
		VABHashmapProvider value2_provider = new VABHashmapProvider((Map<String, Object>) value2);
		int val2 = (int) value2_provider.getModelPropertyValue("entity/value");
		assertEquals(7, val2);

		
	}
	
}
