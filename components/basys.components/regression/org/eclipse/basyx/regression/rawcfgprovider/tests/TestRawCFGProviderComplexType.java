package org.eclipse.basyx.regression.rawcfgprovider.tests;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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
public class TestRawCFGProviderComplexType {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());

	/** 
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = AASHTTPServerResource.getTestResource(new ComponentsRegressionContext());
	
	/**
	 * Test basic queries
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("RawCfgFileTestAAS");

		
		// Read element value back and make sure that complex element does not exist yet
		@SuppressWarnings("unused")
		Object complexElement1 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty");
		//assertTrue(complexElement1 == null);

		
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

		
		// Create new property value
		connSubModel.createElement("/aas/submodels/rawSampleCFG/complexProperty", complexType);
		// - Read values back
		Object value1 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty/prop1");
		assertTrue((int) value1 == 12);
		Object value2 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty/prop2");
		assertTrue((int) value2 == 13);
		Object value3 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty/prop3");
		assertTrue(value3.equals("abc"));
		Object value4 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty/prop4/prop1");
		assertTrue((int) value4 == 21);
		Object value5 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty/prop4/prop2");
		assertTrue((int) value5 == 22);
		Object value6 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty/prop4/prop3");
		assertTrue(value6.equals("def"));
		Object value7 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty/prop4/prop4");
		assertTrue((float) value7 == 2.1f);

		// Read complex property completely
		Map<String, Object> valueC = (Map<String, Object>) connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty");
		// - Compare values
		assertTrue((int) valueC.get("prop1") == 12);
		assertTrue((int) valueC.get("prop2") == 13);
		assertTrue(((String) valueC.get("prop3")).equals("abc"));
		assertTrue((int) ((Map<String, Object>) valueC.get("prop4")).get("prop1") == 21);
		assertTrue((int) ((Map<String, Object>) valueC.get("prop4")).get("prop2") == 22);
		assertTrue(((String) ((Map<String, Object>) valueC.get("prop4")).get("prop3")).equals("def"));
		assertTrue((float) ((Map<String, Object>) valueC.get("prop4")).get("prop4") == 2.1f);
		// - Check keys
		assertTrue(valueC.keySet().size() == 4);
		assertTrue(valueC.keySet().contains("prop1"));
		assertTrue(valueC.keySet().contains("prop2"));
		assertTrue(valueC.keySet().contains("prop3"));
		assertTrue(valueC.keySet().contains("prop4"));
		
		
		// Delete element
		//connSubModel.deleteElement("/aas/submodels/rawSampleCFG", "complexProperty");
		// - Read element value back and make sure that element is deleted
		@SuppressWarnings("unused")
		Object complexElement2 = connSubModel.readElementValue("/aas/submodels/rawSampleCFG/complexProperty");
		//assertTrue(complexElement2 == null);
	}
}
