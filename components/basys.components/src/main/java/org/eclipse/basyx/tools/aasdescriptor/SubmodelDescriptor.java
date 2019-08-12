package org.eclipse.basyx.tools.aasdescriptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.Kind;




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
		put("identification", new Identifier());
		put("metaData", new HashMap<String, Object>());
		put("administration", new AdministrativeInformation());
		put("idShort", new String(""));
		put("category", new String(""));
		put("descriptions", new LinkedList<Description>());		
		put("semanticId", new Identifier());
		put("kind", Kind.Instance);
		put("endpoints", new LinkedList<String>());
	}
	
	
	/**
	 * Create a new sub model descriptor with minimal information
	 */
	@SuppressWarnings("unchecked")
	public SubmodelDescriptor(String id, String idType, String endpoint) {
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
	public SubmodelDescriptor(SubModel submodel, String endpoint, String endpointType) {
		// Invoke default constructor
		//this();
		
		put("idShort", submodel.getId());
		
		// Add identification and end point information
		Identifier identifier = new Identifier();
		identifier.setIdType(submodel.getIdentification().getIdType());
		identifier.setId(submodel.getIdentification().getId());
		put("identification", identifier);
		
		HashMap<String, String> endpointWrapper = new HashMap<String, String>(); 
		endpointWrapper.put("type", endpointType);
		endpointWrapper.put("address", endpoint);
		
		put("endpoints", Arrays.asList(endpointWrapper));
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
		return new Identifier((Map<String, Object>) get("identification")).getId();
	}
	
	
	/**
	 * Return sub model ID type
	 */
	@SuppressWarnings("unchecked")
	public String getIdType() {
		return new Identifier((Map<String, Object>) get("identification")).getIdType();
	}

	
	/**
	 * Return first sub model end point
	 */
	@SuppressWarnings("unchecked")
	public String getFirstEndpoint() {
		return ((List<String>) get("endpoints")).get(0);
	}
}

