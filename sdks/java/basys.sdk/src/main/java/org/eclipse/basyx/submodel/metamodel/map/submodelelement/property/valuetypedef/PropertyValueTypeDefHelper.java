package org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides utility functions for
 * {@link org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDef
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
	private static final String TYPE_NAME = "name";
	private static final String TYPE_OBJECT = "dataObjectType";

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
	public static Map<String, Object> fromObject(Object obj) {

		PropertyValueTypeDef objectType;
		
		if (obj == null) {
			objectType = PropertyValueTypeDef.Null;
		} else {
			Class<?> c = obj.getClass();
			if (c == int.class || c == Integer.class) {
				objectType = PropertyValueTypeDef.Integer;
			} else if (c == void.class || c == Void.class) {
				objectType = PropertyValueTypeDef.Void;
			} else if (c == boolean.class || c == Boolean.class) {
				objectType = PropertyValueTypeDef.Boolean;
			} else if (c == float.class || c == Float.class) {
				// TODO C# deprecated due to new serialization
				objectType = PropertyValueTypeDef.Float;
			} else if (c == double.class || c == Double.class) {
				objectType = PropertyValueTypeDef.Double;
			} else if (c == String.class) {
				objectType = PropertyValueTypeDef.String;
			} else if (Map.class.isAssignableFrom(c)) {
				objectType = PropertyValueTypeDef.Map;
			} else if (Collection.class.isAssignableFrom(c)) {
				objectType = PropertyValueTypeDef.Collection;
			} else {
				throw new RuntimeException("Cannot map object " + obj + " to any PropertyValueTypeDef");
			}
			// TODO: Container, Reference
		}
		return getWrapper(objectType);
	}

	public static Map<String, Object> getWrapper(PropertyValueTypeDef type) {
		HashMap<String, Object> valueTypeWrapper = new HashMap<>();
		HashMap<String, Object> dataObjectTypeWrapper = new HashMap<>();
		valueTypeWrapper.put(TYPE_OBJECT, dataObjectTypeWrapper);
		dataObjectTypeWrapper.put(TYPE_NAME, type.toString());
		return valueTypeWrapper;
	}

	@SuppressWarnings("unchecked")
	public static PropertyValueTypeDef readTypeDef(Object vTypeMap) {
		
		if (vTypeMap instanceof Map<?,?>) {

			Map<String, Object> map = (Map<String, Object>) vTypeMap;
			Map<String, Object> dot = (Map<String, Object>) map.get(TYPE_OBJECT);
			
			return fromName(dot.get(TYPE_NAME).toString());
		}
		return null;
	}
}
