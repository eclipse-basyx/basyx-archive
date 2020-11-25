package org.eclipse.basyx.examples.snippets.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to create an AssetAdministrationShell
 * with only the mandatory attributes.
 * 
 * @author conradi
 *
 */
public class CreateAAS {

	/**
	 * Creates a new AAS
	 * 
	 * @param idShort the idShort of the AAS to be created
	 * @param aasIdentifier the Identifier of the AAS to be created
	 * @param asset the asset to be contained in the new AAS
	 * @return the newly created AAS
	 */
	public static AssetAdministrationShell createAAS(String idShort, IIdentifier aasIdentifier, Asset asset) {
		
		// Create a new AssetAdministrationShell object
		AssetAdministrationShell aas = new AssetAdministrationShell(idShort, aasIdentifier, asset);
		return aas;
	}
	
}
