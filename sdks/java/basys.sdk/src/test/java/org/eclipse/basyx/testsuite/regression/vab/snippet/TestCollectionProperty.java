package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

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
		connVABElement.createElement("/structure/list/", 5);
		connVABElement.createElement("/structure/list/", 12);

		// Test reading whole lists
		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue("/structure/list/");
		assertEquals(2, collection.size());

		// Test reading single entry
		List<Integer> references = (List<Integer>) connVABElement.readElementValue("/structure/list/references");
		int firstEntry = (int) connVABElement.readElementValue("/structure/list/byRef_" + references.get(0));
		int secondEntry = (int) connVABElement.readElementValue("/structure/list/byRef_" + references.get(1));
		assertEquals(5, firstEntry);
		assertEquals(12, secondEntry);

		// Test invalid list access
		assertNull(connVABElement.readElementValue("/structure/list/byref_" + references.get(0)));

		// Delete remaining entries
		connVABElement.deleteElement("/structure/list", 5);
		connVABElement.deleteElement("/structure/list", 12);
	}

	@SuppressWarnings("unchecked")
	public static void testUpdate(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Read original collection
		Collection<Object> original = (Collection<Object>) connVABElement.readElementValue("/structure/list/");

		// Replace complete value of the collection property
		Collection<Object> replacement = new ArrayList<>();
		replacement.add(100);
		connVABElement.updateElementValue("/structure/list/", replacement);

		// Read values back
		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue("/structure/list/");
		List<Integer> references = (List<Integer>) connVABElement.readElementValue("/structure/list/references");

		// Check test case results
		assertEquals(100, connVABElement.readElementValue("/structure/list/byRef_" + references.get(0)));
		assertEquals(1, collection.size());
		assertEquals(replacement, collection);

		// Update element by reference
		connVABElement.updateElementValue("/structure/list/byRef_" + references.get(0), 200);

		// Read single value back
		Object toTest = connVABElement.readElementValue("/structure/list/byRef_" + references.get(0));

		// Check test case result
		assertEquals(200, toTest);

		// Write original back
		connVABElement.updateElementValue("/structure/list/", original);
	}

	@SuppressWarnings("unchecked")
	public static void testCreateDelete(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Create element in Set (no key provided)
		connVABElement.createElement("/structure/set/", true);
		Object toTest = connVABElement.readElementValue("/structure/set/");
		assertTrue(((Collection<?>) toTest).contains(true));

		// Delete at Set
		// - by index - should not work in sets, as they do not have an ordering
		connVABElement.deleteElement("/structure/set/0/");
		toTest = connVABElement.readElementValue("/structure/set/");
		assertEquals(1, ((Collection<?>) toTest).size());
		// - by object
		connVABElement.deleteElement("/structure/set/", true);
		toTest = connVABElement.readElementValue("/structure/set/");
		assertEquals(0, ((Collection<?>) toTest).size());

		// Create elements in List (no key provided)
		connVABElement.createElement("/structure/list/", 56);
		toTest = connVABElement.readElementValue("/structure/list/");
		assertTrue(((List<?>) toTest).contains(56));

		// Delete at List
		// by object
		connVABElement.deleteElement("/structure/list/", 56);
		toTest = connVABElement.readElementValue("/structure/list/");
		assertEquals(0, ((List<?>) toTest).size());

		// Create a list element
		connVABElement.createElement("listInRoot", Arrays.asList(1, 1, 2, 3, 5));
		List<Integer> references = (List<Integer>) connVABElement.readElementValue("listInRoot/references");
		// Test whole list
		toTest = connVABElement.readElementValue("listInRoot");
		assertTrue(toTest instanceof List);
		assertEquals(5, ((List<?>) toTest).size());
		assertEquals(2, ((List<?>) toTest).get(2));
		// Test single element of list
		toTest = connVABElement.readElementValue("listInRoot/byRef_" + references.get(2));
		assertEquals(2, toTest);

		// Delete whole list
		connVABElement.deleteElement("listInRoot");
		toTest = connVABElement.readElementValue("listInRoot");
		assertNull(toTest);

		// Delete at List
		// - referring to new list: [10, 20, 40, 80]
		connVABElement.createElement("/structure/list/", 10);
		connVABElement.createElement("/structure/list/", 20);
		connVABElement.createElement("/structure/list/", 40);
		connVABElement.createElement("/structure/list/", 80);
		// - by index - is not possible, as list access is only allowed using references
		// - in contrast to indices, references always point to the same object in the list
		connVABElement.deleteElement("/structure/list/3");
		toTest = connVABElement.readElementValue("/structure/list/");
		assertEquals(4, ((List<?>) toTest).size());
		// - by reference - referring to newly created list: [10, 20, 40, 80]
		references = (List<Integer>) connVABElement.readElementValue("/structure/list/references");
		int referenceSecondLast = references.get(2); // => list of references reflects list ordering
		connVABElement.deleteElement("/structure/list/byRef_" + referenceSecondLast); // should be [10, 20, 80]
		toTest = connVABElement.readElementValue("/structure/list/");
		assertEquals(3, ((List<?>) toTest).size());
		try {
			// Reference 3 does not exist anymore
			connVABElement.readElementValue("/structure/list/byRef_" + referenceSecondLast);
			fail();
		} catch (ServerException e) {
			// Exception types not implemented yet
			// assertEquals(e.getType(), "org.eclipse.basyx.vab.provider.list.InvalidListReferenceException");
		}

		// Delete remaining elements
		connVABElement.deleteElement("/structure/list/byRef_" + references.get(0));
		connVABElement.deleteElement("/structure/list/byRef_" + references.get(1));
		connVABElement.deleteElement("/structure/list/byRef_" + references.get(3));
		toTest = connVABElement.readElementValue("/structure/list/");
		assertEquals(0, ((List<?>) toTest).size());

	}
}
