package org.eclipse.basyx.regression.xqueryprovider.tests;

import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Test queries to XQuery XML provider
 * 
 * @author kuhn
 *
 */
public class XQueryProviderQueries {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());

	/** 
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = new AASHTTPServerResource(new ComponentsRegressionContext());
	
	/**
	 * Test basic queries
	 */
	@Test
	public void test() throws Exception {

		// Connect to sub model "XMLXQueryFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("XMLXQueryFileTestAAS");

		
		
		// Get property value
		//Object version = connSubModel.readElementValue("/aas/submodels/XMLQTestSubmodel/administration/version");
		//System.out.println("Version:"+version);
		//assertTrue(version.equals("1.0"));

		
		// Get property value
		Object value1 = connSubModel.getModelPropertyValue("/aas/submodels/XMLQTestSubmodel/properties/heavySensorNames");
		System.out.println("Value:"+value1);
	}
}
