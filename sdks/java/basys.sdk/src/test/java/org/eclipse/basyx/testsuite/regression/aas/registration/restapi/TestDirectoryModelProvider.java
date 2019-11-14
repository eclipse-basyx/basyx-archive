package org.eclipse.basyx.testsuite.regression.aas.registration.restapi;

import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.testsuite.regression.aas.registration.proxy.TestRegistryProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

public class TestDirectoryModelProvider extends TestRegistryProvider {

	@Override
	protected IModelProvider getProxyProvider() {
		DirectoryModelProvider provider = new DirectoryModelProvider();
		IModelProvider apiProxy = new VABElementProxy("/api/v1/registry", provider);
		return apiProxy;
	}

}
