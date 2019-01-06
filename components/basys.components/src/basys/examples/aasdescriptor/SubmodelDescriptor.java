package basys.examples.aasdescriptor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.Kind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identification;



/**
 * AAS descriptor class
 * 
 * @author kuhn
 *
 */
public class SubmodelDescriptor extends HashMap<String, Object> {

		
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Default constructor
	 */
	public SubmodelDescriptor() {
		// Add members
		put("identification", new Identification());
		put("metaData", new HashMap<String, Object>());
		put("administration", new AdministrativeInformation());
		put("idShort", new String(""));
		put("category", new String(""));
		put("descriptions", new LinkedList<Description>());		
		put("semanticId", new Identification());
		put("kind", Kind.Instance);
		put("endpoints", new LinkedList<String>());
	}
	
	
	/**
	 * Create a new sub model descriptor with minimal information
	 */
	@SuppressWarnings("unchecked")
	public SubmodelDescriptor(String id, int idType, String endpoint) {
		// Invoke default constructor
		this();
		
		// Add identification and end point information
		((Identification) get("identification")).setIdType(idType);
		((Identification) get("identification")).setId(id);
		((List<String>) get("endpoints")).add(endpoint);
	}
	
	
	/**
	 * Create sub model descriptor from existing hash map
	 */
	public SubmodelDescriptor(Map<String, Object> map) {
		// Put all elements from map into this descriptor
		this.putAll(map);
	}
	
	
	/**
	 * Return sub model ID
	 */
	@SuppressWarnings("unchecked")
	public String getId() {
		return new Identification((Map<String, Object>) get("identification")).getId();
	}
	
	
	/**
	 * Return sub model ID type
	 */
	@SuppressWarnings("unchecked")
	public int getIdType() {
		return new Identification((Map<String, Object>) get("identification")).getIdType();
	}

	
	/**
	 * Return first sub model end point
	 */
	@SuppressWarnings("unchecked")
	public String getFirstEndpoint() {
		return ((List<String>) get("endpoints")).get(0);
	}
}

