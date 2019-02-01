package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * Qualifier class
 * 
 * @author kuhn
 *
 */
public class Qualifier extends Constraint {

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
		put("qualifierType", "");
		put("qualifierValue", null);
		put("qualifierValueId", null);
	}

	public Qualifier(String type, String value, Reference valueId) {
		// Add all attributes from HasSemantics
		this.putAll(new HasSemantics());

		// Default values
		put("qualifierType", "");
		put("qualifierValue", null);
		put("qualifierValueId", null);
	}
}
