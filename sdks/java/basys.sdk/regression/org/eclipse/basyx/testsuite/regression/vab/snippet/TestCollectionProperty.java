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
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue(collectionPath);
		assertEquals(2, collection.size());
	}

	@SuppressWarnings("unchecked")
	public static void testUpdateComplete(VABConnectionManager connManager) {
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		Collection<Object> replacement = new ArrayList<>();
		replacement.add(100);
		connVABElement.updateElementValue(collectionPath, replacement);

		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue(collectionPath);
		assertEquals(1, collection.size());
		assertEquals(replacement, collection);
	}

	@SuppressWarnings("unchecked")
	public static void testUpdateElement(VABConnectionManager connManager) {
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		connVABElement.createElement(collectionPath, 3);

		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue(collectionPath);
		assertEquals(3, collection.size());
		assertTrue(collection.contains(1));
		assertTrue(collection.contains(2));
		assertTrue(collection.contains(3));
	}

	@SuppressWarnings("unchecked")
	public static void testRemoveElement(VABConnectionManager connManager) {
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		connVABElement.deleteElement(collectionPath, 2);

		Collection<Object> collection = (Collection<Object>) connVABElement.readElementValue(collectionPath);
		assertEquals(1, collection.size());
		assertTrue(collection.contains(1));
	}
}
