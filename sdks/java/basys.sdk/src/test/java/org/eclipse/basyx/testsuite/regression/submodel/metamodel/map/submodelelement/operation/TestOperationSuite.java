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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IAsyncInvocation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationExecutionErrorException;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationExecutionTimeoutException;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.vab.exception.provider.WrongNumberOfParametersException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for IOperation
 *
 * @author conradi
 *
 */
public abstract class TestOperationSuite {
	protected static final String IN_VALUE = "inValue";
	protected static final String IN_IDSHORT1 = "testIn1";
	protected static final String IN_IDSHORT2 = "testIn2";
	protected static final String OUT_VALUE = "outValue";
	protected static final String OUT_IDSHORT = "testOut";
	protected static final String INOUT_VALUE = "inOutValue";
	protected static final String INOUT_IDSHORT = "testInOut";
	protected static final String SIMPLE_SUPPLIER_RETURN_VALUE = "10";

	protected static Collection<OperationVariable> ONE_IN;
	protected static Collection<OperationVariable> TWO_IN;
	protected static Collection<OperationVariable> OUT;
	protected static Collection<OperationVariable> INOUT;
	protected static int expectedResultForSimpleConsumerTest;

	protected static final Function<Map<String, SubmodelElement>, SubmodelElement[]> PROPERTY_FUNC = (Function<Map<String, SubmodelElement>, SubmodelElement[]>) inputMap -> {
		Property p1 = (Property) inputMap.get(IN_IDSHORT1);
		Property p2 = (Property) inputMap.get(IN_IDSHORT2);
		int value1 = (int) p1.getValue();
		int value2 = (int) p2.getValue();

		int resultValue = value2 - value1;
		return new SubmodelElement[] { new Property(OUT_IDSHORT, resultValue) };
	};

	protected static final Function<Object[], Object> SIMPLE_FUNC = (Function<Object[], Object>) v -> {
		return (int) v[0] + (int) v[1];
	};

	protected static final Function<Object[], Object> EXCEPTION_FUNC = (Function<Object[], Object>) v -> {
		throw new NullPointerException();
	};

	protected static final Function<Map<String, SubmodelElement>, SubmodelElement[]> PROPERTY_EXCEPTION_FUNC = (Function<Map<String, SubmodelElement>, SubmodelElement[]>) inputMap -> {
		throw new NullPointerException();
	};

	protected static final Supplier<Object> SIMPLE_SUPPLIER_FUNC = (Supplier<Object>) () -> SIMPLE_SUPPLIER_RETURN_VALUE;

	protected static final Consumer<Object[]> SIMPLE_CONSUMER_FUNC = (Consumer<Object[]>) (a) -> {
		expectedResultForSimpleConsumerTest = (Integer) a[0];
	};

	protected IOperation simpleOperation;
	protected IOperation propertyOperation;
	protected IOperation simpleOperationException;
	protected IOperation propertyOperationException;
	protected IOperation simpleSupplierOperation;
	protected IOperation simpleConsumerOperation;

	/**
	 * Converts an Operation into the IOperation to be tested
	 */
	protected abstract IOperation prepareOperation(Operation operation);

	@Before
	public void setup() {
		ONE_IN = createOneInputVariables();
		TWO_IN = createTwoInputVariables();
		OUT = createOutputVariables();
		INOUT = createInOutVariables();

		propertyOperation = createPropertyOperation();
		propertyOperationException = createPropertyExceptionOperation();
		simpleOperation = createAddOperation();
		simpleOperationException = createSimpleExceptionOperation();
		simpleSupplierOperation = createSimpleSupplierOperation();
		simpleConsumerOperation = createSimpleConsumerOperation();
	}

	private IOperation createPropertyOperation() {
		Operation op = new Operation(TWO_IN, OUT, INOUT);
		op.setWrappedInvokable(PROPERTY_FUNC);
		op.setIdShort("PropertyOperation");
		return prepareOperation(op);
	}

	private IOperation createPropertyExceptionOperation() {
		Operation op = new Operation(TWO_IN, OUT, INOUT);
		op.setWrappedInvokable(PROPERTY_EXCEPTION_FUNC);
		op.setIdShort("PropertyExceptionOperation");
		return prepareOperation(op);
	}

