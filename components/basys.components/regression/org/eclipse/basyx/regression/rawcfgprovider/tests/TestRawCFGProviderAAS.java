package org.eclipse.basyx.regression.rawcfgprovider.tests;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell_;
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
class TestRawCFGProviderAAS {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());

	
	
	/**
	 * Test basic queries
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("AASProvider");

		
		// Create map with complex type
		AssetAdministrationShell_ aas = new AssetAdministrationShell_();
		// Create AAS structure on server
		connSubModel.createElement("/aas/submodels/exampleDeviceID/aas", aas);

		
		// Read complex property completely
		Map<String, Object> aasReadBack = (Map<String, Object>) connSubModel.readElementValue("/aas/submodels/exampleDeviceID/aas");
	}
}
