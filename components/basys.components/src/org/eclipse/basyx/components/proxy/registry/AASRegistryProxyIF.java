package org.eclipse.basyx.components.proxy.registry;

import org.eclipse.basyx.tools.aasdescriptor.AASDescriptor;
import org.eclipse.basyx.tools.modelurn.ModelUrn;
import org.eclipse.basyx.vab.core.IDirectoryService;




/**
 * BaSys registry interface
 * 
 * @author kuhn
 *
 */
public interface AASRegistryProxyIF extends IDirectoryService {
	
	
	/**
	 * Register AAS descriptor in registry, delete old registration
	 */
	public void register(ModelUrn aasID, AASDescriptor deviceAASDescriptor);

	
	/**
	 * Only register AAS descriptor in registry
	 */
	public void registerOnly(AASDescriptor deviceAASDescriptor);

	
	/**
	 * Delete AAS descriptor from registry
	 */
	public void delete(ModelUrn aasID);
	
	
	/**
	 * Lookup device AAS
	 */
	public AASDescriptor lookup(ModelUrn aasID);
}

