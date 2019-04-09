package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of collections
 * 
 * @author kuhn
 *
 */
public class TestSerializeDeserializeCollection {


	/**
	 * Run test case
	 */
	@Test @SuppressWarnings("unchecked")
	void test() {		
		// Create GSONTools instance
		GSONTools gsonInstance = new GSONTools(new DefaultTypeFactory());

		Collection<Integer> integers1   = new HashSet<>();
		Collection<Integer> integers2   = new LinkedList<>();
		Collection<Object>  primitives1 = new LinkedList<>();

		// Load values into collections
		integers1.add(12); integers1.add(21); integers1.add(34); integers1.add(45); integers1.add(17); 
		integers2.add(12); integers2.add(21); integers2.add(34); integers2.add(46); integers2.add(17); 
		primitives1.add(12); primitives1.add(21.3); primitives1.add('c'); primitives1.add(false); primitives1.add("test"); primitives1.add(-7.9f); 

		// Serialize collections
		Map<String, Object> serVal1 = gsonInstance.serialize(integers1); // "int1", 
		Map<String, Object> serVal2 = gsonInstance.serialize(integers2); // "int2",
		Map<String, Object> serVal3 = gsonInstance.serialize(primitives1); // "pri1",

		System.out.println("Serialized JSON:"+serVal1);

		// Deserialize collections
		Collection<Integer> integerColl1 = (Collection<Integer>) gsonInstance.deserialize(serVal1);
		Collection<Integer> integerColl2 = (Collection<Integer>) gsonInstance.deserialize(serVal2);
		Collection<Object>  objectColl1  = (Collection<Object>)  gsonInstance.deserialize(serVal3);

		// Check result
		assertTrue(integerColl1.size() == 5);
		assertTrue(integerColl1.contains(12));
		assertTrue(integerColl1.contains(21));
		assertTrue(integerColl1.contains(34));
		assertTrue(integerColl1.contains(45));
		assertTrue(integerColl1.contains(17));

		assertTrue(integerColl2.size() == 5);
		assertTrue(integerColl2.contains(12));
		assertTrue(integerColl2.contains(21));
		assertTrue(integerColl2.contains(34));
		assertTrue(integerColl2.contains(46));
		assertTrue(integerColl2.contains(17));

		assertTrue(objectColl1.size() == 6);
		assertTrue(objectColl1.contains(12));
		assertTrue(objectColl1.contains(21.3));
		assertTrue(objectColl1.contains('c'));
		assertTrue(objectColl1.contains(false));
		assertTrue(objectColl1.contains("test"));
		assertTrue(objectColl1.contains(-7.9f));
	}
}


