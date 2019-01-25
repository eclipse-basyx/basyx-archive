package org.eclipse.basyx.testsuite.regression.aas.backend.connected;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedSubModel;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a SubModel can be created and used correctly
 * 
 * @author schnicke
 *
 */
public class TestConnectedSubModel {

	// String constants used in this test case
	private final static String OP = "add";
	private final static String PROP = "prop1";
	private final static String ID = "TestId";

	ISubModel submodel;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		// Create a simple value property
		Property propertyMeta = factory.create(new Property(), 100);
		propertyMeta.setId(PROP);

		// Create an operation
		Operation op = factory.createOperation(new Operation(), (Function<Object[], Object>) (obj) -> {
			return (int) obj[0] + (int) obj[1];
		});
		op.setId(OP);

		// Create the SubModel using the created property and operation
		SubModel sm = factory.create(new SubModel(), Collections.singletonList(propertyMeta), Collections.singletonList(op));
		sm.setId(ID);

		// Create a dummy connection manager containing the created SubModel map
		VABConnectionManager manager = new VABConnectionManagerStub(new VABHashmapProvider(sm));

		// Create the ConnectedSubModel based on the manager stub
		submodel = new ConnectedSubModel("", manager.connectToVABElement(""));
	}

	/**
	 * Tests if a SubModel's id can be retrieved correctly
	 */
	@Test
	public void getIdTest() {
		assertEquals(submodel.getId(), ID);
	}

	/**
	 * Tests if a SubModel's properties can be used correctly
	 */
	@Test
	public void propertiesTest() throws Exception {
		// Retrieve all properties
		Map<String, IProperty> props = submodel.getProperties();

		// Check if number of properties is as expected
		assertEquals(1, props.size());

		// Check the value of the property
		ISingleProperty prop = (ISingleProperty) props.get(PROP);
		assertEquals(100, prop.get());
	}

	/**
	 * Tests if a SubModel's operations can be used correctly
	 * 
	 * @throws Exception
	 */
	@Test
	public void operationsTest() throws Exception {
		// Retrieve all operations
		Map<String, IOperation> ops = submodel.getOperations();

		// Check if number of operations is as expected
		assertEquals(1, ops.size());

		// Check the operation itself
		IOperation op = ops.get(OP);
		assertEquals(5, op.invoke(2, 3));
	}

}
