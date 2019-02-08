package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization/deseialization of array types
 * 
 * @author kuhn
 *
 */
public class SerializeDeserializeArray {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		// Create arrays
		int[]     integerArray   = {   2,     4,    6,    8,    9};
		String[]  stringArray    = {"ab",  "bc", "cd"};
		Float[]   floatArray     = {2.1f, -4.0f, 6.4f, 8.2f, 9.1f};
		Object[]  objectArray    = {   2,     4,    6,    8,    9};
				
		// Serialize primitives
		Map<String, Object> serVal1 = GSONTools.Instance.serialize(integerArray); 
		Map<String, Object> serVal2 = GSONTools.Instance.serialize(stringArray);
		Map<String, Object> serVal3 = GSONTools.Instance.serialize(floatArray);  
		Map<String, Object> serVal4 = GSONTools.Instance.serialize(objectArray); 
		
		// Deserialize array
		Integer[] val1 = (Integer[]) GSONTools.Instance.deserialize(serVal1);
		String[]  val2 = (String[])  GSONTools.Instance.deserialize(serVal2);
		Float[]   val3 = (Float[])   GSONTools.Instance.deserialize(serVal3);
		Object[]  val4 = (Object[])  GSONTools.Instance.deserialize(serVal4);
				
	
		
		
		// Check result
		assertTrue(val1.length == 5);
		assertTrue(val1[0] == 2);
		assertTrue(val1[1] == 4);
		assertTrue(val1[2] == 6);
		assertTrue(val1[3] == 8);
		assertTrue(val1[4] == 9);

		assertTrue(val2.length == 3);
		assertTrue(val2[0].equals("ab"));
		assertTrue(val2[1].equals("bc"));
		assertTrue(val2[2].equals("cd"));

		assertTrue(val3.length == 5);
		assertTrue(val3[0] ==  2.1f);
		assertTrue(val3[1] == -4.0f);
		assertTrue(val3[2] ==  6.4f);
		assertTrue(val3[3] ==  8.2f);
		assertTrue(val3[4] ==  9.1f);

		// Check result
		assertTrue(val4.length == 5);
		assertTrue(((Integer) val4[0]) == 2);
		assertTrue(((Integer) val4[1]) == 4);
		assertTrue(((Integer) val4[2]) == 6);
		assertTrue(((Integer) val4[3]) == 8);
		assertTrue(((Integer) val4[4]) == 9);
	}
}


