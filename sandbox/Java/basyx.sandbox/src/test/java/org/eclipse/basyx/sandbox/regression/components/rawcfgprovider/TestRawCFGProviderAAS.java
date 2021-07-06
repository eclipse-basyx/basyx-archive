package org.eclipse.basyx.sandbox.regression.components.rawcfgprovider;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
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
public class TestRawCFGProviderAAS {

	
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
	@SuppressWarnings({ "unchecked" })
	@Test
	public void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("AASProvider");

		
		// Create map with complex type
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.setIdShort("testAAS");
		
		// Create AAS structure on server
		connSubModel.createValue("/aas", aas);

		
		// Read complex property completely
		Map<String, Object> aasReadBack = (Map<String, Object>) connSubModel.getModelPropertyValue("/aas");

		assertEquals(aas.getIdShort(), AssetAdministrationShell.createAsFacade(aasReadBack).getIdShort());
		
		// Read AAS SubModel
		Map<String, Object> smReadBack = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG");
		
		assertEquals("rawSampleCFG", SubModel.createAsFacade(smReadBack).getIdShort());
		
	}
}
