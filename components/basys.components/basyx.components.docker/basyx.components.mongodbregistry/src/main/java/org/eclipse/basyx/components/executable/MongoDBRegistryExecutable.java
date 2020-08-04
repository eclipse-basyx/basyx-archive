package org.eclipse.basyx.components.executable;

import org.eclipse.basyx.components.MongoDBRegistryComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.mongodbregistry.BaSyxMongoDBConfiguration;
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
		logger.info("Starting BaSyx SQL registry");

		// Load configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		if (args.length > 0 && args[0] instanceof String) {
			// file path available? => load configs from file
			config.loadFromFile(args[0]);
		} else {
			// fallback: load default configs (in resources)
			config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		}

		MongoDBRegistryComponent component;
		BaSyxMongoDBConfiguration dbConfig = new BaSyxMongoDBConfiguration();
		if (args.length > 1 && args[1] instanceof String) {
			// file path available? => load mongodb configs from file
			dbConfig.loadFromFile(args[1]);
			component = new MongoDBRegistryComponent(config.getHostname(), config.getPort(), config.getContextPath(),
					config.getDocBasePath(), dbConfig);
		} else {
			component = new MongoDBRegistryComponent(config.getHostname(), config.getPort(), config.getContextPath(),
					config.getDocBasePath(), "dockerMongodb.properties");
		}

		component.startComponent();
	}
}