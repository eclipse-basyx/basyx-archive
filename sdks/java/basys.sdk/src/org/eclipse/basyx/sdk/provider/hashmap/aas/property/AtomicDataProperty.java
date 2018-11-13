package org.eclipse.basyx.sdk.provider.hashmap.aas.property;

import java.util.HashMap;



/**
 * Abstract property class
 * 
 * @author kuhn
 *
 */
public class AtomicDataProperty extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	/**
	 * Default constructor
	 */
	public AtomicDataProperty() {
		// Add qualifiers
		putAll(new Property());
	}

	
	/**
	 * Constructor
	 */
	public AtomicDataProperty(Property property) {
		// Add qualifiers
		putAll(property);
	}
}
