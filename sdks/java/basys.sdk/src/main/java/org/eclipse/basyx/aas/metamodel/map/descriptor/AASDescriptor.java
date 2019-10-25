package org.eclipse.basyx.aas.metamodel.map.descriptor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.IdentifiableFacade;


/**
 * AAS descriptor class
 * 
 * @author kuhn, pschorn, espen
 *
 */
public class AASDescriptor extends ModelDescriptor {
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create descriptor from existing hash map
	 */
	public AASDescriptor(Map<String, Object> map) {
		super(map);
	}
	
	/**
	 * Create a new aas descriptor with minimal information based on an existing AAS
	 */
	public AASDescriptor(AssetAdministrationShell aas) {
		// Create descriptor with minimal information (id and idShort)
		this(aas.getIdShort(), aas.getIdentification(), "");
		
		// Overwrite endpoints
		put(AssetAdministrationShell.ENDPOINTS, aas.getEndpoints());

		// Overwrite submodel descriptors
		Set<SubmodelDescriptor> smDescriptors = aas.getSubmodelDescriptors();
		put(AssetAdministrationShell.SUBMODELS, smDescriptors);
	}
	
	/**
	 * Create a new descriptor with minimal information
	 */
	public AASDescriptor(String idShort, IIdentifier id, String httpEndpoint) {
		super(idShort, id, httpEndpoint);

		// Set Submodels
		put(AssetAdministrationShell.SUBMODELS, new HashSet<SubmodelDescriptor>());
	}
	
	/**
	 * Create a new descriptor with minimal information (idShort is assumed to be set to "")
	 */
	public AASDescriptor(IIdentifier id, String httpEndpoint) {
		this("", id, httpEndpoint);
	}
	
	/**
	 * Add a sub model descriptor
	 */
	@SuppressWarnings("unchecked")
	public AASDescriptor addSubmodelDescriptor(SubmodelDescriptor desc) {
		// Sub model descriptors are stored in a list
		Collection<Map<String, Object>> submodelDescriptors = (Collection<Map<String, Object>>) get(AssetAdministrationShell.SUBMODELS);
		
		// Add new sub model descriptor to list
		submodelDescriptors.add(desc);

		// Enable method chaining
		return this;
	}

	/**
	 * Get a specific sub model descriptor
	 */
	@SuppressWarnings("unchecked")
	public SubmodelDescriptor getSubModelDescriptor(String subModelId) {
		// Sub model descriptors are stored in a list
		Collection<Map<String, Object>> smDescriptorMaps = (Collection<Map<String, Object>>) get(
				AssetAdministrationShell.SUBMODELS);

		// Go through all descriptors (as maps) and find the one with the subModelId
		for (Map<String, Object> smDescriptorMap : smDescriptorMaps) {
			// Use a facade to access the identifier
			IIdentifier id = new IdentifiableFacade(smDescriptorMap).getIdentification();
			if (id.getId().equals(subModelId)) {
				return new SubmodelDescriptor(smDescriptorMap);
			}
		}
		
		// No descriptor found
		return null;
	}

	/**
	 * Get a specific sub model descriptor from a ModelUrn
	 */
	public SubmodelDescriptor getSubModelDescriptor(ModelUrn submodelUrn) {
		return getSubModelDescriptor(submodelUrn.getURN());
	}
}

