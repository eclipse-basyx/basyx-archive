package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Tests a IModelProvider's capability to handle collections
 * 
 * @author schnicke
 */
public class TestCollectionProperty {

	private static String collectionPath = "property1/property1.2";

	@SuppressWarnings("unchecked")
	public static void testGet(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Read values
		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue(collectionPath);

		// Check test case results
		assertEquals(2, collection.size());
	}

	@SuppressWarnings("unchecked")
	public static void testUpdateComplete(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Read original collection
		Collection<Object> original = (Collection<Object>) connVABElement.readElementValue(collectionPath);

		// Replace complete value of the collection property
		Collection<Object> replacement = new ArrayList<>();
		replacement.add(100);
		connVABElement.updateElementValue(collectionPath, replacement);

		// Read values back
		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue(collectionPath);

		// Check test case results
		assertEquals(1, collection.size());
		assertEquals(replacement, collection);

		// Write original back
		connVABElement.updateElementValue(collectionPath, original);
	}

	@SuppressWarnings("unchecked")
	public static void testUpdateElement(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Create a new element in the collection
		connVABElement.createElement(collectionPath, 3);

		// Read values back
		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue(collectionPath);

		// Check test case results
		assertEquals(3, collection.size());
		assertTrue(collection.contains(1));
		assertTrue(collection.contains(2));
		assertTrue(collection.contains(3));

		// Remove newly added value value
		connVABElement.deleteElement(collectionPath, 3);
	}

	@SuppressWarnings("unchecked")
	public static void testRemoveElement(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		connVABElement.deleteElement(collectionPath, 2);

		// Read values back
		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue(collectionPath);

		// Check test case results
		assertEquals(1, collection.size());
		assertTrue(collection.contains(1));

		// Reinsert value
		connVABElement.createElement(collectionPath, 2);
	}
}
