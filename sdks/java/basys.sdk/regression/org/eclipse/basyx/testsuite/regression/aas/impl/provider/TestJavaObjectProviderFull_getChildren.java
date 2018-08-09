package org.eclipse.basyx.testsuite.regression.aas.impl.provider;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
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
public class TestJavaObjectProviderFull_getChildren {


	/**
	 * Run test case
	 */
	@Test @SuppressWarnings("unchecked")
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
		Collection<String> modelNames     = subModelProvider.getAllModels();
		Collection<IElement> stub1ChildrenA = (Collection<IElement>) subModelProvider.getModelPropertyValue("Stub1AAS/aas/children");
		Collection<IElement> stub1ChildrenB = (Collection<IElement>) subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/children");
		Collection<IElement> stub1ChildrenC = (Collection<IElement>) subModelProvider.getModelPropertyValue("statusSM/submodel/children");

		// - Check results
		assertTrue(modelNames.size() == 2);
		assertTrue(modelNames.containsAll(Arrays.asList("statusSM", "Stub1AAS")));
		//assertTrue(stub1ChildrenA.size() == 2);
		System.out.println("StatusSM contains "+ stub1ChildrenB.size() +" children.");
		
		assertTrue(stub1ChildrenB.size() == 12);
		assertTrue(stub1ChildrenC.size() == 12);
	}
}


