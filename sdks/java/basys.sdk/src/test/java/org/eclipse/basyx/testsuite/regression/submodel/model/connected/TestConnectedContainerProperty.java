package org.eclipse.basyx.testsuite.regression.submodel.model.connected;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.ICollectionProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IContainerProperty;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.property.ConnectedContainerProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.ContainerProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.restapi.ContainerPropertyProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.testsuite.regression.vab.protocol.TypeDestroyer;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
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
		SingleProperty propertyMeta = new SingleProperty(collection);
		propertyMeta.setIdShort(COLLECTIONPROP);

		// Create operation
		Operation operation = factory.createOperation(new Operation(), (arr) -> {
			return (int) arr[0] + (int) arr[1];
		});
		operation.setIdShort(OPERATION);

		// Create ComplexDataProperty containing the created operation and property
		ContainerProperty complex = factory.createContainer(new ContainerProperty(),
				Collections.singletonList(propertyMeta), Collections.singletonList(operation));

		Map<String, Object> destroyType = TypeDestroyer.destroyType(complex);
		// Create a dummy connection manager containing the created ContainerProperty map
		// The model is wrapped in the corresponding ModelProvider that implements the API access
		VABConnectionManagerStub manager = new VABConnectionManagerStub(
				new ContainerPropertyProvider(new VABLambdaProvider(destroyType)));

		// Retrieve the ConnectedContainerProperty
		prop = new ConnectedContainerProperty(manager.connectToVABElement(""));
	}

	/**
	 * Tests retrieving a contained property
	 */
	@Test
	public void testProperty() {
		// Get contained properties
		Map<String, IDataElement> props = prop.getDataElements();

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
