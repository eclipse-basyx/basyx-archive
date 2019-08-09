package org.eclipse.basyx.testsuite.regression.aas.backend.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.testsuite.regression.vab.provider.TestProvider;
import org.eclipse.basyx.testsuite.support.aas.vab.stub.elements.SimpleAASSubmodel;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.Test;

public class VirtualPathModelProviderTest extends TestProvider {
	private VABConnectionManager connManager;
	private static final String submodelAddr = "urn:fhg:es.iese:aas:1:1:submodel";

	@Override
	protected VABConnectionManager getConnectionManager() {
		if (connManager == null) {
			connManager = new VABConnectionManager(new TestsuiteDirectory(), new ConnectorProvider() {
				@Override
				protected IModelProvider createProvider(String addr) {
					if (addr.contains("SimpleAASSubmodel")) {
						// Simple submodel for testing specific mappings for submodels
						return new VirtualPathModelProvider(new SimpleAASSubmodel("mySubmodelId"));
					} else {
						// Simple vab element for testing general provider capabilities
						return new VirtualPathModelProvider(new SimpleVABElement());
					}
				}
			});
		}
		return connManager;
	}

	@Test
	public void testCreateDataElement() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Create element
		Property prop = new MetaModelElementFactory().create(new Property(), 500);
		submodelElement.createElement("dataElements/newProperty", prop);

		// Read back value
		Object result = submodelElement.readElementValue("dataElements/newProperty/value");
		assertEquals(500, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testReadDataElement() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Read list of properties
		Object result = submodelElement.readElementValue("dataElements");
		HashMap<String, Object> propertyMap = (HashMap<String, Object>) result;
		HashMap<String, Object> property = (HashMap<String, Object>) propertyMap.get("integerProperty");
		assertEquals(123, property.get("value"));

		// Read whole property
		result = submodelElement.readElementValue("dataElements/integerProperty");
		property = (HashMap<String, Object>) result;
		assertEquals(123, property.get("value"));

		// Read idShort
		result = submodelElement.readElementValue("dataElements/stringProperty/idShort");
		assertEquals("stringProperty", result);

		// Read single value
		result = submodelElement.readElementValue("dataElements/stringProperty/value");
		assertEquals("Test", result);
	}

	@Test
	public void testUpdateDataElement() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Wrap object before updating element
		HashMap<String, Object> updatedElement = new HashMap<>();
		updatedElement.put("value", 3);
		updatedElement.put("valueType", PropertyValueTypeDefHelper.fromObject(3));

		// Update element
		submodelElement.updateElementValue("dataElements/integerProperty", updatedElement);

		// Check result
		Object result = submodelElement.readElementValue("dataElements/integerProperty");
		assertEquals(3, result);
	}

	@Test
	public void testDeleteDataElement() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Delete property
		submodelElement.deleteElement("dataElements/integerProperty");

		// Test, if it has been deleted
		Object result = submodelElement.readElementValue("dataElements/integerProperty");
		assertNull(result);
	}

	@Test
	public void testInvokeOperation() {
		VABElementProxy submodelElement = getConnectionManager().connectToVABElement(submodelAddr);

		// Wrap parameters before invoking add-operation
		HashMap<String, Object> param1 = new HashMap<>();
		param1.put("idShort", "SecondNumber");
		param1.put("value", 5);
		param1.put("valueType", PropertyValueTypeDefHelper.fromObject(5));
		HashMap<String, Object> param2 = new HashMap<>();
		param2.put("idShort", "FirstNumber");
		param2.put("value", 2);
		param2.put("valueType", PropertyValueTypeDefHelper.fromObject(2));

		// Invoke operation with wrapped parameters and check result
		Object result = submodelElement.invoke("operations/complex", param1, param2);
		assertEquals(3, result);

		// Invoke operation on parent element
		result = submodelElement.invoke("operations/simple");
		assertTrue((boolean) result);
	}
}
