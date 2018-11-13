package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;
import java.util.HashSet;




/**
 * HasSemantics class 
 * 
 * @author kuhn
 *
 */
public class HasSemantics extends HashMap<String, Object> {
	
	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Constructor
	 */
	public HasSemantics() {
		// Default values
		
		/**
		 * Identificators of the templates used by the element
		 */
		put("id_semantics",               null);
		put("hasFullSemanticDescription", new HashSet<SemanticDescription>());
	}
}
