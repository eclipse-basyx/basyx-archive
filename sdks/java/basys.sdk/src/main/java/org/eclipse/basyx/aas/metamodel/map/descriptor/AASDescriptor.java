package org.eclipse.basyx.aas.metamodel.map.descriptor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;

/**
 * AAS descriptor class
 * 
 * @author kuhn, pschorn, espen
 *
 */
public class AASDescriptor extends ModelDescriptor {
	public static final String MODELTYPE = "AASDescriptor";
	
	/**
	 * Create descriptor from existing hash map
	 */
	public AASDescriptor(Map<String, Object> map) {
		super(map);
	}

	protected AASDescriptor() {
		super();
	}

	/**
	 * Create a new aas descriptor that retrieves the necessary information from a
	 * passend AssetAdministrationShell
	 * 
	 * @param iAssetAdministrationShell
	 * @param endpoint
	 */
	public AASDescriptor(IAssetAdministrationShell assetAdministrationShell, String endpoint) {
		this(assetAdministrationShell.getIdShort(), assetAdministrationShell.getIdentification(), endpoint);

		// Overwrite submodel descriptors
		Collection<SubmodelDescriptor> smDescriptors = assetAdministrationShell.getSubModelDescriptors();
		put(AssetAdministrationShell.SUBMODELS, smDescriptors);
	}

	/**
	 * Create a new descriptor with minimal information
	 */
	public AASDescriptor(String idShort, IIdentifier id, String httpEndpoint) {
		super(idShort, id, httpEndpoint);

		// Set Submodels
		put(AssetAdministrationShell.SUBMODELS, new HashSet<SubmodelDescriptor>());
		
		// Add model type
		putAll(new ModelType(MODELTYPE));
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
			((Collection<Object>) get(AssetAdministrationShell.SUBMODELS)).remove(toRemove.get());
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
			IIdentifier id = Identifiable.createAsFacade(smDescriptorMap, KeyElements.SUBMODEL).getIdentification();
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
	public Collection<SubmodelDescriptor> getSubModelDescriptors() {
		Collection<Map<String, Object>> descriptors = (Collection<Map<String, Object>>) get(AssetAdministrationShell.SUBMODELS);
		return descriptors.stream().map(SubmodelDescriptor::new).collect(Collectors.toSet());
	}

	@Override
	protected String getModelType() {
		return MODELTYPE;
	}
}

