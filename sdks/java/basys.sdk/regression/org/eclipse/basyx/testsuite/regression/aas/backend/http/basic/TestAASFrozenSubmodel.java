package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.ConnectedElement;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;




/**
 * AAS Operations test case
 * 
 * Context: Static configured topology
 * - Connect to AAS "Stub1AAS"
 * - Connect to sub model "statusSM"
 * - Connect to properties "sampleProperty1" and "sampleProperty2"
 * - Retrieve values of both properties
 * 
 * @author pschorn
 *
 */
class TestAASFrozenSubmodel {
	

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	protected ConnectedAssetAdministrationShellManager aasManager2 = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	
	/**
	 *  Connect to AAS with ID "Stub1AAS"
	 *  - Retrieve connected AAS from AAS ID
	 */
	IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("RestAAS");
	IAssetAdministrationShell connAAS2 = this.aasManager2.retrieveAAS("RestAAS");

	
	/**
	 *  Connect to sub model from possibly different clients
	 */
	ISubModel submodel = ((HashMap<String, ISubModel>) connAAS.getSubModels()).get("frozenSM");
	ISubModel submodel2 = connAAS2.getSubModels().get("frozenSM");	
	
	
	/**
	 * Test if submodel is correctly frozen and read-only server-exception is thrown 
	 */
	@Test
	void testSubmodelFrozen() throws Exception {

		
		// Connect to sub model property and change value
		((ISingleProperty) submodel.getProperties().get("sampleProperty1")).set(4);
		
		
		// Freeze Submodel
		submodel.freeze();
		assertTrue(submodel.isFrozen());
		
		// Test if you can still change any property 
		try {
			
			((ISingleProperty) submodel.getProperties().get("sampleProperty1")).set(6);
			fail( "ServerException (ReadOnlyException) was not thrown" );
			
			} catch (ServerException e) {
				
				System.out.println("ServerException was correctly thrown: "+e.getMessage());
				try {
					
					// Test if you can still change a property (from another client)
					((ISingleProperty) submodel2.getProperties().get("sampleProperty1")).set(7);
					fail( "ServerException (ReadOnlyException) was not thrown" );
				} catch (ServerException e2) {
					System.out.println("ServerException was correctly thrown: "+e2.getMessage());
				}
		}
		
		((ConnectedElement) submodel.getProperties().get("sampleProperty1")).invalidate();
				
		int property1Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).get();
		
		// Test if property value has not changed
		assertTrue(property1Val == 4);
		
		// Unfreeze Submodel
		submodel.unfreeze();
		assertTrue(!submodel.isFrozen());
		
		// Reset property value
		((ISingleProperty) submodel.getProperties().get("sampleProperty1")).set(2);
		

	}
}

