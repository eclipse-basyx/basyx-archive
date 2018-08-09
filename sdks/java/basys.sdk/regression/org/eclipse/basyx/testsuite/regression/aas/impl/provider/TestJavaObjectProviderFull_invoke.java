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
public class TestJavaObjectProviderFull_invoke {


	/**
	 * Run test case
	 * @throws Exception 
	 */
	@Test
	void test() throws Exception {
		// Create model provider
		JavaObjectProvider subModelProvider = new JavaObjectProvider();
		// - Create AAS and sub model instances
		IAssetAdministrationShell stub1AAS = new Stub1AAS();
		ISubModel                 stub1SM  = new Stub1Submodel(stub1AAS);
		// - Add models to provider
		subModelProvider.addModel(stub1AAS);
		subModelProvider.addModel(stub1SM);
		
		
		// Invoke simple operation "sum" in sub model "statusSM" of AAS "Stub1AAS"
		Object result = subModelProvider.invokeOperation("Stub1AAS/aas/submodels/statusSM/operations/sum", new Object[] {4, 6});
		// - Check Result
		assertTrue((int) result == 10);

		// Invoke simple operation "sub" in property "sampleProperty3" of sub model "statusSM" of AAS "Stub1AAS"
		Object result2 = subModelProvider.invokeOperation("Stub1AAS/aas/submodels/statusSM/properties/sampleProperty3.sub", new Object[] {7, 5});
		// - Check Result
		assertTrue((int) result2 == 2);
	}
}


