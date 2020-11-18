package org.eclipse.basyx.examples.snippets.registry;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;

/**
 * Snippet that showcases how to register a given AAS in a RegistryComponent
 * 
 * @author conradi
 *
 */
public class RegisterAAS {

	/**
	 * Registers a given AssetAdministrationShell in a registry.
	 * 
	 * @param aas the AssetAdministrationShell to be registered
	 * @param aasEndpoint the address where the AAS will be hosted (e.g. http://localhost:8080/aasList/{aasId}/aas)
	 * @param registryServerURL the address of the registry
	 */
	public static void registerAAS(IAssetAdministrationShell aas, String aasEndpoint, String registryServerURL) {
		
		// Create a proxy pointing to the registry
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Create a Descriptor for the AAS using the endpoint where it will be hosted
		AASDescriptor descriptor = new AASDescriptor(aas, aasEndpoint);
		
		// Register this Descriptor in the registry
		registryProxy.register(descriptor);
	}
	
}
