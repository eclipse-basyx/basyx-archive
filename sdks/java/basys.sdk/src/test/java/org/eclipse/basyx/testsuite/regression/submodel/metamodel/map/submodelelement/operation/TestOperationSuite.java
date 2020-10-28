package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
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
	protected static final String OUT_VALUE = "outValue";
	protected static final String INOUT_VALUE = "inOutValue";
	protected static final Collection<OperationVariable> IN = Collections.singletonList(new OperationVariable(new Property(IN_VALUE)));
	protected static final Collection<OperationVariable> OUT = Collections.singletonList(new OperationVariable(new Property(OUT_VALUE)));
	protected static final Collection<OperationVariable> INOUT = Collections.singletonList(new OperationVariable(new Property(INOUT_VALUE)));
	
	protected static final Function<Object[], Object> FUNC = (Function<Object[], Object>) v -> {
		return (int)v[0] + (int)v[1];
	};
	
	protected static final Function<Object[], Object> EXCEPTION_FUNC = (Function<Object[], Object>) v -> {
		throw new NullPointerException();
	};
	
	protected IOperation operation;
	protected IOperation operationException;

	/**
	 * Converts an Operation into the IOperation to be tested
	 */
	protected abstract IOperation prepareOperation(Operation operation);
	
	@Before
	public void setup() {
		operation = prepareOperation(new Operation(IN, OUT, INOUT, FUNC));
		operationException = prepareOperation(new Operation(IN, OUT, INOUT, EXCEPTION_FUNC));
	}
	
	@Test
	public void testInvoke() throws Exception {
		assertEquals(5, operation.invoke(2, 3));
	}
	
	@Test
	public void testInvokeException() throws Exception {
		try {
			operationException.invoke();
			fail();
		} catch (Exception e) {
			// Exceptions from ConnectedOperation are wrapped in ProviderException
			assertTrue(e instanceof NullPointerException
					|| e.getCause() instanceof NullPointerException);
		}
	}
	
	@Test
	public void testInputVariables() {
		Object value = getValueFromOpVariable(operation.getInputVariables());
		assertEquals(IN_VALUE, value);
	}

	@Test
	public void testOutputVariables() {
		Object value = getValueFromOpVariable(operation.getOutputVariables());
		assertEquals(OUT_VALUE, value);
	}

	@Test
	public void testInOutputVariables() {
		Object value = getValueFromOpVariable(operation.getInOutputVariables());
		assertEquals(INOUT_VALUE, value);
	}
	
	/**
	 * Gets the Value from the OperationVariable in a collection
	 */
	private Object getValueFromOpVariable(Collection<IOperationVariable> vars) {
		assertEquals(1, vars.size());
		IOperationVariable var = new ArrayList<>(vars).get(0);
		return var.getValue().getValue();
	}
	
}
