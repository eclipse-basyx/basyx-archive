package org.eclipse.basyx.testsuite.regression.aas.backend.basyx.basic;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnector;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory_BaSyxNative;
import org.junit.jupiter.api.Test;




/**
 * AAS Operations test case
 * 
 * Context: Static configured topology
 * - Connect to AAS "Stub1AAS"
 * - Connect to sub model "statusSM"
 * - Connect to referenced sub model "Stub2SM"
 * - Connect to properties "samplePropertyA" and "samplePropertyB"
 * - Retrieve values of both properties
 * 
 * @author kuhn
 *
 */
class TestAASSimpleGetPropertyFromReferencedSubmodel {
	

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory_BaSyxNative(), new BaSyxConnector());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub1AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
		
		
		// Connect to sub model
		// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
		// - Retrieve connected sub model
		// - FIXME: Get submodel by type or ID		
		ISubModel submodel = ((HashMap<String, ISubModel>) connAAS.getSubModels()).get("statusSM");		
		
		// Connect to sub model property
		// - Get property values (shortcut here, we know that it is a single property type)
		int property1Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).get();
		int property2Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty2")).get();

		// Connect to referenced sub model property
		int property3Val = (int) ((ISingleProperty) submodel.getProperties().get("subModelPropertyRefLoc")).get();
		int property4Val = (int) ((ISingleProperty) submodel.getProperties().get("subModelPropertyRefGlob")).get();
		
		// Check test case results
		assertTrue(property1Val == 2);
		assertTrue(property2Val == 3);
		assertTrue(property3Val == 5);
		assertTrue(property4Val == 9);
		
		
		
		// Connect to referenced sub model (local)
		ISubModel refSubmodelLoc = (ISubModel) ((ISingleProperty) submodel.getProperties().get("subModelRefLocal")).get();
		{
			// Connect to sub model property
			// - Get property values (shortcut here, we know that it is a single property type)
			int property5Val = (int) ((ISingleProperty) refSubmodelLoc.getProperties().get("samplePropertyA")).get();
			int property6Val = (int) ((ISingleProperty) refSubmodelLoc.getProperties().get("samplePropertyB")).get();
			// - Check test case results
			assertTrue(property5Val == 5);
			assertTrue(property6Val == 6);
		}

		
		// Connect to referenced sub model (global)
		ISubModel refSubmodelGlob = (ISubModel) ((ISingleProperty) submodel.getProperties().get("subModelRefGlobal")).get();
		{
			// Connect to sub model property
			// - Get property values (shortcut here, we know that it is a single property type)
			int property5Val = (int) ((ISingleProperty) refSubmodelGlob.getProperties().get("samplePropertyD")).get();
			int property6Val = (int) ((ISingleProperty) refSubmodelGlob.getProperties().get("samplePropertyE")).get();
			// - Check test case results
			assertTrue(property5Val == 8);
			assertTrue(property6Val == 9);
		}
		
		
		// Connect to referenced aas
		IAssetAdministrationShell refAAS = (IAssetAdministrationShell) ((ISingleProperty) submodel.getProperties().get("referencedAAS")).get();
		{
			// - Connect to sub model
			ISubModel refAASSubModel = ((HashMap<String, ISubModel>) refAAS.getSubModels()).get("mainSM");
			// - Get property values (shortcut here, we know that it is a single property type)
			int property7Val = (int) ((ISingleProperty) refAASSubModel.getProperties().get("samplePropertyD")).get();
			int property8Val = (int) ((ISingleProperty) refAASSubModel.getProperties().get("samplePropertyE")).get();
			// - Check test case results
			assertTrue(property7Val == 8);
			assertTrue(property8Val == 9);
		}
	}
}

