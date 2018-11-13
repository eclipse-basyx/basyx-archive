package org.eclipse.basyx.testsuite.regression.vab.http.basic;

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
class TestVABSimpleInvokeOperation {
	

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
		VABElementProxy connVABElement = this.connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Invoke operation
		Object value1 = connVABElement.invoke("operations/operation1", "operations");
		Object value2 = connVABElement.invoke("property1/operations/operation1.1");

		// Check test case results
		System.out.println("R1:"+value1);
		System.out.println("R2:"+value2);
	}
}

