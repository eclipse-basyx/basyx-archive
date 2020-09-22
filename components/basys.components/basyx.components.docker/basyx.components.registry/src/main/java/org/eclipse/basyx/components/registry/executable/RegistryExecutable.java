package org.eclipse.basyx.components.registry.executable;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;

/**
 * A registry executable for a registry with any backend.
 * 
 * @author espen
 */
public class RegistryExecutable {
	private RegistryExecutable() {
	}

	public static void main(String[] args) {
		// Load context configuration from default source
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromDefaultSource();

		// Load registry configuration from default source
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration();
		registryConfig.loadFromDefaultSource();

		// Create and start component according to the configuration
		RegistryComponent component = new RegistryComponent(contextConfig, registryConfig);
		component.startComponent();
	}
}