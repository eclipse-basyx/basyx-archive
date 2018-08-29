package org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments;

import static org.junit.Assert.assertFalse;

import java.util.Collection;

import org.eclipse.basyx.aas.api.services.IModelProvider;

public class TestProviderFull_delete {
	@SuppressWarnings("unchecked")
	public static void testDelete(IModelProvider provider) throws Exception {
		// Delete value from collection
		provider.deleteValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty4", 92);
		
		// Test value has been added
		Collection<Integer> collection = (Collection<Integer>) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty4");
		assertFalse(collection.contains(92));
	}
}
