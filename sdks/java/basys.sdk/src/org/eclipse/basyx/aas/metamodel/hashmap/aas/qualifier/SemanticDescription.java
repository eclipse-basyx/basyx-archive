package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;




/**
 * SemanticDescription class 
 * 
 * @author kuhn
 *
 */
public class SemanticDescription extends HashMap<String, Object> {


	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public SemanticDescription() {
		// Add HasTemplate, Identifiable, Packageable classes
		putAll(new HasTemplate());
		putAll(new Identifiable());
		putAll(new Packageable());

		// Default values
		put("semanticOf",                 null);
	}
}
