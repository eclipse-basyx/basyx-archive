package org.eclipse.basyx.testsuite.regression.vab.modelprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Snippet to test create and delete functionality of an IModelProvider
 * 
 * @author kuhn, schnicke, espen
 *
 */
public class MapCreateDelete {
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		testCreateElements(connVABElement);
		testDeleteElements(connVABElement);
	}

	@SuppressWarnings("unchecked")
	private static void testCreateElements(VABElementProxy connVABElement) {
		// Create property directly in root element
		connVABElement.createValue("inRoot", 1.2);
		Object toTest = connVABElement.getModelPropertyValue("inRoot");
		assertEquals(1.2, toTest);

		// Create element in Map (with new key contained in the path)
		connVABElement.createValue("/structure/map/inMap", "34");
		toTest = connVABElement.getModelPropertyValue("/structure/map/inMap");
		assertEquals("34", toTest);

		// Create map element
		HashMap<String, Object> newMap = new HashMap<>();
		newMap.put("entryA", 3);
		newMap.put("entryB", 4);
		connVABElement.createValue("mapInRoot", newMap);
		toTest = connVABElement.getModelPropertyValue("mapInRoot");
		assertTrue(toTest instanceof Map<?, ?>);
		assertEquals(2, ((Map<String, Object>) toTest).size());
		assertEquals(3, ((Map<String, Object>) toTest).get("entryA"));

		// Try to overwrite existing element (should be ignored, already exists)
		connVABElement.createValue("inRoot", 0);
		toTest = connVABElement.getModelPropertyValue("inRoot");
		assertEquals(1.2, toTest);

		// Check case-sensitivity
		connVABElement.createValue("inroot", 78);
		toTest = connVABElement.getModelPropertyValue("inRoot");
		assertEquals(1.2, toTest);
		toTest = connVABElement.getModelPropertyValue("inroot");
		assertEquals(78, toTest);

		// Non-existing parent element
		connVABElement.createValue("unkown/x", 5);
		toTest = connVABElement.getModelPropertyValue("unknown/x");
		assertNull(toTest);

		// Empty paths - should execute, but has no effect
		connVABElement.createValue("", "");
		toTest = connVABElement.getModelPropertyValue("");
		assertNotEquals("", toTest);

		// Null path - should throw exception
		try {
			connVABElement.createValue(null, "");
			fail();
		} catch (ServerException e) {
		}
	}

	private static void testDeleteElements(VABElementProxy connVABElement) {
		// Delete at Root
		// - by object - should not work, root is a map
		connVABElement.deleteValue("inRoot", 1.2);
		Object toTest = connVABElement.getModelPropertyValue("inRoot");
		assertEquals(1.2, toTest);
		// - by index
		connVABElement.deleteValue("inRoot");
		toTest = connVABElement.getModelPropertyValue("inRoot");
		assertNull(toTest);

		// Check case-sensitivity
		toTest = connVABElement.getModelPropertyValue("inroot");
		assertEquals(78, toTest);
		connVABElement.deleteValue("inroot");
		toTest = connVABElement.getModelPropertyValue("inroot");
		assertNull(toTest);

		// Delete at Map
		// - by object - should not work in maps, because object refers to a contained object, not the index
		connVABElement.deleteValue("/structure/map/", "inMap");
		toTest = connVABElement.getModelPropertyValue("/structure/map/inMap");
		assertEquals("34", toTest);
		// - by index
		connVABElement.deleteValue("/structure/map/inMap");
		toTest = connVABElement.getModelPropertyValue("/structure/map");
		assertEquals(0, ((Map<?, ?>) toTest).size());

		// Delete remaining complete Map
		connVABElement.deleteValue("mapInRoot");
		toTest = connVABElement.getModelPropertyValue("mapInRoot");
		assertNull(toTest);

		// Empty paths - should not delete anything
		connVABElement.deleteValue("", "");
		toTest = connVABElement.getModelPropertyValue("/primitives/integer");
		assertEquals(123, toTest);

		// Null path - should throw exception
		try {
			connVABElement.deleteValue(null, "");
			fail();
		} catch (ServerException e) {
		}
		try {
			connVABElement.deleteValue(null);
			fail();
		} catch (ServerException e) {
		}
	}
}