	private IOperation createAddOperation() {
		Operation op = new Operation(TWO_IN, OUT, INOUT, SIMPLE_FUNC);
		op.setIdShort("SimpleOperation");
		return prepareOperation(op);
	}

	private IOperation createSimpleExceptionOperation() {
		Operation op = new Operation(TWO_IN, OUT, INOUT, EXCEPTION_FUNC);
		op.setIdShort("SimpleExceptionOperation");
		return prepareOperation(op);
	}

	private IOperation createSimpleSupplierOperation() {
		Operation op = new Operation("supplier");
		op.setOutputVariables(OUT);
		op.setInvokable(SIMPLE_SUPPLIER_FUNC);

		return prepareOperation(op);
	}

	private IOperation createSimpleConsumerOperation() {
		Operation op = new Operation("consumer");
		op.setInputVariables(ONE_IN);
		op.setInvokable(SIMPLE_CONSUMER_FUNC);

		return prepareOperation(op);
	}

	private Collection<OperationVariable> createInOutVariables() {
		Property inOutProp = new Property(INOUT_IDSHORT, INOUT_VALUE);
		inOutProp.setModelingKind(ModelingKind.TEMPLATE);
		return Arrays.asList(new OperationVariable(inOutProp));
	}

	private Collection<OperationVariable> createOutputVariables() {
		Property outProp = new Property(OUT_IDSHORT, OUT_VALUE);
		outProp.setModelingKind(ModelingKind.TEMPLATE);
		return Arrays.asList(new OperationVariable(outProp));
	}

	private Collection<OperationVariable> createOneInputVariables() {
		Property inProp1 = new Property(IN_IDSHORT1, IN_VALUE);
		inProp1.setModelingKind(ModelingKind.TEMPLATE);

		return Arrays.asList(new OperationVariable(inProp1));
	}

	private Collection<OperationVariable> createTwoInputVariables() {
		Property inProp1 = new Property(IN_IDSHORT1, IN_VALUE);
		inProp1.setModelingKind(ModelingKind.TEMPLATE);

		Property inProp2 = new Property(IN_IDSHORT2, IN_VALUE);
		inProp2.setModelingKind(ModelingKind.TEMPLATE);

		return Arrays.asList(new OperationVariable(inProp1), new OperationVariable(inProp2));
	}

	@Test
	public void testInvokeSimple() throws Exception {
		assertEquals(5, simpleOperation.invokeSimple(2, 3));
	}

	@Test
	public void testInvokePropertyOperationWithProperties() throws Exception {
		Property p1 = new Property(IN_IDSHORT1, 2);
		Property p2 = new Property(IN_IDSHORT2, 3);
		SubmodelElement[] directResult = propertyOperation.invoke(p1, p2);
		Property propertyResult = (Property) directResult[0];

		assertEquals(OUT_IDSHORT, propertyResult.getIdShort());
		assertEquals(1, propertyResult.getValue());
	}

	@Test
	public void testInvokeSimpleOperationWithProperties() throws Exception {
		Property p1 = new Property(IN_IDSHORT1, 2);
		Property p2 = new Property(IN_IDSHORT2, 3);
		SubmodelElement[] directResult = simpleOperation.invoke(p1, p2);
		Property propertyResult = (Property) directResult[0];

		assertEquals(OUT_IDSHORT, propertyResult.getIdShort());
		assertEquals(5, propertyResult.getValue());
	}

