package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of collections
 * 
 * @author vikas
 *
 */
public class SerializeCollection {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		GSONTools gsonInstance = GSONTools.Instance;
		
		// Create collections
		Collection<Integer> integers1   = new HashSet<>();
		Collection<Integer> integers2   = new LinkedList<>();
		Collection<Object>  primitives1 = new LinkedList<>();
		
		// Load values into collections
		integers1.add(12); integers1.add(21); integers1.add(34); integers1.add(45); integers1.add(17); 
		integers2.add(12); integers2.add(21); integers2.add(34); integers2.add(46); integers2.add(17); 
		primitives1.add(12); primitives1.add(21.3); primitives1.add('c'); primitives1.add(false); primitives1.add("test"); primitives1.add(-7.9f); 
		
		// Serialize primitives 

		Map<String, Object> serVal1 = gsonInstance.serialize(integers1); // "int1", 
		Map<String, Object> serVal2 = gsonInstance.serialize(integers2); // "int2", 
		Map<String, Object> serVal3 = gsonInstance.serialize(primitives1); 
		// Check result
		assertTrue(serVal1.containsKey("0"));
		assertTrue(serVal1.containsKey("1"));
		assertTrue(serVal1.containsKey("2"));
		assertTrue(serVal1.containsKey("3"));
		assertTrue(serVal1.containsKey("4"));
		assertTrue((int) serVal1.get("size") == 5);
		assertTrue(serVal1.get("basystype").equals("set"));

		assertTrue((int) gsonInstance.getMap(serVal2.get("0")).get("value") == 12);
		assertTrue(gsonInstance.getMap(serVal2.get("0")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal2.get("0")).get("basystype").equals("value"));
		assertTrue((int) gsonInstance.getMap(serVal2.get("1")).get("value") == 21);
		assertTrue(gsonInstance.getMap(serVal2.get("1")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal2.get("1")).get("basystype").equals("value"));
		assertTrue((int) gsonInstance.getMap(serVal2.get("2")).get("value") == 34);
		assertTrue(gsonInstance.getMap(serVal2.get("2")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal2.get("2")).get("basystype").equals("value"));
		assertTrue((int) gsonInstance.getMap(serVal2.get("3")).get("value") == 46);
		assertTrue(gsonInstance.getMap(serVal2.get("3")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal2.get("3")).get("basystype").equals("value"));
		assertTrue((int) gsonInstance.getMap(serVal2.get("4")).get("value") == 17);
		assertTrue(gsonInstance.getMap(serVal2.get("4")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal2.get("4")).get("basystype").equals("value"));
		assertTrue((int) serVal2.get("size") == 5);
		assertTrue(serVal2.get("basystype").equals("collection"));

		assertTrue((int) gsonInstance.getMap(serVal3.get("0")).get("value") == 12);
		assertTrue(gsonInstance.getMap(serVal3.get("0")).get("typeid").equals("int"));
		assertTrue(gsonInstance.getMap(serVal3.get("0")).get("basystype").equals("value"));
		assertTrue((double) gsonInstance.getMap(serVal3.get("1")).get("value") == 21.3);
		assertTrue(gsonInstance.getMap(serVal3.get("1")).get("typeid").equals("double"));
		assertTrue(gsonInstance.getMap(serVal3.get("1")).get("basystype").equals("value"));
		assertTrue((char) gsonInstance.getMap(serVal3.get("2")).get("value") == 'c');
		assertTrue(gsonInstance.getMap(serVal3.get("2")).get("typeid").equals("character"));
		assertTrue(gsonInstance.getMap(serVal3.get("2")).get("basystype").equals("value"));
		assertTrue((boolean) gsonInstance.getMap(serVal3.get("3")).get("value") == false);
		assertTrue(gsonInstance.getMap(serVal3.get("3")).get("typeid").equals("boolean"));
		assertTrue(gsonInstance.getMap(serVal3.get("3")).get("basystype").equals("value"));
		assertTrue(gsonInstance.getMap(serVal3.get("4")).get("value").equals("test"));
		assertTrue(gsonInstance.getMap(serVal3.get("4")).get("typeid").equals("string"));
		assertTrue(gsonInstance.getMap(serVal3.get("4")).get("basystype").equals("value"));
		assertTrue((float) gsonInstance.getMap(serVal3.get("5")).get("value") == -7.9f);
		assertTrue(gsonInstance.getMap(serVal3.get("5")).get("typeid").equals("float"));
		assertTrue(gsonInstance.getMap(serVal3.get("5")).get("basystype").equals("value"));
		assertTrue((int) serVal3.get("size") == 6);
		assertTrue(serVal3.get("basystype").equals("collection"));
	}
}


