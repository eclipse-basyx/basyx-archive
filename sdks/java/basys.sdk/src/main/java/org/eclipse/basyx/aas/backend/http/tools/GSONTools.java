package org.eclipse.basyx.aas.backend.http.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.basyx.aas.backend.http.tools.factory.GSONToolsFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * Provides means for (de-)serialization of Primitives (int, double, string,
 * boolean), Maps, Sets and Lists. <br />
 * Since JSON is not able to differentiate between Sets and Lists, additional
 * information is added. When a Collection of objects is serialized, this
 * information is directly added using an "index" key. <br />
 * However, collections of primitives do not allow adding an "index" key. To
 * handle this, a type tag is added on the same level as the collection. For
 * more details, see <i>TestJson</i>
 * 
 * @author rajashek, schnicke
 *
 */
public class GSONTools implements Serializer {
	// Used string constants
	public static final String INDEX = "index";
	public static final String OPERATION = "operation";
	public static final String LAMBDA = "lambda";
	public static final String BASYXFUNCTIONTYPE = "_basyxFunctionType";
	public static final String BASYXINVOCABLE = "_basyxInvocable";
	public static final String LIST = "list";
	public static final String SET = "set";
	public static final String BASYXTYPE = "_basyxTypes";
	public static final String BASYXVALUE = "_value";
	public static final String BASYXFUNCTIONVALUE = "_basyxFunctionValue";

	/**
	 * JsonParser reference
	 */
	protected static JsonParser parser = new JsonParser();

	/**
	 * Type factory
	 */
	protected GSONToolsFactory toolsFactory = null;

	/**
	 * Constructor
	 */
	public GSONTools(GSONToolsFactory factory) {
		// Store factory reference
		toolsFactory = factory;
	}

	/**
	 * Set factory instance
	 */
	public void setFactory(GSONToolsFactory newFactoryInstance) {
		// Store factory instance
		toolsFactory = newFactoryInstance;
	}

	@Override
	public Object deserialize(String str) {
		JsonElement elem = parser.parse(str);

		// Handle edge case of collection of primitives
		if (elem.isJsonObject()) {
			JsonObject obj = elem.getAsJsonObject();
			if (obj.has(BASYXVALUE)) {
				JsonArray array = (JsonArray) obj.get(BASYXVALUE);
				if (obj.get(BASYXTYPE).getAsString().equals(SET)) {
					return deserializeJsonArrayAsSet(array);
				} else {
					return deserializeJsonArrayAsList(array);
				}
			}
		}

		return deserializeJsonElement(elem);
	}

	@Override
	public String serialize(Object obj) {
		JsonElement elem = serializeToJsonElement(obj);

		// Handle edge case of collection of primitives
		if (obj instanceof Collection<?>) {
			Collection<?> col = (Collection<?>) obj;
			if (col.size() > 0) {
				Object item = col.iterator().next();
				if (item.getClass().isPrimitive() || isWrapperType(item.getClass())) {
					JsonObject jObj = new JsonObject();
					String type;
					if (col instanceof List<?>) {
						type = LIST;
					} else {
						type = SET;
					}
					jObj.add(BASYXTYPE, new JsonPrimitive(type));
					jObj.add(BASYXVALUE, elem);
					elem = jObj;
				}
			}
		}

		return elem.toString();
	}

	/**
	 * Serialized an arbitrary object to a JsonElement
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JsonElement serializeToJsonElement(Object obj) {
		if (obj == null) {
			return JsonNull.INSTANCE;
		} else if (obj.getClass().isPrimitive() || isWrapperType(obj.getClass()) || obj instanceof String) {
			return serializePrimitive(obj);
		} else if (obj instanceof Map<?, ?>) {
			return serializeMap((Map<String, Object>) obj);
		} else if (obj instanceof List<?>) {
			return serializeList((List<?>) obj);
		} else if (obj instanceof Set<?>) {
			return serializeSet((Set<?>) obj);
		} else if (isFunction(obj)) {
			return serializeFunction(obj);
		}
		throw new RuntimeException("Unknown element!");
	}

	/**
	 * Deserializes a JsonElement to an object
	 * 
	 * @param elem
	 * @return
	 */
	private Object deserializeJsonElement(JsonElement elem) {
		if (elem.isJsonPrimitive()) {
			return deserializeJsonPrimitive(elem.getAsJsonPrimitive());
		} else if (elem.isJsonObject()) {
			return deserializeJsonObject(elem.getAsJsonObject());
		} else if (elem.isJsonArray()) {
			return deserializeJsonArray(elem.getAsJsonArray());
		}
		return null;
	}

