package org.eclipse.basyx.testsuite.regression.vab.http.basic;

import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;

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
class TestVABSimpleSetPropertyValue {
	

	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test @SuppressWarnings("rawtypes")
	void runTest() throws Exception {

		// Connect to VAB element with ID "SimpleVABElement"
		// - Retrieve connected AAS from AAS ID
		VABElementProxy connVABElement = this.connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Get property value
		Object value1 = connVABElement.readElementValue("/aas/submodels/SimpleVABElement/property1");
		Object value2 = connVABElement.readElementValue("/aas/submodels/SimpleVABElement/property1/property1.1");

		// Check test case results
		assertTrue(value1 instanceof HashMap);
		assertTrue(((Map) value1).size() == 3);
		assertTrue(value2 instanceof Integer);
		assertTrue((int) value2 == 7);
		

		// Set and reread property value
		connVABElement.updateElementValue("/aas/submodels/SimpleVABElement/property1/property1.1", 12);
		Object value3 = connVABElement.readElementValue("/aas/submodels/SimpleVABElement/property1/property1.1");

		// Check test case results
		assertTrue(value3 instanceof Integer);
		assertTrue((int) value3 == 12);


		// Change value back
		connVABElement.updateElementValue("/aas/submodels/SimpleVABElement/property1/property1.1", 7);
		Object value4 = connVABElement.readElementValue("/aas/submodels/SimpleVABElement/property1/property1.1");

		// Check test case results
		assertTrue(value4 instanceof Integer);
		assertTrue((int) value4 == 7);
	}
}

