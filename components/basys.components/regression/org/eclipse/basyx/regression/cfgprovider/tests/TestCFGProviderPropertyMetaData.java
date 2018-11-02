package org.eclipse.basyx.regression.cfgprovider.tests;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.vab.VABConnectionManager;
import org.eclipse.basyx.vab.proxy.VABElementProxy;
import org.junit.jupiter.api.Test;



/**
 * Test queries to CFG file provider
 * 
 * @author kuhn
 *
 */
class TestCFGProviderPropertyMetaData {

	
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
		Object value1 = connSubModel.readElementValue("/aas/submodels/sampleCFG/properties/cfgProperty1/value");
		System.out.println("V1:"+value1);
		Object value1a = connSubModel.readElementValue("/aas/submodels/sampleCFG/properties/cfgProperty1/description");
		System.out.println("V1a:"+value1a);

		// Get property value
		Object value2 = connSubModel.readElementValue("/aas/submodels/sampleCFG/properties/cfgProperty2/value");
		System.out.println("V2:"+value2);
		Object value2a = connSubModel.readElementValue("/aas/submodels/sampleCFG/properties/cfgProperty2/description");
		System.out.println("V2a:"+value2a);

		// Get property value
		Object value3 = connSubModel.readElementValue("/aas/submodels/sampleCFG/properties/cfgProperty3/value");
		System.out.println("V3:"+value3);
	}
}
