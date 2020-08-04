package org.eclipse.basyx.aas.registration.api;

import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.exception.provider.ProviderException;




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
	public void register(AASDescriptor deviceAASDescriptor) throws ProviderException;

	/**
	 * Register SM descriptor in registry, delete old registration
	 */
	public void register(IIdentifier aas, SubmodelDescriptor smDescriptor) throws ProviderException;

	/**
	 * Delete AAS descriptor from registry
	 */
	public void delete(IIdentifier aasId) throws ProviderException;
	
	/**
	 * Delete SM descriptor from registry
	 */
	public void delete(IIdentifier aasId, String smIdShort) throws ProviderException;
	
	/**
	 * Lookup AAS
	 */
	public AASDescriptor lookupAAS(IIdentifier aasId) throws ProviderException;

	/**
	 * Retrieve all registered AAS
	 * 
	 * @return
	 */
	public List<AASDescriptor> lookupAll() throws ProviderException;
}