	/**
	 * Indicates if a class is a wrapper type, e.g. <i>Integer</i> for <i>int</i>
	 * 
	 * @param clazz
	 * @return
	 */
	private static boolean isWrapperType(Class<?> clazz) {
		return clazz.equals(Boolean.class) || clazz.equals(Integer.class) || clazz.equals(Character.class) || clazz.equals(Byte.class) || clazz.equals(Short.class) || clazz.equals(Double.class) || clazz.equals(Long.class)
				|| clazz.equals(Float.class);
	}

	/**
	 * Deserializes a JsonPrimitive to either string, int, double or boolean
	 * 
	 * @param primitive
	 * @return
	 */
	private Object deserializeJsonPrimitive(JsonPrimitive primitive) {
		if (primitive.isNumber()) {
			if (primitive.getAsString().contains(".")) {
				return primitive.getAsDouble();
			} else {
				return primitive.getAsInt();
			}
		} else if (primitive.isBoolean()) {
			return primitive.getAsBoolean();
		} else {
			return primitive.getAsString();
		}
	}

	/**
	 * Serializes either string, int, double or boolean to a JsonPrimitive
	 * 
	 * @param primitive
	 * @return
	 */
	private JsonPrimitive serializePrimitive(Object primitive) {
		if (primitive instanceof Number) {
			return new JsonPrimitive((Number) primitive);
		} else if (primitive instanceof Boolean) {
			return new JsonPrimitive((Boolean) primitive);
		} else {
			return new JsonPrimitive((String) primitive);
		}
	}
	
	/**
	 * Deserializes a JsonObject to either a map, an operations or an arbitrary
	 * serializable object
	 * 
	 * @param map
	 * @return
	 */
	private Object deserializeJsonObject(JsonObject map) {
		if (map.has(BASYXFUNCTIONTYPE)) {
			if (map.get(BASYXFUNCTIONTYPE).getAsString().equals(OPERATION)) {
				return BASYXINVOCABLE;
			} else {
				// Type equals Lambda
				return deserializeObjectFromString(map.get(BASYXFUNCTIONVALUE).getAsString());
			}
		} else {
			return deserializeToMap(map);
		}
	}

	/**
	 * Deserializes a JsonObject to a map
	 * 
	 * @param map
	 * @return
	 */
	private Map<String, Object> deserializeToMap(JsonObject map) {
		Map<String, Object> ret = toolsFactory.createMap();
		JsonObject collectionTypes = (JsonObject) map.get(BASYXTYPE);

		for (String k : map.keySet()) {
			// Ignore BASYXTYPE since it is only used for type meta data
			if (k.equals(BASYXTYPE)) {
				continue;
			}

			// If there are collections in the map, get their types and deserialize them
			if (collectionTypes != null && collectionTypes.has(k)) {
				String type = collectionTypes.get(k).getAsString();
				if (type.equals(LIST)) {
					ret.put(k, deserializeJsonArrayAsList((JsonArray) map.get(k)));
				} else if (type.equals(SET)) {
					ret.put(k, deserializeJsonArrayAsSet((JsonArray) map.get(k)));
				}
			} else {
				ret.put(k, deserializeJsonElement(map.get(k)));
			}
		}
		return ret;
	}

	/**
	 * Serializes a Map to a JsonObject
	 * 
	 * @param map
	 * @return
	 */
	private JsonObject serializeMap(Map<String, Object> map) {
		JsonObject obj = new JsonObject();
		JsonObject collectionTypes = new JsonObject();
		for (String k : map.keySet()) {
			Object o = map.get(k);
			if (o instanceof List<?>) {
				collectionTypes.add(k, new JsonPrimitive(LIST));
			} else if (o instanceof Set<?>) {
				collectionTypes.add(k, new JsonPrimitive(SET));
			}

			obj.add(k, serializeToJsonElement(o));
		}

		// If there are any collections, add collection type meta data
		if (collectionTypes.size() > 0) {
			obj.add(BASYXTYPE, collectionTypes);
		}

		return obj;
	}

	/**
	 * Removes INDEX string from ordered lists
	 * 
	 * @param obj
	 * @return
	 */
	private JsonObject stripIndex(JsonObject obj) {
		obj.remove(INDEX);
		return obj;
	}

	/**
	 * Deserializes a JSONArray to either a list or a set, depending on the presence
	 * of an INDEX value
	 * 
	 * @param array
	 * @return
	 */
	private Object deserializeJsonArray(JsonArray array) {
		if (isOrdered(array)) {
			return deserializeJsonArrayAsList(array);
		} else {
			return deserializeJsonArrayAsSet(array);
		}
	}

