package org.eclipse.basyx.regression.cfgprovider.tests;

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
class TestCFGProviderSubmodelMetaData {

	
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
		VABElementProxy connSubModel = this.connManager.connectToVABElement("CfgFileTestAAS");

		
		// Get property value
		Object value1 = connSubModel.readElementValue("/aas/submodels/sampleCFG/description");
		assertTrue(value1.equals("BaSys regression test file for CFG file provider"));

		// Get property value
		Object value2 = connSubModel.readElementValue("/aas/submodels/sampleCFG/administration/version");
		assertTrue(value2.equals("1.0"));
	}
}
