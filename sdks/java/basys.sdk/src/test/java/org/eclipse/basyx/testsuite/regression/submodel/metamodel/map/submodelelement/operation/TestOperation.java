package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.operation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, getter and setter of {@link Operation} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestOperation {
	private static final Collection<OperationVariable> IN = Collections.singletonList(new OperationVariable(new Property("inValue")));
	private static final Collection<OperationVariable> OUT = Collections.singletonList(new OperationVariable(new Property("outValue")));
	private static final Collection<OperationVariable> INOUT = Collections.singletonList(new OperationVariable(new Property("inOutValue")));
	private static final Function<Object[], Object> FUNC = (Function<Object[], Object>) v -> {
		return (int)v[0] + (int)v[1];
	};
	
	private Operation operation;
	
	@Before
	public void buildOperation() {
		operation = new Operation(FUNC);
	}
	
	@Test
	public void testConstructor1() throws Exception {
		operation = new Operation(IN, OUT, INOUT, FUNC);
		testInputVariables();
		testOutputVariables();
		testInOutputVariables();
		testInvokable();
	}
	
	@Test
	public void testConstructor2() throws Exception {
		assertEquals(new ArrayList<OperationVariable>(), operation.getInputVariables());
		assertEquals(new ArrayList<OperationVariable>(), operation.getOutputVariables());
		assertEquals(new ArrayList<OperationVariable>(), operation.getInOutputVariables());
		testInvokable();
	}

	@Test
	public void testOptionalElements() throws Exception {
		operation = new Operation(null, null, null, FUNC);
		assertEquals(0, operation.getInputVariables().size());
		assertEquals(0, operation.getOutputVariables().size());
		assertEquals(0, operation.getInOutputVariables().size());
	} 
	
	@Test 
	public void testSetInputVariables() {
		operation.setInputVariables(IN);
		testInputVariables();
	}
	
	@Test 
	public void testSetOutputVariables() {
		operation.setOutputVariables(OUT);
		testOutputVariables();
	}
	
	@Test 
	public void testSetInOutputVariables() {
		operation.setInOutputVariables(INOUT);
		testInOutputVariables();
	}
	
	@Test 
	public void testSetInvocable() throws Exception {
		Function<Object[], Object> newFunction = (Function<Object[], Object>) v -> {
			return (int)v[0] - (int)v[1];
		};
		operation.setInvocable(newFunction);
		assertEquals(1, operation.invoke(3,2));
	}
	
	@Test
	public void testSetDataSpecificationReferences() {
		Collection<IReference> references = Collections.singleton(new Reference(new Key(KeyElements.ASSET, true, "testValue", IdentifierType.IRI)));
		operation.setDataSpecificationReferences(references);
		assertEquals(references, operation.getDataSpecificationReferences());
	}
	
	private void testInvokable() throws Exception {
		assertEquals(5, operation.invoke(2,3));
	} 
	
	private void testInputVariables() {
		assertEquals(IN, operation.getInputVariables());
	}

	private void testOutputVariables() {
		assertEquals(OUT, operation.getOutputVariables());
	}

	private void testInOutputVariables() {
		assertEquals(INOUT, operation.getInOutputVariables());
	}
}
