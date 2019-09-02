package org.eclipse.basyx.regression.cfgprovider.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.testsuite.support.backend.servers.AASHTTPServerResource;
import org.eclipse.basyx.vab.backend.connector.http.HTTPConnectorProvider;
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
public class TestCFGProvider {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());


	/** 
	 * Makes sure Tomcat Server is started with basyx.components regression test case
	 */
	@ClassRule
	public static AASHTTPServerResource res = new AASHTTPServerResource(new ComponentsRegressionContext());
	
	
	/**
	 * Test basic queries
	 */
	@Test
	public void test() throws Exception {
		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("CfgFileTestAAS");

		
		// Get property value
		Object value1 = connSubModel.getModelPropertyValue("/aas/submodels/sampleCFG/dataElements/cfgProperty1/value");
		System.out.println("Res:"+value1);
		System.out.println("ResC:"+value1.getClass());
		assertTrue(value1.equals("exampleStringValue"));

		
		// Get property value
		Object value2 = connSubModel.getModelPropertyValue("/aas/submodels/sampleCFG/dataElements/cfgProperty2/value");
		assertTrue(value2.equals("12"));

		// Get property value
		Object value3 = connSubModel.getModelPropertyValue("/aas/submodels/sampleCFG/dataElements/cfgProperty3/value");
		assertTrue(value3.equals("45.8"));
	}
}
