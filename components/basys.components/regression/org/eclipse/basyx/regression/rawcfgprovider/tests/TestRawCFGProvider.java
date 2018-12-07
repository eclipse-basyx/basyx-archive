package org.eclipse.basyx.regression.rawcfgprovider.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.jupiter.api.Test;



/**
 * Test queries to CFG file provider
 * 
 * @author kuhn
 *
 */
class TestRawCFGProvider {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());

	
	
	/**
	 * Test basic queries
	 */
	@Test
	void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("RawCfgFileTestAAS");

		
		// Check sub model meta data
		Object version = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/administration/version");
		assertTrue(version.equals("1.0"));
		
		
		// Get property value
		Object value1 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty1/value");
		assertTrue(value1.equals("exampleStringValueRaw"));
		Object value1a = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty1/description");
		assertTrue(value1a.equals("Configuration property description"));
		

		// Get property value
		Object value2 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty2/value");
		assertTrue(value2.equals("12"));
		// - Check property meta data (description)
		Object value2a = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty2/description");
		assertTrue(value2a.equals("Configuration property description on multiple lines"));


		// Get property value
		Object value3 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty3/value");
		assertTrue(value3.equals("45.8"));

	}
}
