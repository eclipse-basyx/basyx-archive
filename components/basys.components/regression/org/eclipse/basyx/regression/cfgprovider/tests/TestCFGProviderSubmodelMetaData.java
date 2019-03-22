package org.eclipse.basyx.regression.cfgprovider.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.AASHTTPServerResource;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Test queries to CFG file provider
 * 
 * @author kuhn
 *
 */
public class TestCFGProviderSubmodelMetaData {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());

	/** 
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = AASHTTPServerResource.getTestResource(new ComponentsRegressionContext());
	
	/**
	 * Test basic queries
	 */
	@Test
	public void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("CfgFileTestAAS");

		
		// Get property value
		Object value1 = connSubModel.readElementValue("/aas/submodels/sampleCFG/description");
		assertTrue(value1.equals("BaSys regression test file for CFG file provider"));

		// Get property value
		Object value2 = connSubModel.readElementValue("/aas/submodels/sampleCFG/administration/version");
		assertTrue(value2.equals("1.0"));

		// Update property value
		connSubModel.updateElementValue("/aas/submodels/sampleCFG/administration/version", "2.0");
		Object value2a = connSubModel.readElementValue("/aas/submodels/sampleCFG/administration/version");
		assertTrue(value2a.equals("2.0"));

		// Create property value
		connSubModel.createElement("/aas/submodels/sampleCFG/administration/version2", "3.0");
		Object value2b = connSubModel.readElementValue("/aas/submodels/sampleCFG/administration/version2");
		assertTrue(value2b.equals("3.0"));

		// Delete property value
		connSubModel.deleteElement("/aas/submodels/sampleCFG/administration/version2");
		Object value2c = connSubModel.readElementValue("/aas/submodels/sampleCFG/administration/version2");
		assertTrue(value2c == null);

		// Reset property value
		connSubModel.updateElementValue("/aas/submodels/sampleCFG/administration/version", "1.0");
		Object value2d = connSubModel.readElementValue("/aas/submodels/sampleCFG/administration/version");
		assertTrue(value2d.equals("1.0"));

		// Get complete sub model
		Object value3 = connSubModel.readElementValue("/aas/submodels/sampleCFG");
		System.out.println(value3.toString());
	}
}
