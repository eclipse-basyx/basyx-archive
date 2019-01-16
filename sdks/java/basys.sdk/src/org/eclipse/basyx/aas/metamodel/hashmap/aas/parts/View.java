package org.eclipse.basyx.aas.metamodel.hashmap.aas.parts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * View as defined by DAAS document. <br/>
 * A view is a collection of referable elements w.r.t. to a specific viewpoint
 * of one or more stakeholders.
 * 
 * @author kuhn, schnicke
 *
 */
public class View extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public View() {
		// Add qualifiers
		putAll(new HasSemantics());
		putAll(new HasDataSpecification());
		putAll(new Referable());

		// Default values
		put("containedElement", new HashSet<Reference>());
	}

	/**
	 * 
	 * @param references
	 *            Referable elements that are contained in the view.
	 */
	public View(Set<Reference> references) {
		this();
		put("containedElement", references);
	}

}
