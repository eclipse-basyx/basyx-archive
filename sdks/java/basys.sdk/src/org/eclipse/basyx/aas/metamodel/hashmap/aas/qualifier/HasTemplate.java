package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;
import java.util.HashSet;



/**
 * HasTemplate class 
 * 
 * @author kuhn
 *
 */
public class HasTemplate extends HashMap<String, Object> {
	
	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Constructor
	 */
	public HasTemplate() {
		// Default values
		
		/**
		 * Identificators of the templates used by the element
		 */
		put("id_hasTemplate", new HashSet<Identification>());
	}
}
