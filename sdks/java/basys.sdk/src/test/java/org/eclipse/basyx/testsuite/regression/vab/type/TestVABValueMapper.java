package org.eclipse.basyx.testsuite.regression.vab.type;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.vab.backend.type.VABValueMapper;
import org.junit.Test;

/**
 * Tests TestVABTypeMapper
 * 
 * @author schnicke
 *
 */
public class TestVABValueMapper {

	/**
	 * Tests mapping a map containing several primitives to the VAB type system
	 */
	@Test
	public void testMapToVAB() {
		// Define used constants
		String TEMPERATURE_KEY = "temperature";
		int TEMPERATURE_VAL = 5;

		String TEMPERATUREFLOAT_KEY = "temperatureFloat";
		float TEMPERATUREFLOAT_VAL = 5.1f;

		String TEMPERATUREDOUBLE_KEY = "temperatureDouble";
		double TEMPERATUREDOUBLE_VAL = 5.11;

		String ISENABLED_KEY = "isEnabled";
		boolean ISENABLED_VAL = true;

		String OPERATIONCHARACTER_KEY = "operationCharacter";
		char OPERATIONCHARACTER_VAL = 'c';

		String ID_KEY = "id";
		String ID_VAL = "X-12";

		// Build expected output
		Map<String, Object> value = new HashMap<>();

		Map<String, Object> temperature = new HashMap<>();
		temperature.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_INTEGER);
		temperature.put(VABValueMapper.KEY_VALUE, String.valueOf(TEMPERATURE_VAL));
		value.put(TEMPERATURE_KEY, temperature);

		Map<String, Object> temperatureFloat = new HashMap<>();
		temperatureFloat.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_FLOAT);
		temperatureFloat.put(VABValueMapper.KEY_VALUE, String.valueOf(TEMPERATUREFLOAT_VAL));
		value.put(TEMPERATUREFLOAT_KEY, temperatureFloat);

		Map<String, Object> temperatureDouble = new HashMap<>();
		temperatureDouble.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_DOUBLE);
		temperatureDouble.put(VABValueMapper.KEY_VALUE, String.valueOf(TEMPERATUREDOUBLE_VAL));
		value.put(TEMPERATUREDOUBLE_KEY, temperatureDouble);

		Map<String, Object> isEnabled = new HashMap<>();
		isEnabled.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_BOOLEAN);
		isEnabled.put(VABValueMapper.KEY_VALUE, String.valueOf(ISENABLED_VAL));
		value.put(ISENABLED_KEY, isEnabled);

		Map<String, Object> operationCharacter = new HashMap<>();
		operationCharacter.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_CHARACTER);
		operationCharacter.put(VABValueMapper.KEY_VALUE, String.valueOf(OPERATIONCHARACTER_VAL));
		value.put(OPERATIONCHARACTER_KEY, operationCharacter);

		Map<String, Object> id = new HashMap<>();
		id.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_STRING);
		id.put(VABValueMapper.KEY_VALUE, String.valueOf(ID_VAL));
		value.put(ID_KEY, id);

		Map<String, Object> expected = new HashMap<>();
		expected.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_MAP);
		expected.put(VABValueMapper.KEY_VALUE, value);

		// Build map which will be mapped
		Map<String, Object> map = new HashMap<>();
		map.put(TEMPERATURE_KEY, TEMPERATURE_VAL);
		map.put(TEMPERATUREFLOAT_KEY, TEMPERATUREFLOAT_VAL);
		map.put(TEMPERATUREDOUBLE_KEY, TEMPERATUREDOUBLE_VAL);
		map.put(ISENABLED_KEY, ISENABLED_VAL);
		map.put(OPERATIONCHARACTER_KEY, OPERATIONCHARACTER_VAL);
		map.put(ID_KEY, ID_VAL);

		// Check if mapping was done correct
		assertEquals(expected, new VABValueMapper().toVABMap(map));
		System.out.println(new VABValueMapper().toVABMap(map));
	}

	/**
	 * Tests mapping a collection to the VAB type system
	 */
	@Test
	public void testCollectionToVAB() {
		// Define used constants
		String ENTRY1 = "Test";
		boolean ENTRY2 = true;

		// Build expected output
		Map<String, Object> stringMap = new HashMap<>();
		stringMap.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_STRING);
		stringMap.put(VABValueMapper.KEY_VALUE, String.valueOf(ENTRY1));

		Map<String, Object> collection = new HashMap<>();
		collection.put("0", stringMap);

		Map<String, Object> booleanMap = new HashMap<>();
		booleanMap.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_BOOLEAN);
		booleanMap.put(VABValueMapper.KEY_VALUE, String.valueOf(ENTRY2));
		collection.put("1", booleanMap);

		Map<String, Object> expected = new HashMap<>();
		expected.put(VABValueMapper.KEY_TYPE, VABValueMapper.TYPE_COLLECTION);
		expected.put(VABValueMapper.KEY_VALUE, collection);

		// Build collection which will be mapped
		List<Object> list = new ArrayList<>();
		list.add(ENTRY1);
		list.add(ENTRY2);

		// Check if mapping was done correct
		assertEquals(expected, new VABValueMapper().toVABMap(list));
	}

	/**
	 * Tests mapping a map from the VAB typesystem <br />
	 * <b>Assumption:</b> If the previous tests work, it is only necessary to test
	 * if a round trip does work to show that the inverse mapping is working
	 * correctly
	 */
	@Test
	public void testFromVAB() {
		// Create map to be mapped
		Map<String, Object> map = new HashMap<>();
		map.put("a", 10);
		map.put("b", 12.1f);
		List<Object> list = new ArrayList<>();
		list.add("Test");
		list.add(true);
		list.add(map);

		// Perform the mapping
		VABValueMapper mapper = new VABValueMapper();
		Map<String, Object> mapped = mapper.toVABMap(list);

		// Check if the round trip does produce the same list as the input
		assertEquals(list, mapper.fromVABMap(mapped));
	}
}
