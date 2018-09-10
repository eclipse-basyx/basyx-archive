package org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.services.IModelProvider;

/**
 * Provides a test method to check the set ability of a generic IModelProvider implementation.
 * @author schnicke
 *
 */
public class TestProviderFull_set {
	public static void testSet(IModelProvider provider) throws Exception {
		// Get AAS sub model property values via AAS
		// - First property "sampleProperty1"
		assertTrue((int) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty1") == 2);
		provider.setModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty1", 5);
		assertTrue((int) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty1") == 5);
		provider.setModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty1", 2);
		assertTrue((int) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty1") == 2);
		// - Second property "sampleProperty2"
		assertTrue((int) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty2") == 3);
		provider.setModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty2", 6);
		assertTrue((int) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty2") == 6);
		provider.setModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty2", 3);
		assertTrue((int) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty2") == 3);
		// - Test nested property "sampleProperty3/samplePropertyA"
		assertTrue((int) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.properties.samplePropertyA") == 4);
		provider.setModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.properties.samplePropertyA", 8);
		assertTrue((int) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.properties.samplePropertyA") == 8);
		provider.setModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.properties.samplePropertyA", 2);
		assertTrue((int) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.properties.samplePropertyA") == 2);
		// TODO: Clock/Frozen?
	}
}
