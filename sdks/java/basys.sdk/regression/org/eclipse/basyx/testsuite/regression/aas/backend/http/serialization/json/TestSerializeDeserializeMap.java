package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of maps
 * 
 * @author kuhn
 *
 */
public class TestSerializeDeserializeMap {


	/**
	 * Run test case
	 */
	@Test @SuppressWarnings("unchecked")
	void test() {
		
		// Create collections
		Map<String, Integer> integers1   = new HashMap<>();
		Map<String, Object>  primitives1 = new HashMap<>();
		
		// Load values into collections
		integers1.put("k1", 12); integers1.put("k2", 21); integers1.put("k3", 34); integers1.put("k4", 45); integers1.put("k5", 17); 
		primitives1.put("k1", 12); primitives1.put("k2", 21.3); primitives1.put("k3", 'c'); primitives1.put("k4", false); primitives1.put("k5", "test"); primitives1.put("k6", -7.9f); 
		
		// Serialize primitives
		Map<String, Object> serVal1 = GSONTools.Instance.serialize(integers1); // "int1", 
		Map<String, Object> serVal3 = GSONTools.Instance.serialize(primitives1); // "pri1", 

		// Deserialize maps
		Map<String, Integer> intMap = (Map<String, Integer>) GSONTools.Instance.deserialize(serVal1);
		Map<String, Object>  objMap = (Map<String, Object>)  GSONTools.Instance.deserialize(serVal3);
		
		// Check result
		assertTrue(intMap.size() == 5);
		assertTrue(intMap.get("k1") == 12);
		assertTrue(intMap.get("k2") == 21);
		assertTrue(intMap.get("k3") == 34);
		assertTrue(intMap.get("k4") == 45);
		assertTrue(intMap.get("k5") == 17);

		assertTrue(objMap.size() == 6);
		assertTrue(objMap.get("k1").equals(12));
		assertTrue(objMap.get("k2").equals(21.3));
		assertTrue(objMap.get("k3").equals('c'));
		assertTrue(objMap.get("k4").equals(false));
		assertTrue(objMap.get("k5").equals("test"));
		assertTrue(objMap.get("k6").equals(-7.9f));
	}
}


