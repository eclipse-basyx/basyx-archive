package org.eclipse.basyx.examples.snippets.aas;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;


/**
 * This snippet showcases how to retrieve a Submodel from an AAS
 * 
 * @author conradi
 *
 */
public class RetrieveSubmodelFromAAS {

	/**
	 * Gets a Submodel from an AAS
	 * 
	 * @param smIdentifier the Identifier of the requested Submodel
	 * @return the requested Submodel
	 */
	public static ISubModel retrieveSubmodelFromAAS(IIdentifier smIdentifier, IAssetAdministrationShell aas) {
		// Get the requested Submodel from the AAS
		ISubModel submodel = aas.getSubmodel(smIdentifier);
		return submodel;
	}
	
}
