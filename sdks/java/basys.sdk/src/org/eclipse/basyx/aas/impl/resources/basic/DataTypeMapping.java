package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;

/**
 * Maps a class to its corresponding DataType
 * 
 * @author schnicke
 *
 */
public class DataTypeMapping {
	public static DataObjectType map(Object obj) {
		
		if (obj == null) return DataObjectType.Null;
		
		Class<?> c = obj.getClass();
		
		if (c == int.class || c == Integer.class) {
			return DataObjectType.Int32;
		} else if (c == void.class || c == Void.class) {
			return DataObjectType.Void;
		} else if (c == boolean.class || c == Boolean.class) {
			return DataObjectType.Bool;
		} else if (c == float.class || c == Float.class) {
			return DataObjectType.Float;
		} else if (c == double.class || c == Double.class) {
			return DataObjectType.Double;
		} else if (c == String.class) {
			return DataObjectType.String;
		} else if (Map.class.isAssignableFrom(c)) {
			return DataObjectType.Map;
		} else if (Collection.class.isAssignableFrom(c)) {
			return DataObjectType.Collection;
		} else if (IContainerProperty.class.isAssignableFrom(c)) {
			return DataObjectType.PropertyContainer;
		} else {
			return DataObjectType.Reference;
		}
	}
}
