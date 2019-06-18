package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization/de-serialization of primitive values
 * 
 * @author kuhn
 *
 */
public class TestSerializeDeserializePrimitives {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		// Create GSONTools instance
		GSONTools gsonInstance = new GSONTools(new DefaultTypeFactory());

		
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
		Map<String, Object> serVal11 = gsonInstance.serialize(primVal11); // "val11", 
		Map<String, Object> serVal12 = gsonInstance.serialize(primVal12); // "val12", 
		Map<String, Object> serVal21 = gsonInstance.serialize(primVal21); // "val21",
		Map<String, Object> serVal22 = gsonInstance.serialize(primVal22); // "val22", 
		Map<String, Object> serVal31 = gsonInstance.serialize(primVal31); // "val31", 
		Map<String, Object> serVal32 = gsonInstance.serialize(primVal32); // "val32",
		Map<String, Object> serVal41 = gsonInstance.serialize(primVal41); // "val41", 
		Map<String, Object> serVal51 = gsonInstance.serialize(primVal51); // "val51", 
		Map<String, Object> serVal52 = gsonInstance.serialize(primVal52); // "val52",
		Map<String, Object> serVal61 = gsonInstance.serialize(primVal61); // "val61",

		// Deserialize values
		Object resVal11 = gsonInstance.deserialize(serVal11);
		Object resVal12 = gsonInstance.deserialize(serVal12);
		Object resVal21 = gsonInstance.deserialize(serVal21);
		Object resVal22 = gsonInstance.deserialize(serVal22);
		Object resVal31 = gsonInstance.deserialize(serVal31);
		Object resVal32 = gsonInstance.deserialize(serVal32);
		Object resVal41 = gsonInstance.deserialize(serVal41);
		Object resVal51 = gsonInstance.deserialize(serVal51);
		Object resVal52 = gsonInstance.deserialize(serVal52);
		Object resVal61 = gsonInstance.deserialize(serVal61);
		
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


