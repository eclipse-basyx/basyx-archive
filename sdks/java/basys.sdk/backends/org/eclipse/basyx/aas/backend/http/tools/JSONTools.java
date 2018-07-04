package org.eclipse.basyx.aas.backend.http.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.serializableobject.SerializableObject;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.reference.ElementRef;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Event;
import org.eclipse.basyx.aas.impl.resources.basic.Operation;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.connected.ConnectedSerializableObject;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * JSON Tools for serialization/deserialization from/to JSON
 * 
 * @author kuhn
 *
 */
public class JSONTools {

	
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
	 * Serialize a primitive value
	 */
	protected JSONObject serializePrimitive(JSONObject target, Object serializedObject, String type) {
		// Serialize values
		// - Put object value into the JSON object
		target.put("value", serializedObject);
		// - Substructure defines a primitive type
		target.put("kind", "value");
		// - Put type id into JSON Object
		target.put("typeid", type);
		
		// Return serialized value
		return target;
	}

	
	/**
	 * Deserialize a primitive value
	 */
	protected Object deserializePrimitive(JSONObject serializedValue) {
		// Type check
		if (!serializedValue.get("kind").equals("value")) return null;
		
		// Return serialized value
		return serializedValue.get("value");
	}

	
	/**
	 * Serialize a simple type
	 */
	protected boolean serializePrimitiveType(JSONObject target, Object value, JSONObject serObjRepo) {
		// Serialize known primitive types
		if (value instanceof Integer)   {serializePrimitive(target, value, "int");       return true;} 
		if (value instanceof Float)     {serializePrimitive(target, value, "float");     return true;} 
		if (value instanceof Double)    {serializePrimitive(target, value, "double");    return true;} 
		if (value instanceof String)    {serializePrimitive(target, value, "string");    return true;} 
		if (value instanceof Boolean)   {serializePrimitive(target, value, "boolean");   return true;} 
		if (value instanceof Character) {serializePrimitive(target, value, "character"); return true;}

		// Value is not a primitive type
		return false;
	}

	
	/**
	 * Serialize a null value
	 */
	protected boolean serializeNull(JSONObject target, Object value, JSONObject serObjRepo) {
		// Only serialize null values 
		if (value != null) return false;
		
		// Serialize null value
		target.put("kind", "null");

		// Value is a null
		return true;
	}

	
	/**
	 * Deserialize a null value
	 */
	protected boolean deserializeNull(JSONObject serializedValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		// Type check
		if (!serializedValue.get("kind").equals("null")) return false;
		
		// Return serialized value
		return true;
	}

	
	/**
	 * Implement array type serialization
	 */
	protected <T> boolean doSerializeArray(JSONObject target, T[] arrayValue, String typeName, JSONObject serObjRepo, String scope) {
		// Serialize array data
		target.put("kind", "array");
		target.put("size", arrayValue.length);
		target.put("type", typeName);

		// Serialize array elements
		for (int i=0; i<arrayValue.length; i++) target.put(""+i, serialize(arrayValue[i], serObjRepo, scope));

		// Indicate success
		return true;
	}
	
	
	/**
	 * Serialize an array type
	 */
	protected boolean serializeArrayType(JSONObject target, Object value, JSONObject serObjRepo, String scope) {
		// Serialize known primitive types
		if (value instanceof int[])       return doSerializeArray(target, Arrays.stream((int[]) value).boxed().toArray(Integer[]::new), "int", serObjRepo, scope);
		if (value instanceof Integer[])   return doSerializeArray(target, (Integer[]) value, "int", serObjRepo, scope);
		if (value instanceof Float[])     return doSerializeArray(target, (Float[]) value, "float", serObjRepo, scope);
		if (value instanceof double[])    return doSerializeArray(target, Arrays.stream((double[]) value).boxed().toArray(Double[]::new), "double", serObjRepo, scope);
		if (value instanceof Double[])    return doSerializeArray(target, (Double[]) value, "double", serObjRepo, scope);
		if (value instanceof Character[]) return doSerializeArray(target, (Character[]) value, "character", serObjRepo, scope);
		if (value instanceof Boolean[])   return doSerializeArray(target, (Boolean[]) value, "boolean", serObjRepo, scope);
		if (value instanceof String[])    return doSerializeArray(target, (String[]) value, "string", serObjRepo, scope);
		if (value instanceof Object[])    return doSerializeArray(target, (Object[]) value, "object", serObjRepo, scope);

		// Value is not a primitive type
		return false;
	}

	
	/**
	 * Implement array type deserialization
	 */
	@SuppressWarnings("unchecked")
	protected <T> Object doDeserializeArray(JSONObject serializedValue, T[] arrayValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		// Deserialize array data
		for (int i=0; i<arrayValue.length; i++) {
			// Get JSON Object with value
			JSONObject value = serializedValue.getJSONObject(""+i);
			
			arrayValue[i] = (T) deserialize(value, serObjRepo, repository);
		}

		// Return value
		return arrayValue;
	}

	
	/**
	 * Deserialize an array
	 */
	protected Object deserializeArrayType(JSONObject serializedValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		// Type check
		if (!serializedValue.get("kind").equals("array")) return null;

		// Create array
		switch((String) serializedValue.get("type")) {
			case "int":        {return doDeserializeArray(serializedValue, new Integer[(int) serializedValue.get("size")], serObjRepo, repository);}
			case "float":      {return doDeserializeArray(serializedValue, new Float[(int) serializedValue.get("size")], serObjRepo, repository);}
			case "double":     {return doDeserializeArray(serializedValue, new Double[(int) serializedValue.get("size")], serObjRepo, repository);}
			case "character":  {return doDeserializeArray(serializedValue, new Character[(int) serializedValue.get("size")], serObjRepo, repository);}
			case "boolean":    {return doDeserializeArray(serializedValue, new Boolean[(int) serializedValue.get("size")], serObjRepo, repository);}
			case "string":     {return doDeserializeArray(serializedValue, new String[(int) serializedValue.get("size")], serObjRepo, repository);}
			case "object":     {return doDeserializeArray(serializedValue, new Object[(int) serializedValue.get("size")], serObjRepo, repository);}
		}
		
		// Return serialized value
		return serializedValue.get("value");
	}

	
	/**
	 * Check is a given JSON object contains a primitive type
	 */
	protected boolean isPrimitive(JSONObject serializedValue) {
		// Property "kind" defines whether something is a value (primitive) type or not
		if (serializedValue.get("kind").equals("value")) return true;
		
		// Type is no primitive type
		return false;
	}
	
	
	/**
	 * Deserialize a simple type
	 */
	protected Object deserializePrimitiveType(JSONObject serializedValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		
		// Deserialize known primitive types
		if (isPrimitive(serializedValue)) return deserializePrimitive(serializedValue);
		
		// Value is not a primitive type
		return null;
	}

	
	/**
	 * Serialize a property reference
	 */
	protected JSONObject serializeElementReference(JSONObject target, IElementReference reference) {
		
		System.out.println("Serialize "+ reference);
		// Serialize element
		// - Indicate that it is a reference
		target.put("kind", "ref");
		// - Serialize element type
		// - FIXME: Use interfaces
		if (reference instanceof IProperty) target.put("elementType", "property");
		if (reference instanceof Operation) target.put("elementType", "operation");
		if (reference instanceof Event) target.put("elementType", "event");
		// - Serialize address
		if (reference.isAASReference())      {target.put("aas", reference.getAASID());}
		if (reference.isSubModelReference()) {target.put("aas", reference.getAASID()); target.put("submodel", reference.getSubModelID());}
		if (reference.isPropertyReference()) {target.put("aas", reference.getAASID()); target.put("submodel", reference.getSubModelID()); target.put("path", reference.getPathToProperty());}
		
		// Return serialized element
		return target;
	}

	
	/**
	 * Deserialize a property reference
	 */
	protected Object deserializeElementReference(JSONObject serializedValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		// Type check
		if (!(serializedValue.get("kind").equals("ref"))) return null;
		
		// Get referenced address
		String refAAS  = ""; if (serializedValue.has("aas"))      refAAS = (String) serializedValue.get("aas");
		String refSM   = ""; if (serializedValue.has("submodel")) refSM = (String) serializedValue.get("submodel");
		String refPath = ""; if (serializedValue.has("path"))     refPath = (String) serializedValue.get("path");
		
		// Create return value
		ElementRef result = new ElementRef(refAAS, refSM, refPath);
		
		System.out.println("Deserialized "+serializedValue);
		try {
			
			// Deserialize map and collection information
			String thekind = (String) serializedValue.get("thekind");
			result.setKind(thekind);
		} catch (JSONException e) {
			// Lleave out if it is not map or collection
		}
		
		// Return deserialized element
		return result;
	}


