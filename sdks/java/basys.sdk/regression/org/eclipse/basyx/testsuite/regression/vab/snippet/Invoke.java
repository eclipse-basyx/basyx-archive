package org.eclipse.basyx.testsuite.regression.vab.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Snippet to test invoke functionality of a IModelProvider
 * 
 * @author kuhn, schnicke
 *
 */
public class Invoke {
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Invoke operation
		Object value1 = connVABElement.invoke("operations/operation1", 1, 2);
		Object value2 = connVABElement.invoke("property1/operations/operation1.1/endpoint");
		Object nullValue = connVABElement.invoke("operations/operation2");

		assertEquals(3, value1);

		assertEquals(10, value2);

		assertNull(nullValue);

		// Invoke operations that throw Exceptions
		try {
			connVABElement.invoke("operations/operationEx1");
			fail();
		} catch (Exception e) {
			System.out.println("Ex1:" + e);
		}

		try {
			connVABElement.invoke("operations/operationEx2");
			fail();
		} catch (Exception e) {
			System.out.println("Ex2:" + e);
		}
	}
}
