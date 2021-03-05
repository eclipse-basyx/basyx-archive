/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.testsuite.regression.submodel.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.facade.SubmodelElementMapCollectionConverter;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueTypeHelper;
import org.eclipse.basyx.submodel.restapi.MultiSubmodelElementProvider;
import org.eclipse.basyx.submodel.restapi.OperationProvider;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.submodel.restapi.operation.CallbackResponse;
import org.eclipse.basyx.submodel.restapi.operation.ExecutionState;
import org.eclipse.basyx.submodel.restapi.operation.InvocationResponse;
import org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.operation.AsyncOperationHelper;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.TestsuiteDirectory;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.ConnectorFactory;
import org.junit.Test;

public class SubmodelProviderTest {
	private VABConnectionManager connManager;
	protected static final String submodelAddr = "urn:fhg:es.iese:aas:1:1:submodel";

	protected VABConnectionManager getConnectionManager() {
		if (connManager == null) {
			connManager = new VABConnectionManager(new TestsuiteDirectory(), new ConnectorFactory() {
				@Override
				protected IModelProvider createProvider(String addr) {
					// Simple submodel for testing specific mappings for submodels
					return new SubmodelProvider(new SimpleAASSubmodel("mySubmodelId"));
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
		SubmodelProvider provider = new SubmodelProvider(new SimpleAASSubmodel("mySubmodelId"));
		provider.getValue("/submodel");
		provider.getValue("/submodel/");

		try {
			provider.getValue("invalid");
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
		submodelElement.setValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/newProperty", prop);

		// Read back value
		Integer result = (Integer) submodelElement
				.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/newProperty/value");
		assertEquals(500, result.intValue());
	}

	/**
	 * Test creating single property
	 */
	@Test
	public void testCreatePropertyInCollection() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);
		
		// Create element
		Property prop = new Property(500);
		prop.setIdShort("newProperty");
		submodelElement.setValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/containerRoot/newProperty", prop);
		
		// Read back value
		Integer result = (Integer) submodelElement
				.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/containerRoot/newProperty/value");
		assertEquals(500, result.intValue());


		submodelElement.setValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/containerRoot/container/newProperty", prop);

		// Read back value
		result = (Integer) submodelElement
				.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/containerRoot/container/newProperty/value");
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
		Object result = submodelElement.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "");
		Collection<Map<String, Object>> propertySet = (Collection<Map<String, Object>>) result;
		Map<String, Object> property = propertySet.stream().filter(elem -> elem.get(Identifiable.IDSHORT).equals("integerProperty")).findFirst().get();
		assertEquals(123, property.get(Property.VALUE));

		// Read whole property
		result = submodelElement.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty");
		property = (Map<String, Object>) result;
		assertEquals(123, property.get(Property.VALUE));

		// Read idShort
		result = submodelElement.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/stringProperty");
		property = (Map<String, Object>) result;
		assertEquals("stringProperty", property.get(Identifiable.IDSHORT));

		// Read single value
		String resString = (String) submodelElement
				.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/stringProperty/value");
		assertEquals("Test", resString);

		// Read null value
		Object resObject = submodelElement
				.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/nullProperty/value");
		assertEquals(null, resObject);

		// Read container property
		Collection<Object> resSet = (Collection<Object>) submodelElement
				.getValue("/submodel/submodelElements/containerRoot/value");
		assertEquals(1, resSet.size());
		
		// Get Collection from root-Collection
		Map<String, Object> container = (Map<String, Object>) resSet.iterator().next();
		
		assertEquals("container", container.get(Referable.IDSHORT));
		assertTrue(container.get(Property.VALUE) instanceof Collection<?>);
		
		// Get Value of nested Collection
		Map<String, Object> containerValue = SubmodelElementMapCollectionConverter.convertCollectionToIDMap(container.get(Property.VALUE));
		
		// Check content of nested Collection
		assertTrue(containerValue.containsKey("operationId"));
		assertTrue(containerValue.containsKey("integerProperty"));
		assertEquals(123, ((Property) containerValue.get("integerProperty")).getValue());

		// Read nested property
		String pathToNestedContainer = "/submodel/submodelElements/containerRoot/container";
		String pathToNestedProperty = pathToNestedContainer + "/integerProperty/";
		result = submodelElement.getValue(pathToNestedProperty);
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

		// Update element
		submodelElement.setValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty/value", 3);

		// Check result
		Map<String, Object> result = (Map<String, Object>) submodelElement
				.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty");
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
		String path = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS, "containerRoot");
		submodelElement.setValue(path + "/value", smElements);
		
		// read back the collection
		Map<String, Object> map = (Map<String, Object>) submodelElement
				.getValue(path);
		
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

		String path = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS,
				"containerRoot", "container", "integerProperty", "value");

		Integer value = (Integer) submodelElement.getValue(path);
		assertEquals(123, value.intValue());

		submodelElement.setValue(path, 321);

		value = (Integer) submodelElement.getValue(path);
		assertEquals(321, value.intValue());
	}

