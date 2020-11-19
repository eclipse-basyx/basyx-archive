package org.eclipse.basyx.support.bundle;

import java.util.Collection;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;


/**
 * This class can be used to check if all required resources are present on a server<br>
 * (e.g. after a restart) and upload them if necessary.
 * 
 * @author conradi
 *
 */
public class AASBundleIntegrator {

	/**
	 * Checks (by ID) if all AASs/SMs contained<br>
	 * in the given AASBundles exist in the AASAggregator.<br>
	 * Adds missing ones to the Aggregator.<br>
	 * If a given object already exists in the Aggregator it will NOT be replaced.
	 * 
	 * @param aggregator the Aggregator to be populated
	 * @param bundles the AASBundles
	 * @return true if an AAS/SM was uploaded; false otherwise
	 */
	public static boolean integrate(IAASAggregator aggregator, Collection<AASBundle> bundles) {
		
		if(aggregator == null || bundles == null) {
			throw new RuntimeException("'aggregator' and 'bundles' must not be null.");
		}
		
		boolean objectUploaded = false;
		
		for(AASBundle bundle: bundles) {
			IAssetAdministrationShell aas = bundle.getAAS();
			
			try {				
				aggregator.getAAS(aas.getIdentification());
				// If no ResourceNotFoundException occurs, AAS exists on server
				// -> no further action required
			} catch(ResourceNotFoundException e) {
				// AAS does not exist and needs to be pushed to the server
				// Cast Interface to concrete class
				if(aas instanceof AssetAdministrationShell) {
					aggregator.createAAS((AssetAdministrationShell) aas);
					objectUploaded = true;
				} else {
					throw new RuntimeException("aas Objects in bundles need to be instance of 'AssetAdministrationShell'");
				}
			}
			
			IModelProvider provider = aggregator.getAASProvider(aas.getIdentification());
			for (ISubModel sm : bundle.getSubmodels()) {
				try {
					provider.getModelPropertyValue("/aas/submodels/" + sm.getIdShort());
					// If no ResourceNotFoundException occurs, SM exists on server
					// -> no further action required
				} catch (ResourceNotFoundException e) {
					// AAS does not exist and needs to be pushed to the server
					// Check if ISubModel is a concrete SubModel
					if (sm instanceof SubModel) {
						provider.setModelPropertyValue("/aas/submodels/" + sm.getIdShort(), sm);
						objectUploaded = true;
					} else {
						throw new RuntimeException("sm Objects in bundles need to be instance of 'SubModel'");
					}
				}
			}
		}
		return objectUploaded;
	}
	
}