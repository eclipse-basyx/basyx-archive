package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of maps
 * 
 * @author kuhn
 *
 */
public class SerializeMap {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		// Create collections
		Map<String, Integer> integers1   = new HashMap<>();
		Map<String, Object>  primitives1 = new HashMap<>();
		
		// Load values into collections
		integers1.put("k1", 12); integers1.put("k2", 21); integers1.put("k3", 34); integers1.put("k4", 45); integers1.put("k5", 17); 
		primitives1.put("k1", 12); primitives1.put("k2", 21.3); primitives1.put("k3", 'c'); primitives1.put("k4", false); primitives1.put("k5", "test"); primitives1.put("k6", -7.9f); 
		
		// Serialize primitives
		JSONObject serVal1 = JSONTools.Instance.serialize(integers1); //"int1",
		JSONObject serVal3 = JSONTools.Instance.serialize(primitives1); // "pri1", 
		
		// Check result
		assertTrue((int) serVal1.getJSONObject("k1").get("value") == 12);
		assertTrue(serVal1.getJSONObject("k1").get("typeid").equals("int"));
		assertTrue(serVal1.getJSONObject("k1").get("kind").equals("value"));
		assertTrue((int) serVal1.getJSONObject("k2").get("value") == 21);
		assertTrue(serVal1.getJSONObject("k2").get("typeid").equals("int"));
		assertTrue(serVal1.getJSONObject("k2").get("kind").equals("value"));
		assertTrue((int) serVal1.getJSONObject("k3").get("value") == 34);
		assertTrue(serVal1.getJSONObject("k3").get("typeid").equals("int"));
		assertTrue(serVal1.getJSONObject("k3").get("kind").equals("value"));
		assertTrue((int) serVal1.getJSONObject("k4").get("value") == 45);
		assertTrue(serVal1.getJSONObject("k4").get("typeid").equals("int"));
		assertTrue(serVal1.getJSONObject("k4").get("kind").equals("value"));
		assertTrue((int) serVal1.getJSONObject("k5").get("value") == 17);
		assertTrue(serVal1.getJSONObject("k5").get("typeid").equals("int"));
		assertTrue(serVal1.getJSONObject("k5").get("kind").equals("value"));
		assertTrue((int) serVal1.get("size") == 5);
		assertTrue(serVal1.get("kind").equals("map"));

		assertTrue((int) serVal3.getJSONObject("k1").get("value") == 12);
		assertTrue(serVal3.getJSONObject("k1").get("typeid").equals("int"));
		assertTrue(serVal3.getJSONObject("k1").get("kind").equals("value"));
		assertTrue((double) serVal3.getJSONObject("k2").get("value") == 21.3);
		assertTrue(serVal3.getJSONObject("k2").get("typeid").equals("double"));
		assertTrue(serVal3.getJSONObject("k2").get("kind").equals("value"));
		assertTrue((char) serVal3.getJSONObject("k3").get("value") == 'c');
		assertTrue(serVal3.getJSONObject("k3").get("typeid").equals("character"));
		assertTrue(serVal3.getJSONObject("k3").get("kind").equals("value"));
		assertTrue((boolean) serVal3.getJSONObject("k4").get("value") == false);
		assertTrue(serVal3.getJSONObject("k4").get("typeid").equals("boolean"));
		assertTrue(serVal3.getJSONObject("k4").get("kind").equals("value"));
		assertTrue(serVal3.getJSONObject("k5").get("value").equals("test"));
		assertTrue(serVal3.getJSONObject("k5").get("typeid").equals("string"));
		assertTrue(serVal3.getJSONObject("k5").get("kind").equals("value"));
		assertTrue((float) serVal3.getJSONObject("k6").get("value") == -7.9f);
		assertTrue(serVal3.getJSONObject("k6").get("typeid").equals("float"));
		assertTrue(serVal3.getJSONObject("k6").get("kind").equals("value"));
		assertTrue((int) serVal3.get("size") == 6);
		assertTrue(serVal3.get("kind").equals("map"));
	}
}


