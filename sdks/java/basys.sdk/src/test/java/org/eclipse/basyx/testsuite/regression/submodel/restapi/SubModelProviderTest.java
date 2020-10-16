package org.eclipse.basyx.testsuite.regression.submodel.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
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
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.ConnectorProvider;
import org.junit.Test;

public class SubModelProviderTest {
	private VABConnectionManager connManager;
	protected static final String submodelAddr = "urn:fhg:es.iese:aas:1:1:submodel";

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
	@Test
	public void testCreateProperty() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Create element
		Property prop = new Property(500);
		prop.setIdShort("newProperty");
		submodelElement.setModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "", prop);

		// Read back value
		Integer result = (Integer) submodelElement
				.getModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "/newProperty/value");
		assertEquals(500, result.intValue());
	}

	/**
	 * Test reading single property
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadProperty() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Read list of properties
		Object result = submodelElement.getModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "");
		Collection<Map<String, Object>> propertySet = (Collection<Map<String, Object>>) result;
		Map<String, Object> property = propertySet.stream().filter(elem -> elem.get(Identifiable.IDSHORT).equals("integerProperty")).findFirst().get();
		assertEquals(123, property.get(Property.VALUE));

		// Read whole property
		result = submodelElement.getModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "/integerProperty");
		property = (Map<String, Object>) result;
		assertEquals(123, property.get(Property.VALUE));

		// Read idShort
		result = submodelElement.getModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "/stringProperty");
		property = (Map<String, Object>) result;
		assertEquals("stringProperty", property.get(Identifiable.IDSHORT));

		// Read single value
		String resString = (String) submodelElement
				.getModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "/stringProperty/value");
		assertEquals("Test", resString);

		// Read null value
		Object resObject = submodelElement
				.getModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "/nullProperty/value");
		assertEquals(null, resObject);

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
		submodelElement.setModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "/integerProperty/value", updatedElement);

		// Check result
		Map<String, Object> result = (Map<String, Object>) submodelElement
				.getModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "/integerProperty");
		assertEquals(3, result.get(Property.VALUE));
		

	}
	
	/**
	 * Test updating a SubmodelElementCollection
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateSmElementCollection() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);
		
		Collection<ISubmodelElement> smElements = new ArrayList<>();
		Property newProperty = new Property("propValue");
		newProperty.setIdShort("propIdShort");
		smElements.add(newProperty);
		
		// update value of smElemCollection
		String path = VABPathTools.concatenatePaths("submodel", SubmodelElementProvider.ELEMENTS, "containerRoot");
		submodelElement.setModelPropertyValue(path + "/value", smElements);
		
		// read back the collection
		Map<String, Object> map = (Map<String, Object>) submodelElement
				.getModelPropertyValue(path);
		
		assertTrue(map.get(Property.VALUE) instanceof Collection<?>);
		
		Collection<Map<String, Object>> elements = (Collection<Map<String, Object>>) map.get(Property.VALUE);
		assertEquals(1, elements.size());
		
		Iterator<Map<String, Object>> i = elements.iterator();
		
		assertEquals("propIdShort", i.next().get(Referable.IDSHORT));
	}

	/**
	 * Test updating a Property inside a SubmodelElementCollection 
	 */
	@Test
	public void testUpdateElementInSmElementCollection() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		String path = VABPathTools.concatenatePaths("submodel", SubmodelElementProvider.ELEMENTS,
				"containerRoot", "container", "integerProperty", "value");

		Integer value = (Integer) submodelElement.getModelPropertyValue(path);
		assertEquals(123, value.intValue());

		submodelElement.setModelPropertyValue(path, 321);

		value = (Integer) submodelElement.getModelPropertyValue(path);
		assertEquals(321, value.intValue());
	}

	/**
	 * Test reading a single operation
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadSingleOperation() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Map<String, Object> operation = (Map<String, Object>) submodel.getModelPropertyValue("/submodel/submodelElements/simple");
		assertEquals("simple", operation.get(Identifiable.IDSHORT));
	}

	/**
	 * Checks if the submodel elements in a read submodel are within a collection
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadSubmodelCheckElementsInCollection() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Map<String, Object> smMap = (Map<String, Object>) submodel.getModelPropertyValue("/submodel");
		Object o = smMap.get(SubModel.SUBMODELELEMENT);
		assertTrue(o instanceof Collection<?>);
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
		submodelElement.deleteValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "/integerProperty");

		// Test, if it has been deleted
		try {
			submodelElement.getModelPropertyValue("/submodel/" + SubmodelElementProvider.ELEMENTS + "/integerProperty");
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
		submodelElement.deleteValue("/submodel/submodelElements/simple");

		// Test, if it has been deleted
		try {
			submodelElement.getModelPropertyValue("/submodel/submodelElements/simple");
			fail();
		} catch (ResourceNotFoundException e) {}
	}
	
	/**
	 * Test deleting a single property from a SubmodelElementCollection
	 */
	@Test
	public void testDeletePropertyFromCollection() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		String path = VABPathTools.concatenatePaths("submodel", SubmodelElementProvider.ELEMENTS,
				"containerRoot", "container", "integerProperty");
		
		assertNotNull(submodelElement.getModelPropertyValue(path));

		// Delete property
		submodelElement.deleteValue(path);
		
		// Test if parent Collection is still there
		assertNotNull(submodelElement.getModelPropertyValue(VABPathTools.getParentPath(path)));

		// Test, if it has been deleted
		try {
			submodelElement.getModelPropertyValue(path);
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Test delete the Collection "container"
		path = VABPathTools.getParentPath(path);
		
		// Delete property
		submodelElement.deleteValue(path);
		
		// Test if parent Collection is still there
		assertNotNull(submodelElement.getModelPropertyValue(VABPathTools.getParentPath(path)));

		// Test, if it has been deleted
		try {
			submodelElement.getModelPropertyValue(path);
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
		Object result = submodelElement.invokeOperation("/submodel/submodelElements/complex/invoke", param1, param2);
		assertEquals(3, result);

		// Invoke operation on parent element
		result = submodelElement.invokeOperation("/submodel/submodelElements/simple/invoke");
		assertTrue((boolean) result);
	}
	
	/**
	 * Test invoking an operation from within a SubmodelElementCollection
	 */
	@Test
	public void testInvokeOperationInCollection() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);
		
		String path = VABPathTools.concatenatePaths("submodel", SubmodelElementProvider.ELEMENTS,
				"containerRoot", "container", "operationId", "invoke");
		
		Object result = submodelElement.invokeOperation(path);
		assertEquals(123, result);
	}
}
