package org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier;

import java.util.HashMap;

/**
 * Identification class
 * 
 * @author kuhn, schnicke
 *
 */
public class Identifier extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Identifier() {
		// Default values
		put("idType", IdentifierType.IRDI);
		put("id", "");
	}

	/**
	 * Constructor that accepts parameter
	 */
	public Identifier(String idType, String id) {
		// Load values
		put("idType", idType);
		put("id", id);
	}

	/**
	 * Get value of 'idType' property
	 */
	public IdentifierType getIdType() {
		return (IdentifierType) get("idType");
	}

	/**
	 * Update value of 'idType' property
	 */
	public void setIdType(IdentifierType newValue) {
		put("idType", newValue);
	}

	/**
	 * Get value of 'id' property
	 */
	public String getId() {
		return (String) get("id");
	}

	/**
	 * Update value of 'id' property
	 */
	public void setId(String newValue) {
		put("id", newValue);
	}
}
