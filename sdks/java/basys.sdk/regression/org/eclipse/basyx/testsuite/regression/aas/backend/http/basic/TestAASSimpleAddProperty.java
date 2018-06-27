package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.ConnectedElement;
import org.eclipse.basyx.aas.backend.ConnectedSubmodel;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;




/**
 * AAS Add Property test case
 * 
 * Test if all property types can be added to a remote submodel
 * 
 * @author pschorn
 *
 */
class TestAASSimpleAddProperty {

	/**
	 * Set up 
	 */
	// Store HTTP asset administration shell manager backend
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	
	// Connect to AAS with ID "Stub1AAS"
	// - Retrieve connected AAS from AAS ID
	IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");


	// Connect to sub model
	// - Retrieve connected sub model
	ConnectedSubmodel submodel = (ConnectedSubmodel) connAAS.getSubModels().get("statusSM");		
	
		
	/**
	 * Test Add Single Property 
	 * TODO Fix infinite loop fault
	 * @throws Exception
	 */
	@Test 
	void testAddSingleProperty() throws Exception {

		// Create sample integer property 
		Property property1 = new Property();
		property1.setName("newProperty1");
		property1.setId("newProperty1");
		property1.setDataType(DataType.INTEGER);
		
		// Create new property on the connected submodel
		submodel.createProperty(property1); 
		
		// Connect to the new property 
		ISingleProperty connected_property1 = ((ISingleProperty) submodel.getProperties().get("newProperty1"));
		
		// Set value to 14
		connected_property1.set(14); // ok, sometimes http request parameter are not transmitted reliably?
		
		// Check if the value has been set
		((ConnectedElement) connected_property1).invalidate();
		int result = (int) connected_property1.get();
		assertTrue(result == 14);
		
	}
	
	/**
	 * Test Add Collection Property 
	 * @throws Exception
	 */
	@Test 
	void testAddCollectionProperty() throws Exception {
		
		Property property2 = new Property();
		property2.setName("newCollection");
		property2.setId("newCollection");
		property2.setDataType(DataType.COLLECTION);
		property2.setCollection(true);
		
		// Create new property on the connected submodel
		submodel.createProperty(property2); 
		
		// Connect to the new property 
		ICollectionProperty connected_property2 = ((ICollectionProperty) submodel.getProperties().get("newCollection"));
		
		// Set new collection
		Collection<Object> collection = new ArrayList<Object>(); collection.add(2);collection.add(3);collection.add(4);
		connected_property2.set(collection);
		
		// Check if collection was successfully instantiated
		((ConnectedElement) connected_property2).invalidate();
		int x0 = (int) connected_property2.get(0);
		int x1 = (int) connected_property2.get(1);
		int x2 = (int) connected_property2.get(2);
		
		assertTrue(x0 == 2);
		assertTrue(x1 == 3);
		assertTrue(x2 == 4);
		
	}
	
	
	/**
	 * Test Add Map Property
	 * @throws Exception
	 */
	@Test 
	void testAddMapProperty() throws Exception {
		
		Property property3 = new Property();
		property3.setName("newMap");
		property3.setId("newMap");
		property3.setDataType(DataType.MAP);
		property3.setMap(true);
		
		// Create new property on the connected submodel
		submodel.createProperty(property3); 
		
		// Connect to the new property 
		IMapProperty connected_property3 = ((IMapProperty) submodel.getProperties().get("newMap"));
		
		// Set new map
		Map<String, Object> map = new HashMap<String, Object>(); map.put("two", 2);map.put("three", 3);map.put("four",4);
		connected_property3.set(map);
		
		// Check if map was successfully instantiated
		((ConnectedElement) connected_property3).invalidate();
		int x0 = (int) connected_property3.getValue("two");
		int x1 = (int) connected_property3.getValue("three");
		int x2 = (int) connected_property3.getValue("four");
		
		assertTrue(x0 == 2);
		assertTrue(x1 == 3);
		assertTrue(x2 == 4);
	}
}

