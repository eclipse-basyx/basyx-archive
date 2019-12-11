package org.eclipse.basyx.aas.registration.api;

import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
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
	 * Register SM descriptor in registry, delete old registration
	 */
	public void register(IIdentifier aas, SubmodelDescriptor smDescriptor);

	/**
	 * Delete AAS descriptor from registry
	 */
	public void delete(IIdentifier aasId);
	
	/**
	 * Delete SM descriptor from registry
	 */
	public void delete(IIdentifier aasId, String smIdShort);
	
	/**
	 * Lookup AAS
	 */
	public AASDescriptor lookupAAS(IIdentifier aasId);

	/**
	 * Retrieve all registered AAS
	 * 
	 * @return
	 */
	public List<AASDescriptor> lookupAll();
}

