package org.eclipse.basyx.sdk.provider.hashmap.aas;


import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.*;



/**
 * Submodel class
 * 
 * @author kuhn
 *
 */
public class Submodel extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Submodel properties
	 */
	protected Map<String, Object> properties = new HashMap<String, Object>();
	
	
	/**
	 * Submodel operations
	 */
	protected Map<String, Object> operations = new HashMap<String, Object>();
	
	
	
	
	/**
	 * Default constructor
	 */
	public Submodel() {
		// Add qualifiers
		putAll(new HasSemantics());
		putAll(new HasTemplate());
		putAll(new Identifiable());
		putAll(new Qualifiable());
		putAll(new Typable());

		// Default values
		put("id_carrier",            null);
		put("id_submodelDefinition", null);
		
		// Properties
		put("properties", properties);
		put("operations", operations);
	}
	
	
	
	/**
	 * Constructor
	 */
	public Submodel(HasSemantics semantics, Identifiable identifiable, Qualifiable qualifiable, Typable typeable) {
		// Add qualifiers
		putAll(semantics);
		putAll(new HasTemplate());
		putAll(identifiable);
		putAll(qualifiable);
		putAll(typeable);

		// Default values
		put("id_carrier",            null);
		put("id_submodelDefinition", null);
		
		// Properties
		put("properties", properties);
		put("operations", operations);
	}

	
	
	
	/**
	 * Get submodel properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	
	/**
	 * Get submodel operations
	 */
	public Map<String, Object> getOperations() {
		return operations;
	}
}
