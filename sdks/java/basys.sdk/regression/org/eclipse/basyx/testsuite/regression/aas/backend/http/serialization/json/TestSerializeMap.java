package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of maps
 * 
 * @author kuhn
 *
 */
public class TestSerializeMap {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		// Create GSONTools instance
		GSONTools gsonInstance = new GSONTools(new DefaultTypeFactory());
		
		// Create collections
		Map<String, Integer> integers1   = new HashMap<>();
		Map<String, Object>  primitives1 = new HashMap<>();
		
		// Load values into collections
		integers1.put("k1", 12); integers1.put("k2", 21); integers1.put("k3", 34); integers1.put("k4", 45); integers1.put("k5", 17); 
		primitives1.put("k1", 12); primitives1.put("k2", 21.3); primitives1.put("k3", 'c'); primitives1.put("k4", false); primitives1.put("k5", "test"); primitives1.put("k6", -7.9f); 
		
		// Serialize primitives
		Map<String, Object> serVal1 = gsonInstance.serialize(integers1); //"int1",
		Map<String, Object> serVal3 = gsonInstance.serialize(primitives1); // "pri1", 
		
		// Check result
		assertTrue((int) gsonInstance.getMap(serVal1.get("k1")).get("value") == 12);
		assertTrue(gsonInstance.getMap(serVal1.get("k1")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal1.get("k1")).get("basystype").equals("value"));
		assertTrue((int) gsonInstance.getMap(serVal1.get("k2")).get("value") == 21);
		assertTrue(gsonInstance.getMap(serVal1.get("k2")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal1.get("k2")).get("basystype").equals("value"));
		assertTrue((int) gsonInstance.getMap(serVal1.get("k3")).get("value") == 34);
		assertTrue(gsonInstance.getMap(serVal1.get("k3")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal1.get("k3")).get("basystype").equals("value"));
		assertTrue((int) gsonInstance.getMap(serVal1.get("k4")).get("value") == 45);
		assertTrue(gsonInstance.getMap(serVal1.get("k4")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal1.get("k4")).get("basystype").equals("value"));
		assertTrue((int) gsonInstance.getMap(serVal1.get("k5")).get("value") == 17);
		assertTrue(gsonInstance.getMap(serVal1.get("k5")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal1.get("k5")).get("basystype").equals("value"));
		assertTrue((int) serVal1.get("size") == 5);
		assertTrue(serVal1.get("basystype").equals("map"));

		assertTrue((int) gsonInstance.getMap(serVal3.get("k1")).get("value") == 12);
		assertTrue(gsonInstance.getMap(serVal3.get("k1")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal3.get("k1")).get("basystype").equals("value"));
		assertTrue((double) gsonInstance.getMap(serVal3.get("k2")).get("value") == 21.3);
		assertTrue(gsonInstance.getMap(serVal3.get("k2")).get("typeid").equals("double"));
		assertTrue(gsonInstance.getMap(serVal3.get("k2")).get("basystype").equals("value"));
		assertTrue((char) gsonInstance.getMap(serVal3.get("k3")).get("value") == 'c');
		assertTrue(gsonInstance.getMap(serVal3.get("k3")).get("typeid").equals("character"));
		assertTrue(gsonInstance.getMap(serVal3.get("k3")).get("basystype").equals("value"));
		assertTrue((boolean) gsonInstance.getMap(serVal3.get("k4")).get("value") == false);
		assertTrue(gsonInstance.getMap(serVal3.get("k4")).get("typeid").equals("boolean"));
		assertTrue(gsonInstance.getMap(serVal3.get("k4")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal3.get("k5")).get("value").equals("test"));
		assertTrue(gsonInstance.getMap(serVal3.get("k5")).get("typeid").equals("string"));
		assertTrue(gsonInstance.getMap(serVal3.get("k5")).get("basystype").equals("value"));
		assertTrue((float) gsonInstance.getMap(serVal3.get("k6")).get("value") == -7.9f);
		assertTrue(gsonInstance.getMap(serVal3.get("k6")).get("typeid").equals("float"));
		assertTrue(gsonInstance.getMap(serVal3.get("k6")).get("basystype").equals("value"));
		assertTrue((int) serVal3.get("size") == 6);
		assertTrue(serVal3.get("basystype").equals("map"));
	}
}


