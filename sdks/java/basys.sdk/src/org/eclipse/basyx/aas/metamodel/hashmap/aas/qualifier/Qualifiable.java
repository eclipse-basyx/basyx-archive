package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;



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
		// The instance of an element may be further qualified by one or more qualifiers.
		put("qualifier", null);
	}


	/**
	 * Constructor
	 */
	public Qualifiable(String qualifier) {
		// Create collection with qualifiers
		Set<String> qualifiers = new HashSet<String>();
		// - Add qualifier
		qualifiers.add(qualifier);
		
		// The instance of an element may be further qualified by one or more qualifiers.
		put("qualifier", qualifiers);
	}


	/**
	 * Constructor
	 */
	public Qualifiable(Collection<String> qualifier) {
		// The instance of an element may be further qualified by one or more qualifiers.
		put("qualifier", qualifier);
	}
}

