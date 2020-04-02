package org.eclipse.basyx.regression.registry;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.components.sqlregistry.SQLRegistry;
import org.eclipse.basyx.testsuite.regression.aas.registration.proxy.TestRegistryProvider;

/**
 * Test class for a local registry provider based on SQL tables
 * 
 * @author espen
 *
 */
public class TestSQLRegistryProvider extends TestRegistryProvider {

	@Override
	protected IAASRegistryService getRegistryService() {
		return new SQLRegistry("localRegistry.properties");
	}
}
