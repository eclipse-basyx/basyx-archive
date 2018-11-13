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
 * Test case for serialization/de-serialization of children with JSON
 * 
 * @author kuhn
 *
 */
public class SerializeAASChildren {


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
		
		
		// Serialize AAS children (properties, operations, events)
		JSONObject aas1ChildrenA   = JSONTools.Instance.serializeProperty("statusSM.Stub1AAS/children", subModelProvider);
		// - Check results
		assertTrue(aas1ChildrenA.getJSONObject("children").get("sampleProperty1") instanceof JSONObject);
		assertTrue(aas1ChildrenA.getJSONObject("children").get("sampleProperty2") instanceof JSONObject);
		assertTrue(aas1ChildrenA.getJSONObject("children").get("sampleProperty3") instanceof JSONObject);
		assertTrue(aas1ChildrenA.getJSONObject("children").get("sum") instanceof JSONObject);
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty1").get("type").equals("ref"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty1").get("aas").equals("Stub1AAS"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty1").get("submodel").equals("statusSM"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty1").get("path").equals("sampleProperty1"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty2").get("type").equals("ref"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty2").get("aas").equals("Stub1AAS"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty2").get("submodel").equals("statusSM"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty2").get("path").equals("sampleProperty2"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty3").get("type").equals("ref"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty3").get("aas").equals("Stub1AAS"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty3").get("submodel").equals("statusSM"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sampleProperty3").get("path").equals("sampleProperty3"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sum").get("type").equals("ref"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sum").get("aas").equals("Stub1AAS"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sum").get("submodel").equals("statusSM"));
		assertTrue(aas1ChildrenA.getJSONObject("children").getJSONObject("sum").get("path").equals("sum"));

		
		// Serialize AAS children (properties, operations, events)
		JSONObject aas1ChildrenB  = JSONTools.Instance.serializeProperty("statusSM.Stub1AAS", subModelProvider);
		// - Check results
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").get("sampleProperty1") instanceof JSONObject);
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").get("sampleProperty2") instanceof JSONObject);
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").get("sampleProperty3") instanceof JSONObject);
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").get("sum") instanceof JSONObject);
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty1").get("type").equals("ref"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty1").get("aas").equals("Stub1AAS"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty1").get("submodel").equals("statusSM"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty1").get("path").equals("sampleProperty1"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty2").get("type").equals("ref"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty2").get("aas").equals("Stub1AAS"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty2").get("submodel").equals("statusSM"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty2").get("path").equals("sampleProperty2"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty3").get("type").equals("ref"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty3").get("aas").equals("Stub1AAS"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty3").get("submodel").equals("statusSM"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sampleProperty3").get("path").equals("sampleProperty3"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sum").get("type").equals("ref"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sum").get("aas").equals("Stub1AAS"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sum").get("submodel").equals("statusSM"));
		assertTrue(aas1ChildrenB.getJSONObject("statusSM").getJSONObject("sum").get("path").equals("sum"));

		
		// Serialize AAS children (properties, operations, events)	
		JSONObject statusSMChildren = JSONTools.Instance.serializeProperty("statusSM/children", subModelProvider);
		// - Check results
		assertTrue(statusSMChildren.getJSONObject("children").get("sampleProperty1") instanceof JSONObject);
		assertTrue(statusSMChildren.getJSONObject("children").get("sampleProperty2") instanceof JSONObject);
		assertTrue(statusSMChildren.getJSONObject("children").get("sampleProperty3") instanceof JSONObject);
		assertTrue(statusSMChildren.getJSONObject("children").get("sum") instanceof JSONObject);
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty1").get("type").equals("ref"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty1").get("aas").equals("Stub1AAS"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty1").get("submodel").equals("statusSM"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty1").get("path").equals("sampleProperty1"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty2").get("type").equals("ref"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty2").get("aas").equals("Stub1AAS"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty2").get("submodel").equals("statusSM"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty2").get("path").equals("sampleProperty2"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty3").get("type").equals("ref"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty3").get("aas").equals("Stub1AAS"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty3").get("submodel").equals("statusSM"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sampleProperty3").get("path").equals("sampleProperty3"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sum").get("type").equals("ref"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sum").get("aas").equals("Stub1AAS"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sum").get("submodel").equals("statusSM"));
		assertTrue(statusSMChildren.getJSONObject("children").getJSONObject("sum").get("path").equals("sum"));
	}
}


