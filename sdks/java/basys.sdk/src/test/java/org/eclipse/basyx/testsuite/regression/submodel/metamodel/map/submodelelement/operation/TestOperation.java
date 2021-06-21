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
import static org.junit.Assert.fail;

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
	
	@Override
	protected IOperation prepareOperation(Operation operation) {
		return operation;
	}
	
	@Test
	public void testOptionalElements() throws Exception {
		operation = new Operation(null, null, null, FUNC);
		assertEquals(0, operation.getInputVariables().size());
		assertEquals(0, operation.getOutputVariables().size());
		assertEquals(0, operation.getInOutputVariables().size());
	}
	
	@Test 
	public void testSetFunctionAsInvokable() throws Exception {
		Operation operation = new Operation(IN, OUT, INOUT, FUNC);
		assertEquals(5, operation.invoke(3, 2));
		
		Function<Object[], Object> newFunction = (Function<Object[], Object>) v -> {
			return (int)v[0] - (int)v[1];
		};
		operation.setInvokable(newFunction);
		
		assertEquals(1, operation.invoke(3,2));
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

	private int setValue;

	@Test
	public void testSetConsumerAsInvokable() {
		Consumer<Object[]> consumer = (a) -> {
			setValue = (Integer) a[0];
		};

		Operation operation = new Operation("consumer");

		Property inputProperty = new Property("input", ValueType.Integer);
		inputProperty.setModelingKind(ModelingKind.TEMPLATE);

		OperationVariable inputVariable = new OperationVariable(inputProperty);
		operation.setInputVariables(Collections.singleton(inputVariable));
		operation.setInvokable(consumer);

		int expected = 5;
		operation.invokeSimple(expected);

		assertEquals(expected, setValue);

	}

	@Override
	@Test
	public void testInvokeWithSubmodelElements() {
		Property param1 = new Property("testIn1", 1);
		Property param2 = new Property("testIn2", 1);
		try {
			operation.invoke(param1, param2);
			// Only unwrapped invokation is supported for local operations
			fail();
		} catch (UnsupportedOperationException e) {
		}
	}

	@Test
	public void testSetDataSpecificationReferences() {
		Operation operation = new Operation(IN, OUT, INOUT, FUNC);
		Collection<IReference> references = Collections.singleton(new Reference(new Key(KeyElements.ASSET, true, KEY_VALUE, IdentifierType.IRI)));
		operation.setDataSpecificationReferences(references);
		
		Collection<IReference> newReferences = operation.getDataSpecificationReferences();
		assertEquals(1, newReferences.size());
		
		IReference newReference = new ArrayList<>(newReferences).get(0);
		
		assertEquals(KEY_VALUE, newReference.getKeys().get(0).getValue());
	}

	@Override
	public void testInvokeWithSubmodelElementsException() {
		try {
			Property param1 = new Property("testIn1", 1);
			Property param2 = new Property("testIn2", 1);

			// Only unwrapped invokation is supported for local operations
			operationException.invoke(param1, param2);
			fail();
		} catch (UnsupportedOperationException e) {
		}
	}
}
