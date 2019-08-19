package org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;


/**
 * AAS descriptor class
 * 
 * @author kuhn, pschorn
 *
 */
public class AASDescriptor extends HashMap<String, Object> {

		
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Create a new sub model descriptor with minimal information
	 */
	public AASDescriptor(AssetAdministrationShell aas, String endpoint, String endpointType) {
		// Invoke default constructor
		
		
		put(Referable.IDSHORT, aas.getId());
		put(AssetAdministrationShell.SUBMODELS, new LinkedList<SubmodelDescriptor>());
		
		// Add identification and end point information
		Identifier identifier =  new Identifier();
		
		identifier.setIdType(aas.getIdentification().getIdType());
		identifier.setId(aas.getIdentification().getId());
		put(Identifiable.IDENTIFICATION, identifier);
		
		HashMap<String, String> endpointWrapper = new HashMap<String, String>(); 
		endpointWrapper.put(AssetAdministrationShell.TYPE, endpointType);
		endpointWrapper.put(AssetAdministrationShell.ADDRESS, endpoint + "/aas");
		
		put("endpoints", Arrays.asList(endpointWrapper));
	}
	
	/**
	 * Create a new sub model descriptor with minimal information
	 */
	public AASDescriptor(AssetAdministrationShell aas) {
		// Invoke default constructor
		
		
		put(Referable.IDSHORT, aas.getId());
		put(AssetAdministrationShell.SUBMODELS, new LinkedList<SubmodelDescriptor>());
		
		// Add identification and end point information
		Identifier identifier =  new Identifier();
		
		identifier.setIdType(aas.getIdentification().getIdType());
		identifier.setId(aas.getIdentification().getId());
		put(Identifiable.IDENTIFICATION, identifier);
		
		put(AssetAdministrationShell.ENDPOINTS, aas.getEndpoints());
	}
	
	
	
	/**
	 * Create AAS descriptor from existing hash map
	 */
	public AASDescriptor(Map<String, Object> map) {
		// Put all elements from map into this descriptor
		this.putAll(map);
	}
	
	
	
	
	/**
	 * Return AAS ID
	 */
	@SuppressWarnings("unchecked")
	public String getId() {
		return new Identifier((Map<String, Object>) get(Identifiable.IDENTIFICATION)).getId();
	}
	
	
	/**
	 * Return AAS ID type
	 */
	@SuppressWarnings("unchecked")
	public String getIdType() {
		return new Identifier((Map<String, Object>) get(Identifiable.IDENTIFICATION)).getIdType();
	}

	
	/**
	 * Return first AAS end point
	 */
	@SuppressWarnings("unchecked")
	public String getFirstEndpoint() {
		Object e = get(AssetAdministrationShell.ENDPOINTS);
		// Extract String from endpoint in set and list representation
		String endpoint = null;
		if (e instanceof List<?>) {
			List<String> list = (List<String>) e;
			if (list.size() == 0) {
				return null;
			} else {
				return list.get(0);
			}
		} else if (e instanceof HashSet<?>) {
			HashSet<Map<String, Object>> set = (HashSet<Map<String, Object>>) e;
			if (set.size() == 0) {
				return null;
			} else {
				return (String) set.iterator().next().get(AssetAdministrationShell.ADDRESS);
			}
		} else {
			endpoint = null;
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
		
		// Return 'this' reference
		return this;
	}
	
	
	
	
	/**
	 * Get a specific sub model descriptor
	 */
	@SuppressWarnings("unchecked")
	public SubmodelDescriptor getSubModelDescriptor(String subModelId) {
		// Sub model descriptors are stored in a list
		Collection<Map<String, Object>> submodelDescriptorMaps = (Collection<Map<String, Object>>) get(AssetAdministrationShell.SUBMODELS);

		System.out.println("Checking submodel desc");

		// Create sub model descriptors from contained maps
		// - We cannot guarantee here that these are really SubmodelDescriptors already and therefore need to default to maps
		Collection<SubmodelDescriptor>  submodelDescriptors    = new LinkedList<>();
		// - Fill sub model descriptors
		for (Map<String, Object> currentMap: submodelDescriptorMaps) submodelDescriptors.add(new SubmodelDescriptor(currentMap)); 
		
		// Look for descriptor
		for (SubmodelDescriptor desc: submodelDescriptors) {
			System.out.println("Checking: "+desc.getId());		
			
			if (desc.getId().equals(subModelId)) return desc;
		}
		
		// No Descritor found
		return null;
	}



	public String getAASId() {
		return (String) get(Referable.IDSHORT);
	}
}

