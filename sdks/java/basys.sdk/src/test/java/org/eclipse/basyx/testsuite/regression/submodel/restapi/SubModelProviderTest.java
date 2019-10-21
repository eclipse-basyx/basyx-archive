package org.eclipse.basyx.testsuite.regression.submodel.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.TestsuiteDirectory;
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
	 * Test creating single data elements
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateDataElement() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Create element
		SingleProperty prop = new SingleProperty(500);
		prop.setIdShort("newProperty");
		submodelElement.createValue("/submodel/dataElements", prop);

		// Read back value
		Map<String, Object> result = (Map<String, Object>) submodelElement
				.getModelPropertyValue("/submodel/dataElements/newProperty/value");
		assertEquals(500, result.get(SingleProperty.VALUE));
	}

	/**
	 * Test reading single data elements
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadDataElement() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Read list of properties
		Object result = submodelElement.getModelPropertyValue("/submodel/dataElements");
		Set<Map<String, Object>> propertySet = (Set<Map<String, Object>>) result;
		HashMap<String, Object> property = (HashMap<String, Object>) propertySet.stream().filter(elem -> elem.get(Identifiable.IDSHORT).equals("integerProperty")).findFirst().get();
		assertEquals(123, property.get(SingleProperty.VALUE));

		// Read whole property
		result = submodelElement.getModelPropertyValue("/submodel/dataElements/integerProperty");
		property = (HashMap<String, Object>) result;
		assertEquals(123, property.get(SingleProperty.VALUE));

		// Read idShort
		result = submodelElement.getModelPropertyValue("/submodel/dataElements/stringProperty");
		property = (HashMap<String, Object>) result;
		assertEquals("stringProperty", property.get(Identifiable.IDSHORT));

		// Read single value
		Map<String, Object> resMap = (Map<String, Object>) submodelElement
				.getModelPropertyValue("/submodel/dataElements/stringProperty/value");
		assertEquals("Test", resMap.get(SingleProperty.VALUE));

		// Read container property
		Set<Object> resSet = (Set<Object>) submodelElement
				.getModelPropertyValue("/submodel/dataElements/containerRoot/submodelElement");
		assertEquals(1, resSet.size());
		resSet.forEach(x -> assertEquals("container", ((Map<String, Object>) x).get(Referable.IDSHORT)));

		// Read nested property
		String pathToNestedContainer = "/submodel/dataElements/containerRoot/submodelElement/container";
		String pathToNestedProperty = pathToNestedContainer + "/dataElements/integerProperty/";
		result = submodelElement.getModelPropertyValue(pathToNestedProperty);
		property = (HashMap<String, Object>) result;
		assertEquals(123, property.get(SingleProperty.VALUE));
	}

	/**
	 * Test updating single data elements
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDataElement() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Wrap object before updating element
		HashMap<String, Object> updatedElement = new HashMap<>();
		updatedElement.put(SingleProperty.VALUE, 3);
		updatedElement.put("valueType", PropertyValueTypeDefHelper.fromObject(3));

		// Update element
		submodelElement.setModelPropertyValue("/submodel/dataElements/integerProperty/value", updatedElement);

		// Check result
		Map<String, Object> result = (Map<String, Object>) submodelElement
				.getModelPropertyValue("/submodel/dataElements/integerProperty");
		assertEquals(3, result.get(SingleProperty.VALUE));
	}

	/**
	 * Test reading all data elements of the submodel
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadDataElements() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) submodel.getModelPropertyValue("/submodel/dataElements");
		assertEquals(3, set.size());
	}

	/**
	 * Test reading all operations of the submodel
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadOperations() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) submodel.getModelPropertyValue("/submodel/operations");
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
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) submodel.getModelPropertyValue("/submodel/submodelElement");
		assertEquals(7, set.size());
	}

	/**
	 * Test deleting a single data element
	 */
	@Test
	public void testDeleteDataElement() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Delete property
		submodelElement.deleteValue("/submodel/dataElements/integerProperty");

		// Test, if it has been deleted
		Object result = submodelElement.getModelPropertyValue("/submodel/dataElements/integerProperty");
		assertNull(result);
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
		Object result = submodelElement.getModelPropertyValue("/submodel/operations/simple");
		assertNull(result);
	}

	/**
	 * Test invoking an operation
	 */
	@Test
	public void testInvokeOperation() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Wrap parameters before invoking add-operation
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("idShort", "SecondNumber");
		param1.put(SingleProperty.VALUE, 5);
		param1.put("valueType", PropertyValueTypeDefHelper.fromObject(5));
		HashMap<String, Object> param2 = new HashMap<>();
		param2.put("idShort", "FirstNumber");
		param2.put(SingleProperty.VALUE, 2);
		param2.put("valueType", PropertyValueTypeDefHelper.fromObject(2));

		// Invoke operation with wrapped parameters and check result
		Object result = submodelElement.invokeOperation("/submodel/operations/complex", param1, param2);
		assertEquals(3, result);

		// Invoke operation on parent element
		result = submodelElement.invokeOperation("/submodel/operations/simple");
		assertTrue((boolean) result);
	}
}
