/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.testsuite.regression.submodel.restapi;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
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

		Property nullProp = new Property("nullProperty", PropertyValueTypeDef.String);
		nullProp.setValue(null);
		addSubModelElement(nullProp);

		// Create example operations
		Operation complex = new Operation((Function<Object[], Object>) v -> {
			return (int) v[0] - (int) v[1];
		});
		Property inProp1 = new Property("complexIn1", 0);
		inProp1.setModelingKind(ModelingKind.TEMPLATE);
		Property inProp2 = new Property("complexIn2", 0);
		inProp2.setModelingKind(ModelingKind.TEMPLATE);
		Property outProp = new Property("complexOut", 0);
		outProp.setModelingKind(ModelingKind.TEMPLATE);
		complex.setInputVariables(Arrays.asList(new OperationVariable(inProp1),
				new OperationVariable(inProp2)));
		complex.setOutputVariables(Collections.singleton(new OperationVariable(outProp)));
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
