package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.submodel.metamodel.TestSubmodelSuite;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.support.TypeDestroyingProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectSubmodel can be created and used correctly
 *
 * @author schnicke
 *
 */
public class TestConnectedSubModel extends TestSubmodelSuite {

	// String constants used in this test case
	private final static String OP = "add";

	private final String OPERATION_ID = "operation_id";

	ConnectedSubModel submodel;

	@Before
	public void build() {

		SubModel reference = getReferenceSubmodel();
		// Create an operation
		Operation op = new Operation((Function<Object[], Object> & Serializable) obj -> {
			return (int) obj[0] + (int) obj[1];
		});
		op.setIdShort(OP);
		reference.addSubModelElement(op);

		SubModelProvider provider = new SubModelProvider(new TypeDestroyingProvider(new VABLambdaProvider(reference)));

		// Create the ConnectedSubModel based on the manager
		submodel = new ConnectedSubModel(new VABConnectionManagerStub(provider).connectToVABElement(""));
	}

	/**
	 * Tests if a SubModel's operations can be used correctly
	 *
	 * @throws Exception
	 */
	@Test
	public void operationsTest() throws Exception {
		// Retrieve all operations
		Map<String, IOperation> ops = submodel.getOperations();

		// Check if number of operations is as expected
		assertEquals(1, ops.size());

		// Check the operation itself
		IOperation op = ops.get(OP);
		assertEquals(5, op.invoke(2, 3));
	}

	@Test
	public void saveAndLoadOperationTest() throws Exception {
		// Get sample Operations and save them into SubModel
		Map<String, IOperation> testOperations = getTestOperations();
		for (ISubmodelElement element : testOperations.values()) {
			submodel.addSubModelElement(element);
		}

		// Load it
		Map<String, IOperation> map = submodel.getOperations();

		// Check if it loaded correctly
		checkOperations(map);
	}

	@Test
	public void testGetLocalCopy() {
		assertEquals(getReferenceSubmodel(), submodel.getLocalCopy());
	}

	/**
	 * Generates test IOperations
	 */
	private Map<String, IOperation> getTestOperations() {
		Map<String, IOperation> ret = new HashMap<>();

		Operation operation = new Operation();
		operation.setIdShort(OPERATION_ID);
		ret.put(operation.getIdShort(), operation);

		return ret;
	}

	/**
	 * Checks if the given Map contains all expected IOperations
	 */
	private void checkOperations(Map<String, ? extends ISubmodelElement> actual) throws Exception {
		assertNotNull(actual);

		Map<String, IOperation> expected = getTestOperations();

		IOperation expectedOperation = expected.get(OPERATION_ID);
		IOperation actualOperation = (IOperation) actual.get(OPERATION_ID);

		assertNotNull(actualOperation);
		assertEquals(expectedOperation.getIdShort(), actualOperation.getIdShort());
	}

	@Override
	protected ConnectedSubModel getSubmodel() {
		return submodel;
	}
}
