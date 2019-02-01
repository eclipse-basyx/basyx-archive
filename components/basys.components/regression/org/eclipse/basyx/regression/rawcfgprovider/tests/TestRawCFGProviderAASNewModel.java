package org.eclipse.basyx.regression.rawcfgprovider.tests;

import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.AASHTTPServerResource;
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
public class TestRawCFGProviderAASNewModel {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());

	/** 
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = AASHTTPServerResource.getTestResource();
	
	/**
	 * Test basic queries
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("RawCfgFileTestAAS");

		
		// Create map with complex type
		AssetAdministrationShell aas = new AssetAdministrationShell();
		// Create AAS structure on server
		connSubModel.createElement("/aas/submodels/rawSampleCFG/aas", aas);

		
		// Read complex property completely
		Map<String, Object> aasReadBack = (Map<String, Object>) connSubModel.readElementValue("/aas/submodels/rawSampleCFG/aas");
	}
}
