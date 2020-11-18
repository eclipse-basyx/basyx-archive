package org.eclipse.basyx.examples.snippets.aas;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;

/**
 * This snippet showcases how to add a Submodel to an AAS,
 * that already exists on a server
 * 
 * @author conradi
 *
 */
public class AddSubmodelToAAS {

	/**
	 * Adds a Submodel to an AAS
	 * 
	 * @param submodel the Submodel to be added
	 * @param aas the AAS the Submodel should be added to
	 */
	public static void addSubmodelToAAS(SubModel submodel, IAssetAdministrationShell aas) {
		
		// Add the submodel to the AAS
		// If the given AAS is a ConnectedAssetAdministrationShell
		// the Submodel will be uploaded to the server the AAS is hosted on
		// The Submodel will NOT be registered. The RegisterSubmodel snippet can be used for that.
		aas.addSubModel(submodel);
	}
	
}
