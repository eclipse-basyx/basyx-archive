package org.eclipse.basyx.testsuite.regression.submodel.restapi;

import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.ContainerProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.Property;
import org.eclipse.basyx.vab.exception.ServerException;

/**
 * A simple VAB submodel element that explains the development of conforming
 * submodels using the VAB hashmap provider
 * 
 * @author kuhn
 *
 */
public class SimpleAASSubmodel extends SubModel {

	private static final long serialVersionUID = 1L;

	public SimpleAASSubmodel() {
		this("SimpleAASSubmodel");
	}

	/**
	 * Constructor
	 */
	public SimpleAASSubmodel(String idShort) {
		// Create sub model

		setIdShort(idShort);

		Property intProp = new Property(123);
		intProp.setIdShort("integerProperty");
		addSubModelElement(intProp);

		Property stringProp = new Property("Test");
		stringProp.setIdShort("stringProperty");
		addSubModelElement(stringProp);

		// Create example operations
		Operation complex = new Operation((Function<Object[], Object>) v -> {
			return (int) v[0] - (int) v[1];
		});
		complex.setIdShort("complex");
		addSubModelElement(complex);

		Operation simple = new Operation((Function<Object[], Object>) v -> {
			return true;
		});
		simple.setIdShort("simple");
		addSubModelElement(simple);

		// Create example operations
		// - Contained operation that throws native JAVA exception
		Operation exception1 = new Operation((Function<Object[], Object>) elId -> {
			throw new NullPointerException();
		});
		exception1.setIdShort("exception1");
		addSubModelElement(exception1);

		// - Contained operation that throws VAB exception
		Operation exception2 = new Operation((Function<Object[], Object>) elId -> {
			throw new ServerException("ExType", "Exception description");
		});
		exception2.setIdShort("exception2");
		addSubModelElement(exception2);

		ContainerProperty containerProp = new ContainerProperty();
		containerProp.setIdShort("container");
		containerProp.addSubModelElement(intProp);

		ContainerProperty containerPropRoot = new ContainerProperty();
		containerPropRoot.setIdShort("containerRoot");
		containerPropRoot.addSubModelElement(containerProp);
		addSubModelElement(containerPropRoot);
	}
}
