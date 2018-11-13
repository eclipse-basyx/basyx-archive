package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;



/**
 * Qualifiable class 
 * 
 * @author kuhn
 *
 */
public class Qualifiable extends HashMap<String, Object> {
	
	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Constructor
	 */
	public Qualifiable() {
		// Default values
		
		/**
		 * Identificators of the templates used by the element
		 */
		put("qualifier", null);
	}
}

