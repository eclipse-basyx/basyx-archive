/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.operation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.junit.Test;

/**
 * Tests constructor, getter and setter of {@link Operation} for their
 * correctness
 * 
 * @author haque, conradi
 *
 */
public class TestOperation extends TestOperationSuite {
	
	private static final String KEY_VALUE = "testKeyValue";
	
	private int setValueForTestSetNoInNoOutFunctionAsInvokable;
	private int setValueForTestConsumerAsInvokable;

	@Override
	protected IOperation prepareOperation(Operation operation) {
		return operation;
	}
	
	@Test
	public void testOptionalElements() throws Exception {
		simpleOperation = new Operation(null, null, null, SIMPLE_FUNC);
		assertEquals(0, simpleOperation.getInputVariables().size());
		assertEquals(0, simpleOperation.getOutputVariables().size());
		assertEquals(0, simpleOperation.getInOutputVariables().size());
	}
	
	@Test 
	public void testSetFunctionAsInvokable() throws Exception {
		Operation operation = new Operation(IN, OUT, INOUT, SIMPLE_FUNC);
		operation.setIdShort("function");
		assertEquals(5, operation.invoke(3, 2));
		
		Function<Object[], Object> newFunction = (Function<Object[], Object>) v -> {
			return (int)v[0] - (int)v[1];
		};
		operation.setInvokable(newFunction);
		
		assertEquals(1, operation.invoke(3,2));
	}


	@Test
	public void testSetNoInNoOutFunctionAsInvokable() throws Exception {
		Operation operation = new Operation("noInNoOutFunction");
		setValueForTestSetNoInNoOutFunctionAsInvokable = 0;
		int expected = 10;
		
		operation.setInvokable(() -> {
			setValueForTestSetNoInNoOutFunctionAsInvokable = expected;
		});
		
		operation.invokeSimple();

		assertEquals(expected, setValueForTestSetNoInNoOutFunctionAsInvokable);
	}

	@Test
	public void testSetSupplierAsInvokable() {
		int returnValue = 10;
		Supplier<Object> supplier = () -> returnValue;
		Operation operation = new Operation("supplier");
		
		Property outputProperty = new Property("output", ValueType.Integer);
		outputProperty.setModelingKind(ModelingKind.TEMPLATE);
		
		OperationVariable outputVariable = new OperationVariable(outputProperty);
		operation.setOutputVariables(Collections.singleton(outputVariable));
		operation.setInvokable(supplier);

		assertEquals(returnValue, operation.invokeSimple());
	}

	@Test
	public void testSetConsumerAsInvokable() {
		Consumer<Object[]> consumer = (a) -> {
			setValueForTestConsumerAsInvokable = (Integer) a[0];
		};

		Operation operation = new Operation("consumer");

		Property inputProperty = new Property("input", ValueType.Integer);
		inputProperty.setModelingKind(ModelingKind.TEMPLATE);

		OperationVariable inputVariable = new OperationVariable(inputProperty);
		operation.setInputVariables(Collections.singleton(inputVariable));
		operation.setInvokable(consumer);

		int expected = 5;
		operation.invokeSimple(expected);

		assertEquals(expected, setValueForTestConsumerAsInvokable);

	}

	@Test
	public void testSetDataSpecificationReferences() {
		Operation operation = new Operation(IN, OUT, INOUT, SIMPLE_FUNC);
		Collection<IReference> references = Collections.singleton(new Reference(new Key(KeyElements.ASSET, true, KEY_VALUE, IdentifierType.IRI)));
		operation.setDataSpecificationReferences(references);
		
		Collection<IReference> newReferences = operation.getDataSpecificationReferences();
		assertEquals(1, newReferences.size());
		
		IReference newReference = new ArrayList<>(newReferences).get(0);
		
		assertEquals(KEY_VALUE, newReference.getKeys().get(0).getValue());
	}
}