	/**
	 * Deserializes a JsonArray to a List<br/>
	 * <b>Assumption:</b> The order in the json is equals the correct order
	 * 
	 * @param array
	 * @return
	 */
	private List<Object> deserializeJsonArrayAsList(JsonArray array) {
		List<Object> list = toolsFactory.createList();
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i) instanceof JsonObject) {
				// If it is a JsonObject it contains an index --> Strip it
				JsonObject stripped = stripIndex(array.get(i).getAsJsonObject());
				list.add(deserializeJsonElement(stripped));
			} else {
				list.add(deserializeJsonElement(array.get(i)));

			}
		}

		return list;
	}

	/**
	 * Deserializes a JsonArray to a Set
	 * 
	 * @param array
	 * @return
	 */
	private Set<Object> deserializeJsonArrayAsSet(JsonArray array) {
		Set<Object> set = toolsFactory.createSet();
		for (int i = 0; i < array.size(); i++) {
			set.add(deserializeJsonElement(array.get(i)));
		}
		return set;
	}

	/**
	 * Checks if a JsonArray is an ordered list or a set <br/>
	 * FIXME: Replace this here with BASYXTYPE checks
	 * 
	 * @param array
	 * @return
	 */
	private boolean isOrdered(JsonArray array) {
		if (array.size() == 0) {
			return true;
		}

		JsonElement elem = array.get(0);

		if (!elem.isJsonObject()) {
			return false;
		}

		JsonObject obj = elem.getAsJsonObject();
		if (obj.has(INDEX)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if an object is a lambda function
	 * 
	 * @param value
	 * @return
	 */
	private boolean isFunction(Object value) {
		return (value instanceof Supplier<?>) || (value instanceof Function<?, ?>) || (value instanceof Consumer<?>) || (value instanceof BiConsumer<?, ?>);
	}

	/**
	 * Checks if an object implements Serializable
	 * 
	 * @param value
	 * @return
	 */
	private boolean isSerializable(Object value) {
		return value instanceof Serializable;
	}

	/**
	 * Serializes a set to a JsonArray
	 * 
	 * @param set
	 * @return
	 */
	private JsonArray serializeSet(Set<?> set) {
		JsonArray array = new JsonArray();
		for (Object o : set) {
			array.add(serializeToJsonElement(o));
		}
		return array;
	}

	/**
	 * Serializes a list to a JsonArray and adds index where appropriate
	 * 
	 * @param list
	 * @return
	 */
	private JsonElement serializeList(List<?> list) {

		if (list.size() > 0 && list.get(0) instanceof Map<?, ?>) {
			// If the list contains maps, attach the index property
			JsonArray array = new JsonArray();

			for (int i = 0; i < list.size(); i++) {
				JsonObject elem = (JsonObject) serializeToJsonElement(list.get(i));
				elem.add(INDEX, new JsonPrimitive(i));
				array.add(elem);
			}
			return array;
		} else {
			// If it does not contain maps, it is not possible to attach the index property.
			// Thus BASYXTYPE has to handle the type conversion
			JsonArray array = new JsonArray();
			for (Object o : list) {
				array.add(serializeToJsonElement(o));
			}
			return array;
		}
	}

	/**
	 * Serializes a function if possible
	 * 
	 * @param function
	 * @return
	 */
	private JsonObject serializeFunction(Object function) {
		if (isSerializable(function)) {
			return serializeSerializableOperation((Serializable) function);
		} else {
			return serializeNotSerializableOperation(function);
		}
	}

	/**
	 * Read an object from Base64 string.
	 */
	protected Object deserializeObjectFromString(String s) {
		// Return value
		Object result = null;

		// Decode String
		byte[] data = Base64.getDecoder().decode(s);

		// Try to deserialize object
		try {
			// Object input stream
			ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(data));
			// - Read object
			result = stream.readObject();

			// Close stream
			stream.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Return object
		return result;
	}

	/**
	 * Write the object to a Base64 string.
	 */
	protected String serializeObjectToString(Serializable obj) {
		// Write object into byte array
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// Try to serialize object
		try {
			ObjectOutputStream oos = new ObjectOutputStream(outStream);
			oos.writeObject(obj);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Try to encode to string
		return Base64.getEncoder().encodeToString(outStream.toByteArray());
	}

	/**
	 * Serialize an operation descriptor
	 */
	private JsonObject serializeSerializableOperation(Serializable value) {
		JsonObject target = new JsonObject();
		// Serializable functions will be serialized.
		// - Serialize operation kind
		target.add(BASYXFUNCTIONTYPE, new JsonPrimitive(LAMBDA));

		// - Add value
		String serialized = serializeObjectToString(value);
		target.add(BASYXFUNCTIONVALUE, new JsonPrimitive(serialized));

		return target;
	}

	/**
	 * Serializes a NonSerializableOperation to a String indicating that fact
	 * 
	 * @param function
	 * @return
	 */
	private JsonObject serializeNotSerializableOperation(Object function) {
		JsonObject target = new JsonObject();
		// Not serializable functions will be not be serialized.
		// - Serialize operation kind
		target.add(BASYXFUNCTIONTYPE, new JsonPrimitive(OPERATION));

		return target;
	}
}
