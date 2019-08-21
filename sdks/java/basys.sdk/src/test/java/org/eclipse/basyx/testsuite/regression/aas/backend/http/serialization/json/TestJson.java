package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import jersey.repackaged.com.google.common.collect.Sets;

/**
 * Tests if the json serialization is behaving as expected
 * 
 * @author schnicke
 *
 */
public class TestJson {
	GSONTools tools = new GSONTools(new DefaultTypeFactory());

	public static void main(String[] args) {
		GSONTools tools = new GSONTools(new DefaultTypeFactory());

		System.out.println(tools.deserialize(tools.serialize(new SimpleVABElement())));
	}

	/**
	 * Tests if a double is correctly (de-)serialized
	 */
	@Test
	public void testDouble() {
		testDeserializePrimitive(12.3);

		JsonPrimitive primitive = new JsonPrimitive(12.3);
		assertEquals(primitive.toString(), tools.serialize(12.3));
	}
	
	/**
	 * Tests if an integer is correctly (de-)serialized
	 */
	@Test
	public void testInteger() {
		testDeserializePrimitive(12);

		JsonPrimitive primitive = new JsonPrimitive(12);
		assertEquals(primitive.toString(), tools.serialize(12));
	}

	/**
	 * Tests if a boolean is correctly (de-)serialized
	 */
	@Test
	public void testBoolean() {
		testDeserializePrimitive(false);

		JsonPrimitive primitive = new JsonPrimitive(false);
		assertEquals(primitive.toString(), tools.serialize(false));
	}

	/**
	 * Tests if a string is correctly (de-)serialized
	 */
	@Test
	public void testString() {
		testDeserializePrimitive("HelloWorld");
		testDeserializePrimitive("12.2.2");

		JsonPrimitive primitive = new JsonPrimitive("HelloWorld");
		assertEquals(primitive.toString(), tools.serialize("HelloWorld"));
	}

	/**
	 * Tests if a map is correctly (de-)serialized
	 */
	@Test
	public void testMap() {
		String str = "{\"a\": { \"x\" : 123}, \"b\": \"123\"}";

		Map<String, Object> expected = new HashMap<>();
		Map<String, Object> a = new HashMap<>();
		a.put("x", 123);
		expected.put("a", a);
		expected.put("b", "123");

		assertEquals(expected, tools.deserialize(str));
		
		
		JsonObject aObj = new JsonObject();
		aObj.add("x", new JsonPrimitive(123));
		
		JsonObject expectedObj = new JsonObject();
		expectedObj.add("a", aObj);
		expectedObj.add("b", new JsonPrimitive("123"));

		assertEquals(expectedObj.toString(), tools.serialize(expected));
	}

	/**
	 * Tests if a map containing Sets/Lists is (de-)serialized correctly
	 */
	@Test
	public void testMapWithCollection() {
		String str = "{\"" + GSONTools.BASYXTYPE + "\": {\"a\": \"" + GSONTools.LIST + "\", \"b\": \"" + GSONTools.SET + "\"}, \"a\": [1,2,3], \"b\": [4,5,6]}";

		Map<String, Object> map = new HashMap<>();
		map.put("a", Arrays.asList(1, 2, 3));
		map.put("b", Sets.newHashSet(4, 5, 6));

		assertEquals(map, tools.deserialize(str));
		
		assertEquals(map, tools.deserialize(tools.serialize(map)));
	}

	/**
	 * Tests if a list is correctly (de-)serialized
	 */
	@Test
	public void testList() {
		String strComplexOrdered = "[{\"" + GSONTools.INDEX + "\": 0, \"a\": 2}, {\"" + GSONTools.INDEX + "\": 1, \"a\": false}]";
		List<Map<String, Object>> orderedCollection = new ArrayList<>();
		orderedCollection.add(Collections.singletonMap("a", 2));
		orderedCollection.add(Collections.singletonMap("a", false));

		assertEquals(orderedCollection, tools.deserialize(strComplexOrdered));

		JsonObject o1 = new JsonObject();
		o1.add("a", new JsonPrimitive(2));
		o1.add(GSONTools.INDEX, new JsonPrimitive(0));
		JsonObject o2 = new JsonObject();
		o2.add("a", new JsonPrimitive(false));
		o2.add(GSONTools.INDEX, new JsonPrimitive(1));

		JsonArray ordered = new JsonArray();
		ordered.add(o1);
		ordered.add(o2);

		assertEquals(ordered.toString(), tools.serialize(orderedCollection));

		List<Integer> primitiveList = new ArrayList<>();
		primitiveList.add(1);

		assertEquals(primitiveList, tools.deserialize(tools.serialize(primitiveList)));
	}

	/**
	 * Tests if a set is correctly (de-)serialized
	 */
	@Test
	public void testSet() {
		String strComplexUnordered = "[{\"a\": 4}]";

		Set<Map<String, Object>> unorderedSet = new HashSet<>();
		unorderedSet.add(Collections.singletonMap("a", 4));
		
		assertEquals(unorderedSet, tools.deserialize(strComplexUnordered));
		
		
		JsonArray unorderedArray = new JsonArray();
		JsonObject o1 = new JsonObject();
		o1.add("a", new JsonPrimitive(4));
		unorderedArray.add(o1);
		
		assertEquals(unorderedArray.toString(), tools.serialize(unorderedSet));

		Set<Integer> primitiveSet = new HashSet<>();
		primitiveSet.add(1);

		assertEquals(primitiveSet, tools.deserialize(tools.serialize(primitiveSet)));
	}

	/**
	 * Tests if a nonserializable function is correctly serialized.
	 */
	@Test
	public void testNonSerializableFunction() {
		JsonObject functionObject = new JsonObject();
		functionObject.add(GSONTools.BASYXFUNCTIONTYPE, new JsonPrimitive(GSONTools.OPERATION));

		Consumer<Integer> testConsumer = x -> {
		};

		assertEquals(functionObject.toString(), tools.serialize(testConsumer));
		
		
		String operation = GSONTools.BASYXINVOCABLE;
		assertEquals(operation, tools.deserialize(functionObject.toString()));

	}

	/**
	 * Tests if a serializable function is correctly (de-)serialized.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testSerializableFunction() {
		Function<Integer, Integer> testFunction = (Function<Integer, Integer> & Serializable) (x -> x * x);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// Try to serialize object
		try {
			ObjectOutputStream oos = new ObjectOutputStream(outStream);
			oos.writeObject(testFunction);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Try to encode to string
		String encoded = Base64.getEncoder().encodeToString(outStream.toByteArray());

		JsonObject functionObject = new JsonObject();
		functionObject.add(GSONTools.BASYXFUNCTIONTYPE, new JsonPrimitive(GSONTools.LAMBDA));
		functionObject.add(GSONTools.BASYXFUNCTIONVALUE, new JsonPrimitive(encoded));

		assertEquals(functionObject.toString(), tools.serialize(testFunction));

		Function<Integer, Integer> deserialized = (Function<Integer, Integer>) tools.deserialize(functionObject.toString());

		assertEquals(testFunction.apply(5), deserialized.apply(5));
	}

	/**
	 * Tests for an arbitrary primitive object if it is deserialized correctly
	 * 
	 * @param toTest
	 */
	private void testDeserializePrimitive(Object toTest) {
		Object obj = tools.deserialize(toTest.toString());
		assertTrue(obj.getClass().equals(toTest.getClass()));
		assertEquals(toTest, obj);
	}
}
