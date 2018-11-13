package org.eclipse.basyx.testsuite.regression.vab.http.submodel;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.vab.VABConnectionManager;
import org.eclipse.basyx.vab.proxy.VABElementProxy;
import org.junit.jupiter.api.Test;




/**
 * AAS Operations test case
 * 
 * Context: Static configured topology
 * - Connect to AAS "Stub1AAS"
 * - Connect to sub model "statusSM"
 * - Connect to properties "sampleProperty1" and "sampleProperty2"
 * - Retrieve values of both properties
 * 
 * @author kuhn
 *
 */
class TestAASInvokeOperationExceptions {
	

	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to VAB element with ID "SimpleVABElement"
		// - Retrieve connected AAS from AAS ID
		VABElementProxy connVABElement = this.connManager.connectToVABElement("urn:fhg:es.iese:aas:1:1:submodel");

		// Invoke operationEx1
		try {
			connVABElement.invoke("/aas/submodels/SimpleAASSubmodel/operations/operationEx1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Invoke operationEx2
		try {
			connVABElement.invoke("/aas/submodels/SimpleAASSubmodel/operations/operationEx2", "prop1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

