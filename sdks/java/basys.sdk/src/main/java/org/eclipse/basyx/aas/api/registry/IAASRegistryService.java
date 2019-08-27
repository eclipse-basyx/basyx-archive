package org.eclipse.basyx.aas.api.registry;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;




/**
 * BaSys registry interface
 * 
 * @author kuhn
 *
 */
public interface IAASRegistryService {
	
	public IAASRegistryService addAASMapping(String key, String value);
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
	public AASDescriptor lookupAAS(ModelUrn aasID);
}

