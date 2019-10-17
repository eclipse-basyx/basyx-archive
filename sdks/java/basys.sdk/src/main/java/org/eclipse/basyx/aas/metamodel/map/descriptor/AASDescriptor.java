package org.eclipse.basyx.aas.metamodel.map.descriptor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.facade.identifier.IdentifierFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.IdentifiableFacade;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;


/**
 * AAS descriptor class
 * 
 * @author kuhn, pschorn, espen
 *
 */
public class AASDescriptor extends HashMap<String, Object> {
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create AAS descriptor from existing hash map
	 */
	public AASDescriptor(Map<String, Object> map) {
		// Put all elements from map into this descriptor
		this.putAll(map);
	}

	/**
	 * Create a new aas descriptor with minimal information
	 */
	public AASDescriptor(String idShort, IIdentifier id, String httpEndpoint) {
		// Set idShort
		put(Referable.IDSHORT, idShort);

		// Set Identifier, make sure the stored data structure is a map
		Identifier identifierMap = new Identifier(id.getIdType(), id.getId());
		put(Identifiable.IDENTIFICATION, identifierMap);

		// Set Endpoints
		HashMap<String, String> endpointWrapper = new HashMap<String, String>();
		endpointWrapper.put(AssetAdministrationShell.TYPE, "http");
		endpointWrapper.put(AssetAdministrationShell.ADDRESS, httpEndpoint);
		put(AssetAdministrationShell.ENDPOINTS, Arrays.asList(endpointWrapper));

		// Set Submodels
		put(AssetAdministrationShell.SUBMODELS, new HashSet<SubmodelDescriptor>());
	}

	/**
	 * Create a new aas descriptor with minimal information (idShort is assumed to be set to "")
	 */
	public AASDescriptor(IIdentifier id, String httpEndpoint) {
		this("", id, httpEndpoint);
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
	 * Return AAS ID
	 */
	@SuppressWarnings("unchecked")
	public IIdentifier getIdentifier() {
		Map<String, Object> identifierModel = (Map<String, Object>) get(Identifiable.IDENTIFICATION);
		return new IdentifierFacade(identifierModel);
	}
	
	/**
	 * Return first AAS end point
	 */
	@SuppressWarnings("unchecked")
	public String getFirstEndpoint() {
		Object e = get(AssetAdministrationShell.ENDPOINTS);
		// Extract String from endpoint for set or list representations of the endpoint wrappers
		String endpoint = null;
		if (e instanceof Collection<?>) {
			Collection<Map<?, ?>> endpoints = (Collection<Map<?, ?>>) e;
			if (endpoints.isEmpty()) {
				return null;
			} else {
				// assume the endpoint is wrapped in a map with address and address type
				// return the first endpoint address
				return (String) endpoints.iterator().next().get(AssetAdministrationShell.ADDRESS);
			}
		}
		return endpoint;
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

