package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.SubmodelElementCollectionProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.support.TypeDestroyer;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedContainerProperty can be created and used correctly
 * 
 * @author schnicke
 *
 */
public class TestConnectedSubmodelElementCollection {
	// String constants used in this test case
	private static final String PROP = "prop";
	private static final String OPERATION = "sum";

	ISubmodelElementCollection prop;

	@Before 
	public void build() {
		// Create PropertySingleValued containing the collection
		Property propertyMeta = new Property(4);
		propertyMeta.setIdShort(PROP);

		// Create operation
		Operation operation = new Operation(arr -> {
			return (int) arr[0] + (int) arr[1];
		});
		operation.setIdShort(OPERATION);

		// Create ComplexDataProperty containing the created operation and property
		SubmodelElementCollection complex = new SubmodelElementCollection();
		complex.addElement(propertyMeta);
		complex.addElement(operation);

		Map<String, Object> destroyType = TypeDestroyer.destroyType(complex);
		// Create a dummy connection manager containing the created ContainerProperty map
		// The model is wrapped in the corresponding ModelProvider that implements the API access
		VABConnectionManagerStub manager = new VABConnectionManagerStub(
				new SubmodelElementCollectionProvider(new VABLambdaProvider(destroyType)));

		// Retrieve the ConnectedContainerProperty
		prop = new ConnectedSubmodelElementCollection(manager.connectToVABElement(""));
	}

	/**
	 * Tests retrieving a contained property
	 * 
	 * @throws Exception
	 */
	@Test
	public void testProperty() throws Exception {
		// Get contained properties
		Map<String, IProperty> props = prop.getProperties();

		// Check number of properties
		assertEquals(1, props.size());

		// Retrieves collection property
		IProperty prop = props.get(PROP);

		// Check contained values
		assertEquals(4, prop.get());
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
