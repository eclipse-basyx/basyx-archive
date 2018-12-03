package org.eclipse.basyx.testsuite.regression.aas.backend.connected.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.ICollectionProperty;
import org.eclipse.basyx.aas.backend.connected.property.ConnectedCollectionProperty;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.PropertySingleValued;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedCollectionProperty can be created and used correctly
 * 
 * @author schnicke
 *
 */
public class TestConnectedCollectionProperty {
	ICollectionProperty prop;
	Collection<Integer> collection;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		// Create collection
		collection = new ArrayList<>();
		collection.add(1);
		collection.add(2);

		// Create PropertySingleValued containing the collection
		PropertySingleValued propertySingleValued = factory.create(new PropertySingleValued(), collection);

		// Create dummy connection manager containing the
		// created PropertySingleValued map
		VABConnectionManager manager = new VABConnectionManagerStub(new VABHashmapProvider(propertySingleValued));

		// Create ConnectedCollectionProperty
		prop = new ConnectedCollectionProperty("", manager.connectToVABElement(""));
	}

	/**
	 * Tests getting the size of the contained collection
	 */
	@Test
	public void testGetSize() {
		assertEquals(2, prop.getElementCount());
	}

	/**
	 * Tests getting the elements of the contained collection
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetElements() throws Exception {
		// Tests if number of elements is as expected
		assertEquals(collection.size(), prop.getElementCount());

		// Test if the contained collection is equal to the reference collection
		assertEquals(collection, prop.getElements());
	}

	/**
	 * Tests adding a value to the contained collection
	 * 
	 * @throws ServerException
	 * @throws TypeMismatchException
	 */
	@Test
	public void testPut() throws ServerException, TypeMismatchException {
		// Add value
		prop.add(3);

		// Check that a value was added
		assertEquals(3, prop.getElementCount());

		// Check that the correct value has been added
		prop.getElements().contains(3);
	}

	/**
	 * Tests overwriting the complete contained collection
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSet() throws Exception {
		// Set the contained collection to { 10 }
		prop.set(Collections.singletonList(10));

		// Check number of elements
		assertEquals(1, prop.getElementCount());

		// Check that the correct value is contained
		assertTrue(prop.getElements().contains(10));
	}

	/**
	 * Tests removing a value from the contained collection
	 * 
	 * @throws ServerException
	 * @throws TypeMismatchException
	 */
	@Test
	public void testRemove() throws ServerException, TypeMismatchException {
		// Remove a value from the contained collection
		prop.remove(2);

		// Read values back
		Collection<Object> collection = prop.getElements();

		// Check number of elements
		assertEquals(1, collection.size());

		// Check that the correct value is contained
		assertTrue(collection.contains(1));
	}
}
