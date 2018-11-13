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
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedCollectionProperty {
	ICollectionProperty prop;
	Collection<Integer> collection;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();
		collection = new ArrayList<>();
		collection.add(1);
		collection.add(2);
		PropertySingleValued propertyMeta = factory.create(new PropertySingleValued(), collection);
		prop = new ConnectedCollectionProperty("",
				new VABConnectionManagerStub(new VABHashmapProvider(propertyMeta)).connectToVABElement(""));
	}

	@Test
	public void testGetSize() {
		assertEquals(2, prop.getElementCount());
	}

	@Test
	public void testGetElements() throws Exception {
		assertEquals(collection, prop.getElements());
		assertEquals(collection.size(), prop.getElementCount());
	}

	@Test
	public void testPut() throws ServerException, TypeMismatchException {
		prop.add(3);
		assertEquals(3, prop.getElementCount());
		prop.getElements().contains(3);
	}

	@Test
	public void testSet() throws Exception {
		prop.set(Collections.singletonList(10));
		assertEquals(1, prop.getElementCount());
		assertTrue(prop.getElements().contains(10));
	}

	@Test
	public void testRemove() throws ServerException, TypeMismatchException {
		prop.remove(2);
		Collection<Object> collection = prop.getElements();
		assertEquals(1, collection.size());
		assertTrue(collection.contains(1));
	}
}
