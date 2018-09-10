package org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.services.IModelProvider;

/**
 * Provides a test method to check the get ability of a generic IModelProvider
 * implementation.
 * 
 * @author schnicke
 *
 */
public class TestProviderFull_get {
	@SuppressWarnings("unchecked")
	public static void testGet(IModelProvider provider) {
		Object property1N = provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty1");
		Object property2N = provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty2");
		
		Object property3AN = provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.properties.samplePropertyA");
		Object property3BN = provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.properties.samplePropertyB");

		
		// - Check results
		assertTrue((int) property1N == 2);
		assertTrue((int) property2N == 3);
		// assertTrue(property3N instanceof Stub1Submodel.NestedPropertyType);
		assertTrue((int) property3AN == 4);
		assertTrue((int) property3BN == 5);

		// Get AAS sub model property values via unique sub model ID
		Object property1a = provider.getModelPropertyValue("statusSM/submodel/properties/sampleProperty1");
		Object property2a = provider.getModelPropertyValue("statusSM/submodel/properties/sampleProperty2");

		// - Check results
		assertTrue((int) property1a == 2);
		assertTrue((int) property2a == 3);

		Map<String, IElementReference> submodels = (Map<String, IElementReference>) provider.getModelPropertyValue("Stub1AAS/aas/submodels");

		assertTrue(submodels.containsKey("statusSM"));

		Map<String, IElementReference> properties = (Map<String, IElementReference>) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties");
		assertEquals(4, properties.size());
		assertTrue(properties.containsKey("sampleProperty1"));
		assertTrue(properties.containsKey("sampleProperty2"));
		assertTrue(properties.containsKey("sampleProperty3"));
		assertTrue(properties.containsKey("sampleProperty4"));
		

		Map<String, IElementReference> nestedProperties = (Map<String, IElementReference>) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.properties");
		assertEquals(2, nestedProperties.size());
		assertTrue(nestedProperties.containsKey("samplePropertyA"));
		assertTrue(nestedProperties.containsKey("samplePropertyB"));
	}
}
