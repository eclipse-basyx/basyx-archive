package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Snippet to test create and delete functionality of a IModelProvider
 * 
 * @author kuhn, schnicke, espen
 *
 */
public class CreateDelete {
	@SuppressWarnings("unchecked")
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Get property value
		Object value1 = connVABElement.readElementValue("property1");
		Object value2 = connVABElement.readElementValue("property1/property1.1");

		// Check test case results
		assertTrue(value1 instanceof HashMap);
		assertTrue(value2 instanceof Integer);
		assertEquals(7, value2);

		// Create new properties
		// - Create property directly in VAB element
		connVABElement.createElement("property2", 21);
		// - Create property in contained hashmap
		connVABElement.createElement("property1/property1.4", 22);
		// - Try to delete key from map (should be ignored)
		connVABElement.deleteElement("property1", "property1.4");
		// - Create property in collection in contained hashmap
		connVABElement.createElement("property1/property1.2", 23);
		// - Create property in collection in contained hashmap
		connVABElement.createElement("property1/property1.2", 28);
		// - Check case-sensitivity
		connVABElement.createElement("Property2", 26);
		// - Try to overwrite existing property in a Map (should be ignored, already exists)
		connVABElement.createElement("property2", 24);
		// - Create a list property
		connVABElement.createElement("property1/propertyList", new Integer[] { 25 });
		// - Try to overwrite existing property in a List (should be ignored, already exists)
		connVABElement.createElement("property1/propertyList/byRef_0", 26);

		// Read values back
		Object value3 = connVABElement.readElementValue("property2");
		// - Check test case results
		assertTrue(value3 instanceof Integer);
		assertEquals(21, value3);

		// Read values back
		Object value4 = connVABElement.readElementValue("property1/property1.4");
		// - Check test case results
		assertTrue(value4 instanceof Integer);
		assertEquals(22, value4);

		// Read values back
		Object value5 = connVABElement.readElementValue("property1/property1.2");
		// - Check test case results
		assertTrue(value5 instanceof Collection);
		Collection<Object> value5Collection = (Collection<Object>) ((Collection<?>) value5);
		assertEquals(4, value5Collection.size());

		// Read values back
		Object valueList = connVABElement.readElementValue("property1/propertyList");
		Object valueListRef = connVABElement.readElementValue("property1/propertyList/byRef_0");
		// - Check test case results
		assertTrue(valueList instanceof Collection);
		Collection<Object> valueListCollection = (Collection<Object>) ((Collection<?>) valueList);
		assertEquals(1, valueListCollection.size());
		assertTrue(valueListRef instanceof Integer);
		assertEquals(25, valueListRef);

		// Delete properties
		connVABElement.deleteElement("property2");
		connVABElement.deleteElement("property1/property1.4");
		connVABElement.deleteElement("property1/propertyList");

		// Remove list elements (by reference and by object)
		List<Integer> references = (List<Integer>) connVABElement.readElementValue("property1/property1.2/references");
		int lastReference = references.get(references.size() - 1);
		connVABElement.deleteElement("property1/property1.2", 23);
		connVABElement.deleteElement("property1/property1.2/byRef_" + lastReference);

		// Read values back
		Object value6 = connVABElement.readElementValue("property2");
		// - Check test case results
		assertEquals(null, value6);

		// Read values back
		Object value7 = connVABElement.readElementValue("property1/property1.4");
		// - Check test case results
		assertEquals(null, value7);

		// Read values back
		Object value8 = connVABElement.readElementValue("property1/property1.2");
		// - Check test case results
		assertTrue(value8 instanceof Collection);
		assertEquals(2, ((Collection<?>) value8).size());

		// Read values back
		Object value9 = connVABElement.readElementValue("property1/propertyList");
		// - Check test case results
		assertEquals(null, value9);

		// Read values back
		Object value10 = connVABElement.readElementValue("Property2");
		// Check test case results
		assertEquals(26, value10);

		// Delete remaining property
		connVABElement.deleteElement("Property2");
	}
}
