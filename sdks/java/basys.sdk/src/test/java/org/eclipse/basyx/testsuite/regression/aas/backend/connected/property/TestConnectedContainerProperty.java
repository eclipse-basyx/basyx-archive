package org.eclipse.basyx.testsuite.regression.aas.backend.connected.property;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property.ConnectedContainerProperty;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedContainerProperty can be created and used correctly
 * 
 * @author schnicke
 *
 */
public class TestConnectedContainerProperty {
	// String constants used in this test case
	private static final String COLLECTIONPROP = "coll";
	private static final String OPERATION = "sum";

	IContainerProperty prop;
	Collection<Integer> collection;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		// Create collection for the collection property
		collection = new ArrayList<>();
		collection.add(1);
		collection.add(2);

		// Create PropertySingleValued containing the collection
		Property propertyMeta = factory.create(new Property(), collection);
		propertyMeta.setId(COLLECTIONPROP);

		// Create operation
		Operation operation = factory.createOperation(new Operation(), (arr) -> {
			return (int) arr[0] + (int) arr[1];
		});
		operation.setId(OPERATION);

		// Create ComplexDataProperty containing the created operation and property
		SubmodelElementCollection complex = factory.createContainer(new SubmodelElementCollection(),
				Collections.singletonList(propertyMeta), Collections.singletonList(operation));

		Map<String, Object> destroyType = TypeDestroyer.destroyType(complex);
		// Create a dummy connection manager containing the created
		// ComplexDataProperty map
		VABConnectionManager manager = new VABConnectionManagerStub(new VirtualPathModelProvider(destroyType));

		// Retrieve the ConnectedContainerProperty
		prop = new ConnectedContainerProperty("", manager.connectToVABElement(""));
	}

	/**
	 * Tests retrieving a contained property
	 */
	@Test
	public void testProperty() {
		// Get contained properties
		Map<String, IProperty> props = prop.getProperties();

		// Check number of properties
		assertEquals(1, props.size());

		// Retrieves collection property
		ICollectionProperty collProp = (ICollectionProperty) props.get(COLLECTIONPROP);

		// Check contained values
		assertEquals(collection, collProp.getElements());
	}

	/**
	 * Tests retrieving a contained operation
	 */
	@Test
	public void testOperation() throws Exception {
		// Get contained operations
		Map<String, IOperation> ops = prop.getOperations();

		// Check number of operations
		assertEquals(1, ops.size());

		// Retrieves operations
		IOperation sum = ops.get(OPERATION);

		// Check operation invocation
		assertEquals(5, sum.invoke(2, 3));
	}
}
