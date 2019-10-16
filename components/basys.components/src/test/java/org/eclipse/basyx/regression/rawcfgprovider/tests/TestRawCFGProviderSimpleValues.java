package org.eclipse.basyx.regression.rawcfgprovider.tests;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.AdministrativeInformationFacade;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
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
public class TestRawCFGProviderSimpleValues {

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
		VABElementProxy connSubModel = this.connManager.connectToVABElement("RawCfgFileTestAAS");

		// Check sub model meta data
		Map<String, Object> submodel = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG");
		assertEquals("1.0",
				new AdministrativeInformationFacade((Map<String, Object>) submodel.get(Identifiable.ADMINISTRATION))
						.getVersion());

		// Get property value
		Map<String, Object> value1 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/cfgProperty1/value");
		assertEquals("exampleStringValueRaw", value1.get(SingleProperty.VALUE));
		Map<String, Object> cfgProperty1 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/cfgProperty1");
		assertEquals("Configuration property description", cfgProperty1.get("description"));

		// Get property value
		Map<String, Object> value2 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/cfgProperty2/value");
		assertEquals(12, value2.get(SingleProperty.VALUE));
		// - Check property meta data (description)
		Map<String, Object> cfgProperty2 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/cfgProperty2");
		assertEquals("Configuration property description on multiple lines", cfgProperty2.get("description"));

		// Get property value
		Map<String, Object> value3 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/cfgProperty3/value");
		assertEquals("45.8", value3.get(SingleProperty.VALUE));

		// Get property value
		Map<String, Object> value4 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/cfgProperty4/value");
		assertEquals("44.8", value4.get(SingleProperty.VALUE));
		Map<String, Object> cfgProperty4 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/cfgProperty4");
		assertEquals("Another configuration property description", cfgProperty4.get("description"));
		assertEquals("8", cfgProperty4.get("newMetaData"));
	}
}
