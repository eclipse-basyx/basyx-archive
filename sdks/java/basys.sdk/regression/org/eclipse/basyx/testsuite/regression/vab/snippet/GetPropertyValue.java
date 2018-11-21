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
		Map<String, Object>   value1 = (Map<String, Object>)  connVABElement.readElementValue("property1");
		Map<String, Object>   value2 = (Map<String, Object>)  connVABElement.readElementValue("property1/property1.1");
		
		// Check test case results
		assertTrue(value1 instanceof HashMap);
		assertTrue(value2 instanceof HashMap);
		
		assertEquals(4, value1.size());
		
		
		int val2 	  = (int) value2.get("value");
		int val2_type = (int) value2.get("type");
		
		assertEquals(7, val2);
		System.out.println(val2_type);
		
	}
	
}
