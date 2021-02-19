package org.eclipse.basyx.wrapper.provider;

import java.util.Collection;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.wrapper.receiver.IPropertyWrapperService;
import org.eclipse.basyx.wrapper.receiver.configuration.PropertyConfiguration;

/**
 * Interface for a generic connector that makes use of the HTTPModelProvider interface
 * 
 * @author espen
 *
 */
public interface IWrapperProvider extends HTTPModelProvider {

	/**
	 * Returns the path for this provider
	 * 
	 * @return
	 */
	public String getProviderPath();

	/**
	 * Sets the path for this provider
	 * 
	 * @param path
	 */
	public void setProviderPath(String path);

	/**
	 * Initialize the provider
	 * 
	 * @param proxyService
	 * @param registry
	 */
	public void initialize(IPropertyWrapperService proxyService, IAASRegistryService registry,
			Collection<PropertyConfiguration> configs);
}
