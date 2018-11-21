package org.eclipse.basyx.aas.backend.http.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.impl.resources.basic.DataTypeMapping;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.AtomicDataProperty;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JSON Tools for serialization/deserialization from/to JSON
 * 
 * @author kuhn, pschorn
 *
 */
public class JSONTools {

	String placeholder;

	/**
	 * Singleton instance
	 */
	public static JSONTools Instance = new JSONTools();

	/**
	 * Static constructor
	 */
	private JSONTools() {
		// Do nothing
	}


	/**
	 * Serialize a map
	 */
	@SuppressWarnings("unchecked")
	protected boolean serializeMapType(JSONObject target, Object value) {

		if (value instanceof Map) {

			// Cast to map
			Map<String, Object> map = (Map<String, Object>) value;

			// Serialize map elements
			for (String key : map.keySet()) {
				target.put(key, serializeInnerValue(new JSONObject(), map.get(key)));

			}

			// Value has been serialized
			return true;

		} else {
			return false;
		}
	}

	/**
	 * TODO change Deserialize a map
	 *
	protected Object deserializeMapType(JSONObject serializedValue, Map<Integer, Object> serObjRepo,
			JSONObject repository) {
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
			result.put(key, deserialize(serializedValue.getJSONObject(key), serObjRepo, repository));
		}

		// Return deserialized value
		return result;
	}*/

