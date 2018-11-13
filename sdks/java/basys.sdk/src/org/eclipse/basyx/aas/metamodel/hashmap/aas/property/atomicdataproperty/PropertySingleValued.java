package org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty;

/**
 * Single valued property
 * 
 * FIXME: Add TimeStamped qualifier
 * 
 * @author kuhn
 *
 */
public class PropertySingleValued extends AtomicDataProperty {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public PropertySingleValued() {
		// Add qualifiers
		// putAll(new AtomicDataProperty());
		super();

		// Default value
		put("value", null);
		put("valueType", new ValueType());
	}

	/**
	 * Constructor
	 */
	public PropertySingleValued(Object value) {
		// Add qualifiers
		super();

		// Put value and valueType
		put("value", value);
		put("valueType", new ValueType(value));
	}

}
