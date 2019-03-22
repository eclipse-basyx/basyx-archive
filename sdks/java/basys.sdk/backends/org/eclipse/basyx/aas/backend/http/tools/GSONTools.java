package org.eclipse.basyx.aas.backend.http.tools;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.exception.ServerException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
/**
 * 
 * @author rajashek
 *
 */
public class GSONTools {

	/**
	 * Singleton instance
	 */
	public static GSONTools Instance = new GSONTools();
	static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	


	/**
	 * Static constructor
	 */
	private GSONTools() {
		// Do nothing
	}
	
	/**
	 * Take Object and returns Map obj
	 * @param mapObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(Object mapObj){
		if(mapObj instanceof Map) {
			return (Map<String, Object>)mapObj;
		}
		else {
			assertFalse("Something is not right! Check the implemntation",true);
			return null;
		}
	}
	
	/**
	 * This function accepts the JSOn string and returns the GSON object
	 * @param str
	 * @return
	 */
	public Object getObjFromJsonStr(String str)
	{
		
		Object fromJson=null;
		try {
			fromJson = gson.fromJson(str, new TypeToken<HashMap<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			System.out.println("This should not happen! the defected string is :"+str);
		}
		return fromJson;
	}
	
	/**
	 * Takes GSON object and returns the JSON string with using JSON 
	 * @param obj
	 * @return
	 */
	public String getJsonString(Object obj)
	{
		
		return gson.toJson(obj);
	}

	/**
	 * Serialize a primitive value
	 */
	protected Object serializePrimitive(Map<String, Object>  target, Object serializedObject, String type) {
		// Serialize values
		// - Put object value into the JSON object
		target.put("value", serializedObject);
		// - Substructure defines a primitive type
		target.put("basystype", "value");
		// - Put type id into JSON Object
		target.put("typeid", type);

		// Return serialized value
		return target;
	}

	/**
	 * Deserialize a primitive value
	 */
	protected Object deserializePrimitive(Map<String, Object> serializedValue) {
		// Type check
		if (!serializedValue.get("basystype").equals("value"))
			return null;

		Object result = serializedValue.get("value");
		String typeId = (String) serializedValue.get("typeid");

		if (typeId.equals("int"))
			return getInt(result);
		if (typeId.equals("float"))
			return getFloat(result);
		if (typeId.equals("double"))
			return getDouble(result);
		if (typeId.equals("string"))
			return getString(result);
		if (typeId.equals("boolean"))
			return getBoolean(result);
		if (typeId.equals("character")) {
			return getCharacter(result);
		}
		return result;
	}

	/**
	 * Deserializes a value to int
	 * 
	 * @param obj
	 * @return
	 */
	private int getInt(Object obj) {
		try {
			return obj instanceof Number ? ((Number) obj).intValue() : Integer.parseInt((String) obj);
		} catch (Exception e) {
			System.out.println("obj not int but defined as int");
			return 0;
		}

	}

	/**
	 * Deserializes a value to float
	 * 
	 * @param obj
	 * @return
	 */
	private float getFloat(Object obj) {
		try {
			return obj instanceof Number ? ((Number) obj).floatValue() : Float.parseFloat(obj.toString());
		} catch (Exception e) {
			System.out.println("obj not float but defined as float");
			return 0.0f;
		}
	}

	/**
	 * Deserializes a value to double
	 * 
	 * @param obj
	 * @return
	 */
	private double getDouble(Object obj) {
		try {
			return obj instanceof Number ? ((Number) obj).doubleValue() : Double.parseDouble(obj.toString());
		} catch (Exception e) {
			System.out.println("obj not double but defined as double");
			return 0.0f;
		}
	}

	/**
	 * Deserializes a value to string
	 * 
	 * @param obj
	 * @return
	 */
	private String getString(Object obj) {
		if (obj instanceof String) {
			return (String) obj;
		}
		return null;
	}

	/**
	 * Deserializes a value to boolean
	 * 
	 * @param obj
	 * @return
	 */
	private boolean getBoolean(Object obj) {
		if (obj.equals(Boolean.FALSE) || (obj instanceof String && ((String) obj).equalsIgnoreCase("false"))) {
			return false;
		} else if (obj.equals(Boolean.TRUE) || (obj instanceof String && ((String) obj).equalsIgnoreCase("true"))) {
			return true;
		}
		return false;
	}

	/**
	 * Deserializes a value to character
	 * 
	 * @param obj
	 * @return
	 */
	private char getCharacter(Object obj) {
		// Character can be directly returned since it is deserialized as character by
		// GSON
		return (char) obj;
	}

	/**
	 * Serialize a simple type
	 */
	protected boolean serializePrimitiveType(Map<String, Object> target, Object value) {
		// Serialize known primitive types
		if (value instanceof Integer) {
			serializePrimitive(target, value, "int");
			return true;
		}
		if (value instanceof Float) {
			serializePrimitive(target, value, "float");
			return true;
		}
		if (value instanceof Double) {
			serializePrimitive(target, value, "double");
			return true;
		}
		if (value instanceof String) {
			serializePrimitive(target, value, "string");
			return true;
		}
		if (value instanceof Boolean) {
			serializePrimitive(target, value, "boolean");
			return true;
		}
		if (value instanceof Character) {
			serializePrimitive(target, value, "character");
			return true;
		}

		// Value is not a primitive type
		return false;
	}

	/**
	 * Serialize a null value
	 */
	protected boolean serializeNull(Map<String, Object> target, Object value) {
		// Only serialize null values
		if (value != null)
			return false;

		// Serialize null value
		target.put("basystype", "null");

		// Value is a null
		return true;
	}

	/**
	 * Deserialize a null value
	 */
	protected boolean deserializeNull(Map<String, Object> serializedValue, Map<Integer, Object> serObjRepo) {
		// Type check
		if (!serializedValue.get("basystype").equals("null"))
			return false;

		// Return serialized value
		return true;
	}

	/**
	 * Implement array type serialization
	 */
	protected <T> boolean doSerializeArray(Map<String, Object> target, T[] arrayValue, String typeName, Map<String, Object> serObjRepo, String scope) {
		// Serialize array data
		target.put("basystype", "array");
		target.put("size", arrayValue.length);
		target.put("type", typeName);

		// Serialize array elements
		for (int i = 0; i < arrayValue.length; i++)
			target.put("" + i, serialize(arrayValue[i], serObjRepo, scope));

		// Indicate success
		return true;
	}

	/**
	 * Serialize an array type
	 */
	protected boolean serializeArrayType(Map<String, Object> target, Object value, Map<String, Object> serObjRepo, String scope) {
		// Serialize known primitive types
		if (value instanceof int[])
			return doSerializeArray(target, Arrays.stream((int[]) value).boxed().toArray(Integer[]::new), "int", serObjRepo, scope);
		if (value instanceof Integer[])
			return doSerializeArray(target, (Integer[]) value, "int", serObjRepo, scope);
		if (value instanceof Float[])
			return doSerializeArray(target, (Float[]) value, "float", serObjRepo, scope);
		if (value instanceof double[])
			return doSerializeArray(target, Arrays.stream((double[]) value).boxed().toArray(Double[]::new), "double", serObjRepo, scope);
		if (value instanceof Double[])
			return doSerializeArray(target, (Double[]) value, "double", serObjRepo, scope);
		if (value instanceof Character[])
			return doSerializeArray(target, (Character[]) value, "character", serObjRepo, scope);
		if (value instanceof Boolean[])
			return doSerializeArray(target, (Boolean[]) value, "boolean", serObjRepo, scope);
		if (value instanceof String[])
			return doSerializeArray(target, (String[]) value, "string", serObjRepo, scope);
		if (value instanceof Object[])
			return doSerializeArray(target, (Object[]) value, "object", serObjRepo, scope);

		// Value is not a primitive type
		return false;
	}

	/**
	 * Implement array type deserialization
	 */
	@SuppressWarnings("unchecked")
	protected <T> Object doDeserializeArray(Map<String, Object> serializedValue, T[] arrayValue, Map<Integer, Object> serObjRepo, Map<String, Object> repository) {
		// Deserialize array data
		for (int i = 0; i < arrayValue.length; i++) {
			// Get JSON Object with value
			Map<String, Object> value = (Map<String, Object>) serializedValue.get("" + i);

			arrayValue[i] = (T) deserialize(value, serObjRepo, repository);
		}

		// Return value
		return arrayValue;
	}

	/**
	 * Deserialize an array
	 */
	protected Object deserializeArrayType(Map<String, Object> serializedValue, Map<Integer, Object> serObjRepo, Map<String, Object> repository) {
		// Type check
		if (!serializedValue.get("basystype").equals("array"))
			return null;

		// GSON deserializes numbers to Double
		int sizeVal = ((Number) serializedValue.get("size")).intValue();
		
		// Create array
		switch ((String) serializedValue.get("type")) {
		case "int": {
			return doDeserializeArray(serializedValue, new Integer[sizeVal], serObjRepo, repository);
		}
		case "float": {
			return doDeserializeArray(serializedValue, new Float[sizeVal], serObjRepo, repository);
		}
		case "double": {
			return doDeserializeArray(serializedValue, new Double[sizeVal], serObjRepo, repository);
		}
		case "character": {
			return doDeserializeArray(serializedValue, new Character[sizeVal], serObjRepo, repository);
		}
		case "boolean": {
			return doDeserializeArray(serializedValue, new Boolean[sizeVal], serObjRepo, repository);
		}
		case "string": {
			return doDeserializeArray(serializedValue, new String[sizeVal], serObjRepo, repository);
		}
		case "object": {
			return doDeserializeArray(serializedValue, new Object[sizeVal], serObjRepo, repository);
		}
		}

		// Return serialized value
		return serializedValue.get("value");
	}

	/**
	 * Check if a given GSON object contains a primitive type
	 */
	protected boolean isPrimitive(Map<String, Object> serializedValue) {
		// Property "basystype" defines whether something is a value (primitive) type or
		// not
		if (serializedValue.get("basystype").equals("value"))
			return true;

		// Type is no primitive type
		return false;
	}

	/**
	 * Deserialize a simple type
	 */
	protected Object deserializePrimitiveType(Map<String, Object> serializedValue, Map<Integer, Object> serObjRepo, Map<String, Object> repository) {

		// Deserialize known primitive types
		if (isPrimitive(serializedValue))
			return deserializePrimitive(serializedValue);

		// Value is not a primitive type
		return null;
	}

	/**
	 * Serialize a map
	 */
	@SuppressWarnings("unchecked")
	protected boolean serializeMapType(Map<String, Object> target, Object value, Map<String, Object> serObjRepo, String scope) {
		if (value instanceof Map) {

			// Convert to collection
			Map<String, Object> map = (Map<String, Object>) value;

			// Convert elements
			// - Substructure defines a collection
			target.put("basystype", "map");
			target.put("size", map.size());

			// Serialize collection elements
			for (String key : map.keySet())
				target.put(key, serialize(map.get(key), serObjRepo, scope));

			// Value containsKey been serialized
			return true;

		} else {
			return false;
		}
	}

	/**
	 * Deserialize a map
	 */
	@SuppressWarnings("unchecked")
	protected Object deserializeMapType(Map<String, Object> serializedValue, Map<Integer, Object> serObjRepo, Map<String, Object> repository) {
		// Serialize known primitive types
		if (!(serializedValue.get("basystype").equals("map")))
			return null;

		// Create hash map return value
		Map<String, Object> result = new HashMap<String, Object>();

		// Deserialize map elements
		for (String key : serializedValue.keySet()) {
			// Skip predefined keys
			if (key.equals("basystype"))
				continue; // this caused kind and size information to be lost!
			if (key.equals("size"))
				continue;

			// Deserialize element
			result.put(key, deserialize((Map<String, Object>)serializedValue.get(key), serObjRepo, repository));
		}

		// Return deserialized value
		return result;
	}

	/**
	 * Serialize a collection
	 */
	@SuppressWarnings("unchecked")
	protected boolean serializeCollectionType(Map<String, Object> target, Object value, Map<String, Object> serObjRepo, String scope) {
		if (value instanceof Collection) {
			// Convert to collection
			Collection<Object> collection = (Collection<Object>) value;

			// Convert elements
			// - Substructure defines a collection
			if (value instanceof Set)  target.put("basystype", "set");
			else {

				target.put("basystype", "collection");
			}
			
			target.put("size", collection.size());

			// Serialize collection elements
			int i = 0;
			for (Object obj : collection)
				target.put("" + i++, serialize(obj, serObjRepo, scope));

			// Value containsKey been serialized
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Deserialize a collection
	 */
	@SuppressWarnings("unchecked")
	protected Object deserializeCollectionType(Map<String, Object> serializedValue, Map<Integer, Object> serObjRepo, Map<String, Object> repository) {
		// Deserialize known types
		String basysType = (String) serializedValue.get("basystype");
		Boolean isCollection = basysType.equals("set") || basysType.equals("collection");
		
		// Deserialize known types
		if (!isCollection)
			return null;
		
		Object sizeObj = serializedValue.get("size");
		int sizeVal=0;
		if(sizeObj instanceof Double&&sizeObj.toString().contains(".")) {
			Double size = new Double((double) sizeObj);  
			sizeVal=size.intValue()	;
		}
		else {
			sizeVal=(int) sizeObj;
		}
		// Create collection return value
		Collection<Object> result = null;
		if (basysType.equals("set"))  result = new HashSet<Object>();
		else {
			result = new ArrayList<Object>();
		}


		// Deserialize collection elements
		for (int i = 0; i <  sizeVal; i++) {

			result.add(deserialize((Map<String, Object>) serializedValue.get("" + i), serObjRepo, repository));
		}

		// Value containsKey been serialized
		return result;
	}

	/**
	 * Serialize an exception
	 * 
	 * @param target
	 * @param value
	 * @return
	 */
	protected boolean serializeException(Map<String, Object> target, Object value) {
		// Check if object to be serialized is an exception
		if (value instanceof Exception) {

			Exception e = (Exception) value;

			target.put("basystype", "exception");
			target.put("type", e.getClass().getName());
			target.put("message", e.getMessage());

			return true;
		}

		return false;
	}

	/**
	 * Deserialize an exception
	 * 
	 * @param serializedValue
	 *            Serialized JSON object
	 * @return Server exception object
	 */
	protected Object deserializeException(Map<String, Object> serializedValue) {

		// Only deserialize exceptions
		if (!(serializedValue.get("basystype").equals("exception")))
			return null;

		// Get exception type and message
		// - Store exception details
		String type = null;
		String message = null;
		// - Get exception details
		if (serializedValue.containsKey("type"))
			type = (String) serializedValue.get("type");
		if (serializedValue.containsKey("message"))
			message = (String) serializedValue.get("message");

		// Return server exception
		return new ServerException(type, message);
	}

	/**
	 * Serialize an operation descriptor
	 */
	protected boolean serializeOperation(Map<String, Object> target, Object value) {
		// Check if object to be serialized is a function
		if ((value instanceof Function) == false)
			return false;

		// Serialize operation kind
		target.put("basystype", "operation");

		// Indicate success
		return true;
	}

	/**
	 * Deserialize an operation
	 * 
	 * @param serializedValue
	 *            Serialized JSON object
	 * @return Server exception object
	 */
	protected Object deserializeOperation(Map<String, Object> serializedValue) {

		// Only deserialize exceptions
		if (!(serializedValue.get("basystype").equals("operation")))
			return null;

		// Return server exception
		return "OPERATION!!";
	}

	/**
	 * Serialize a primitive or complex value into JSON object
	 */
	protected Map<String, Object> serialize(Object value, Map<String, Object> serObjRepo, String scope) {
		// Create return value
		Map<String, Object> returnValue = new HashMap<String, Object>();

		// Serialize type
		if (serializeNull(returnValue, value))
			return returnValue;
		if (serializePrimitiveType(returnValue, value))
			return returnValue;
		if (serializeArrayType(returnValue, value, serObjRepo, scope))
			return returnValue;
		if (serializeCollectionType(returnValue, value, serObjRepo, scope))
			return returnValue;
		if (serializeMapType(returnValue, value, serObjRepo, scope))
			return returnValue;
		if (serializeException(returnValue, value))
			return returnValue;
		if (serializeOperation(returnValue, value))
			return returnValue;

		// Complex types not supported yet
		System.err.println("Could not serialize object :" + value);
		return returnValue;
	}

	/**
	 * Serialize a primitive or complex value into JSON object
	 */
	public Map<String, Object> serialize(Object value, String scope) {
		return serialize(value, null, scope);
	}

	/**
	 * Serialize a primitive or complex value into JSON object
	 */
	public Map<String, Object> serialize(Object value) {
		return serialize(value, null, null);
	}

	/**
	 * Deserialize a primitive or complex value from JSON object
	 */
	protected Object deserialize(Map<String, Object> serializedValue, Map<Integer, Object> serObjRepo, Map<String, Object> repository) {
		// Create return value
		Object returnValue = null;

		// If object type is primitive or string, serialize right away
		if (deserializeNull(serializedValue, serObjRepo))
			return null;
		if ((returnValue = deserializePrimitiveType(serializedValue, serObjRepo, repository)) != null)
			return returnValue;
		if ((returnValue = deserializeArrayType(serializedValue, serObjRepo, repository)) != null)
			return returnValue;
		if ((returnValue = deserializeCollectionType(serializedValue, serObjRepo, repository)) != null)
			return returnValue;
		if ((returnValue = deserializeMapType(serializedValue, serObjRepo, repository)) != null)
			return returnValue;
		if ((returnValue = deserializeException(serializedValue)) != null)
			return returnValue;
		if ((returnValue = deserializeOperation(serializedValue)) != null)
			return returnValue;

		System.err.println("Could not deserialize object :" + serializedValue);
		// Complex types not supported yet
		return returnValue;
	}

	/**
	 * Deserialize a primitive or complex value from JSON object
	 */
	public Object deserialize(Map<String, Object> serializedValue) {
		return deserialize(serializedValue, null, null);
	}
}
