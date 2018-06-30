package org.eclipse.basyx.testsuite.regression.aas.backend.http.generichandler;

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
 * - Connect to AAS "Stub3AAS"
 * - Connect to sub model "Stub3SM"
 * - Invoke operations "testOp1"
 * 
 * @author kuhn
 *
 */
class TestAASSimpleInvokeOperation_GenericHandlerSubmodelProvider {

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub3AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub3AAS");


		// Connect to sub model
		// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
		// - Retrieve connected sub model
		// - FIXME: Get submodel by type or ID		
		ISubModel submodel = connAAS.getSubModels().get("Stub3SM");		


		// Connect to sub model operations
		// - Invoke operation "sum"
		int result1 = (int) ((ConnectedOperation) submodel.getOperations().get("testOp1")).invoke(2, 3);

		// Check test case results
		assertTrue(result1 == 6);
	}
}

