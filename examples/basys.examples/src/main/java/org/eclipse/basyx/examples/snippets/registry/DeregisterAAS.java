package org.eclipse.basyx.examples.snippets.registry;

import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to deregister a AAS from a RegistryComponent
 * 
 * @author conradi
 *
 */
public class DeregisterAAS {

	/**
	 * Deregisters a given AssetAdministrationShell from a registry.
	 * 
	 * @param smIdentifier the Identifier of the AAS to be deregistered 
	 * @param registryServerURL the address of the registry
	 */
	public static void registerAAS(IIdentifier aasIdentifier, String registryServerURL) {
		
		// Create a proxy pointing to the registry
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Delete the AAS from the registry
		registryProxy.delete(aasIdentifier);
	}
	
}
