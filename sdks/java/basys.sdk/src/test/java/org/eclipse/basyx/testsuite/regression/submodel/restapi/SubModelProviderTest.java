package org.eclipse.basyx.testsuite.regression.submodel.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.TestsuiteDirectory;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.ConnectorProvider;
import org.junit.Test;

public class SubModelProviderTest {
	private VABConnectionManager connManager;
	private static final String submodelAddr = "urn:fhg:es.iese:aas:1:1:submodel";

	protected VABConnectionManager getConnectionManager() {
		if (connManager == null) {
			connManager = new VABConnectionManager(new TestsuiteDirectory(), new ConnectorProvider() {
				@Override
				protected IModelProvider createProvider(String addr) {
					// Simple submodel for testing specific mappings for submodels
					return new SubModelProvider(new SimpleAASSubmodel("mySubmodelId"));
				}
			});
		}
		return connManager;
	}

	/**
	 * Tests accessing different paths that should be supported
	 * @throws Exception 
	 */
	@Test
	public void testPathsRaw() throws Exception {
		SubModelProvider provider = new SubModelProvider(new SimpleAASSubmodel("mySubmodelId"));
		provider.getModelPropertyValue("/submodel");
		provider.getModelPropertyValue("/submodel/");

		try {
			provider.getModelPropertyValue("invalid");
			fail();
		} catch (Exception e) {
			
		}
	}

	/**
	 * Test creating single property
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateProperty() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Create element
		Property prop = new Property(500);
		prop.setIdShort("newProperty");
		submodelElement.createValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "", prop);

		// Read back value
		Map<String, Object> result = (Map<String, Object>) submodelElement
				.getModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "/newProperty/value");
		assertEquals(500, result.get(Property.VALUE));
	}

	/**
	 * Test reading single property
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadProperty() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Read list of properties
		Object result = submodelElement.getModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "");
		Collection<Map<String, Object>> propertySet = (Collection<Map<String, Object>>) result;
		Map<String, Object> property = propertySet.stream().filter(elem -> elem.get(Identifiable.IDSHORT).equals("integerProperty")).findFirst().get();
		assertEquals(123, property.get(Property.VALUE));

		// Read whole property
		result = submodelElement.getModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty");
		property = (Map<String, Object>) result;
		assertEquals(123, property.get(Property.VALUE));

		// Read idShort
		result = submodelElement.getModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "/stringProperty");
		property = (Map<String, Object>) result;
		assertEquals("stringProperty", property.get(Identifiable.IDSHORT));

		// Read single value
		Map<String, Object> resMap = (Map<String, Object>) submodelElement
				.getModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "/stringProperty/value");
		assertEquals("Test", resMap.get(Property.VALUE));

		// Read null value
		resMap = (Map<String, Object>) submodelElement
				.getModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "/nullProperty/value");
		assertEquals(null, resMap.get(Property.VALUE));

		// Read container property
		Collection<Object> resSet = (Collection<Object>) submodelElement
				.getModelPropertyValue("/submodel/submodelElements/containerRoot/value");
		assertEquals(1, resSet.size());
		resSet.forEach(x -> assertEquals("container", ((Map<String, Object>) x).get(Referable.IDSHORT)));

		// Read nested property
		String pathToNestedContainer = "/submodel/submodelElements/containerRoot/container";
		String pathToNestedProperty = pathToNestedContainer + "/integerProperty/";
		result = submodelElement.getModelPropertyValue(pathToNestedProperty);
		property = (Map<String, Object>) result;
		assertEquals(123, property.get(Property.VALUE));
	}

	/**
	 * Test updating single property
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateProperty() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Wrap object before updating element
		Map<String, Object> updatedElement = new HashMap<>();
		updatedElement.put(Property.VALUE, 3);
		updatedElement.put("valueType", PropertyValueTypeDefHelper.getTypeWrapperFromObject(3));

		// Update element
		submodelElement.setModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty/value", updatedElement);

		// Check result
		Map<String, Object> result = (Map<String, Object>) submodelElement
				.getModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty");
		assertEquals(3, result.get(Property.VALUE));
	}

	/**
	 * Test reading all properties of the submodel
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadProperties() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Collection<Map<String, Object>> set = (Collection<Map<String, Object>>) submodel.getModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES);
		// Should be two properties, one collection and four operations
		assertEquals(3, set.size());
	}

	/**
	 * Test reading all operations of the submodel
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadOperations() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Collection<Map<String, Object>> set = (Collection<Map<String, Object>>) submodel.getModelPropertyValue("/submodel/operations");
		assertEquals(4, set.size());
	}

	/**
	 * Test reading a single operation
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadSingleOperation() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Map<String, Object> operation = (Map<String, Object>) submodel.getModelPropertyValue("/submodel/operations/simple");
		assertEquals("simple", operation.get(Identifiable.IDSHORT));
	}

	/**
	 * Test reading all submodel elements of the submodel
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadSubModelElements() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Collection<Map<String, Object>> set = (Collection<Map<String, Object>>) submodel
				.getModelPropertyValue("/submodel/submodelElements");
		assertEquals(8, set.size());
	}

	/**
	 * Test deleting a single property
	 */
	@Test
	public void testDeleteProperty() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Delete property
		submodelElement.deleteValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty");

		// Test, if it has been deleted
		try {
			submodelElement.getModelPropertyValue("/submodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty");
			fail();
		} catch (ResourceNotFoundException e) {}
	}

	/**
	 * Test deleting a single operation
	 */
	@Test
	public void testDeleteOperation() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Delete operation
		submodelElement.deleteValue("/submodel/operations/simple");

		// Test, if it has been deleted
		try {
			submodelElement.getModelPropertyValue("/submodel/operations/simple");
			fail();
		} catch (ResourceNotFoundException e) {}
	}

	/**
	 * Test invoking an operation
	 */
	@Test
	public void testInvokeOperation() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Wrap parameters before invoking add-operation
		Map<String, Object> param1 = new HashMap<>();
		param1.put("idShort", "SecondNumber");
		param1.put(Property.VALUE, 5);
		param1.put("valueType", PropertyValueTypeDefHelper.getTypeWrapperFromObject(5));
		Map<String, Object> param2 = new HashMap<>();
		param2.put("idShort", "FirstNumber");
		param2.put(Property.VALUE, 2);
		param2.put("valueType", PropertyValueTypeDefHelper.getTypeWrapperFromObject(2));

		// Invoke operation with wrapped parameters and check result
		Object result = submodelElement.invokeOperation("/submodel/operations/complex", param1, param2);
		assertEquals(3, result);

		// Invoke operation on parent element
		result = submodelElement.invokeOperation("/submodel/operations/simple");
		assertTrue((boolean) result);
	}
}
