package org.eclipse.basyx.regression.xqueryprovider.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.vab.VABConnectionManager;
import org.eclipse.basyx.vab.proxy.VABElementProxy;
import org.junit.jupiter.api.Test;



/**
 * Test queries to XQuery XML provider
 * 
 * @author kuhn
 *
 */
class XQueryProviderQueries {

	
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
		VABElementProxy connSubModel = this.connManager.connectToVABElement("XMLXQueryFileTestAAS");

		
		
		// Get property value
		//Object version = connSubModel.readElementValue("/aas/submodels/XMLQTestSubmodel/administration/version");
		//System.out.println("Version:"+version);
		//assertTrue(version.equals("1.0"));

		
		// Get property value
		Object value1 = connSubModel.readElementValue("/aas/submodels/XMLQTestSubmodel/properties/heavySensorNames");
		System.out.println("Value:"+value1);
	}
}
