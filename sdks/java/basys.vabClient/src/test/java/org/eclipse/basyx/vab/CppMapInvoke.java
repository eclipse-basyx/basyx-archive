package org.eclipse.basyx.vab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Snippet to test invoke functionality of a IModelProvider.
 * Based on the MapInvoke tests within the SDK, but removes
 * Java-specific local tests.
 * 
 * @author espen
 *
 */
public class CppMapInvoke {
	
	public static void test(VABConnectionManager connManager) {
		// Connect to VAB element with ID "urn:fhg:es.iese:vab:1:1:simplevabelement"
		VABElementProxy connVABElement = connManager.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
	
		// Invoke complex function
		Object complex = connVABElement.invokeOperation("operations/complex", 12, 34);
		assertEquals(46, complex);
	
		// Invoke non-existing operation
		try {
			connVABElement.invokeOperation("operations/unknown");
			fail();
		} catch (ResourceNotFoundException e) {}
	
		// Invoke invalid operation -> not a function, but a primitive data type
		try {
			connVABElement.invokeOperation("operations/invalid");
			fail();
		} catch (ProviderException e) {}
	
		// Invoke operations that throw Exceptions
		try {
			connVABElement.invokeOperation("operations/providerException");
			fail();
		} catch (ProviderException e) {
			// exception type not implemented, yet
			// assertEquals(e.getType(), "testExceptionType");
		}
	
		try {
			connVABElement.invokeOperation("operations/nullException");
			fail();
		} catch (ProviderException e) {
			// exception type not implemented, yet
			// assertEquals(e.getType(), "java.lang.NullPointerException");
		}
	
		// Empty paths - should execute, but has no effect
		try {
			connVABElement.invokeOperation("", "");
			fail();
		} catch (ProviderException e) {}
		
	
		// Null path - should throw exception
		try {
			connVABElement.invokeOperation(null, "");
			fail();
		} catch (MalformedRequestException e) {}
	}
}
