package org.eclipse.basyx.regression.cfgprovider.tests;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;
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
public class TestCFGProviderPropertyMetaData {

	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(),
			new HTTPConnectorProvider());

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
		Map<String, Object> value1 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/sampleCFG/dataElements/cfgProperty1");
		assertEquals("exampleStringValue", value1.get(SingleProperty.VALUE));
		// - Check property meta data (description)
		Map<String, Object> value1a = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/sampleCFG/dataElements/cfgProperty1");
		assertEquals("Configuration property description", value1a.get("description"));

		// Get property value
		Map<String, Object> value2 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/sampleCFG/dataElements/cfgProperty2");
		assertEquals("12", value2.get(SingleProperty.VALUE));
		// - Check property meta data (description)
		Map<String, Object> value2a = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/sampleCFG/dataElements/cfgProperty2");
		assertEquals("Configuration property description on multiple lines", value2a.get("description"));

		// Get property value
		Map<String, Object> value3 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/sampleCFG/dataElements/cfgProperty3");
		assertEquals("45.8", value3.get(SingleProperty.VALUE));
	}
}
