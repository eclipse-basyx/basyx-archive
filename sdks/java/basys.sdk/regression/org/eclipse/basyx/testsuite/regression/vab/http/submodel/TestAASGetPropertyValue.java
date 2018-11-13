package org.eclipse.basyx.testsuite.regression.vab.http.submodel;

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
class TestAASGetPropertyValue {
	

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
		VABElementProxy connVABElement = this.connManager.connectToVABElement("urn:fhg:es.iese:aas:1:1:submodel");

		// Get property value
		//Object value1 = connVABElement.readElementValue("/aas/submodels/SimpleAASSubmodel");
		Object value2 = connVABElement.readElementValue("/aas/submodels/SimpleAASSubmodel/properties");
		System.out.println("V2:"+value2);

		//System.out.println("V1:"+value1);

		Object value3 = connVABElement.readElementValue("/aas/submodels/SimpleAASSubmodel/properties/prop1");
		System.out.println("V3:"+value3);

	
		Object value4 = connVABElement.readElementValue("/aas/submodels/SimpleAASSubmodel/properties/prop1/value");
		System.out.println("V4:"+value4);
	}
}

