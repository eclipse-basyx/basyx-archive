package org.eclipse.basyx.aas.registration.api;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;




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
	public void register(AASDescriptor deviceAASDescriptor);
	
	/**
	 * Only register AAS descriptor in registry
	 */
	public void registerOnly(AASDescriptor deviceAASDescriptor);

	
	/**
	 * Delete AAS descriptor from registry
	 */
	public void delete(IIdentifier aasID);
	
	
	/**
	 * Lookup device AAS
	 */
	public AASDescriptor lookupAAS(IIdentifier aasID);
}

