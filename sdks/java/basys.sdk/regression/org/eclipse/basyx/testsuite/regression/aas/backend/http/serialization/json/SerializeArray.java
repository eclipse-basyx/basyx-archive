package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of array types
 * 
 * @author kuhn
 *
 */
public class SerializeArray {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		// Create arrays
		int[]    integerArray   = {2, 4, 6, 8, 9};
		String[] stringArray    = {"ab", "bc", "cd"};
				
		// Serialize primitives
		JSONObject serVal1 = JSONTools.Instance.serialize(integerArray); // "int1", 
		JSONObject serVal2 = JSONTools.Instance.serialize(stringArray); // "str", 

		// Check result
		assertTrue((int) serVal1.get("size") == 5);
		assertTrue(serVal1.get("kind").equals("array"));
		assertTrue(serVal1.get("type").equals("int"));
		assertTrue(serVal1.getJSONObject("0").get("kind").equals("value"));
		assertTrue(serVal1.getJSONObject("0").get("typeid").equals("int"));
		assertTrue((int) serVal1.getJSONObject("0").get("value") == 2);
		assertTrue(serVal1.getJSONObject("1").get("kind").equals("value"));
		assertTrue(serVal1.getJSONObject("1").get("typeid").equals("int"));
		assertTrue((int) serVal1.getJSONObject("1").get("value") == 4);
		assertTrue(serVal1.getJSONObject("2").get("kind").equals("value"));
		assertTrue(serVal1.getJSONObject("2").get("typeid").equals("int"));
		assertTrue((int) serVal1.getJSONObject("2").get("value") == 6);
		assertTrue(serVal1.getJSONObject("3").get("kind").equals("value"));
		assertTrue(serVal1.getJSONObject("3").get("typeid").equals("int"));
		assertTrue((int) serVal1.getJSONObject("3").get("value") == 8);
		assertTrue(serVal1.getJSONObject("4").get("kind").equals("value"));
		assertTrue(serVal1.getJSONObject("4").get("typeid").equals("int"));
		assertTrue((int) serVal1.getJSONObject("4").get("value") == 9);

		assertTrue((int) serVal2.get("size") == 3);
		assertTrue(serVal2.get("kind").equals("array"));
		assertTrue(serVal2.get("type").equals("string"));
		assertTrue(serVal2.getJSONObject("0").get("kind").equals("value"));
		assertTrue(serVal2.getJSONObject("0").get("typeid").equals("string"));
		assertTrue(serVal2.getJSONObject("0").get("value").equals("ab"));
		assertTrue(serVal2.getJSONObject("1").get("kind").equals("value"));
		assertTrue(serVal2.getJSONObject("1").get("typeid").equals("string"));
		assertTrue(serVal2.getJSONObject("1").get("value").equals("bc"));
		assertTrue(serVal2.getJSONObject("2").get("kind").equals("value"));
		assertTrue(serVal2.getJSONObject("2").get("typeid").equals("string"));
		assertTrue(serVal2.getJSONObject("2").get("value").equals("cd"));
	}
}


