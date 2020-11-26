package org.eclipse.basyx.testsuite.regression.submodel.restapi;

import java.util.function.Function;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.exception.provider.ProviderException;

/**
 * A simple VAB submodel element that explains the development of conforming
 * submodels using the VAB hashmap provider
 * 
 * @author kuhn
 *
 */
public class SimpleAASSubmodel extends SubModel {

	public static final String INTPROPIDSHORT = "integerProperty";
	public static final String OPERATIONSIMPLEIDSHORT = "simple";

	public SimpleAASSubmodel() {
		this("SimpleAASSubmodel");
	}

	/**
	 * Constructor
	 */
	public SimpleAASSubmodel(String idShort) {
		// Create sub model

		setIdShort(idShort);
		setIdentification(new ModelUrn("simpleAASSubmodelUrn"));

		Property intProp = new Property(123);
		intProp.setIdShort(INTPROPIDSHORT);
		addSubModelElement(intProp);

		Property stringProp = new Property("Test");
		stringProp.setIdShort("stringProperty");
		addSubModelElement(stringProp);

		Property nullProp = new Property(null, PropertyValueTypeDef.String);
		nullProp.setIdShort("nullProperty");
		addSubModelElement(nullProp);

		// Create example operations
		Operation complex = new Operation((Function<Object[], Object>) v -> {
			return (int) v[0] - (int) v[1];
		});
		complex.setIdShort("complex");
		addSubModelElement(complex);

		Operation simple = new Operation((Function<Object[], Object>) v -> {
			return true;
		});
		simple.setIdShort(OPERATIONSIMPLEIDSHORT);
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
			throw new ProviderException("Exception description");
		});
		exception2.setIdShort("exception2");
		addSubModelElement(exception2);

		Operation opInCollection = new Operation((Function<Object[], Object>) v -> {
			return 123;
		});
		opInCollection.setIdShort("operationId");
		
		SubmodelElementCollection containerProp = new SubmodelElementCollection();
		containerProp.setIdShort("container");
		containerProp.addSubModelElement(intProp);
		containerProp.addSubModelElement(opInCollection);

		SubmodelElementCollection containerPropRoot = new SubmodelElementCollection();
		containerPropRoot.setIdShort("containerRoot");
		containerPropRoot.addSubModelElement(containerProp);
		addSubModelElement(containerPropRoot);
	}
}
