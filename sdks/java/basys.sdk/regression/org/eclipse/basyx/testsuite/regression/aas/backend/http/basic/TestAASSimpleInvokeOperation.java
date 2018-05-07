package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.ConnectedOperation;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;




/**
 * AAS Operations test case
 * 
 * Context: Static configured topology
 * - Connect to AAS "Stub1AAS"
 * - Connect to sub model "statusSM"
 * - Invoke operations "sum", "property3/sub"
 * 
 * @author kuhn
 *
 */
class TestAASSimpleInvokeOperation {

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub1AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");


		// Connect to sub model
		// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
		// - Retrieve connected sub model
		// - FIXME: Get submodel by type or ID		
		ISubModel submodel = connAAS.getSubModels().get("statusSM");		


		System.out.println("Get1:"+submodel.getOperations().get("sum"));
		System.out.println("Get2:"+((ConnectedOperation) submodel.getOperations().get("sum")).invoke(2, 3));

		// Connect to sub model operations
		// - Invoke operation "sum"
		int result1 = (int) ((ConnectedOperation) submodel.getOperations().get("sum")).invoke(2, 3);

		// Check test case results
		assertTrue(result1 == 5);
	}
}

