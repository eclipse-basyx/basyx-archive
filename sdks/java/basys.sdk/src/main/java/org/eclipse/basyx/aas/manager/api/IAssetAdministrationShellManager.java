package org.eclipse.basyx.aas.manager.api;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;



/**
 * Technology independent interface to technology specific Asset Administration Shell (AAS) manager class. 
 * 
 * @author schoeffler, ziesche, kuhn
 *
 */
public interface IAssetAdministrationShellManager {
	/**
	 * Retrieve an AAS based on its ID
	 */
	public IAssetAdministrationShell retrieveAAS(IIdentifier aasId) throws Exception;
	
	/**
	 * Retrieve all local AAS from the technology layer
	 */
	public Collection<IAssetAdministrationShell> retrieveAASAll();
	
	/**
	 * Creates an AAS on a remote server.
	 */
	@Deprecated
	void createAAS(AssetAdministrationShell aas, IIdentifier aasId, String endpoint);
	
	/**
	 * Creates an AAS on a remote server
	 * 
	 * @param aas
	 * @param aasId
	 * @param endpoint
	 */
	void createAAS(AssetAdministrationShell aas, String endpoint);

	/**
	 * Unlink an AAS from the system
	 */
	void deleteAAS(IIdentifier id) throws Exception;

	/**
	 * Retrieves a submodel
	 */
	ISubModel retrieveSubModel(IIdentifier aasId, IIdentifier subModelId);

	/**
	 * Creates a submodel on a remote server. Assumes that the AAS is already
	 * registered in the directory
	 */
	void createSubModel(IIdentifier aasId, SubModel submodel);

	/**
	 * Deletes a submodel on a remote server and removes its registry entry
	 * 
	 * @param aasId
	 * @param submodelId
	 */
	void deleteSubModel(IIdentifier aasId, IIdentifier submodelId);

	/**
	 * Retrieves all submodels in a specific AAS
	 */
	Map<String, ISubModel> retrieveSubmodels(IIdentifier aasId);
}
