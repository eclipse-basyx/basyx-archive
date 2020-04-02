package org.eclipse.basyx.testsuite.regression.aas.registration.restapi;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.testsuite.regression.aas.registration.proxy.TestRegistryProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Tests correct behaviour of the DirectoryModelProvider using an InMemory
 * database
 * 
 * @author schnicke
 *
 */
public class TestDirectoryModelProvider extends TestRegistryProvider {
	@Override
	protected IAASRegistryService getRegistryService() {
		DirectoryModelProvider provider = new DirectoryModelProvider();
		IModelProvider apiProxy = new VABElementProxy("/api/v1/registry", provider);
		return new AASRegistryProxy(apiProxy);
	}

}