	/**
	 * Serialize a collection
	 */
	@SuppressWarnings("unchecked")
	protected boolean serializeCollectionType(JSONArray parent, Object value) {

		if (value instanceof Collection) {
			// Convert to collection
			Collection<Object> collection = (Collection<Object>) value;

			// Append collection elements
			for (Object obj : collection)
				parent.put(serializeInnerValue(new JSONObject(), obj));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * TODO Change Deserialize a collection
	 *
	protected Object deserializeCollectionType(JSONObject serializedValue, Map<Integer, Object> serObjRepo,
			JSONObject repository) {
		// Deserialize known types
		if (!(serializedValue.get("basystype").equals("collection")))
			return null;

		// Create collection return value
		Collection<Object> result = new LinkedList<Object>();

		// Deserialize collection elements
		for (int i = 0; i < (int) serializedValue.get("size"); i++) {

			result.add(deserialize(serializedValue.getJSONObject("" + i), serObjRepo, repository));
		}

		// Value has been serialized
		return result;
	}*/

	/**
	 * Serialize an exception
	 * 
	 * @param target
	 * @param value
	 * @return
	 */
	protected boolean serializeException(JSONObject response, JSONObject target, Object value) {

		// Check if object to be serialized is an exception
		if (value instanceof Exception) {

			response.put("isException", true);

			Exception e = (Exception) value;

			target.put("type", e.getClass().getName());
			target.put("message", e.getMessage());

			return true;
		}

		// Not an Exception
		response.put("isException", false);
		return false;
	}

	/**
	 * TODO Change Deserialize an exception
	 * 
	 * @param serializedValue
	 *            Serialized JSON object
	 * @return Server exception object
	 *
	protected Object deserializeException(JSONObject serializedValue) {

		// Only deserialize exceptions
		if (!(serializedValue.get("basystype").equals("exception")))
			return null;

		// Get exception type and message
		// - Store exception details
		String type = null;
		String message = null;
		// - Get exception details
		if (serializedValue.has("type"))
			type = (String) serializedValue.get("type");
		if (serializedValue.has("message"))
			message = (String) serializedValue.get("message");

		// Return server exception
		return new ServerException(type, message);
	}*/

	/**
	 *  Serialize an operation descriptor
	 */
	protected String serializeOperation(Object value) {
		// Check if object to be serialized is a function
		if ((value instanceof Function) == false)
			return null;

		// Indicates non-serializable method
		return "isMethod";
	}

	/**
	 * TODO Change Deserialize an operation
	 * 
	 * @param serializedValue
	 *            Serialized JSON object
	 * @return VABOperation Reference object
	 */
	protected Object deserializeOperation(JSONObject serializedValue) {

		// Only deserialize operations
		if (!(serializedValue.get("basystype").equals("operation")))
			return null;

		System.out.println("OPS not supported yet...");
		// return operation reference
		return null; // TODO operations deserialization
	}

	/**
	 * Serialize a primitive or complex value into JSON object.
	 * 
	 * @return primitive value or nested json object
	 */
	protected Object serializeInnerValue(JSONObject parent, Object value) {

		// Try single value
		if (serializeAtomicDataProperty(parent, value)) {
			return parent;
		}

		// Try collection
		JSONArray c = new JSONArray();
		if (serializeCollectionType(c, value)) {
			return c;
		}

		// Try map
		if (serializeMapType(parent, value)) {
			return parent;
		}

		// Try operation
		if ((placeholder = serializeOperation(value)) != null) {
			return placeholder;
		}

		// Other primitive
		JSONObject typed_value = new JSONObject();
		typed_value.put("value", value);
		typed_value.put("type", DataTypeMapping.map(value).getId());
		
		return typed_value;  
	}

	/**
	 * Serialize a primitive or complex value into JSON object 
	 * 
	 * TODO get success, entityTpye, messageType, code, text parameter
	 */
	public JSONObject serialize(Object value) {

		JSONObject response = new JSONObject();

		response.put("success", true);
		response.put("entityType", "string");

		JSONObject messages = new JSONObject();
		messages.put("messageType", "Unspecified");
		messages.put("code", "string");
		messages.put("text", "string");
		response.put("messages", messages);

		JSONArray entityArray = new JSONArray();
		JSONObject entityMap = new JSONObject();

		// Try exception
		if (serializeException(response, entityMap, value)) {
			response.put("entity", entityMap);
			return response;
		}

		// Try single value
		if (serializeAtomicDataProperty(entityMap, value)) {
			response.put("entity", entityMap);
			return response;
		}

		// Try collection
		if (serializeCollectionType(entityArray, value)) {
			response.put("entity", entityArray);
			return response;
		}

		// Try map
		if (serializeMapType(entityMap, value)) {
			response.put("entity", entityMap);
			return response;
		}
		
		// Other primitive value
		JSONObject typed_value = new JSONObject();
		typed_value.put("value", value);
		typed_value.put("type", DataTypeMapping.map(value).getId());
		
		response.put("entity", typed_value); 
		
		return response;
	}

	protected boolean serializeAtomicDataProperty(JSONObject entityMap, Object value) {

		if (!(value instanceof AtomicDataProperty))
			return false;

		AtomicDataProperty property = (AtomicDataProperty) value;

		Iterator<Entry<String, Object>> it = property.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object val = entry.getValue();

			if (val instanceof Map) {
				JSONObject obj = new JSONObject();
				serializeMapType(obj, val);
				entityMap.put(key, obj);
			} else if (val instanceof Collection) {
				JSONArray obj = new JSONArray();
				serializeCollectionType(obj, val);
				entityMap.put(key, obj);

			} else {
				entityMap.put(key, val);
			}
		}

		return true;
	}

	/**
	 * TODO change to new representation <br />
	 * Deserialize a primitive or complex value from JSON object
	 *
	protected Object deserialize(JSONObject serializedValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		// Create return value
		Object returnValue = null;

		// If object type is primitive or string, serialize right away
		if (deserializeNull(serializedValue, serObjRepo, repository))
			return null;

		if ((returnValue = deserializeCollectionType(serializedValue, serObjRepo, repository)) != null)
			return returnValue;
		if ((returnValue = deserializeMapType(serializedValue, serObjRepo, repository)) != null)
			return returnValue;

		if ((returnValue = deserializeException(serializedValue)) != null)
			return returnValue;
		if ((returnValue = deserializeOperation(serializedValue)) != null)
			return returnValue;

		// Complex types not supported yet
		return returnValue;
	}*/

	/**
	 * Deserialize a JSON message and return element map
	 */
	public Map<String, Object> deserialize(JSONObject serializedValue) {
		
		Map<String, Object> message = serializedValue.toMap(); 
		
		Iterator<Entry<String, Object>> it = message.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			
			if (value instanceof JSONObject) {
				message.put(key, deserializeJSONObject((JSONObject) value));
			}
			
			if (value instanceof JSONArray) {
				message.put(key, deserializeJSONArray((JSONArray) value));
			}
		}
		
		return message;
	}
	
	public Object deserializeJSONObject(JSONObject serializedValue) {
		
		Map<String, Object> map = serializedValue.toMap(); 
		
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			
			if (value instanceof JSONObject) {
				map.put(key, deserializeJSONObject((JSONObject) value));
			}
			
			if (value instanceof JSONArray) {
				map.put(key, deserializeJSONArray((JSONArray) value));
			}
		}
		
		return map;
	}
	
	
	public Object deserializeJSONArray(JSONArray serializedValue) {
		
		List<Object> list = serializedValue.toList();
		
		for (Object value : list) {
		
			if (value instanceof JSONObject) {
				list.remove(value); list.add(deserializeJSONObject((JSONObject) value));
			}
			
			if (value instanceof JSONArray) {
				list.remove(value); list.add(deserializeJSONArray((JSONArray) value));
			}
		}
		
		return list;
	}

}
