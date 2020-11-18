package org.eclipse.basyx.examples.snippets.submodel;

import org.eclipse.basyx.examples.snippets.manager.RetrieveSubmodelFromAAS;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;

/**
 * This snippet showcases how to retrieve a SubmodelElement from a remote Submodel
 * 
 * @author conradi
 *
 */
public class RetrieveSubmodelElement {

	/**
	 * Gets a SubmodelElement from a remote Submodel
	 * 
	 * @param elementId the idShort SubmodelElement to be retrieved
	 * @param smIdentifier the Identifier of the Submodel the element belongs to
	 * @param aasIdentifier the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL the URL of the registry server
	 * @return the requested SubmodelElement
	 */
	public static ISubmodelElement retrieveSubmodelElement(String elementId, IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) {

		// Get the ConnectedSubmodel
		ISubModel submodel = RetrieveSubmodelFromAAS.retrieveSubmodelFromAAS(smIdentifier, aasIdentifier, registryServerURL);
		
		// Add the element to it
		return submodel.getSubmodelElement(elementId);
		
	}
}