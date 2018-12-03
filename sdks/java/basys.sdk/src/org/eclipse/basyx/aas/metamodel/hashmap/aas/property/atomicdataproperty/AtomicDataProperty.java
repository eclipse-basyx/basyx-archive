package org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;



/**
 * AtomicData property class
 * 
 * @author kuhn
 *
 */
public class AtomicDataProperty extends Property {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public AtomicDataProperty() {
		// Add qualifiers
		// putAll(new Property());

		super();
	}

	
	/**
	 * Constructor
	 */
	public AtomicDataProperty(Property property) {
		// Add qualifiers
		putAll(property);
	}
}
