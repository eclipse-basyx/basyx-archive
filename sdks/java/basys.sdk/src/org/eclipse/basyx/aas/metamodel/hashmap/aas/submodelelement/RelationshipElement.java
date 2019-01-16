package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * RelationshipElement as defined in DAAS document <br/>
 * A relationship element is used to define a relationship between two referable
 * elements.
 * 
 * 
 * @author schnicke
 *
 */
public class RelationshipElement extends SubmodelElement {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public RelationshipElement() {
		put("first", null);
		put("second", null);
	}

	/**
	 * 
	 * @param first
	 *            First element in the relationship taking the role of the subject.
	 * @param second
	 *            Second element in the relationship taking the role of the object.
	 */
	public RelationshipElement(Reference first, Reference second) {
		put("first", first);
		put("second", second);
	}
}
