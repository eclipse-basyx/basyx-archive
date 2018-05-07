package org.eclipse.basyx.testsuite.regression.aas.impl.provider;

import static org.junit.Assert.assertTrue;

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
public class TestJavaObjectProviderFull_set {


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
		// - First property "sampleProperty1"
		assertTrue((int) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty1") == 2);
		subModelProvider.setModelPropertyValue("statusSM.Stub1AAS/sampleProperty1", 5);
		assertTrue((int) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty1") == 5);
		subModelProvider.setModelPropertyValue("statusSM.Stub1AAS/sampleProperty1", 2);
		assertTrue((int) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty1") == 2);
		// - Second property "sampleProperty2"
		assertTrue((int) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty2") == 3);
		subModelProvider.setModelPropertyValue("statusSM.Stub1AAS/sampleProperty2", 6);
		assertTrue((int) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty2") == 6);
		subModelProvider.setModelPropertyValue("statusSM.Stub1AAS/sampleProperty2", 3);
		assertTrue((int) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty2") == 3);
		// - Test nested property "sampleProperty3/samplePropertyA"
		assertTrue((int) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty3/samplePropertyA") == 4);
		subModelProvider.setModelPropertyValue("statusSM.Stub1AAS/sampleProperty3/samplePropertyA", 8);
		assertTrue((int) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty3/samplePropertyA") == 8);
		subModelProvider.setModelPropertyValue("statusSM.Stub1AAS/sampleProperty3/samplePropertyA", 2);
		assertTrue((int) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty3/samplePropertyA") == 2);
		
		
		System.out.println(subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/clock"));
		subModelProvider.setModelPropertyValue("statusSM.Stub1AAS/clock", 5);
		
		System.out.println(subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/clock"));

	}
}


