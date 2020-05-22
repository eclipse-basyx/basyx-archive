package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.operation;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, getter and setter of {@link OperationVariable} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestOperationVariable {
	private static final Property PROPERTY = new Property("testOpVariable");
	
	private OperationVariable operationVariable;
	@Before
	public void buildOperationVariable() {
		operationVariable = new OperationVariable(PROPERTY);
	} 
	
	@Test
	public void testConstructor() {
		assertEquals(PROPERTY, operationVariable.getValue());
	} 
	
	@Test
	public void testSetValue() {
		Property property = new Property("testNewProperty");
		operationVariable.setValue(property);
		assertEquals(property, operationVariable.getValue());
	}
}
