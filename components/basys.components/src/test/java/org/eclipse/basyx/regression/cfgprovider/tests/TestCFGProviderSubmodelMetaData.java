package org.eclipse.basyx.regression.cfgprovider.tests;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.AdministrativeInformationFacade;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
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
	public static AASHTTPServerResource res = new AASHTTPServerResource(new ComponentsRegressionContext());
	
	/**
	 * Test basic queries
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("CfgFileTestAAS");

		
		// Get property value
		Map<String, Object> sampleCFG = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/sampleCFG");
		assertEquals("BaSys regression test file for CFG file provider", sampleCFG.get("description"));

		// Get property value
		assertEquals("1.0",
				new AdministrativeInformationFacade((Map<String, Object>) sampleCFG.get(Identifiable.ADMINISTRATION))
				.getVersion());

		// Get complete sub model
		Object value3 = connSubModel.getModelPropertyValue("/aas/submodels/sampleCFG");
		System.out.println(value3.toString());
	}
}
