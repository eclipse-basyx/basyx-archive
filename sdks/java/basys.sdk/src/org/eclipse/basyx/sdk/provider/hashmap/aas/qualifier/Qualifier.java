package org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier;

import java.util.HashMap;




/**
 * Qualifier class 
 * 
 * @author kuhn
 *
 */
public class Qualifier extends HashMap<String, Object> {
	
	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Constructor
	 */
	public Qualifier() {
		// Add all attributes from HasSemantics
		this.putAll(new HasSemantics());

		// Default values
		put("qualifierType",  "");
		put("qualifierValue", null);
	}
}