	/**
	 * Test reading a single operation
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadSingleOperation() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Map<String, Object> operation = (Map<String, Object>) submodel.getValue("/submodel/submodelElements/simple");
		assertEquals("simple", operation.get(Identifiable.IDSHORT));
		
		try {
			submodel.getValue("/submodel/submodelElements/simple/value");
			fail();
		} catch (MalformedRequestException e) {
			// An Operation has no value
		}
	}

	/**
	 * Checks if the submodel elements in a read submodel are within a collection
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadSubmodelCheckElementsInCollection() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Map<String, Object> smMap = (Map<String, Object>) submodel.getValue("/submodel");
		Object o = smMap.get(Submodel.SUBMODELELEMENT);
		assertTrue(o instanceof Collection<?>);
	}

	/**
	 * Test reading all submodel elements of the submodel
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadSubmodelElements() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Collection<Map<String, Object>> set = (Collection<Map<String, Object>>) submodel
				.getValue("/submodel/submodelElements");
		assertEquals(8, set.size());
	}

	/**
	 * Test deleting a single property
	 */
	@Test
	public void testDeleteProperty() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Delete property
		submodelElement.deleteValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty");

		// Test, if it has been deleted
		try {
			submodelElement.getValue("/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty");
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
			submodelElement.getValue("/submodel/submodelElements/simple");
			fail();
		} catch (ResourceNotFoundException e) {}
	}
	
	/**
	 * Test deleting a single property from a SubmodelElementCollection
	 */
	@Test
	public void testDeletePropertyFromCollection() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		String path = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS,
				"containerRoot", "container", "integerProperty");
		
		assertNotNull(submodelElement.getValue(path));

		// Delete property
		submodelElement.deleteValue(path);
		
		// Test if parent Collection is still there
		assertNotNull(submodelElement.getValue(VABPathTools.getParentPath(path)));

		// Test, if it has been deleted
		try {
			submodelElement.getValue(path);
			fail();
		} catch (ResourceNotFoundException e) {}
		
		// Test delete the Collection "container"
		path = VABPathTools.getParentPath(path);
		
		// Delete property
		submodelElement.deleteValue(path);
		
		// Test if parent Collection is still there
		assertNotNull(submodelElement.getValue(VABPathTools.getParentPath(path)));

		// Test, if it has been deleted
		try {
			submodelElement.getValue(path);
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
		Map<String, Object> param1 = wrapParameter("FirstNumber", 5);
		Map<String, Object> param2 = wrapParameter("SecondNumber", 2);

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
		
		String path = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS,
				"containerRoot", "container", "operationId", "invoke");
		
		Object result = submodelElement.invokeOperation(path);
		assertEquals(123, result);
	}
	
	/**
	 * Test getting /values of the Submodel
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetValues() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);
		Map<String, Object> values = (Map<String, Object>) submodelElement.getValue("submodel/" + SubmodelProvider.VALUES);
		
		assertEquals(4, values.size());
		
		// Check if all expected Values are present
		assertTrue(values.containsKey("containerRoot"));
		Map<String, Object> collection1 = (Map<String, Object>) values.get("containerRoot");
		
		assertTrue(collection1.containsKey("container"));
		Map<String, Object> collection2 = (Map<String, Object>) collection1.get("container");
		
		// Check the Value in /containerRoot/container/integerProperty
		assertEquals(123, collection2.get("integerProperty"));
		
		assertEquals("Test", values.get("stringProperty"));
		assertEquals(123, values.get("integerProperty"));
		assertEquals(null, values.get("nullProperty"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testInvokeAsync() throws Exception {
		VABElementProxy elementProxy = getConnectionManager().connectToVABElement(submodelAddr);
		AsyncOperationHelper helper = new AsyncOperationHelper();
		
		String path = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS, AsyncOperationHelper.ASYNC_OP_ID);
		elementProxy.setValue(path, helper.getAsyncOperation());
		
		// Wrap parameters before invoking add-operation
		Map<String, Object> param1 = wrapParameter("FirstNumber", 5);
		Map<String, Object> param2 = wrapParameter("SecondNumber", 2);
		
		path = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS, AsyncOperationHelper.ASYNC_OP_ID, "invoke?async=true");
		
		CallbackResponse response = CallbackResponse.createAsFacade((Map<String, Object>) elementProxy.invokeOperation(path, param1, param2));
		String requestId = response.getRequestId();

		String listPath = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS, AsyncOperationHelper.ASYNC_OP_ID, OperationProvider.INVOCATION_LIST);
		
		// Try correct operationId, wrong requestId
		try {
			elementProxy.getValue(VABPathTools.append(listPath, "nonexistent"));
			fail();
		} catch (ResourceNotFoundException e) {
		}
		
		// Try wrong operationId, correct requestId
		try {
			elementProxy.getValue("/submodel/submodelElements/simple/invocationList/" + requestId);
			fail();
		} catch (ResourceNotFoundException e) {
		}
		
		String requestPath = VABPathTools.append(listPath, requestId);
		
		// Check that it has not finished yet
		InvocationResponse result = (InvocationResponse) elementProxy.getValue(requestPath);
		assertEquals(ExecutionState.INITIATED, result.getExecutionState());
		
		helper.releaseWaitingOperation();
		
		result = (InvocationResponse) elementProxy.getValue(requestPath);
		assertEquals(7, result.getFirstOutput());
		
		// Check if the async-invocation is deleted after retrieving its result
		try {
			elementProxy.getValue(requestPath);
			fail();
		} catch (ResourceNotFoundException e) {
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testInvokeAsyncException() throws Exception {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);
		AsyncOperationHelper helper = new AsyncOperationHelper();
		
		String path = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS, AsyncOperationHelper.ASYNC_EXCEPTION_OP_ID);
		submodelElement.setValue(path, helper.getAsyncExceptionOperation());

		path = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS,
				AsyncOperationHelper.ASYNC_EXCEPTION_OP_ID, "invoke?async=true");
		
		CallbackResponse response = (CallbackResponse) submodelElement.invokeOperation(path);
		String requestId = response.getRequestId();
		
		String requestPath = VABPathTools.concatenatePaths("submodel", MultiSubmodelElementProvider.ELEMENTS,
				AsyncOperationHelper.ASYNC_EXCEPTION_OP_ID, OperationProvider.INVOCATION_LIST, requestId);
		
		// Check that it has not finished yet
		InvocationResponse invResp = InvocationResponse
				.createAsFacade((Map<String, Object>) submodelElement.getValue(requestPath));
		assertNotEquals(ExecutionState.COMPLETED, invResp.getExecutionState());
		assertNotEquals(ExecutionState.FAILED, invResp.getExecutionState());
		
		helper.releaseWaitingOperation();
		

		invResp = InvocationResponse
				.createAsFacade((Map<String, Object>) submodelElement.getValue(requestPath));
		assertEquals(ExecutionState.FAILED, invResp.getExecutionState());
		
		// Check if the async-invocation is deleted after retrieving its result
		try {
			submodelElement.getValue(requestPath);
			fail();
		} catch (ResourceNotFoundException e) {
		}		
	}
	
	private Map<String, Object> wrapParameter(String name, Object value) {
		Map<String, Object> param = new HashMap<>();
		param.put(Identifiable.IDSHORT, name);
		param.put(Property.VALUE, value);
		param.put(Property.VALUETYPE, ValueTypeHelper.getType(value).toString());
		return param;
	}
}
