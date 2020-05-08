package org.eclipse.basyx.testsuite.regression.vab.modelprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ResourceAlreadyExistsException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
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
		
		// Try to overwrite existing element (should throw Exception, already exists)
		try {
			connVABElement.createValue("inRoot", 0);
			fail();
		} catch (MalformedRequestException e) {
			// If inRoot would have been a list 0 could be added here
			// => 1.2 has an "invalid" type for creating values in it
		}
		toTest = connVABElement.getModelPropertyValue("inRoot");
		assertEquals(1.2, toTest);
		
		// Check case-sensitivity
		connVABElement.createValue("inroot", 78);
		toTest = connVABElement.getModelPropertyValue("inRoot");
		assertEquals(1.2, toTest);
		toTest = connVABElement.getModelPropertyValue("inroot");
		assertEquals(78, toTest);
		
		// Non-existing parent element
		try {
			connVABElement.createValue("unkown/x", 5);
			fail();
		} catch (ResourceNotFoundException e) {}
		try {
			connVABElement.getModelPropertyValue("unknown/x");
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Empty paths - at "" is a Map. Therefore create should throw an Exception
		try {
			connVABElement.createValue("", "");
			fail();
		} catch (ResourceAlreadyExistsException e) {}
		
		// Null path - should throw exception
		try {
			connVABElement.createValue(null, "");
			fail();
		} catch (MalformedRequestException e) {}
	}

	private static void testDeleteElements(VABElementProxy connVABElement) {
		// Delete at Root
		// - by object - should not work, root is a map
		try {
			connVABElement.deleteValue("inRoot", 1.2);
			fail();
		} catch (MalformedRequestException e) {
		}
		Object toTest = connVABElement.getModelPropertyValue("inRoot");
		assertEquals(1.2, toTest);
		
		// - by index
		connVABElement.deleteValue("inRoot");
		try {
			// "inRoot" should not exist anymore
			connVABElement.getModelPropertyValue("inRoot");
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Check case-sensitivity
		toTest = connVABElement.getModelPropertyValue("inroot");
		assertEquals(78, toTest);
		connVABElement.deleteValue("inroot");
		try {
			// "inroot" should not exist anymore
			connVABElement.getModelPropertyValue("inroot");
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Delete at Map
		// - by object - should not work in maps, because object refers to a contained object, not the index
		try {
			connVABElement.deleteValue("/structure/map/", "inMap");
			fail();
		} catch (MalformedRequestException e) {
		}
		
		toTest = connVABElement.getModelPropertyValue("/structure/map/inMap");
		assertEquals("34", toTest);
		// - by index
		connVABElement.deleteValue("/structure/map/inMap");
		toTest = connVABElement.getModelPropertyValue("/structure/map");
		assertEquals(0, ((Map<?, ?>) toTest).size());
		
		// Delete remaining complete Map
		connVABElement.deleteValue("mapInRoot");
		try {
			// "mapInRoot" should not exist anymore
			connVABElement.getModelPropertyValue("mapInRoot");
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Empty paths - should not delete anything and throw Exception
		try {
			connVABElement.deleteValue("", "");
			fail();
		} catch (MalformedRequestException e) {
			// Can not delete an object ("") from a map (root map)
			// It would be possible to delete "" from a "root list"
			// => invalid type
		}
		toTest = connVABElement.getModelPropertyValue("/primitives/integer");
		assertEquals(123, toTest);
		
		// Null path - should throw exception
		try {
			connVABElement.deleteValue(null, "");
			fail();
		} catch (MalformedRequestException e) {}
		try {
			connVABElement.deleteValue(null);
			fail();
		} catch (MalformedRequestException e) {}
	}
}
