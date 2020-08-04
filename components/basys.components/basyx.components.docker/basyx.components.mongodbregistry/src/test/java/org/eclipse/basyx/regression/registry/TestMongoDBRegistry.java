package org.eclipse.basyx.regression.registry;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.AASRegistry;
import org.eclipse.basyx.components.mongodbregistry.MongoDBRegistryHandler;
import org.eclipse.basyx.testsuite.regression.aas.registration.TestRegistryProviderSuite;

/**
 * Test class for a local registry provider based on SQL tables
 * 
 * @author espen
 *
 */
public class TestMongoDBRegistry extends TestRegistryProviderSuite {

	@Override
	protected IAASRegistryService getRegistryService() {
		return new AASRegistry(new MongoDBRegistryHandler("localMongodb.properties"));
	}
}
