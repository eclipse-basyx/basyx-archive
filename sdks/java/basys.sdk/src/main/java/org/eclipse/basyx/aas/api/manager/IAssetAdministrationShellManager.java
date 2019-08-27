package org.eclipse.basyx.aas.api.manager;

import java.util.Collection;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;



/**
 * Technology independent interface to technology specific Asset Administration Shell (AAS) manager class. 
 * 
 * @author schoeffler, ziesche, kuhn
 *
 */
public interface IAssetAdministrationShellManager {
	
	/**
	 * Create and register an asset administration shell with the technology layer
	 * 
	 * @param aas
	 * @return
	 * @throws Exception
	 */
	public IAssetAdministrationShell createAAS(IAssetAdministrationShell aas) throws Exception;
	
	
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
	 * Unlink an AAS from the system
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteAAS(String id) throws Exception;
}
