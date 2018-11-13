package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;



/**
 * Typable class 
 * 
 * @author kuhn
 *
 */
public class Typable extends HashMap<String, Object> {
	
	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Type kind
	 */
	public static final int KIND_TYPE = 0;

	/**
	 * Instance kind
	 */
	public static final int KIND_INSTANCE = 1;

	
	
	/**
	 * Constructor
	 */
	public Typable() {	
		/**
		 * Identificators of the templates used by the element
		 */
		put("kind", KIND_INSTANCE);
	}
}
