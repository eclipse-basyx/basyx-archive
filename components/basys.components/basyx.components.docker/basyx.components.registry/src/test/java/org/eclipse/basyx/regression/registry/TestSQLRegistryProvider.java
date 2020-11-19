package org.eclipse.basyx.regression.registry;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.components.configuration.BaSyxSQLConfiguration;
import org.eclipse.basyx.components.registry.sql.SQLRegistry;
import org.eclipse.basyx.testsuite.regression.aas.registration.TestRegistryProviderSuite;

/**
 * Test class for a local registry provider based on SQL tables
 * 
 * @author espen
 *
 */
public class TestSQLRegistryProvider extends TestRegistryProviderSuite {

	@Override
	protected IAASRegistryService getRegistryService() {
		BaSyxSQLConfiguration sqlConfig = new BaSyxSQLConfiguration();
		sqlConfig.loadFromResource("sql.properties");
		return new SQLRegistry(sqlConfig);
	}
}
