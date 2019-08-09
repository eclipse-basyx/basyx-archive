package org.eclipse.basyx.testsuite.regression.aas.backend.connected;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.backend.connected.aas.ConnectedSubModel;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory_BaSyxNative;
import org.eclipse.basyx.testsuite.support.backend.servers.AASTCPServerResource;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.Before;
import org.junit.ClassRule;
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

	ConnectedSubModel submodel;
	
	@ClassRule
	public static AASTCPServerResource res = AASTCPServerResource.getTestResource();

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		// Create a simple value property
		Property propertyMeta = factory.create(new Property(), 100);
		propertyMeta.setId(PROP);

		// Create an operation
		Operation op = factory.createOperation(new Operation(), (Function<Object[], Object> & Serializable) (obj) -> {
			return (int) obj[0] + (int) obj[1];
		});
		op.setId(OP);

		// Create the SubModel using the created property and operation		
		SubModel sm = factory.create(new SubModel(), Collections.singletonList(propertyMeta), Collections.singletonList(op));
		sm.setId(ID);
		// Create a connection manager
		VABConnectionManager manager = new VABConnectionManager(new TestsuiteDirectory_BaSyxNative(),
				new BaSyxConnectorProvider());
		
		// Connect to the new VABElement
		VABElementProxy proxy = manager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		
		// Put the subModel into the newly created proxy
		proxy.updateElementValue("", sm);
		
		// Create the ConnectedSubModel based on the manager
		submodel = new ConnectedSubModel("", proxy);
	}

	/**
	 * Tests if a SubModel's id can be retrieved correctly
	 */
	@Test
	public void getIdTest() {
		assertEquals(ID, submodel.getId());
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
	
	@Test
	public void saveAndLoadPropertyTest() throws Exception {
		
		// Construct test data
		Property property = new Property();
		property.setId("test1");
		property.setValue("test2");
		
		// Save it
		submodel.addDataElement(property);
		
		// Load it
		Map<String, IProperty> map = submodel.getProperties();
		
		// Check if it loaded correctly
		assertNotNull(map);
		IProperty loadedProp = map.get(property.getId());
		assertNotNull(loadedProp);
		assertEquals(property.getValue(), loadedProp.getValue());
	}
	
	@Test
	public void saveAndLoadOperationTest() throws Exception {
		
		// Construct test data
		Operation operation = new Operation();
		operation.setId("test1");
		
		// Save it
		submodel.addOperation(operation);
		
		// Load it
		Map<String, IOperation> map = submodel.getOperations();
		
		// Check if it loaded correctly
		assertNotNull(map);
		IOperation loadedOp = map.get(operation.getId());
		assertNotNull(loadedOp);
		assertEquals(operation.getId(), loadedOp.getId());
	}
}
