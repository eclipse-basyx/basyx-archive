package org.eclipse.basyx.components.executable;

import org.eclipse.basyx.components.SQLRegistryComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry servlet based on an SQL database. The servlet therefore provides an implementation
 * for the IAASRegistryService interface with a permanent storage solution. The properties for the 
 * SQL connection will be read from executables.properties in the resource folder.
 * 
 * @author espen
 */
public class SQLRegistryExecutable {
	private static Logger logger = LoggerFactory.getLogger(SQLRegistryExecutable.class);

	private SQLRegistryExecutable() {
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

		SQLRegistryComponent component = new SQLRegistryComponent(config.getHostname(), config.getPort(),
				config.getContextPath(), config.getDocBasePath(), "dockerRegistry.properties");
		component.startComponent();
	}
}