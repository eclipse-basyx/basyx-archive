package org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier;

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
	 * Default constructor
	 */
	public HasSemantics() {
		// Default values

		// Identificator of the semantic definition of the element. It is called semantic id of the
		// element. Its type is "Identification"
		put("id_semantics",               null);
		put("hasFullSemanticDescription", new HashSet<SemanticDescription>());
	}


	/**
	 * Constructor
	 */
	public HasSemantics(Identification idSemantics) {
		// Default values

		// Identificator of the semantic definition of the element. It is called semantic id of the
		// element. Its type is "Identification"
		put("id_semantics",               idSemantics);
		put("hasFullSemanticDescription", new HashSet<SemanticDescription>());
	}
}
