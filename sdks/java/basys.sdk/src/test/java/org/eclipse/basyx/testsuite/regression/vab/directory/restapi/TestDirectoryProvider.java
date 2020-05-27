package org.eclipse.basyx.testsuite.regression.vab.directory.restapi;

import org.eclipse.basyx.testsuite.regression.vab.directory.proxy.TestDirectory;
import org.eclipse.basyx.vab.directory.api.IVABDirectoryService;
import org.eclipse.basyx.vab.directory.proxy.VABDirectoryProxy;
import org.eclipse.basyx.vab.directory.restapi.DirectoryModelProvider;

/**
 * Tests the directory provider using the TestDirectory Suite
 * 
 * @author schnicke
 *
 */
public class TestDirectoryProvider extends TestDirectory {

	@Override
	protected IVABDirectoryService getRegistry() {
		DirectoryModelProvider provider = new DirectoryModelProvider();
		return new VABDirectoryProxy(provider);
	}

}
