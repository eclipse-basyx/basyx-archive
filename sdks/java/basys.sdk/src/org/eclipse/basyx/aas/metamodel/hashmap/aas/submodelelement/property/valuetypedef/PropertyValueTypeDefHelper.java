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
	public static PropertyValueTypeDef fromObject(Object obj) {
		if (obj == null)
			return PropertyValueTypeDef.Null;

		Class<?> c = obj.getClass();

		if (c == int.class || c == Integer.class) {
			return PropertyValueTypeDef.Integer;
		} else if (c == void.class || c == Void.class) {
			return PropertyValueTypeDef.Void;
		} else if (c == boolean.class || c == Boolean.class) {
			return PropertyValueTypeDef.Boolean;
		} else if (c == float.class || c == Float.class) {
			return PropertyValueTypeDef.Float;
		} else if (c == double.class || c == Double.class) {
			return PropertyValueTypeDef.Double;
		} else if (c == String.class) {
			return PropertyValueTypeDef.String;
		} else if (Map.class.isAssignableFrom(c)) {
			return PropertyValueTypeDef.Map;
		} else if (Collection.class.isAssignableFrom(c)) {
			return PropertyValueTypeDef.Collection;
		} else {
			throw new RuntimeException("Cannot map object " + obj + " to any PropertyValueTypeDef");
		}
		// TODO: Container, Reference
	}
}
