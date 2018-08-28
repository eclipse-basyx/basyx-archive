package org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.services.IModelProvider;


/**
 * Provides a test method to check the invoke ability of a generic IModelProvider implementation.
 * @author schnicke
 *
 */
public class TestProviderFull_invoke {
	public static void invokeTest(IModelProvider provider) throws Exception {
		// Invoke simple operation "sum" in sub model "statusSM" of AAS "Stub1AAS"
		Object result = provider.invokeOperation("Stub1AAS/aas/submodels/statusSM/operations/sum", new Object[] {4, 6});
		// - Check Result
		assertTrue((int) result == 10);

		// Invoke simple operation "sub" in property "sampleProperty3" of sub model "statusSM" of AAS "Stub1AAS"
		Object result2 = provider.invokeOperation("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.sub", new Object[] {7, 5});
		// - Check Result
		assertTrue((int) result2 == 2);
	}
}
