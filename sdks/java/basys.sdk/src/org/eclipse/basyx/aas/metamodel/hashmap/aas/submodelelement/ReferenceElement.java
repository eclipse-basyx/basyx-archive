package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * ReferenceElement as described by DAAS document <br/>
 * A reference element is a data element that defines a reference to another
 * element within the same or another AAS or a reference to an external object
 * or entity.
 * 
 * @author schnicke
 *
 */
public class ReferenceElement extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public ReferenceElement() {
		put("value", null);
	}

	/**
	 * 
	 * @param ref
	 *            Reference to any other referable element of the same of any other
	 *            AAS or a reference to an external object or entity.
	 */
	public ReferenceElement(Reference ref) {
		put("value", ref);
	}
}
