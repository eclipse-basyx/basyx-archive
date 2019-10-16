package org.eclipse.basyx.regression.rawcfgprovider.tests;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;



/**
 * Test queries to CFG file provider
 * 
 * @author kuhn
 *
 */
public class TestRawCFGProviderAASNewModel {

	
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
	@SuppressWarnings({ "unchecked", "unused" })
	@Ignore
	@Test
	public void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("RawCfgFileTestAAS");

		
		// Create map with complex type
		AssetAdministrationShell aas = new AssetAdministrationShell();
		// Create AAS structure on server
		connSubModel.createValue("/aas/submodels/rawSampleCFG/aas", aas);

		
		// Read complex property completely
		Map<String, Object> aasReadBack = (Map<String, Object>) connSubModel.getModelPropertyValue("/aas/submodels/rawSampleCFG/aas");
	}
}
