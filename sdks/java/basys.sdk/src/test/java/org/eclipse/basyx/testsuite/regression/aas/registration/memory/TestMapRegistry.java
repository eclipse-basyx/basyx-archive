package org.eclipse.basyx.testsuite.regression.aas.registration.memory;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.memory.MapRegistryHandler;
import org.eclipse.basyx.testsuite.regression.aas.registration.TestRegistryProviderSuite;

/**
 * Tests functionalities of {@link MapRegistryHandler} for their correctness
 * Includes test cases for exceptions
 * 
 * @author haque
 *
 */
public class TestMapRegistry extends TestRegistryProviderSuite {

	@Override
	protected IAASRegistryService getRegistryService() {
		return new InMemoryRegistry();
	}
}
