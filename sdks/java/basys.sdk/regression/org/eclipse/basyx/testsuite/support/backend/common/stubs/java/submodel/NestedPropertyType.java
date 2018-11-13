/**
 * 
 */
package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.ComplexDataProperty;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.PropertySingleValued;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation.Operation;

/**
 * @author schnicke
 *
 */
public class NestedPropertyType extends ComplexDataProperty {
	private static final long serialVersionUID = 1L;

	/**
	 * Nested operation
	 */
	public int sub(int a, int b) {
		return a - b;
	}

	/**
	 * Constructor
	 */
	public NestedPropertyType(String id) {
		setId(id);
		MetaModelElementFactory factory = new MetaModelElementFactory();

		PropertySingleValued propertyA = factory.create(new PropertySingleValued(), 4);
		propertyA.setId("propertyA");

		PropertySingleValued propertyB = factory.create(new PropertySingleValued(), 5);
		propertyB.setId("propertyB");

		// Create and add operation
		Operation op = factory.createOperation(new Operation(), (Function<Object[], Object>) (obj) -> {
			return sub((int) obj[0], (int) obj[1]);
		});
		op.setId("sub");

		List<Property> props = new ArrayList<>();
		props.add(propertyA);
		props.add(propertyB);

		putAll(factory.createContainer(this, props, Collections.singletonList(op)));
	}
}
