package org.eclipse.basyx.aas.metamodel.map.descriptor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;

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
		Set<SubmodelDescriptor> smDescriptors = aas.getSubModelDescriptors();
		put(AssetAdministrationShell.SUBMODELS, smDescriptors);
	}
	
	/**
	 * Create a new aas descriptor that retrieves the necessary information from a
	 * passend AssetAdministrationShell
	 * 
	 * @param aas
	 * @param endpoint
	 */
	public AASDescriptor(AssetAdministrationShell aas, String endpoint) {
		this(aas.getIdShort(), aas.getIdentification(), endpoint);

		// Overwrite submodel descriptors
		Set<SubmodelDescriptor> smDescriptors = aas.getSubModelDescriptors();
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

	@SuppressWarnings("unchecked")
	public void removeSubmodelDescriptor(String idShort) {
		Optional<SubmodelDescriptor> toRemove = getSubModelDescriptors().stream().filter(x -> x.getIdShort().equals(idShort)).findAny();

		// TODO: Exception in else case
		if (toRemove.isPresent()) {
			// Don't use getSubmodelDescriptors here since it returns a copy
			((Set<Object>) get(AssetAdministrationShell.SUBMODELS)).remove(toRemove.get());
		}
	}

	/**
	 * Retrieves a submodel descriptor based on the globally unique id of the
	 * submodel
	 * 
	 * @param subModelId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SubmodelDescriptor getSubModelDescriptorFromIdentifierId(String subModelId) {
		// Sub model descriptors are stored in a list
		Collection<Map<String, Object>> smDescriptorMaps = (Collection<Map<String, Object>>) get(
				AssetAdministrationShell.SUBMODELS);

		// Go through all descriptors (as maps) and find the one with the subModelId
		for (Map<String, Object> smDescriptorMap : smDescriptorMaps) {
			// Use a facade to access the identifier
			IIdentifier id = Identifiable.createAsFacade(smDescriptorMap).getIdentification();
			if (id.getId().equals(subModelId)) {
				return new SubmodelDescriptor(smDescriptorMap);
			}
		}
		
		// No descriptor found
		return null;
	}

	/**
	 * Retrieves a submodel descriptor based on the idShort of the submodel
	 * 
	 * @param idShort
	 * @return
	 */
	public SubmodelDescriptor getSubmodelDescriptorFromIdShort(String idShort) {
		return getSubModelDescriptors().stream().filter(x -> x.getIdShort().equals(idShort)).findAny().orElse(null); // TODO: Exception
	}

	/**
	 * Get a specific sub model descriptor from a ModelUrn
	 */
	public SubmodelDescriptor getSubModelDescriptor(ModelUrn submodelUrn) {
		return getSubModelDescriptorFromIdentifierId(submodelUrn.getURN());
	}

	/**
	 * Retrieves all submodel descriptors of the aas described by this descriptor
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Set<SubmodelDescriptor> getSubModelDescriptors() {
		Collection<Map<String, Object>> descriptors = (Collection<Map<String, Object>>) get(AssetAdministrationShell.SUBMODELS);
		return descriptors.stream().map(m -> new SubmodelDescriptor(m)).collect(Collectors.toSet());
	}
}

