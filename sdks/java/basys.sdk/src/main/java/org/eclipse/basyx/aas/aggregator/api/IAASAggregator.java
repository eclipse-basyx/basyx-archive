package org.eclipse.basyx.aas.aggregator.api;

import java.util.List;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;


/**
 * Interface for the Asset Administration Shell Aggregator API <br />
 * It is used to manage multiple AASs at the same endpoint
 * 
 * @author conradi
 *
 */
public interface IAASAggregator {
	
	/**
	 * Retrieves all Asset Administration Shells from the endpoint
	 * 
	 * @return a List of all found Asset Administration Shells
	 */
	public List<IAssetAdministrationShell> getAASList();
	
	/**
	 * Retrieves a specific Asset Administration Shell
	 * 
	 * @param aasId the ID of the AAS
	 * @return the requested AAS
	 */
	public IAssetAdministrationShell getAAS(IIdentifier aasId);
	
	/**
	 * Creates a new Asset Administration Shell at the endpoint
	 * 
	 * @param aas the AAS to be created
	 */
	public void createAAS(AssetAdministrationShell aas);
	
	/**
	 * Updates a specific Asset Administration Shell
	 * 
	 * @param aas the updated AAS
	 */
	public void updateAAS(AssetAdministrationShell aas);
	
	/**
	 * Deletes a specific Asset Administration Shell
	 * 
	 * @param aasId the ID of the AAS to be deleted
	 */
	public void deleteAAS(IIdentifier aasId);

}