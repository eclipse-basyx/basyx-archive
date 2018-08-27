package org.eclipse.basyx.testsuite.regression.aas.impl.references;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
import org.junit.jupiter.api.Test;



/**
 * Test case for IModelProviders. It checks whether IModelProviders are able to perform basic VAB operations on AAS without
 * external references in their AAS or sub models.  
 * 
 *  TODO adapt test cases to new identifier pattern
 * 
 * @author kuhn
 *
 */
public class TestAASPathAccess {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		// Create model provider
		JavaObjectProvider subModelProvider = new JavaObjectProvider();
		// - Create AAS and sub model instances
		IAssetAdministrationShell stub1AAS = new Stub1AAS();
		ISubModel                 stub1SM  = new Stub1Submodel(stub1AAS);
		// - Add models to provider
		subModelProvider.addModel(stub1AAS);
		subModelProvider.addModel(stub1SM);

		// Get IDs
		String aasID     = BaSysID.instance.buildAASID("Stub1AAS");
		String smID      = BaSysID.instance.buildSMID("statusSM");
		String qualSMID  = BaSysID.instance.buildPath("Stub1AAS", "statusSM");
		String prop1ID   = BaSysID.instance.buildPath("Stub1AAS", "statusSM", "sampleProperty1", "properties");
		String prop2ID    =BaSysID.instance.buildPath("Stub1AAS", "statusSM", "sampleProperty2", "properties");
		String prop3ID   = BaSysID.instance.buildPath("Stub1AAS", "statusSM", "sampleProperty3", "properties");
		String prop3AID  = BaSysID.instance.buildPath("Stub1AAS", "statusSM", "sampleProperty3.samplePropertyA", "properties" );
		String prop3BID  = BaSysID.instance.buildPath("Stub1AAS", "statusSM", "sampleProperty3.samplePropertyB", "properties" );

		
		// - Get AAS sub model property values via AAS
		IAssetAdministrationShell aasStub      = (IAssetAdministrationShell) subModelProvider.getModelPropertyValue(aasID);
		ISubModel                 smStub1      = (ISubModel)                 subModelProvider.getModelPropertyValue(smID);
		ISubModel                 smStub2      = (ISubModel)                 subModelProvider.getModelPropertyValue(qualSMID);
		Object                    property1    =                             subModelProvider.getModelPropertyValue(prop1ID);
		Object                    property2    =                             subModelProvider.getModelPropertyValue(prop2ID);
		Object                    property3    =                             subModelProvider.getModelPropertyValue(prop3ID);
		Object                    property3A   =                             subModelProvider.getModelPropertyValue(prop3AID);
		Object                    property3B   =                             subModelProvider.getModelPropertyValue(prop3BID);
		
		// - Check results
		assertTrue(aasStub==stub1AAS);
		assertTrue(smStub1==stub1SM);
		assertTrue(smStub2==stub1SM);
		assertTrue((int) property1==2);
		assertTrue((int) property2==3);
		assertTrue(property3 instanceof Stub1Submodel.NestedPropertyType);
		assertTrue((int) property3A==4);
		assertTrue((int) property3B==5);
		
		
		
		// Get AAS sub model property values via unique sub model ID
		ISubModel                 subMode2Stub = (ISubModel)                 subModelProvider.getModelPropertyValue("statusSM/submodel");
		Object                    property1a   =                             subModelProvider.getModelPropertyValue("statusSM/submodel/properties/sampleProperty1");
		Object                    property2a   =                             subModelProvider.getModelPropertyValue("statusSM/submodel/properties/sampleProperty2");

		// - Check results
		assertTrue(subMode2Stub==stub1SM);
		assertTrue((int) property1a==2);
		assertTrue((int) property2a==3);
		
		
		
		// Set AAS property values TODO expand this to be the ModelProvider Testsuite
		
	}
}