	/**
	 * Serialize a property into JSON object
	 */
	public JSONObject serializeProperty(String pathToObject, IModelProvider provider) {
		// Create return value
		JSONObject returnValue = new JSONObject();
		
		// Get value and scope
		Object value = provider.getModelPropertyValue(pathToObject);
		String scope = provider.getElementScope(pathToObject);
		
		// Serialize value
		returnValue = this.serialize(value, scope);
				
		// Return serialized value
		return returnValue;
	}

	
	/**
	 * Serialize an IElement
	 */
	protected boolean serializeIElement(JSONObject target, Object value, JSONObject serObjRepo, String scope) {
		// Type check
		if (!(value instanceof IElement)) return false;
		
		// Create reference
		IElementReference reference = new ElementRef((IElement) value);

		// Serialize IElementReference
		return serializeIElementRef(target, reference, serObjRepo, scope);
	}

	
	/**
	 * Serialize an IElement reference
	 */
	protected boolean serializeIElementRef(JSONObject target, Object value, JSONObject serObjRepo, String scope) {
		// Type check
		if (!(value instanceof IElementReference)) return false;
		
		// Create reference
		IElementReference reference = (IElementReference) value; 	
		// Add scope to reference
		reference.addScope(scope);
		
		// Serialize element
		// - Indicate that is is a reference
		target.put("kind", "ref");
		// - Serialize element type
		// - FIXME: Use interfaces
		if (value instanceof IProperty) target.put("elementType", "property");
		if (value instanceof Operation) target.put("elementType", "operation");
		if (value instanceof Event) target.put("elementType", "event");
		// - Serialize address
		if (reference.isAASReference())      {target.put("aas", reference.getAASID());}
		if (reference.isSubModelReference()) {target.put("aas", reference.getAASID()); target.put("submodel", reference.getSubModelID());}
		if (reference.isPropertyReference()) {target.put("aas", reference.getAASID()); target.put("submodel", reference.getSubModelID()); target.put("path", reference.getPathToProperty());}
		
		if (reference.getAASID().length() == 0) {
			throw new RuntimeException("aasid empty");
		}
		
		// Add map and collection information
		if (reference.isCollection()) {target.put("thekind", "collection");}
		if (reference.isMap()) {target.put("thekind", "map");}
		
		System.out.println("Serialized " + target + " from value " + value);
		
		// Return serialized element
		return true;
	}

	
	/**
	 * Serialize a map
	 */
	@SuppressWarnings("unchecked")
	protected boolean serializeMapType(JSONObject target, Object value, JSONObject serObjRepo, String scope) {
		
		if (value instanceof Property) if (((Property) value).getDataType() == DataType.MAP) {
			
			target.put("thekind", "map");
		}

		if (value instanceof Map) {

			// Convert to collection
			Map<String, Object> map = (Map<String, Object>) value;
			
			// Convert elements
			// - Substructure defines a collection
			target.put("kind", "map");
			target.put("size", map.size());
			
			// Serialize collection elements
			for (String key: map.keySet()) target.put(key, serialize(map.get(key), serObjRepo, scope));
			
			// Value has been serialized
			return true;
			
		} else {
			return false;
		}
	}

	
	/**
	 * Deserialize a map
	 */
	protected Object deserializeMapType(JSONObject serializedValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		// Serialize known primitive types
		if (!(serializedValue.get("kind").equals("map"))) return null;
		
		// Create hash map return value
		Map<String, Object> result = new HashMap<String, Object>();
		
		// Deserialize map elements
		for (String key: serializedValue.keySet()) {
			// Skip predefined keys
			if (key.equals("kind")) continue; // this caused kind and size information to be lost!
			if (key.equals("size")) continue;
			
			// Deserialize element
			result.put(key, deserialize(serializedValue.getJSONObject(key), serObjRepo, repository));
		}
		
		// Return deserialized value
		return result;
	}

	
	/**
	 * Serialize a collection
	 */
	@SuppressWarnings("unchecked")
	protected boolean serializeCollectionType(JSONObject target, Object value, JSONObject serObjRepo, String scope) {
		
		if (value instanceof Property) if (((Property) value).getDataType() == DataType.COLLECTION) {
			
			target.put("thekind", "collection");
		}

		if (value instanceof Collection) {
			// Convert to collection
			Collection<Object> collection = (Collection<Object>) value;
			
			// Convert elements
			// - Substructure defines a collection
			target.put("kind", "collection");
			target.put("size", collection.size());
			
			// Serialize collection elements
			int i=0; for (Object obj: collection) target.put(""+i++, serialize(obj, serObjRepo, scope));
			
			// Value has been serialized
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * Deserialize a collection
	 */
	protected Object deserializeCollectionType(JSONObject serializedValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		// Deserialize known types
		if (!(serializedValue.get("kind").equals("collection"))) return null;
		
		// Create collection return value
		Collection<Object> result = (Collection<Object>) new LinkedList<Object>();
		
		// Deserialize collection elements
		for (int i=0; i<(int) serializedValue.get("size"); i++) {
			
			result.add(deserialize(serializedValue.getJSONObject(""+i), serObjRepo, repository));
		}
		
		// Value has been serialized
		return result;
	}
	
	
	
	/**
	 * Serialize a SerializableObject
	 */
	protected boolean serializeSerializableObject(JSONObject target, Object value, JSONObject serObjRepo, String scope) {
		// Repository for serialized objects
		JSONObject serializedObjectRepository = serObjRepo;
		
		// Serialize SerializableObjects only
		if (!(value instanceof SerializableObject)) return false;
		// - Convert parameter to serializable object
		SerializableObject serObj = (SerializableObject) value;

		// Create serialized object repository if no repository is given
		if (serializedObjectRepository == null) serializedObjectRepository = new JSONObject();
		// - Check if repository contains serialized object already
		if (serializedObjectRepository.has("id_"+serObj.getObjectID())) return true; 

		// Serialize element
		// - Serializable object is defined by its substructure
		Map<String, Object> fields = serObj.getElements();
		// - Create substructure element
		JSONObject subStructure = new JSONObject();
		// - Serialize substructure
		for (String name: fields.keySet()) subStructure.put(name, serialize(fields.get(name), serializedObjectRepository, scope));

		// Store serialized SerializeableObject in repository
		serializedObjectRepository.put("id_"+serObj.getObjectID(), subStructure);
		
		// Serialize reference to serialized object 
		target.put("kind", "serializedobject");
		target.put("id",   serObj.getObjectID());

		// Serialized repository with serialized SerializableObjects iff this is the root node
		if (serObjRepo == null) target.put("repo", serializedObjectRepository);

		// Value has been serialized
		return true;
	}
	
	
	protected boolean serializeException(JSONObject target, Object value) {

		if (value instanceof Exception) {
			
			Exception e = (Exception) value;
			
			target.put("kind", "exception");
			target.put("message", e.getMessage());
			
			return true;
		}
		
		return false;
	}
	
	

	protected Object deserializeException(JSONObject serializedValue) {

		// Only deserialize exceptions
		if (!(serializedValue.get("kind").equals("exception"))) return null;
		
		System.out.println("Deserialize Exception");
		return new ServerException( (String) serializedValue.get("message"));
	}



	
	/**
	 * Serialize a SerializableObject
	 */
	protected Object deserializeSerializableObject(JSONObject serializedValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		// Temporary variables for serialized objects
		Map<Integer, Object> serializedObjectRepository = serObjRepo;
		JSONObject          repo                        = repository;

		// Deserialize known types
		if (!(serializedValue.get("kind").equals("serializedobject"))) return null;

		// Create collection return value
		ConnectedSerializableObject result = new ConnectedSerializableObject();
		// - Create serialized object repository if no repository is given
		if (serializedObjectRepository == null) serializedObjectRepository = new HashMap<>();
		if (repo == null)                       repo = serializedValue.getJSONObject("repo");

		// Deserialize collection elements
		// - Check if element has already been deserialized
		if (serializedObjectRepository.containsKey(serializedValue.get("id"))) return serializedObjectRepository.get(serializedValue.get("id"));
		
		// Deserialize requested element
		JSONObject substructure = repo.getJSONObject("id_"+serializedValue.get("id"));
		// - Deserialize substructure
		for (String key: substructure.keySet()) {System.out.println(">>>>"+key); result.addElement(key, deserialize(substructure.getJSONObject(key), serializedObjectRepository, repo));}
		// - Add deserialized object to cache to make sure that references to same element point to same element
		serializedObjectRepository.put((Integer) serializedValue.get("id"), result);

		// Return serialized object
		return result;
	}

	
		
	/**
	 * Serialize a primitive or complex value into JSON object
	 */
	protected JSONObject serialize(Object value, JSONObject serObjRepo, String scope) {
		// Create return value
		JSONObject returnValue = new JSONObject();

		// Serialize type
		if (serializeNull(returnValue, value, serObjRepo)) return returnValue;
		if (serializePrimitiveType(returnValue, value, serObjRepo)) return returnValue;
		if (serializeArrayType(returnValue, value, serObjRepo, scope)) return returnValue;
		if (serializeCollectionType(returnValue, value, serObjRepo, scope)) return returnValue;
		if (serializeMapType(returnValue, value, serObjRepo, scope)) return returnValue; 
		if (serializeIElement(returnValue, value, serObjRepo, scope)) return returnValue;
		if (serializeIElementRef(returnValue, value, serObjRepo, scope)) return returnValue;
		if (serializeSerializableObject(returnValue, value, serObjRepo, scope)) return returnValue;
		if (serializeException(returnValue, value)) return returnValue;
		// Complex types not supported yet
		
		
		return returnValue;
	}


	/**
	 * Serialize a primitive or complex value into JSON object
	 */
	public JSONObject serialize(Object value, String scope) {
		return serialize(value, null, scope); 
	}

	
	/**
	 * Serialize a primitive or complex value into JSON object
	 */
	public JSONObject serialize(Object value) {
		return serialize(value, null, null); 
	}



	/**
	 * Deserialize a primitive or complex value from JSON object
	 */
	protected Object deserialize(JSONObject serializedValue, Map<Integer, Object> serObjRepo, JSONObject repository) {
		// Create return value
		Object returnValue = null;
		
		
		
		// If object type is primitive or string, serialize right away
		if (deserializeNull(serializedValue, serObjRepo, repository)) return null;
		if ((returnValue = deserializePrimitiveType(serializedValue, serObjRepo, repository)) != null) return returnValue;
		if ((returnValue = deserializeArrayType(serializedValue, serObjRepo, repository)) != null) return returnValue;
		if ((returnValue = deserializeCollectionType(serializedValue, serObjRepo, repository)) != null) return returnValue;
		if ((returnValue = deserializeMapType(serializedValue, serObjRepo, repository)) != null) return returnValue;
		if ((returnValue = deserializeElementReference(serializedValue, serObjRepo, repository)) != null) return returnValue;
		if ((returnValue = deserializeSerializableObject(serializedValue, serObjRepo, repository)) != null) return returnValue;
		if ((returnValue = deserializeException(serializedValue)) != null) return returnValue;

		
		
		
		// Complex types not supported yet
		return returnValue;
	}

	


	/**
	 * Deserialize a primitive or complex value from JSON object
	 */
	public Object deserialize(JSONObject serializedValue) {
		return deserialize(serializedValue, null, null);
	}
	
	
	
    /**
     * Decode a received, serialized JSON data type
     * @return
     */
    public DataType decodeDataType(JSONObject element) {
		// Decode data type
		switch ((String) element.get("kind")) {
			// Primitive values
			case "value":
				switch ((String) element.get("typeid")) {
					case "int":       return DataType.INTEGER;
					case "float":     return DataType.FLOAT;
					case "double":    return DataType.DOUBLE;
					case "string":    return DataType.STRING;
					case "boolean":   return DataType.BOOLEAN;
					case "character": return DataType.CHARACTER;
				}
				
			// Array types
			case "array":	
				switch ((String) element.get("typeid")) {
					case "int":       return DataType.INTEGER;
					case "float":     return DataType.FLOAT;
					case "double":    return DataType.DOUBLE;
					case "string":    return DataType.STRING;
					case "boolean":   return DataType.BOOLEAN;
					case "character": return DataType.CHARACTER;
					case "object":    return DataType.OBJECT;
				}
				
			// Map type
			case "map": return DataType.MAP;

			// Collection type
			case "collection": return DataType.COLLECTION;
				
				
			// IElement reference
			case "ref": return DataType.REFERENCE;
				
				
			// Serialized object
			case "serializedobject": return DataType.OBJECT;				
		}
		
		// Unknown type
		return null;
    }

}


