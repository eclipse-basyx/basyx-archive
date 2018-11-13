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
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		Map<String, Object> map = (Map<String, Object>) connVABElement.readElementValue(mapPath);
		assertEquals(1, map.size());
	}

	@SuppressWarnings("unchecked")
	public static void testUpdateComplete(VABConnectionManager connManager) {
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		Map<String, Object> replacement = new HashMap<>();
		replacement.put("a", 1);
		replacement.put("b", 2);
		connVABElement.updateElementValue(mapPath, replacement);

		Map<String, Object> map = (Map<String, Object>) connVABElement.readElementValue(mapPath);
		assertEquals(2, map.size());
		assertEquals(replacement, map);
	}

	@SuppressWarnings("unchecked")
	public static void testUpdateElement(VABConnectionManager connManager) {
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		connVABElement.createElement(mapPath + "/a", 2);

		Map<String, Object> map = (Map<String, Object>) connVABElement.readElementValue(mapPath);
		assertEquals(2, map.size());
		assertTrue(map.containsKey("test"));
		assertTrue(map.containsKey("a"));
	}

	/**
	 * @param connManager
	 */
	@SuppressWarnings("unchecked")
	public static void testRemoveElement(VABConnectionManager connManager) {
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		connVABElement.deleteElement(mapPath, "test");

		Map<String, Object> map = (Map<String, Object>) connVABElement.readElementValue(mapPath);
		assertEquals(0, map.size());
	}
}
