package org.eclipse.basyx.aas.impl.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.basyx.aas.api.annotation.AASOperation;
import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;

/**
 * Provider class that converts a Java Object to a BaSys according to DAAS
 * 
 * @author pschorn
 *
 */
public class JavaObjectVABMapper {

	private MetaModelElementFactory fac;

	public JavaObjectVABMapper(MetaModelElementFactory factory) {
		super();
		this.fac = factory;
	}

	public VABElementContainer map(VABElementContainer target, Object obj) {

		scanProperties(target, obj);
		scanOperations(target, obj);

		return target;
	}

	private void scanOperations(VABElementContainer target, Object submodel) {

		// Get Class and Operation Fields
		Class<?> cls = submodel.getClass();
		Method[] methods = cls.getDeclaredMethods();

		// Iterate over method fields and add methods to MetaModel
		for (Method method : methods) {

			// Skip if this method is not tagged AASOperation
			if (method.getAnnotation(AASOperation.class) == null) {
				continue;
			}

			// Add Operation
			String id = method.getName();

			Operation op = fac.createOperation(new Operation(), (param) -> { // write in serialization
				// "isMethod"
				try {
					return method.invoke(submodel, param);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			});
			op.setId(id);
			target.addOperation(op);

		}

	}

	private void scanProperties(VABElementContainer target, Object submodel) {

		// Get Class and Property Fields
		Class<?> cls = submodel.getClass();
		Field[] fields = cls.getDeclaredFields();

		// Iterate over fields and add properties to MetaModel
		for (int i = 0; i < fields.length; i++) {

			Field field = fields[i];

			// Skip if this field is not tagged AASProperty
			if (field.getAnnotation(AASProperty.class) == null) {
				continue;
			}

			// Add Property
			String id = field.getName();
			try {

				// Get property value
				field.setAccessible(true);
				Class<?> type = field.getType();

				if (IContainerProperty.class.isAssignableFrom(type)) {

					// Add complex property or submodel and scan nested properties and operations
					SubmodelElementCollection metaProperty = new SubmodelElementCollection();
					metaProperty.setId(id);
					try {
						scanProperties(metaProperty, field.get(submodel));
						scanOperations(metaProperty, field.get(submodel));

					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

					target.addElementCollection(metaProperty);

				} else {

					// Add single property
					Property prop = fac.create(new Property(), getSupplier(field, submodel), getConsumer(field, submodel));
					prop.setId(id);
					target.addDataElement(prop);
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	private Supplier<Object> getSupplier(Field f, Object o) {
		return () -> {
			try {
				return f.get(o);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		};
	}

	private Consumer<Object> getConsumer(Field f, Object obj) {
		return (o) -> {
			try {
				f.set(obj, o);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		};
	}
}
