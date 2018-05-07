package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization/de-serialization of primitive values
 * 
 * @author kuhn
 *
 */
public class SerializeDeserializePrimitives {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		// Create primitive values
		int     primVal11 = 1;
		int     primVal12 = -1;
		float   primVal21 = 1.4f;
		float   primVal22 = -4.5f;
		double  primVal31 = 5.2;
		double  primVal32 = -8921.2;
		char    primVal41 = 'a';
		boolean primVal51 = true;
		boolean primVal52 = false;
		String  primVal61 = "Test12234!";
		
		
		// Serialize primitives
		JSONObject serVal11 = JSONTools.Instance.serialize(primVal11); // "val11", 
		JSONObject serVal12 = JSONTools.Instance.serialize(primVal12); // "val12", 
		JSONObject serVal21 = JSONTools.Instance.serialize(primVal21); // "val21",
		JSONObject serVal22 = JSONTools.Instance.serialize(primVal22); // "val22", 
		JSONObject serVal31 = JSONTools.Instance.serialize(primVal31); // "val31", 
		JSONObject serVal32 = JSONTools.Instance.serialize(primVal32); // "val32",
		JSONObject serVal41 = JSONTools.Instance.serialize(primVal41); // "val41", 
		JSONObject serVal51 = JSONTools.Instance.serialize(primVal51); // "val51", 
		JSONObject serVal52 = JSONTools.Instance.serialize(primVal52); // "val52",
		JSONObject serVal61 = JSONTools.Instance.serialize(primVal61); // "val61",

		// Deserialize values
		Object resVal11 = JSONTools.Instance.deserialize(serVal11);
		Object resVal12 = JSONTools.Instance.deserialize(serVal12);
		Object resVal21 = JSONTools.Instance.deserialize(serVal21);
		Object resVal22 = JSONTools.Instance.deserialize(serVal22);
		Object resVal31 = JSONTools.Instance.deserialize(serVal31);
		Object resVal32 = JSONTools.Instance.deserialize(serVal32);
		Object resVal41 = JSONTools.Instance.deserialize(serVal41);
		Object resVal51 = JSONTools.Instance.deserialize(serVal51);
		Object resVal52 = JSONTools.Instance.deserialize(serVal52);
		Object resVal61 = JSONTools.Instance.deserialize(serVal61);
		
		// Check results
		assertTrue((int)     resVal11 == primVal11);
		assertTrue((int)     resVal12 == primVal12);
		assertTrue((float)   resVal21 == primVal21);
		assertTrue((float)   resVal22 == primVal22);
		assertTrue((double)  resVal31 == primVal31);
		assertTrue((double)  resVal32 == primVal32);
		assertTrue((char)    resVal41 == primVal41);
		assertTrue((boolean) resVal51 == primVal51);
		assertTrue((boolean) resVal52 == primVal52);
		assertTrue(((String) resVal61).equals(primVal61));
	}
}


