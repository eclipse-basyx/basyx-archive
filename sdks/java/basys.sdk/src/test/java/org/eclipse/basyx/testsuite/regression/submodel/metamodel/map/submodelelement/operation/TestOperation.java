package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.operation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
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
	public void testSetInvocable() throws Exception {
		Operation operation = new Operation(IN, OUT, INOUT, FUNC);
		assertEquals(5, operation.invoke(3, 2));
		
		Function<Object[], Object> newFunction = (Function<Object[], Object>) v -> {
			return (int)v[0] - (int)v[1];
		};
		operation.setInvocable(newFunction);
		
		assertEquals(1, operation.invoke(3,2));
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
}
