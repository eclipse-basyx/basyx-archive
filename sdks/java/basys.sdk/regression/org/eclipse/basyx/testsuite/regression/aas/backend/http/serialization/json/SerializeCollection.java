package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of collections
 * 
 * @author kuhn
 *
 */
public class SerializeCollection {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		// Create collections
		Collection<Integer> integers1   = new HashSet<>();
		Collection<Integer> integers2   = new LinkedList<>();
		Collection<Object>  primitives1 = new LinkedList<>();
		
		// Load values into collections
		integers1.add(12); integers1.add(21); integers1.add(34); integers1.add(45); integers1.add(17); 
		integers2.add(12); integers2.add(21); integers2.add(34); integers2.add(46); integers2.add(17); 
		primitives1.add(12); primitives1.add(21.3); primitives1.add('c'); primitives1.add(false); primitives1.add("test"); primitives1.add(-7.9f); 
		
		// Serialize primitives
		JSONObject serVal1 = JSONTools.Instance.serialize(integers1); // "int1", 
		JSONObject serVal2 = JSONTools.Instance.serialize(integers2); // "int2", 
		JSONObject serVal3 = JSONTools.Instance.serialize(primitives1); // "pri1", 

		// Check result
		assertTrue(serVal1.has("0"));
		assertTrue(serVal1.has("1"));
		assertTrue(serVal1.has("2"));
		assertTrue(serVal1.has("3"));
		assertTrue(serVal1.has("4"));
		assertTrue((int) serVal1.get("size") == 5);
		assertTrue(serVal1.get("kind").equals("collection"));

		assertTrue((int) serVal2.getJSONObject("0").get("value") == 12);
		assertTrue(serVal2.getJSONObject("0").get("typeid").equals("int"));
		assertTrue(serVal2.getJSONObject("0").get("kind").equals("value"));
		assertTrue((int) serVal2.getJSONObject("1").get("value") == 21);
		assertTrue(serVal2.getJSONObject("1").get("typeid").equals("int"));
		assertTrue(serVal2.getJSONObject("1").get("kind").equals("value"));
		assertTrue((int) serVal2.getJSONObject("2").get("value") == 34);
		assertTrue(serVal2.getJSONObject("2").get("typeid").equals("int"));
		assertTrue(serVal2.getJSONObject("2").get("kind").equals("value"));
		assertTrue((int) serVal2.getJSONObject("3").get("value") == 46);
		assertTrue(serVal2.getJSONObject("3").get("typeid").equals("int"));
		assertTrue(serVal2.getJSONObject("3").get("kind").equals("value"));
		assertTrue((int) serVal2.getJSONObject("4").get("value") == 17);
		assertTrue(serVal2.getJSONObject("4").get("typeid").equals("int"));
		assertTrue(serVal2.getJSONObject("4").get("kind").equals("value"));
		assertTrue((int) serVal2.get("size") == 5);
		assertTrue(serVal2.get("kind").equals("collection"));

		assertTrue((int) serVal3.getJSONObject("0").get("value") == 12);
		assertTrue(serVal3.getJSONObject("0").get("typeid").equals("int"));
		assertTrue(serVal3.getJSONObject("0").get("kind").equals("value"));
		assertTrue((double) serVal3.getJSONObject("1").get("value") == 21.3);
		assertTrue(serVal3.getJSONObject("1").get("typeid").equals("double"));
		assertTrue(serVal3.getJSONObject("1").get("kind").equals("value"));
		assertTrue((char) serVal3.getJSONObject("2").get("value") == 'c');
		assertTrue(serVal3.getJSONObject("2").get("typeid").equals("character"));
		assertTrue(serVal3.getJSONObject("2").get("kind").equals("value"));
		assertTrue((boolean) serVal3.getJSONObject("3").get("value") == false);
		assertTrue(serVal3.getJSONObject("3").get("typeid").equals("boolean"));
		assertTrue(serVal3.getJSONObject("3").get("kind").equals("value"));
		assertTrue(serVal3.getJSONObject("4").get("value").equals("test"));
		assertTrue(serVal3.getJSONObject("4").get("typeid").equals("string"));
		assertTrue(serVal3.getJSONObject("4").get("kind").equals("value"));
		assertTrue((float) serVal3.getJSONObject("5").get("value") == -7.9f);
		assertTrue(serVal3.getJSONObject("5").get("typeid").equals("float"));
		assertTrue(serVal3.getJSONObject("5").get("kind").equals("value"));
		assertTrue((int) serVal3.get("size") == 6);
		assertTrue(serVal3.get("kind").equals("collection"));
	}
}


