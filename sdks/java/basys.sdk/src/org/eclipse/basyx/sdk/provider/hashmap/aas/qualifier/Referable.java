package org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier;

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
		// Identifies an element within its name space (String)
		put("idShort", ""); 
		// Coded value that gives further meta information w.r.t. to the type of the element. It affects the
		// expected existence of attributes and the	applicability of constraints. (String)
		put("category", "");
		// Description or comments on the element (String)
		put("description", "");
		// Reference to the parent of this element (Referable)
		put("parent", null);
	}
	
	
	/**
	 * Constructor with idShort, category and description
	 */
	public Referable(String idShort, String category, String description) {
		// Identifies an element within its name space (String)
		put("idShort", idShort); 
		// Coded value that gives further meta information w.r.t. to the type of the element. It affects the
		// expected existence of attributes and the	applicability of constraints. (String)
		put("category", category);
		// Description or comments on the element (String)
		put("description", description);
		// Reference to the parent of this element (Referable)
		put("parent", null);
	}	
}
