package org.eclipse.basyx.vab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.basyx.vab.coder.json.metaprotocol.Message;
import org.eclipse.basyx.vab.coder.json.metaprotocol.Result;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ResourceAlreadyExistsException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Snippet to test the exception handling of an IModelProvider
 * Based on the VAB Exceptions tests within the SDK, but removes
 * Java-specific local tests.
 * 
 * @author espen
 *
 */
public class CppExceptions {
	/**
	 * Tests for handling an exception and its code
	 */
	public static void testHandlingException(VABConnectionManager connManager) {
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");

		// Empty paths - at "" is a Map. Therefore create should throw an Exception
		try {
			connVABElement.createValue("", "");
			fail();
		} catch (ResourceAlreadyExistsException e) {
			Result result = new Result(e);
			Message msg = result.getMessages().get(0);
			assertEquals("422", msg.getCode());
		}

		// Non-existing parent element
		try {
			connVABElement.getModelPropertyValue("unknown/x");
			fail();
		} catch (ResourceNotFoundException e) {
			Result result = new Result(e);
			Message msg = result.getMessages().get(0);
			assertEquals("404", msg.getCode());

		}

		// Null path - should throw exception
		try {
			connVABElement.createValue(null, "");
			fail();
		} catch (MalformedRequestException e) {
			Result result = new Result(e);
			Message msg = result.getMessages().get(0);
			assertEquals("400", msg.getCode());
		}

	}
}
