package org.eclipse.basyx.components.executable;

import org.eclipse.basyx.components.InMemoryRegistryComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry servlet based on an InMemory Registry. The servlet therefore provides an implementation
 * for the IAASRegistryService interface without a permanent storage capability.
 * 
 * Do not use this registry in a productive environment - the entries are not persistent!
 * 
 * @author espen
 */
public class InMemoryRegistryExecutable {
	private static Logger logger = LoggerFactory.getLogger(InMemoryRegistryExecutable.class);

	private InMemoryRegistryExecutable() {
	}

	public static void main(String[] args) {
		logger.info("Starting BaSyx InMemory registry");

		// Load configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		if (args.length > 0 && args[0] instanceof String) {
			// file path available? => load configs from file
			config.loadFromFile(args[0]);
		} else {
			// fallback: load default configs (in resources)
			config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		}

		InMemoryRegistryComponent component = new InMemoryRegistryComponent(config.getHostname(), config.getPort(),
				config.getContextPath(), config.getDocBasePath());
		component.startComponent();
	}
}