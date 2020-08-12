package org.eclipse.basyx.vab;

import org.eclipse.basyx.testsuite.regression.vab.modelprovider.TestProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract test suite for testing CRUD-operations for different types of model providers.
 * The concrete test cases implement concrete VABConnectionManagers that are tested
 * This extension supports resetting a remote VAB test server before each test.
 * 
 * @author espen
 *
 */
public abstract class CppTestProvider extends TestProvider {
	@Before
	public void before() {
		VABElementProxy connVABElement = getConnectionManager()
				.connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		connVABElement.invokeOperation("/reset", 1);
	}

	@Test
	public void testMapRead() {
		CppMapRead.test(getConnectionManager());
	}

	@Test
	public void testMapUpdate() {
		CppMapUpdate.test(getConnectionManager());
	}

	@Test
	public void testCollectionCreateDelete() throws Exception {
		CppTestCollectionProperty.testCreateDelete(getConnectionManager());
	}

	@Test
	public void testCollectionUpdate() {
		CppTestCollectionProperty.testUpdate(getConnectionManager());
	}

	@Test
	public void testMapInvoke() {
		CppMapInvoke.test(getConnectionManager());
	}

	@Test
	public void testHandlingException() {
		CppExceptions.testHandlingException(getConnectionManager());
	}
}
