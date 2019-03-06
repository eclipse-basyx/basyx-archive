package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Tests a IModelProvider's capability to handle list references
 * 
 * @author espen
 */
public class ListReferences {
	private static String listPath = "property1/newList";

	@SuppressWarnings("unchecked")
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Create list
		HashMap<String, Object> map = new HashMap<>();
		map.put("x", 5);
		Object[] original = new Object[] { 3, 4, "test", "test", true, false, map };
		connVABElement.createElement(listPath, original);

		// Read values
		List<Object> list = (List<Object>) connVABElement.readElementValue(listPath);
		Integer[] references = (Integer[]) connVABElement.readElementValue(listPath + "/references");

		// Check list properties
		assertEquals(7, list.size());
		assertEquals(7, references.length);
		checkOriginalReferences(connVABElement);

		// Test invalid reference
		try {
			connVABElement.readElementValue(listPath + "/byRef_23");
			fail();
		} catch (ServerException e) {
			assertTrue(e.getType().contains("InvalidListReferenceException"));
		}

		// Test invalid list access
		assertNull(connVABElement.readElementValue(listPath + "/byref_1"));

		// Add element
		connVABElement.createElement(listPath, "new");
		checkOriginalReferences(connVABElement);

		// Read new value
		Object value = connVABElement.readElementValue(listPath + "/byRef_7");

		// Check new value
		assertTrue(value instanceof String);
		assertEquals("new", value);

		// remove elements
		connVABElement.deleteElement(listPath + "/byRef_1");
		connVABElement.deleteElement(listPath + "/byRef_3");
		connVABElement.deleteElement(listPath, false);
		connVABElement.deleteElement(listPath, "new");

		// Check remaining references
		checkConstantReferences(connVABElement);

		// Modify list entry
		connVABElement.updateElementValue(listPath + "/byRef_2", 100);

		// Read updated value
		value = connVABElement.readElementValue(listPath + "/byRef_2");

		// Check new value
		assertTrue(value instanceof Integer);
		assertEquals(100, value);

		// Read list
		list = (List<Object>) connVABElement.readElementValue(listPath);
		references = (Integer[]) connVABElement.readElementValue(listPath + "/references");

		// Check list properties
		assertEquals(4, list.size());
		assertEquals(4, references.length);

		// Revert changes
		connVABElement.deleteElement(listPath);
	}

	private static void checkOriginalReferences(VABElementProxy connVABElement) {
		checkConstantReferences(connVABElement);
		checkModifiedReferences(connVABElement);
	}

	private static void checkConstantReferences(VABElementProxy connVABElement) {
		Object value1 = connVABElement.readElementValue(listPath + "/byRef_0");
		Object value2 = connVABElement.readElementValue(listPath + "/byRef_2");
		Object value3 = connVABElement.readElementValue(listPath + "/byRef_4");
		Object value4 = connVABElement.readElementValue(listPath + "/byRef_6/x");

		// Check test case results
		assertTrue(value1 instanceof Integer);
		assertEquals(3, value1);
		assertTrue(value2 instanceof String);
		assertEquals("test", value2);
		assertTrue(value3 instanceof Boolean);
		assertEquals(true, value3);
		assertTrue(value4 instanceof Integer);
		assertEquals(5, value4);
	}

	private static void checkModifiedReferences(VABElementProxy connVABElement) {
		Object value1 = connVABElement.readElementValue(listPath + "/byRef_1");
		Object value2 = connVABElement.readElementValue(listPath + "/byRef_3");
		Object value3 = connVABElement.readElementValue(listPath + "/byRef_5");

		// Check test case results
		assertTrue(value1 instanceof Integer);
		assertEquals(4, value1);
		assertTrue(value2 instanceof String);
		assertEquals("test", value2);
		assertTrue(value3 instanceof Boolean);
		assertEquals(false, value3);
	}
}
