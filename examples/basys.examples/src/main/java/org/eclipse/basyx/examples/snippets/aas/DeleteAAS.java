package org.eclipse.basyx.examples.snippets.aas;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to delete an AAS from a server
 * 
 * @author conradi
 *
 */
public class DeleteAAS {

	/**
	 * Removes an AAS from the server
	 * 
	 * @param aasIdentifier the Identifier of the AAS to be deleted
	 * @param registryServerURL the URL of the registry server (e.g. http://localhost:8080/registry)
	 */
	public static void deleteAAS(IIdentifier aasIdentifier, String registryServerURL) {
	
		// Create a proxy pointing to the registry server
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Create a ConnectedAASManager using the registryProxy as its registry
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(registryProxy);
		
		// Delete the AAS
		// Automatically deregisters it
		manager.deleteAAS(aasIdentifier);
	}
}