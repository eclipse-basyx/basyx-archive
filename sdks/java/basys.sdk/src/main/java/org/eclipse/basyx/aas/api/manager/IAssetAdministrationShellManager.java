package org.eclipse.basyx.aas.api.manager;

import java.util.Collection;

import org.eclipse.basyx.aas.api.metamodel.aas.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.metamodel.aas.ISubModel;
import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;



/**
 * Technology independent interface to technology specific Asset Administration Shell (AAS) manager class. 
 * 
 * @author schoeffler, ziesche, kuhn
 *
 */
public interface IAssetAdministrationShellManager {
	/**
	 * Retrieve an AAS based on its ID
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IAssetAdministrationShell retrieveAAS(ModelUrn aasUrn) throws Exception;
	
	
	/**
	 * Retrieve all local AAS from the technology layer
	 * 
	 * @return
	 */
	public Collection<IAssetAdministrationShell> retrieveAASAll();
	
	/**
	 * Creates an AAS on a remote server. Assumes that the urn is already registered
	 * in the directory
	 * 
	 * @param urn
	 */
	void createAAS(AssetAdministrationShell aas, ModelUrn urn);
	
	/**
	 * Unlink an AAS from the system
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteAAS(String id) throws Exception;

	/**
	 * Retrieves a submodel
	 * 
	 * @param aasUrn
	 * @param subModelID
	 * @return
	 */
	ISubModel retrieveSubModel(ModelUrn aasUrn, String subModelId);

	/**
	 * Creates a submodel on a remote server. Assumes that the urn is already
	 * registered in the directory
	 * 
	 * @param urn
	 */
	void createSubModel(ModelUrn aasUrn, String subModelId, SubModel submodel);
}
