package org.eclipse.basyx.aas.impl.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.annotation.AASOperation;
import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.impl.resources.basic.CollectionReflectionProperty;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.DataTypeMapping;
import org.eclipse.basyx.aas.impl.resources.basic.MapReflectionProperty;
import org.eclipse.basyx.aas.impl.resources.basic.Operation;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.PropertyContainer;
import org.eclipse.basyx.aas.impl.resources.basic.ReflectionOperation;
import org.eclipse.basyx.aas.impl.resources.basic.SingleReflectionProperty;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;

/**
 * Generates properties using the @AASProperty annotation and operations tagged
 * with @AASOperation
 * 
 * @author schnicke
 *
 */
public class DescriptorGenerator {
	/**
	 * Get all fields that are tagged with AASProperty
	 */
	private static Collection<Field> getAllFields(Class<?> cls) {
		// Return value
		Collection<Field> result = new LinkedList<>();
		while (cls != null) {
			try {
				// Get fields
				Field[] fields = cls.getDeclaredFields();

				// Add fields to result
				for (Field field : fields)
					if (field.getAnnotation(AASProperty.class) != null)
						result.add(field);
			} catch (Exception e) {
				// Do nothing
			}
			cls = cls.getSuperclass();
		}

		// Return result
		return result;
	}

	/**
	 * Get all methods that are tagged with AASOperation
	 */
	private static Collection<Method> getAllMethods(Class<?> cls) {
		// Return value
		Collection<Method> result = new LinkedList<>();

		// Try to find field
		while (cls != null) {
			try {
				// Get fields
				Method[] methods = cls.getDeclaredMethods();

				// Add methods to result
				for (Method method : methods)
					if (method.getAnnotation(AASOperation.class) != null)
						result.add(method);
			} catch (Exception e) {
				// Do nothing
			}

			cls = cls.getSuperclass();
		}

		// Return result
		return result;
	}

	public static void addProperties(SubModel sm) {
		for (Property p : generateProperty(sm)) {
			sm.addProperty(p);
		}
	}

	public static void addOperations(SubModel sm) {
		for (Operation o : generateOperations(sm)) {
			sm.addOperation(o);
		}
	}

	public static List<Operation> generateOperations(SubModel sm) {
		List<Operation> ret = new ArrayList<>();
		Collection<Method> methods = getAllMethods(sm.getClass());
		for (Method m : methods) {
			m.setAccessible(true);
			Operation o = new ReflectionOperation(sm, m);
			o.setReturnDataType(DataTypeMapping.map(m.getReturnType()));
			// TODO: Parametertype
			o.setId(m.getName());
			ret.add(o);
		}
		return ret;
	}

	public static void addProperties(PropertyContainer container) {
		for (Property p : generateProperty(container)) {
			container.addProperty(p);
		}
	}

	public static List<Property> generateProperty(Object obj) {
		List<Property> ret = new ArrayList<>();
		Collection<Field> fields = getAllFields(obj.getClass());

		// Iterate over the fields and assign the correct property type
		// Uses reflection properties to allow modifying the standard values of the data
		// in contrast to a standard property
		for (Field f : fields) {
			f.setAccessible(true);
			Property p;
			if (f.getType().isPrimitive() || isPrimitiveWrapper(f.getType()) || f.getType() == String.class) {
				SingleReflectionProperty singleProp = new SingleReflectionProperty(f, obj);
				DataType type = DataTypeMapping.map(obj.getClass());
				singleProp.setDataType(type);
				p = singleProp;
			} else if (Collection.class.isAssignableFrom(f.getType())) {
				CollectionReflectionProperty collectionProp = new CollectionReflectionProperty(f, obj);
				collectionProp.setDataType(DataType.COLLECTION);
				p = collectionProp;
			} else if (Map.class.isAssignableFrom(f.getType())) {
				MapReflectionProperty mapProp = new MapReflectionProperty(f, obj);
				p = mapProp;
			} else if (PropertyContainer.class.isAssignableFrom(f.getType())) {
				try {
					p = (PropertyContainer) f.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
					throw new RuntimeException("Reflection error with property " + f.getName() + " of type " + f.getType());
				}
			} else {
				throw new RuntimeException("Unknown property type " + f.getType());
			}

			// Set name and id independent of the property type
			String name = f.getName();
			p.setName(name);
			p.setId(name);
			ret.add(p);

		}
		return ret;
	}

	private static boolean isPrimitiveWrapper(Class<?> c) {
		return c == Integer.class || c == Boolean.class || c == Double.class || c == Float.class || c == Character.class;
	}
}
