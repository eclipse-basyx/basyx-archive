package org.eclipse.basyx.regression.rawcfgprovider.tests;

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
	public static AASHTTPServerResource res = AASHTTPServerResource.getTestResource(new ComponentsRegressionContext());

	/**
	 * Test basic queries
	 */
	@Test
	public void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("RawCfgFileTestAAS");

		// Check sub model meta data
		Object version = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/administration/version");
		assertTrue(version.equals("1.0"));

		// Get property value
		Object value1 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty1/value");
		assertTrue(value1.equals("exampleStringValueRaw"));
		Object value1a = connSubModel
				.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty1/description");
		assertTrue(value1a.equals("Configuration property description"));

		// Get property value
		Object value2 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty2/value");
		assertTrue(value2.equals(12));
		// - Check property meta data (description)
		Object value2a = connSubModel
				.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty2/description");
		assertTrue(value2a.equals("Configuration property description on multiple lines"));

		// Get property value
		Object value3 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/properties/cfgProperty3/value");
		assertTrue(value3.equals("45.8"));

		// Get property value
		Object value4 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/value");
		assertTrue(value4.equals("44.8"));
		Object value4a = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/description");
		assertTrue(value4a.equals("Another configuration property description"));
		Object value4b = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData");
		assertTrue(value4b.equals("8"));

		// Update property value
		connSubModel.updateElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData", "9");
		// - Read updated value back
		Object value4c = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData");
		assertTrue(value4c.equals("9"));
		// - Change value back for next test
		connSubModel.updateElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData", "8");
		// - Read updated value back
		Object value4d = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData");
		assertTrue(value4d.equals("8"));

		// Update property value again, this time we are not using a string type
		connSubModel.updateElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData", 89);
		// - Read updated value back
		Object value4e = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData");
		assertTrue((int) value4e == 89);
		// - Change value back for next test
		connSubModel.updateElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData", "8");
		// - Read updated value back
		Object value4f = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData");
		assertTrue(value4f.equals("8"));

		// Create new property value
		connSubModel.createElement("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData2", "19");
		// - Read updated value back
		Object value4g = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData2");
		assertTrue(value4g.equals("19"));

		// Delete element
		connSubModel.deleteElement("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData2");
		// - Read element value back and make sure that element is deleted
		Object value4h = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/cfgProperty4/newMetaData2");
		assertTrue(value4h == null);
	}
}
