package org.eclipse.basyx.testsuite.regression.vab.modelprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Tests a IModelProvider's capability to handle collections
 * 
 * @author schnicke, espen
 */
public class TestCollectionProperty {

	@SuppressWarnings("unchecked")
	public static void testRead(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Adding elements to tested list
		connVABElement.createValue("/structure/list/", 5);
		connVABElement.createValue("/structure/list/", 12);

		// Test reading whole lists
		Collection<Object> collection = (Collection<Object>) connVABElement.getModelPropertyValue("/structure/list/");
		assertEquals(2, collection.size());

		// Test reading single entry
		List<Integer> references = (List<Integer>) connVABElement.getModelPropertyValue("/structure/list/references");
		int firstEntry = (int) connVABElement.getModelPropertyValue("/structure/list/byRef_" + references.get(0));
		int secondEntry = (int) connVABElement.getModelPropertyValue("/structure/list/byRef_" + references.get(1));
		assertEquals(5, firstEntry);
		assertEquals(12, secondEntry);

		// Test invalid list access
		assertNull(connVABElement.getModelPropertyValue("/structure/list/byref_" + references.get(0)));

		// Delete remaining entries
		connVABElement.deleteValue("/structure/list", 5);
		connVABElement.deleteValue("/structure/list", 12);
	}

	@SuppressWarnings("unchecked")
	public static void testUpdate(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Read original collection
		Collection<Object> original = (Collection<Object>) connVABElement.getModelPropertyValue("/structure/list/");

		// Replace complete value of the collection property
		Collection<Object> replacement = new ArrayList<>();
		replacement.add(100);
		connVABElement.setModelPropertyValue("/structure/list/", replacement);

		// Read values back
		Collection<Object> collection = (Collection<Object>) connVABElement.getModelPropertyValue("/structure/list/");
		List<Integer> references = (List<Integer>) connVABElement.getModelPropertyValue("/structure/list/references");

		// Check test case results
		assertEquals(100, connVABElement.getModelPropertyValue("/structure/list/byRef_" + references.get(0)));
		assertEquals(1, collection.size());
		assertEquals(replacement, collection);

		// Update element by reference
		connVABElement.setModelPropertyValue("/structure/list/byRef_" + references.get(0), 200);

		// Read single value back
		Object toTest = connVABElement.getModelPropertyValue("/structure/list/byRef_" + references.get(0));

		// Check test case result
		assertEquals(200, toTest);

		// Write original back
		connVABElement.setModelPropertyValue("/structure/list/", original);
	}

	@SuppressWarnings("unchecked")
	public static void testCreateDelete(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Create element in Set (no key provided)
		connVABElement.createValue("/structure/set/", true);
		Object toTest = connVABElement.getModelPropertyValue("/structure/set/");
		assertTrue(((Collection<?>) toTest).contains(true));

		// Delete at Set
		// - by index - should not work in sets, as they do not have an ordering
		connVABElement.deleteValue("/structure/set/0/");
		toTest = connVABElement.getModelPropertyValue("/structure/set/");
		assertEquals(1, ((Collection<?>) toTest).size());
		// - by object
		connVABElement.deleteValue("/structure/set/", true);
		toTest = connVABElement.getModelPropertyValue("/structure/set/");
		assertEquals(0, ((Collection<?>) toTest).size());

		// Create elements in List (no key provided)
		connVABElement.createValue("/structure/list/", 56);
		toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertTrue(((List<?>) toTest).contains(56));

		// Delete at List
		// by object
		connVABElement.deleteValue("/structure/list/", 56);
		toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertEquals(0, ((List<?>) toTest).size());

		// Create a list element
		connVABElement.createValue("listInRoot", Arrays.asList(1, 1, 2, 3, 5));
		List<Integer> references = (List<Integer>) connVABElement.getModelPropertyValue("listInRoot/references");
		// Test whole list
		toTest = connVABElement.getModelPropertyValue("listInRoot");
		assertTrue(toTest instanceof List);
		assertEquals(5, ((List<?>) toTest).size());
		assertEquals(2, ((List<?>) toTest).get(2));
		// Test single element of list
		toTest = connVABElement.getModelPropertyValue("listInRoot/byRef_" + references.get(2));
		assertEquals(2, toTest);

		// Delete whole list
		connVABElement.deleteValue("listInRoot");
		toTest = connVABElement.getModelPropertyValue("listInRoot");
		assertNull(toTest);

		// Delete at List
		// - referring to new list: [10, 20, 40, 80]
		connVABElement.createValue("/structure/list/", 10);
		connVABElement.createValue("/structure/list/", 20);
		connVABElement.createValue("/structure/list/", 40);
		connVABElement.createValue("/structure/list/", 80);
		// - by index - is not possible, as list access is only allowed using references
		// - in contrast to indices, references always point to the same object in the list
		connVABElement.deleteValue("/structure/list/3");
		toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertEquals(4, ((List<?>) toTest).size());
		// - by reference - referring to newly created list: [10, 20, 40, 80]
		references = (List<Integer>) connVABElement.getModelPropertyValue("/structure/list/references");
		int referenceSecondLast = references.get(2); // => list of references reflects list ordering
		connVABElement.deleteValue("/structure/list/byRef_" + referenceSecondLast); // should be [10, 20, 80]
		toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertEquals(3, ((List<?>) toTest).size());
		try {
			// Reference 3 does not exist anymore
			connVABElement.getModelPropertyValue("/structure/list/byRef_" + referenceSecondLast);
			fail();
		} catch (ServerException e) {
			// Exception types not implemented yet
			// assertEquals(e.getType(), "org.eclipse.basyx.vab.provider.list.InvalidListReferenceException");
		}

		// Delete remaining elements
		connVABElement.deleteValue("/structure/list/byRef_" + references.get(0));
		connVABElement.deleteValue("/structure/list/byRef_" + references.get(1));
		connVABElement.deleteValue("/structure/list/byRef_" + references.get(3));
		toTest = connVABElement.getModelPropertyValue("/structure/list/");
		assertEquals(0, ((List<?>) toTest).size());

	}
}
