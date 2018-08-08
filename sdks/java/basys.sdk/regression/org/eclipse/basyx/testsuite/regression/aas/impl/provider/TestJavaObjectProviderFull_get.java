package org.eclipse.basyx.testsuite.regression.aas.impl.provider;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
import org.junit.jupiter.api.Test;



/**
 * Test case for IModelProviders. It checks whether IModelProviders are able to perform basic VAB operations on AAS without
 * external references in their AAS or sub models. No serialization is evaluated.
 * 
 * @author kuhn
 *
 */
public class TestJavaObjectProviderFull_get {


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
		
		
		// Get AAS sub model property values via AAS
		Collection<String>        modelNames   =                             subModelProvider.getAllModels();
		IAssetAdministrationShell aasStub      = (IAssetAdministrationShell) subModelProvider.getModelPropertyValue("Stub1AAS/aas");
		ISubModel                 subModelStub = (ISubModel)                 subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM");
		Object                    property1    =                             subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty1");
		Object                    property2    =                             subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty2");
		Object                    property3    =                             subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3");
		Object                    property3A   =                             subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.samplePropertyA");
		Object                    property3B   =                             subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.samplePropertyB");
		
		// - Check results
		assertTrue(modelNames.size()==2);
		assertTrue(modelNames.contains("statusSM"));
		assertTrue(modelNames.contains("Stub1AAS"));
		assertTrue(aasStub==stub1AAS);
		assertTrue(subModelStub==stub1SM);
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
		
	}
}