	@Test
	public void testInvokeSimpleWithSubmodelElementsException() {
		try {
			Property param1 = new Property(IN_IDSHORT1, 1);
			Property param2 = new Property(IN_IDSHORT2, 1);

			simpleOperationException.invoke(param1, param2);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException || e.getCause() instanceof NullPointerException);
		}
	}

	@Test
	public void testInvokePropertyOperationException() throws Exception {
		try {
			Property p1 = new Property(IN_IDSHORT1, 2);
			Property p2 = new Property(IN_IDSHORT2, 3);
			propertyOperationException.invoke(p1, p2);
			fail();
		} catch (Exception e) {
			// Exceptions from ConnectedOperation are wrapped in ProviderException
			assertTrue(e instanceof NullPointerException || e.getCause() instanceof NullPointerException);
		}
	}

	@Test
	public void testInvokePropertyOperationSimple() throws Exception {
		assertEquals(1, propertyOperation.invokeSimple(2, 3));
	}

	@Test
	public void testInvokeSimpleException() throws Exception {
		try {
			simpleOperationException.invokeSimple(1, 2);
			fail();
		} catch (Exception e) {
			// Exceptions from ConnectedOperation are wrapped in ProviderException
			assertTrue(e instanceof NullPointerException || e.getCause() instanceof NullPointerException);
		}
	}

	@Test
	public void testInvokeSimpleParametersException() {
		try {
			simpleOperation.invokeSimple(1);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof WrongNumberOfParametersException || e.getCause() instanceof WrongNumberOfParametersException);
		}
	}

	@Test
	public void testInvokeAsync() throws Exception {
		AsyncOperationHelper helper = new AsyncOperationHelper();
		IOperation operation = prepareOperation(helper.getAsyncOperation());

		IAsyncInvocation invocation = operation.invokeAsync(3, 2);

		assertFalse(invocation.isFinished());

		helper.releaseWaitingOperation();

		assertTrue(invocation.isFinished());
		assertEquals(5, invocation.getResult());
	}

	@Test
	public void testInvokeMultipleAsync() throws Exception {
		AsyncOperationHelper helper = new AsyncOperationHelper();
		IOperation operation = prepareOperation(helper.getAsyncOperation());

		IAsyncInvocation invocation1 = operation.invokeAsync(1, 2);
		IAsyncInvocation invocation2 = operation.invokeAsync(6, 2);

		assertFalse(invocation1.isFinished());
		assertFalse(invocation2.isFinished());

		helper.releaseWaitingOperation();

		assertTrue(invocation1.isFinished());
		assertTrue(invocation2.isFinished());
		assertEquals(3, invocation1.getResult());
		assertEquals(8, invocation2.getResult());
	}

	@Test(expected = OperationExecutionTimeoutException.class)
	public void testInvokeAsyncTimeout() throws Exception {
		AsyncOperationHelper helper = new AsyncOperationHelper();
		IOperation operation = prepareOperation(helper.getAsyncOperation());

		IAsyncInvocation invocation = operation.invokeAsyncWithTimeout(1, 3, 2);

		// Should be more than enough to trigger the timeout exception
		Thread.sleep(100);
		helper.releaseWaitingOperation();

		assertTrue(invocation.isFinished());
		invocation.getResult();
	}

	@Test(expected = OperationExecutionErrorException.class)
	public void testInvokeExceptionAsync() throws Exception {
		AsyncOperationHelper helper = new AsyncOperationHelper();
		IOperation operationException = prepareOperation(helper.getAsyncExceptionOperation());
		IAsyncInvocation invocation = operationException.invokeAsync();
		assertFalse(invocation.isFinished());

		helper.releaseWaitingOperation();

		invocation.getResult();
	}

	@Test
	public void testInvokeSimpleSupplier() {
		assertEquals(SIMPLE_SUPPLIER_RETURN_VALUE, simpleSupplierOperation.invokeSimple());
	}

	@Test
	public void testInvokeSimpleConsumer() {
		int expected = 5;

		simpleConsumerOperation.invokeSimple(expected);

		assertEquals(expected, expectedResultForSimpleConsumerTest);
	}

	@Test
	public void testInputVariables() {
		Collection<IOperationVariable> inputVariables = simpleOperation.getInputVariables();
		assertEquals(2, inputVariables.size());
		Object value = getValueFromOpVariable(inputVariables);
		assertEquals(IN_VALUE, value);
	}

	@Test
	public void testOutputVariables() {
		Collection<IOperationVariable> outputVariables = simpleOperation.getOutputVariables();

		Object value = getValueFromOpVariable(outputVariables);
		assertEquals(OUT_VALUE, value);
		assertEquals(1, outputVariables.size());
	}

	@Test
	public void testInOutputVariables() {
		Collection<IOperationVariable> inoutVariables = simpleOperation.getInOutputVariables();
		assertEquals(1, inoutVariables.size());
		Object value = getValueFromOpVariable(inoutVariables);
		assertEquals(INOUT_VALUE, value);
	}

	private Object getValueFromOpVariable(Collection<IOperationVariable> vars) {
		IOperationVariable var = new ArrayList<>(vars).get(0);
		return var.getValue().getValue();
	}

}

