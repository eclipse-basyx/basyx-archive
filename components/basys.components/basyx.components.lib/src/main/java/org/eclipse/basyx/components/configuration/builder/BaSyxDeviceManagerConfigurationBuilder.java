package org.eclipse.basyx.components.configuration.builder;

import org.eclipse.basyx.components.configuration.ConfigurableComponent;

/**
 * Configuration builder for BaSyx device managers
 * 
 * @author kuhn
 *
 */
public class BaSyxDeviceManagerConfigurationBuilder<T extends BaSyxDeviceManagerConfigurationBuilder<T>> extends BaSyxServiceConfigurationBuilder<T> {

	
	
	/**
	 * Constructor
	 */
	public BaSyxDeviceManagerConfigurationBuilder(ConfigurableComponent<?> component) {
		// Invoke base constructor
		super(component);
	}
}

