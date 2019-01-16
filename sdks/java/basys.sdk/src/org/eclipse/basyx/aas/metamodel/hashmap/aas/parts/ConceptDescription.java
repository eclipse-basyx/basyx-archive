package org.eclipse.basyx.aas.metamodel.hashmap.aas.parts;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;

/**
 * ConceptDescription class as described in DAAS document
 * 
 * @author schnicke
 *
 */
public class ConceptDescription extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public ConceptDescription() {
		// Add qualifiers
		putAll(new HasDataSpecification());
		putAll(new Identifiable());

		// Add attributes
		put("isCaseOf", new HashSet<String>());
	}

}
