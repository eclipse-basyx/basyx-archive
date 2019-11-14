package org.eclipse.basyx.testsuite.regression.vab.directory.restapi;

import org.eclipse.basyx.testsuite.regression.vab.directory.proxy.TestDirectory;
import org.eclipse.basyx.vab.directory.restapi.DirectoryModelProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

public class TestDirectoryProvider extends TestDirectory {

	@Override
	protected IModelProvider getProxyProvider() {
		DirectoryModelProvider provider = new DirectoryModelProvider();
		IModelProvider apiProxy = new VABElementProxy("", provider);
		return apiProxy;
	}

}
