package org.eclipse.basyx.aas.manager.api;

import java.util.Collection;

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
	 * Creates an AAS on a remote server. Assumes that the AAS is already registered
	 * in the directory
	 */
	void createAAS(AssetAdministrationShell aas, IIdentifier aasId);
	
	/**
	 * Unlink an AAS from the system
	 */
	void deleteAAS(String id) throws Exception;

	/**
	 * Retrieves a submodel
	 */
	ISubModel retrieveSubModel(IIdentifier aasId, IIdentifier subModelId);

	/**
	 * Creates a submodel on a remote server. Assumes that the AAS is already
	 * registered in the directory
	 */
	void createSubModel(IIdentifier aasId, SubModel submodel);
}
