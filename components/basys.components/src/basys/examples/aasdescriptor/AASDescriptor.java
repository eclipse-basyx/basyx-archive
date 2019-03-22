package basys.examples.aasdescriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.Asset;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;



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
		return ((List<String>) get("endpoints")).get(0);
	}
	
	
	/**
	 * Add a sub model descriptor
	 */
	@SuppressWarnings("unchecked")
	public void addSubmodelDescriptor(SubmodelDescriptor desc) {
		// Sub model descriptors are stored in a list
		Collection<Map<String, Object>> submodelDescriptors = (Collection<Map<String, Object>>) get("submodels");
		
		// Add new sub model descriptor to list
		submodelDescriptors.add(desc);
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

