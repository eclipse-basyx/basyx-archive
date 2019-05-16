package org.eclipse.basyx.components.proxy.registry;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.aasdescriptor.ModelUrn;




/**
 * BaSys registry interface
 * 
 * @author kuhn
 *
 */
public interface AASRegistryProxyIF {
	
	
	/**
	 * Register AAS descriptor in registry
	 */
	public void register(AASDescriptor deviceAASDescriptor);
	
	
	/**
	 * Delete AAS descriptor from registry
	 */
	public void delete(ModelUrn aasID);
	
	
	/**
	 * Lookup device AAS
	 */
	public AASDescriptor lookup(ModelUrn aasID);
}

