package org.eclipse.basyx.testsuite.regression.aas.registration.restapi;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.testsuite.regression.aas.registration.TestRegistryProviderSuite;

/**
 * Tests correct behaviour of the DirectoryModelProvider using an InMemory
 * database
 * 
 * @author schnicke
 *
 */
public class TestDirectoryModelProvider extends TestRegistryProviderSuite {
	@Override
	protected IAASRegistryService getRegistryService() {
		DirectoryModelProvider provider = new DirectoryModelProvider();
		return new AASRegistryProxy(provider);
	}

}
