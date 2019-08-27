package org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor;

import java.util.HashMap;



/**
 * AAS description class
 * 
 * @author kuhn
 *
 */
public class Description extends HashMap<String, Object> {

		
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Constructor
	 */
	public Description() {
		// Add qualifiers
		put("language", "");
		put("text",     "");
	}
}
