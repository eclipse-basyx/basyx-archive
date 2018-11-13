package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json.aas;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.provider.JavaObjectVABMapper;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;



/**
 * Test case for serialization/de-serialization of primitives with JSON
 * 
 * @author kuhn
 *
 */
public class SerializeAASProperties {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		// Create model provider
		JavaObjectVABMapper subModelProvider = new JavaObjectVABMapper();
		// - Create AAS and sub model instances
		IAssetAdministrationShell stub1AAS = new Stub1AAS();
		ISubModel                 stub1SM  = new Stub1Submodel(stub1AAS);
		// - Add models to provider
		subModelProvider.addScopedModel(stub1AAS);
		subModelProvider.addScopedModel(stub1SM);


		// Serialize primitive properties
		JSONObject prop1JSONObject      = JSONTools.Instance.serializeProperty("statusSM.Stub1AAS/sampleProperty1", subModelProvider);
		JSONObject prop2JSONObject      = JSONTools.Instance.serializeProperty("statusSM.Stub1AAS/sampleProperty2", subModelProvider);
		// - Check result
		assertTrue((int) prop1JSONObject.getJSONObject("sampleProperty1").get("value") == 2);
		assertTrue((int) prop2JSONObject.getJSONObject("sampleProperty2").get("value") == 3);


		// Serialize complex properties
		JSONObject ssmJSONObject        = JSONTools.Instance.serializeProperty("statusSM.Stub1AAS", subModelProvider);
		// - Check result
		assertTrue(ssmJSONObject.getJSONObject("statusSM").get("sampleProperty1") instanceof JSONObject);
		assertTrue(ssmJSONObject.getJSONObject("statusSM").get("sampleProperty2") instanceof JSONObject);
		assertTrue(ssmJSONObject.getJSONObject("statusSM").get("sampleProperty3") instanceof JSONObject);
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty1").get("type").equals("ref"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty1").get("aas").equals("Stub1AAS"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty1").get("submodel").equals("statusSM"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty1").get("path").equals("sampleProperty1"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty2").get("type").equals("ref"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty2").get("aas").equals("Stub1AAS"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty2").get("submodel").equals("statusSM"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty2").get("path").equals("sampleProperty2"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty3").get("type").equals("ref"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty3").get("aas").equals("Stub1AAS"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty3").get("submodel").equals("statusSM"));
		assertTrue(ssmJSONObject.getJSONObject("statusSM").getJSONObject("sampleProperty3").get("path").equals("sampleProperty3"));


		// Resolve 1st referenced element (which is a no reference)
		JSONObject prop3JSONObject      = JSONTools.Instance.serializeProperty("statusSM.Stub1AAS/sampleProperty3", subModelProvider);
		// - Check result
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").get("samplePropertyA") instanceof JSONObject);
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").get("samplePropertyB") instanceof JSONObject);
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").get("samplePropertyC") instanceof JSONObject);
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyA").get("type").equals("ref"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyA").get("aas").equals("Stub1AAS"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyA").get("submodel").equals("statusSM"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyA").get("path").equals("sampleProperty3/samplePropertyA"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyB").get("type").equals("ref"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyB").get("aas").equals("Stub1AAS"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyB").get("submodel").equals("statusSM"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyB").get("path").equals("sampleProperty3/samplePropertyB"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyC").get("type").equals("ref"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyC").get("aas").equals("Stub1AAS"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyC").get("submodel").equals("statusSM"));
		assertTrue(prop3JSONObject.getJSONObject("sampleProperty3").getJSONObject("samplePropertyC").get("path").equals("sampleProperty3/samplePropertyC"));

		
		// Get value of nested properties
		JSONObject prop3AJSONObject     = JSONTools.Instance.serializeProperty("statusSM.Stub1AAS/sampleProperty3/samplePropertyA", subModelProvider);
		JSONObject prop3BJSONObject     = JSONTools.Instance.serializeProperty("statusSM.Stub1AAS/sampleProperty3/samplePropertyB", subModelProvider);
		// - Check result
		assertTrue((int) prop3AJSONObject.getJSONObject("samplePropertyA").get("value") == 4);
		assertTrue((int) prop3BJSONObject.getJSONObject("samplePropertyB").get("value") == 5);


		// Resolve 2nd referenced element (which is a null reference)
		JSONObject prop4JSONObject      = JSONTools.Instance.serializeProperty("statusSM.Stub1AAS/sampleProperty3/samplePropertyC", subModelProvider);
		// - Check result
		assertTrue(prop4JSONObject == null);
	}

}

