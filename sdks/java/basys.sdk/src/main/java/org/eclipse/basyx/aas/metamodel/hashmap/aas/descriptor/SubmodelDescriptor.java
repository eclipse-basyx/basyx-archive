package org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;




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
	 * Create a new sub model descriptor with minimal information
	 */
	public SubmodelDescriptor(SubModel submodel, String endpoint, String endpointType) {
		// Invoke default constructor
		//this();
		
		put(Referable.IDSHORT, submodel.getId());
		
		// Add identification and end point information
		Identifier identifier = new Identifier();
		identifier.setIdType(submodel.getIdentification().getIdType());
		identifier.setId(submodel.getIdentification().getId());
		put(Identifiable.IDENTIFICATION, identifier);
		
		HashMap<String, String> endpointWrapper = new HashMap<String, String>(); 
		endpointWrapper.put(AssetAdministrationShell.TYPE, endpointType);
		endpointWrapper.put(AssetAdministrationShell.ADDRESS, endpoint);
		
		put(AssetAdministrationShell.ENDPOINTS, Arrays.asList(endpointWrapper));
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
	public String getId() {
		return (String) get(Referable.IDSHORT);
	}
	
	/**
	 * Return sub model identification ID
	 */
	@SuppressWarnings("unchecked")
	public String getIdentificationId() {
		return new Identifier((Map<String, Object>) get(Identifiable.IDENTIFICATION)).getId();
	}
	
	
	/**
	 * Return sub model ID type
	 */
	@SuppressWarnings("unchecked")
	public String getIdType() {
		return new Identifier((Map<String, Object>) get(Identifiable.IDENTIFICATION)).getIdType();
	}

	
	/**
	 * Return first sub model end point
	 */
	@SuppressWarnings("unchecked")
	public String getFirstEndpoint() {
		return ((List<String>) get(AssetAdministrationShell.ENDPOINTS)).get(0);
	}
}

