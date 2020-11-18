package org.eclipse.basyx.examples.snippets.registry;

import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to deregister a Submodel
 * 
 * @author conradi
 *
 */
public class DeregisterSubmodel {

	/**
	 * Deregisters a Submodel from a registry.
	 * 
	 * @param smIdentifier the Identifier of the Submodel to be deregistered 
	 * @param aasIdentifier the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL the address of the registry
	 */
	public static void registerSubmodel(IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) {
		
		// Create a proxy pointing to the registry
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Delete the Submodel from the registry
		registryProxy.delete(aasIdentifier, smIdentifier);
	}

}
