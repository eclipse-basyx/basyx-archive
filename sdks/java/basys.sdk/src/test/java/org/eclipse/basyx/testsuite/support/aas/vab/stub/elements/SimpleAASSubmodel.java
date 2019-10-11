package org.eclipse.basyx.testsuite.support.aas.vab.stub.elements;

import java.util.function.Function;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.impl.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.ContainerProperty;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;

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
	public SimpleAASSubmodel(String id) {
		// Create sub model

		setId(id);

		// Create example properties
		MetaModelElementFactory fac = new MetaModelElementFactory();

		SingleProperty intProp = new SingleProperty(123);
		intProp.setId("integerProperty");
		addSubModelElement(intProp);

		SingleProperty stringProp = new SingleProperty("Test");
		stringProp.setId("stringProperty");
		addSubModelElement(stringProp);

		// Create example operations
		Operation complex = fac.createOperation(new Operation(), (Function<Object[], Object>) (v) -> {
			return (int) v[0] - (int) v[1];
		});
		complex.setId("complex");
		addSubModelElement(complex);

		Operation simple = fac.createOperation(new Operation(), (Function<Object[], Object>) (v) -> {
			return true;
		});
		simple.setId("simple");
		addSubModelElement(simple);

		// Create example operations
		// - Contained operation that throws native JAVA exception
		Operation exception1 = fac.createOperation(new Operation(), (Function<Object[], Object>) (elId) -> {
			throw new NullPointerException();
		});
		exception1.setId("exception1");
		addSubModelElement(exception1);

		// - Contained operation that throws VAB exception
		Operation exception2 = fac.createOperation(new Operation(), (Function<Object[], Object>) (elId) -> {
			throw new ServerException("ExType", "Exception description");
		});
		exception2.setId("exception2");
		addSubModelElement(exception2);

		ContainerProperty containerProp = new ContainerProperty();
		containerProp.setId("container");
		containerProp.addSubModelElement(intProp);

		ContainerProperty containerPropRoot = new ContainerProperty();
		containerPropRoot.setId("containerRoot");
		containerPropRoot.addSubModelElement(containerProp);
		addSubModelElement(containerPropRoot);
	}
}
