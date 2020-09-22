package org.eclipse.basyx.components.executable;

import org.eclipse.basyx.components.MongoDBRegistryComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry servlet based on a MongoDB database. The servlet therefore provides an implementation
 * for the IAASRegistryService interface with a permanent storage solution. The properties for the
 * MongoDB connection will be read from executables.properties in the resource folder.
 * 
 * @author espen
 */
public class MongoDBRegistryExecutable {
	private static Logger logger = LoggerFactory.getLogger(MongoDBRegistryExecutable.class);

	private MongoDBRegistryExecutable() {
	}

	public static void main(String[] args) {
		logger.info("Starting BaSyx MongoDB Registry component...");
		// Load context configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromDefaultSource();

		// Load db configuration
		BaSyxMongoDBConfiguration dbConfig = new BaSyxMongoDBConfiguration();
		dbConfig.loadFromDefaultSource();

		// Create and start component according to the configuration
		MongoDBRegistryComponent component = new MongoDBRegistryComponent(contextConfig, dbConfig);
		component.startComponent();
		logger.info("BaSyx MongoDB Registry component started");
	}
}