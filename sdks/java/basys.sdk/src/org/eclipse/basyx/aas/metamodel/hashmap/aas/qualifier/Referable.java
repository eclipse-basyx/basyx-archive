package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;



/**
 * Referable class 
 * 
 * @author kuhn
 *
 */
public class Referable extends HashMap<String, Object> {
	
	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Constructor
	 */
	public Referable() {
		// Default values
		put("idShort", ""); 
		put("category", "");
		put("description", "");
		put("parent", null);
	}
}
