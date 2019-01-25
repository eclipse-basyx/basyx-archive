package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDef;

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
		put("qualifierType", "");
		put("qualifierValue", null);
		put("qualifierValueId", null);
	}

	public Qualifier(QualifierType type, PropertyValueTypeDef value, Reference valueId) {
		// Add all attributes from HasSemantics
		this.putAll(new HasSemantics());

		// Default values
		put("qualifierType", "");
		put("qualifierValue", null);
		put("qualifierValueId", null);
	}
}
