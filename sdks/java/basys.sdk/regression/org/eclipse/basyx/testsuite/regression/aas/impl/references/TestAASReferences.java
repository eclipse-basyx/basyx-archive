package org.eclipse.basyx.testsuite.regression.aas.impl.references;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
import org.junit.jupiter.api.Test;



/**
 * Test case that ensures that everybody is using the correct path format as defined by class BaSysID
 * 
 *  TODO adapt test cases to new identifier pattern
 * 
 * @author kuhn
 *
 */
public class TestAASReferences {


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
		
		// Get element references from object provider
		Map<String, IElementReference> stub1AASModels     = subModelProvider.getContainedElements("Stub1AAS/aas");
		Map<String, IElementReference> statusSMProperties = subModelProvider.getContainedElements("Stub1AAS/aas/submodels/statusSM");
		
		// Print contained elements
		for (Entry<String, IElementReference> entry : stub1AASModels.entrySet()) {System.out.println(entry.getKey() + " -> "+ entry.getValue());}
		for (Entry<String, IElementReference> entry : statusSMProperties.entrySet()) {System.out.println(entry.getKey() + " -> "+ entry.getValue());}
					
	}
}


