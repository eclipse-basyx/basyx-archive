package org.eclipse.basyx.vab.backend.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Handles the VAB value system: <br />
 * - Mapping to it <br />
 * - Mapping from it <br />
 * <br />
 * E.g. the integer 10 is mapped to {"type": "int", "value": "10"}
 * 
 * @author schnicke
 *
 */
public class VABValueMapper {
	// Used string constants
	public static final String TYPE_INTEGER = "int";
	public static final String TYPE_FLOAT = "float";
	public static final String TYPE_DOUBLE = "double";
	public static final String TYPE_STRING = "string";
	public static final String TYPE_BOOLEAN = "boolean";
	public static final String TYPE_CHARACTER = "character";

	public static final String TYPE_COLLECTION = "collection";
	public static final String TYPE_MAP = "map";

	public static final String KEY_TYPE = "type";
	public static final String KEY_VALUE = "value";

	// Used to check if a primitive is wrapped by an Object
	private List<Class<?>> primitiveWrappers = Arrays.asList(Integer.class, Double.class, Float.class, Boolean.class,
			Character.class);

	/**
	 * Creates a java value from a previously created VAB value
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object fromVABMap(Map<String, Object> map) {
		// Get value type
		String type = (String) map.get(KEY_TYPE);
		switch (type) {
		case TYPE_COLLECTION:
			return toCollection((Map<String, Object>) map.get(KEY_VALUE));
		case TYPE_MAP:
			return toMap((Map<String, Object>) map.get(KEY_VALUE));
		default: // If it is not a collection nor a map, it has to be a primitive
			return toPrimitive(map);
		}
	}

	/**
	 * Creates a primitive value from a VAB value
	 * 
	 * @param map
	 * @return
	 */
	private Object toPrimitive(Map<String, Object> map) {
		// Get type and value
		String type = (String) map.get(KEY_TYPE);
		String value = (String) map.get(KEY_VALUE);

		// Create appropriate value by using valueOf()
		switch (type) {
		case TYPE_INTEGER:
			return Integer.valueOf(value);
		case TYPE_FLOAT:
			return Float.valueOf(value);
		case TYPE_DOUBLE:
			return Double.valueOf(value);
		case TYPE_STRING:
			return value;
		case TYPE_BOOLEAN:
			return Boolean.valueOf(value);
		case TYPE_CHARACTER:
			return value.toCharArray()[0];
		default:
			throw new RuntimeException("Unknown type " + type + " with value " + value);
		}
	}

	/**
	 * Create
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object toMap(Map<String, Object> map) {
		Map<String, Object> ret = new HashMap<>();

		for (String s : map.keySet()) {
			ret.put(s, fromVABMap((Map<String, Object>) map.get(s)));
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	private Object toCollection(Map<String, Object> map) {
		List<Object> ret = new ArrayList<>();

		for (String s : map.keySet()) {
			ret.add(fromVABMap((Map<String, Object>) map.get(s)));
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> toVABMap(Object o) {
		if (o instanceof Map<?, ?>) {
			return fromMap((Map<String, Object>) o);
		} else if (o.getClass().isPrimitive() || o instanceof String || primitiveWrappers.contains(o.getClass())) {
			return fromPrimitive(o);
		} else if (o instanceof Collection<?>) {
			return fromCollection((Collection<?>) o);
		} else {
			throw new RuntimeException("Unknown object to serialize!");
		}
	}

	private Map<String, Object> fromMap(Map<String, Object> map) {
		Map<String, Object> ret = new HashMap<>();
		ret.put(KEY_TYPE, TYPE_MAP);

		Map<String, Object> value = new HashMap<>();
		for (String k : map.keySet()) {
			value.put(k, toVABMap(map.get(k)));
		}

		ret.put(KEY_VALUE, value);

		return ret;
	}

	private Map<String, Object> fromCollection(Collection<?> c) {
		Iterator<?> it = c.iterator();
		Map<String, Object> ret = new HashMap<>();
		ret.put("type", TYPE_COLLECTION);
		Map<String, Object> value = new HashMap<>();
		int i = 0;

		while (it.hasNext()) {
			value.put(String.valueOf(i), toVABMap(it.next()));
			i++;
		}

		ret.put(KEY_VALUE, value);

		return ret;
	}

	/**
	 * Creates a map containing the description of the primitive value
	 * 
	 * @param value
	 * @return
	 */
	private Map<String, Object> fromPrimitive(Object value) {
		//
		String type;
		if (value instanceof Integer) {
			type = TYPE_INTEGER;
		} else if (value instanceof Float) {
			type = TYPE_FLOAT;
		} else if (value instanceof Double) {
			type = TYPE_DOUBLE;
		} else if (value instanceof String) {
			type = TYPE_STRING;
		} else if (value instanceof Boolean) {
			type = TYPE_BOOLEAN;
		} else if (value instanceof Character) {
			type = TYPE_CHARACTER;
		} else {
			throw new RuntimeException(
					"Unknown type " + value.getClass() + " with value " + value + " detected while serializing");
		}

		Map<String, Object> ret = new HashMap<>();
		ret.put(KEY_VALUE, value.toString());
		ret.put(KEY_TYPE, type);
		return ret;
	}
}
