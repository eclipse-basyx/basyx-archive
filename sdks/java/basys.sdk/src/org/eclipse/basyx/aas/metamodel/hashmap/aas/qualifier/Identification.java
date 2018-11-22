package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;



/**
 * Identification class 
 * 
 * @author kuhn
 *
 */
public class Identification extends HashMap<String, Object> {
	
	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Identification IRDI constant
	 */
	public static final int IRDI = 0;

	/**
	 * Identification URI constant
	 */
	public static final int URI = 1;

	/**
	 * Identification Internal constant
	 */
	public static final int Internal = 2;

	
	
	/**
	 * Constructor
	 */
	public Identification() {
		// Default values
		put("idType", IRDI);
		put("id",     "");
	}
	
	
	/**
	 * Constructor that accepts parameter
	 */
	public Identification(int idType, String id) {
		// Load values
		put("idType", idType);
		put("id",     id);		
	}
	
	
	
	
	/**
	 * Get value of 'idType' property
	 */
	public int getIdType() {
		return (int) get("idType");
	}

	/**
	 * Update value of 'idType' property
	 */
	public void setIdType(int newValue) {
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
