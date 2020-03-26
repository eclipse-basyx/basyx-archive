package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.modeltype;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.junit.Test;

/**
 * Tests constructor and getter of {@link ModelType} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestModelType {

	@Test
	public void testConstructor() {
		String type = "testType";
		ModelType modelType = new ModelType(type);
		assertEquals(type, modelType.getName());
	}
}
