package org.eclipse.basyx.aas.registration.api;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;




/**
 * BaSys registry interface
 * 
 * @author kuhn
 *
 */
public interface IAASRegistryService {
	
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

