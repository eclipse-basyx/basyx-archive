package org.eclipse.basyx.sandbox.regression.components.cfgprovider;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
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
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("CfgFileTestAAS");

		
		// Get property value
		Map<String, Object> value1 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/sampleCFG/" + SubmodelElementProvider.PROPERTIES + "/cfgProperty1/value");

		assertEquals("exampleStringValue", value1.get(Property.VALUE));

		
		// Get property value
		Map<String, Object> value2 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/sampleCFG/" + SubmodelElementProvider.PROPERTIES + "/cfgProperty2/value");
		assertEquals("12", value2.get(Property.VALUE));

		// Get property value
		Map<String, Object> value3 = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/sampleCFG/" + SubmodelElementProvider.PROPERTIES + "/cfgProperty3/value");
		assertEquals("45.8", value3.get(Property.VALUE));
	}
}
