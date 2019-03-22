/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Tests a IModelProvider's capability to handle maps
 * 
 * @author schnicke
 */
public class TestMapProperty {
	private static String mapPath = "property1/propertyMap";

	@SuppressWarnings("unchecked")
	public static void testGet(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Read values
		Map<String, Object> map = (Map<String, Object>) connVABElement.readElementValue(mapPath);

		// Check test case results
		assertEquals(1, map.size());
	}

	@SuppressWarnings("unchecked")
	public static void testUpdateComplete(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Save original
		Map<String, Object> original = (Map<String, Object>) connVABElement.readElementValue(mapPath);

		// Replace entries in map
		Map<String, Object> replacement = new HashMap<>();
		replacement.put("a", 1);
		replacement.put("b", 2);
		connVABElement.updateElementValue(mapPath, replacement);

		// Read values back
		Map<String, Object> map = (Map<String, Object>) connVABElement.readElementValue(mapPath);

		// Check test case results
		assertEquals(2, map.size());
		assertEquals(replacement, map);

		// Roll back map
		connVABElement.updateElementValue(mapPath, original);
	}

	@SuppressWarnings("unchecked")
	public static void testUpdateElement(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Create new entry in map
		connVABElement.createElement(mapPath + "/a", 2);

		// Read values back
		Map<String, Object> map = (Map<String, Object>) connVABElement.readElementValue(mapPath);

		// Check test case results
		assertEquals(2, map.size());
		assertTrue(map.containsKey("test"));
		assertTrue(map.containsKey("a"));

		// Remove entry again
		connVABElement.deleteElement(mapPath, "a");
	}

	/**
	 * @param connManager
	 */
	@SuppressWarnings("unchecked")
	public static void testRemoveElement(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Remove entry from map
		connVABElement.deleteElement(mapPath, "test");

		// Read values back
		Map<String, Object> map = (Map<String, Object>) connVABElement.readElementValue(mapPath);

		// Check test case results
		assertEquals(0, map.size());

		// Put entry back
		connVABElement.createElement(mapPath + "/test", 123);
	}
}
