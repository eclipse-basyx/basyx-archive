package org.eclipse.basyx.testsuite.regression.aas.registration;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;

/**
 * Instantiate a concrete test class for the abstract test suite
 * 
 * @author zhangzai
 *
 */
public class RegistryProviderSuiteWithDefinedURL extends TestRegistryProviderSuite {

	public static String url;// for example: "http://localhost:4999/";

	@Override
	protected IAASRegistryService getRegistryService() {
		return new AASRegistryProxy(url);
	}

}
