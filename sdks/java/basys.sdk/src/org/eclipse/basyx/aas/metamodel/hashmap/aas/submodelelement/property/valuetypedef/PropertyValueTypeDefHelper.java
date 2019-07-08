package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides utility functions for
 * {@link org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDef
 * PropertyValueTypeDef} <br />
 * * Creating a PropertyValueTypeDef from name <br/>
 * * Creating a PropertyValueTypeDef for an object
 * 
 * @author schnicke
 *
 */
public class PropertyValueTypeDefHelper {
	private static Map<String, PropertyValueTypeDef> typeMap = new HashMap<>();

	// insert all types into a Map to allow getting a PropertyValueType based on a
	// String
	static {
		for (PropertyValueTypeDef t : PropertyValueTypeDef.values()) {
			typeMap.put(t.toString(), t);
		}
	}
	
	// Strings required for meta-model conformant valueType format
	static String name = "name";
	static String dataObjectType = "dataObjectType";

	/**
	 * Map the name of a PropertyValueTypeDef to a PropertyValueTypeDef
	 * 
	 * @param name
	 * @return
	 */
	public static PropertyValueTypeDef fromName(String name) {
		if (typeMap.containsKey(name)) {
			return typeMap.get(name);
		} else {
			throw new RuntimeException("Unknown type name " + name + "; can not handle this PropertyValueType");
		}
	}

	/**
	 * Creates the PropertyValueTypeDef for an arbitrary object
	 * 
	 * @param obj
	 * @return
	 */
	public static HashMap<String, Object> fromObject(Object obj) {
		
		HashMap<String, Object> valueTypeWrapper = new HashMap<String, Object>();
		HashMap<String, Object> dataObjectTypeWrapper = new HashMap<String, Object>();
		valueTypeWrapper.put(dataObjectType, dataObjectTypeWrapper);
		
		if (obj == null) {
			dataObjectTypeWrapper.put(name, PropertyValueTypeDef.Null.toString());
			return valueTypeWrapper;
		}
		
		Class<?> c = obj.getClass();

		if (c == int.class || c == Integer.class) {
			dataObjectTypeWrapper.put(name, PropertyValueTypeDef.Integer.toString());
			return valueTypeWrapper;
		} else if (c == void.class || c == Void.class) {
			dataObjectTypeWrapper.put(name, PropertyValueTypeDef.Void.toString());
			return valueTypeWrapper;
		} else if (c == boolean.class || c == Boolean.class) {
			dataObjectTypeWrapper.put(name, PropertyValueTypeDef.Boolean.toString());
			return valueTypeWrapper;
		} else if (c == float.class || c == Float.class) {
			dataObjectTypeWrapper.put(name, PropertyValueTypeDef.Float.toString()); // TODO C# deprecated due to new serialization
			return valueTypeWrapper;
		} else if (c == double.class || c == Double.class) {
			dataObjectTypeWrapper.put(name, PropertyValueTypeDef.Double.toString());
			return valueTypeWrapper;
		} else if (c == String.class) {
			dataObjectTypeWrapper.put(name, PropertyValueTypeDef.String.toString());
			return valueTypeWrapper;
		} else if (Map.class.isAssignableFrom(c)) {
			dataObjectTypeWrapper.put(name, PropertyValueTypeDef.Map.toString());
			return valueTypeWrapper;
		} else if (Collection.class.isAssignableFrom(c)) {
			dataObjectTypeWrapper.put(name, PropertyValueTypeDef.Collection.toString());
			return valueTypeWrapper;
		} else {
			throw new RuntimeException("Cannot map object " + obj + " to any PropertyValueTypeDef");
		}
		// TODO: Container, Reference
	}

	@SuppressWarnings("unchecked")
	public static PropertyValueTypeDef readTypeDef(Object vTypeMap) {
		
		if (vTypeMap instanceof Map<?,?>) {

			Map<String, Object> map = (Map<String, Object>) vTypeMap;
			Map<String, Object> dot = (Map<String, Object>) map.get(dataObjectType);
			
			return fromName(dot.get(name).toString());
		}
		return null;
	}
}
