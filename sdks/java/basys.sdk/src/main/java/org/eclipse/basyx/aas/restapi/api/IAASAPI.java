package org.eclipse.basyx.aas.restapi.api;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * Specifies overall AAS API
 * 
 * @author schnicke
 *
 */
public interface IAASAPI {
	/**
	 * 
	 * @return the AAS
	 */
	public IAssetAdministrationShell getAAS();

	/**
	 * Adds a submodel reference to the AAS
	 * 
	 * @param submodel
	 *            the reference of the submodel to-be-added
	 */
	public void addSubmodel(IReference submodel);

	/**
	 * Removes a submodel reference from the AAS
	 * 
	 * @param id
	 *            the unique id of the submodel to-be-deleted
	 */
	public void removeSubmodel(String id);
}

