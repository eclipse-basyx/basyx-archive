package org.eclipse.basyx.regression.rawcfgprovider.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
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
public class TestRawCFGProviderComplexType {

	
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
		VABElementProxy connSubModel = this.connManager.connectToVABElement("RawCfgFileTestAAS");

		
		// Read element value back and make sure that complex element does not exist yet
		Object complexElement1 = connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/complexProperty");
		assertNull(complexElement1);

		
		// Create map with complex type
		Map<String, Object> complexType = new HashMap<>();
		complexType.put("prop1", 12);
		complexType.put("prop2", 13);
		complexType.put("prop3", "abc");
		Map<String, Object> containedType = new HashMap<>();
		containedType.put("prop1", 21);
		containedType.put("prop2", 22);
		containedType.put("prop3", "def");
		containedType.put("prop4", 2.1f);
		complexType.put("prop4", containedType);

		SingleProperty prop = new SingleProperty(complexType);
		prop.setIdShort("complexProperty");
		// Create new property value

		connSubModel.createValue("/aas/submodels/rawSampleCFG/dataElements", prop);
		// - Read values back
		Map<String, Object> value = (Map<String, Object>) connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/complexProperty/value");
		value = (Map<String, Object>) value.get(SingleProperty.VALUE);
		assertEquals(12, value.get("prop1"));
		assertEquals(13, value.get("prop2"));
		assertEquals("abc", value.get("prop3"));

		Map<String, Object> contained = (Map<String, Object>) value.get("prop4");
		assertEquals(21, contained.get("prop1"));
		assertEquals(22, contained.get("prop2"));
		assertEquals("def", contained.get("prop3"));
		assertEquals(2.1, contained.get("prop4")); // only double supported
		
		
		// Delete element
		connSubModel.deleteValue("/aas/submodels/rawSampleCFG/dataElements/complexProperty");
		// - Read element value back and make sure that element is deleted
		Object complexElement2 = connSubModel
				.getModelPropertyValue("/aas/submodels/rawSampleCFG/dataElements/complexProperty");
		assertNull(complexElement2);
	}
}
