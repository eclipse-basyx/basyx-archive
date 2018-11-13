package org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;

/**
 * AtomicData property class
 * 
 * @author kuhn
 *
 */
public abstract class AtomicDataProperty extends Property {

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

}
