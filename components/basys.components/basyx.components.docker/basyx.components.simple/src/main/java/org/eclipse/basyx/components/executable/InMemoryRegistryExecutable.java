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
		logger.info("Starting BaSyx InMemory Registry component...");
		// Load context configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromDefaultSource();

		// Create and start component according to the configuration
		InMemoryRegistryComponent component = new InMemoryRegistryComponent(contextConfig);
		component.startComponent();
		logger.info("BaSyx InMemory Registry component started");
	}
}