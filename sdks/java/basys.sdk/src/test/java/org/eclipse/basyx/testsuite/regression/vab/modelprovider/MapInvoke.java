package org.eclipse.basyx.testsuite.regression.vab.modelprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Snippet to test invoke functionality of a IModelProvider
 * 
 * @author kuhn, schnicke, espen
 *
 */
public class MapInvoke {
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Invoke complex function
		Object complex = connVABElement.invokeOperation("operations/complex", 12, 34);
		assertEquals(46, complex);

		// Invoke unsupported functional interface
		Object supplier = connVABElement.invokeOperation("operations/supplier");
		assertNull(supplier);

		// Invoke non-existing operation
		Object nonExisting = connVABElement.invokeOperation("operations/unknown");
		assertNull(nonExisting);

		// Invoke invalid operation -> not a function, but a primitive data type
		Object invalid = connVABElement.invokeOperation("operations/invalid");
		assertNull(invalid);

		// Invoke operations that throw Exceptions
		try {
			connVABElement.invokeOperation("operations/serverException");
			fail();
		} catch (ServerException e) {
			// exception type not implemented, yet
			// assertEquals(e.getType(), "testExceptionType");
		}

		try {
			connVABElement.invokeOperation("operations/nullException");
			fail();
		} catch (ServerException e) {
			// exception type not implemented, yet
			// assertEquals(e.getType(), "java.lang.NullPointerException");
		}

		// Empty paths - should execute, but has no effect
		connVABElement.invokeOperation("", "");

		// Null path - should throw exception
		try {
			connVABElement.invokeOperation(null, "");
			fail();
		} catch (ServerException e) {
		}
	}
}
