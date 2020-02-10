package org.eclipse.basyx.components.configuration;

import org.eclipse.basyx.components.configuration.builder.BaSyxConfigurationBuilder;

/**
 * Base interface for configurable components
 * 
 * @author kuhn
 *
 */
public interface ConfigurableComponent<T extends BaSyxConfigurationBuilder<?>> {

	
	/**
	 * Create a component builder for this component that is used for configurating the component
	 */
	public T configure();
	
	
	/**
	 * Configure the component
	 */
	public void configureComponent(T configuration);
}
