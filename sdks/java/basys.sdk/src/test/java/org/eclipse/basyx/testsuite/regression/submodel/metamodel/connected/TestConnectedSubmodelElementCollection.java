package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
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

	ConnectedSubmodelElementCollection prop;

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
		complex.addSubModelElement(propertyMeta);
		complex.addSubModelElement(operation);
		complex.setIdShort("CollectionId");

		SubModel sm = new SubModel("submodelId", new ModelUrn("testUrn"));
		sm.addSubModelElement(complex);

		Map<String, Object> destroyType = TypeDestroyer.destroyType(sm);
		// Create a dummy connection manager containing the created ContainerProperty map
		// The model is wrapped in the corresponding ModelProvider that implements the API access
		VABConnectionManagerStub manager = new VABConnectionManagerStub(
				new SubModelProvider(new VABLambdaProvider(destroyType)));

		// Retrieve the ConnectedContainerProperty
		prop = new ConnectedSubmodelElementCollection(manager.connectToVABElement("").getDeepProxy("/submodel/submodelElements/" + complex.getIdShort()));
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
		assertEquals(4, prop.getValue());
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
	
	@Test
	public void testSetValue() {
		Property property = new Property("testProperty");
		property.setIdShort(PROP);
		
		
		Collection<ISubmodelElement> newValue = new ArrayList<>();
		newValue.add(property);
		
		prop.setValue(newValue);
		
		Map<String, ISubmodelElement> value = prop.getSubmodelElements();
		IProperty property2 = (IProperty) value.get(PROP);
		
		assertEquals("testProperty", property2.getValue());
	}

	@Test
	public void testGetSubmodelElement() {
		ISubmodelElement element = prop.getSubmodelElement(PROP);
		assertEquals(PROP, element.getIdShort());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteSubmodelElement() {
		prop.deleteSubmodelElement(PROP);
		prop.getSubmodelElement(PROP);
	}
	
	@Test
	public void testAddSubmodelElement() {
		String newId = "abc";
		Property newProp = new Property(6);
		newProp.setIdShort(newId);
		prop.addSubModelElement(newProp);
		ISubmodelElement element = prop.getSubmodelElement(newId);
		assertEquals(newId, element.getIdShort());
	}
}
