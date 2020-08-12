package org.eclipse.basyx.vab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Tests a IModelProvider's capability to handle collections.
 * Based on the TestCollectionProperty tests within the SDK, but removes
 * Java-specific local tests.
 * 
 * @author espen
 */
public class CppTestCollectionProperty {
	@SuppressWarnings("unchecked")
	public static void testUpdate(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
	
		// Read original collection
		Collection<Object> original = (Collection<Object>) connVABElement.getModelPropertyValue("/structure/list/");
	
		// Replace complete value of the collection property
		Collection<Object> replacement = new ArrayList<>();
		replacement.add(100);
		replacement.add(200);
		replacement.add(300);
		connVABElement.setModelPropertyValue("/structure/list/", replacement);
	
		// Read values back
		Collection<Object> collection = (Collection<Object>) connVABElement.getModelPropertyValue("/structure/list/");
	
		// Check test case results
		assertEquals(3, collection.size());
		assertEquals(replacement, collection);

		// Test invalid list access - single list elements cannot be accessed directly
		try {
			connVABElement.setModelPropertyValue("/structure/list/0", 3);
			fail();
		} catch (ResourceNotFoundException e) {
		}
	
		// Write original back
		connVABElement.setModelPropertyValue("/structure/list/", original);
	}

	public static void testCreateDelete(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
	
		// Create elements in List (no key provided)
		connVABElement.createValue("/structure/list/", 56);
		Object toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertTrue(((List<?>) toTest).contains(56));
	
		// Delete at List
		// by object
		connVABElement.deleteValue("/structure/list/", 56);
		toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertEquals(0, ((List<?>) toTest).size());
	
		// Create a list element
		connVABElement.createValue("listInRoot", Arrays.asList(1, 1, 2, 3, 5));
		// Test whole list
		toTest = connVABElement.getModelPropertyValue("listInRoot");
		assertTrue(toTest instanceof List);
		assertEquals(5, ((List<?>) toTest).size());
		assertEquals(2, ((List<?>) toTest).get(2));
	
		// Delete whole list
		connVABElement.deleteValue("listInRoot");
		try {
			connVABElement.getModelPropertyValue("listInRoot");
			fail();
		} catch (ResourceNotFoundException e) {}
	
		// Delete at List
		// - referring to new list: [10, 20, 40, 80]
		connVABElement.createValue("/structure/list/", 10);
		connVABElement.createValue("/structure/list/", 20);
		connVABElement.createValue("/structure/list/", 40);
		connVABElement.createValue("/structure/list/", 80);
		// - by index - is not possible, as list access is only allowed using references
		// - in contrast to indices, references always point to the same object in the list
		try {
			connVABElement.deleteValue("/structure/list/3");
			fail();
		} catch (ResourceNotFoundException e) {}
	
		toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertEquals(4, ((List<?>) toTest).size());
	
		// Delete half of the elements
		connVABElement.deleteValue("/structure/list/", 10);
		connVABElement.deleteValue("/structure/list/", 40);
		toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertEquals(2, ((List<?>) toTest).size());

		// Delete remaining elements
		connVABElement.deleteValue("/structure/list/", 20);
		connVABElement.deleteValue("/structure/list/", 80);
		toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertEquals(0, ((List<?>) toTest).size());
	}
}
