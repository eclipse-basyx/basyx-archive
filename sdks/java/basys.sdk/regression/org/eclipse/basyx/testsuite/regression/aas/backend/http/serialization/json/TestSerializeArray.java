package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of array types
 * 
 * @author vikas
 *
 */
public class TestSerializeArray {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		GSONTools gsonInstance = GSONTools.Instance;
		// Create arrays
		int[]    integerArray   = {2, 4, 6, 8, 9};
		String[] stringArray    = {"ab", "bc", "cd"};
				
		// Serialize primitives
		Map<String, Object> serVal1Gson = gsonInstance.serialize(integerArray); // "int1", 
		Map<String, Object> serVal2JGson = gsonInstance.serialize(stringArray); // "str", 
		
		//Check if the internal Values are again instances of map if not throw error.
		
		
		assertTrue((int) serVal1Gson.get("size") == 5);
		assertTrue(serVal1Gson.get("basystype").equals("array"));
		assertTrue(serVal1Gson.get("type").equals("int"));
		assertTrue(gsonInstance.getMap(serVal1Gson.get("0")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal1Gson.get("0")).get("typeid").equals("int"));
		assertTrue((int) gsonInstance.getMap(serVal1Gson.get("0")).get("value") == 2);
		assertTrue(gsonInstance.getMap(serVal1Gson.get("1")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal1Gson.get("1")).get("typeid").equals("int"));
		assertTrue((int) gsonInstance.getMap(serVal1Gson.get("1")).get("value") == 4);
		assertTrue(gsonInstance.getMap(serVal1Gson.get("2")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal1Gson.get("2")).get("typeid").equals("int"));
		assertTrue((int)gsonInstance.getMap( serVal1Gson.get("2")).get("value") == 6);
		assertTrue(gsonInstance.getMap(serVal1Gson.get("3")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal1Gson.get("3")).get("typeid").equals("int"));
		assertTrue((int)gsonInstance.getMap( serVal1Gson.get("3")).get("value") == 8);
		assertTrue(gsonInstance.getMap(serVal1Gson.get("4")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal1Gson.get("4")).get("typeid").equals("int"));
		assertTrue((int)gsonInstance.getMap( serVal1Gson.get("4")).get("value") == 9);

		assertTrue((int) serVal2JGson.get("size") == 3);
		assertTrue(serVal2JGson.get("basystype").equals("array"));
		assertTrue(serVal2JGson.get("type").equals("string"));
		assertTrue(gsonInstance.getMap(serVal2JGson.get("0")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal2JGson.get("0")).get("typeid").equals("string"));
		assertTrue(gsonInstance.getMap(serVal2JGson.get("0")).get("value").equals("ab"));
		assertTrue(gsonInstance.getMap(serVal2JGson.get("1")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal2JGson.get("1")).get("typeid").equals("string"));
		assertTrue(gsonInstance.getMap(serVal2JGson.get("1")).get("value").equals("bc"));
		assertTrue(gsonInstance.getMap(serVal2JGson.get("2")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal2JGson.get("2")).get("typeid").equals("string"));
		assertTrue(gsonInstance.getMap(serVal2JGson.get("2")).get("value").equals("cd"));

		
		
		

		// Check result

	}


	
}


