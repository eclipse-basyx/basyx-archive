package org.eclipse.basyx.tools.aasdescriptor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.Asset;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.tools.modelurn.ModelUrn;



/**
 * AAS descriptor class
 * 
 * @author kuhn
 *
 */
public class AASDescriptor extends HashMap<String, Object> {

		
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Default constructor
	 */
	public AASDescriptor() {
		// Add members
		put("identification", new Identifier());
		put("metaData", new HashMap<String, Object>());
		put("administration", new AdministrativeInformation());
		put("idShort", new String(""));
		put("category", new String(""));
		put("descriptions", new LinkedList<Description>());
		put("asset", new Asset());
		put("submodels", new LinkedList<SubmodelDescriptor>());
		put("endpoints", new LinkedList<String>());
	}
	
	
	/**
	 * Create a new AAS descriptor with minimal information
	 */
	@SuppressWarnings("unchecked")
	public AASDescriptor(String id, String idType, String endpoint) {
		// Invoke default constructor
		this();
		
		// Add identification and end point information
		((Identifier) get("identification")).setIdType(idType);
		((Identifier) get("identification")).setId(id);
		((List<String>) get("endpoints")).add(endpoint);
	}
	
	/**
	 * Create a new sub model descriptor with minimal information
	 */
	public AASDescriptor(AssetAdministrationShell aas, String endpoint, String endpointType) {
		// Invoke default constructor
		
		
		put("idShort", aas.getId());
		put("submodels", new LinkedList<SubmodelDescriptor>());
		
		// Add identification and end point information
		Identifier identifier =  new Identifier();
		
		identifier.setIdType(aas.getIdentification().getIdType());
		identifier.setId(aas.getIdentification().getId());
		put("identification", identifier);
		
		HashMap<String, String> endpointWrapper = new HashMap<String, String>(); 
		endpointWrapper.put("type", endpointType);
		endpointWrapper.put("address", endpoint + "/aas");
		
		put("endpoints", Arrays.asList(endpointWrapper));
	}
	
	public String getAASId() {
		return (String) get("idShort");
	}
	

	
	/**
	 * Create a new AAS descriptor with minimal information
	 */
	public AASDescriptor(ModelUrn urn, String aasSrvURL) {
		// Invoke default constructor
		this(urn.getURN(), IdentifierType.URI, aasSrvURL+"/aas/submodels/aasRepository/"+urn.getEncodedURN());
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
		return new Identifier((Map<String, Object>) get("identification")).getId();
	}
	
	
	/**
	 * Return AAS ID type
	 */
	@SuppressWarnings("unchecked")
	public String getIdType() {
		return new Identifier((Map<String, Object>) get("identification")).getIdType();
	}

	
	/**
	 * Return first AAS end point
	 */
	@SuppressWarnings("unchecked")
	public String getFirstEndpoint() {
		Object e = get("endpoints");
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
				return (String) set.iterator().next().get("address");
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
		Collection<Map<String, Object>> submodelDescriptors = (Collection<Map<String, Object>>) get("submodels");
		
		// Add new sub model descriptor to list
		submodelDescriptors.add(desc);
		
		// Return 'this' reference
		return this;
	}
	
	
	/**
	 * Add a sub model descriptor - simplified operation with default fields
	 * 
	 * @param urn URN of sub model
	 */
	public AASDescriptor addSubmodelDescriptor(ModelUrn urn, String aasSrvURL) {
		// Add sub model descriptor
		addSubmodelDescriptor(new SubmodelDescriptor(urn.getURN(), IdentifierType.URI, aasSrvURL+"/aas/submodels/aasRepository/"+urn.getEncodedURN()));
		
		// Return 'this' reference
		return this;
	}
	
	
	
	/**
	 * Get a specific sub model descriptor
	 */
	public SubmodelDescriptor getSubModelDescriptor(ModelUrn subModelId) {
		return getSubModelDescriptor(subModelId.getURN());
	}
	
	
	/**
	 * Get a specific sub model descriptor
	 */
	@SuppressWarnings("unchecked")
	public SubmodelDescriptor getSubModelDescriptor(String subModelId) {
		// Sub model descriptors are stored in a list
		Collection<Map<String, Object>> submodelDescriptorMaps = (Collection<Map<String, Object>>) get("submodels");

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
}

