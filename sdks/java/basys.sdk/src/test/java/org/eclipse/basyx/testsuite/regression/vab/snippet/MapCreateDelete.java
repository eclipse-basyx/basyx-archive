package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

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
		connVABElement.createElement("inRoot", 1.2);
		Object toTest = connVABElement.readElementValue("inRoot");
		assertEquals(1.2, toTest);

		// Create element in Map (with new key contained in the path)
		connVABElement.createElement("/structure/map/inMap", "34");
		toTest = connVABElement.readElementValue("/structure/map/inMap");
		assertEquals("34", toTest);

		// Create map element
		HashMap<String, Object> newMap = new HashMap<>();
		newMap.put("entryA", 3);
		newMap.put("entryB", 4);
		connVABElement.createElement("mapInRoot", newMap);
		toTest = connVABElement.readElementValue("mapInRoot");
		assertTrue(toTest instanceof Map<?, ?>);
		assertEquals(2, ((Map<String, Object>) toTest).size());
		assertEquals(3, ((Map<String, Object>) toTest).get("entryA"));

		// Try to overwrite existing element (should be ignored, already exists)
		connVABElement.createElement("inRoot", 0);
		toTest = connVABElement.readElementValue("inRoot");
		assertEquals(1.2, toTest);

		// Check case-sensitivity
		connVABElement.createElement("inroot", 78);
		toTest = connVABElement.readElementValue("inRoot");
		assertEquals(1.2, toTest);
		toTest = connVABElement.readElementValue("inroot");
		assertEquals(78, toTest);

		// Non-existing parent element
		connVABElement.createElement("unkown/x", 5);
		toTest = connVABElement.readElementValue("unknown/x");
		assertNull(toTest);

		// Empty paths - should execute, but has no effect
		connVABElement.createElement("", "");
		toTest = connVABElement.readElementValue("");
		assertNotEquals("", toTest);

		// Null path - should throw exception
		try {
			connVABElement.createElement(null, "");
			fail();
		} catch (ServerException e) {
		}
	}

	private static void testDeleteElements(VABElementProxy connVABElement) {
		// Delete at Root
		// - by object - should not work, root is a map
		connVABElement.deleteElement("inRoot", 1.2);
		Object toTest = connVABElement.readElementValue("inRoot");
		assertEquals(1.2, toTest);
		// - by index
		connVABElement.deleteElement("inRoot");
		toTest = connVABElement.readElementValue("inRoot");
		assertNull(toTest);

		// Check case-sensitivity
		toTest = connVABElement.readElementValue("inroot");
		assertEquals(78, toTest);
		connVABElement.deleteElement("inroot");
		toTest = connVABElement.readElementValue("inroot");
		assertNull(toTest);

		// Delete at Map
		// - by object - should not work in maps, because object refers to a contained object, not the index
		connVABElement.deleteElement("/structure/map/", "inMap");
		toTest = connVABElement.readElementValue("/structure/map/inMap");
		assertEquals("34", toTest);
		// - by index
		connVABElement.deleteElement("/structure/map/inMap");
		toTest = connVABElement.readElementValue("/structure/map");
		assertEquals(0, ((Map<?, ?>) toTest).size());

		// Delete remaining complete Map
		connVABElement.deleteElement("mapInRoot");
		toTest = connVABElement.readElementValue("mapInRoot");
		assertNull(toTest);

		// Empty paths - should not delete anything
		connVABElement.deleteElement("", "");
		toTest = connVABElement.readElementValue("/primitives/integer");
		assertEquals(123, toTest);

		// Null path - should throw exception
		try {
			connVABElement.deleteElement(null, "");
			fail();
		} catch (ServerException e) {
		}
		try {
			connVABElement.deleteElement(null);
			fail();
		} catch (ServerException e) {
		}
	}
}
