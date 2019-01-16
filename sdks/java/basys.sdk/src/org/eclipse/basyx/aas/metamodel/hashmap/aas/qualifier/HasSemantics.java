package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * HasSemantics class
 * 
 * @author kuhn, schnicke
 *
 */
public class HasSemantics extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public HasSemantics() {
		// Default values
		put("semanticId", null);
	}

	/**
	 * Constructor
	 */
	public HasSemantics(Reference idSemantics) {
		put("semanticId", idSemantics);
	}
}
