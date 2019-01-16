package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.DataElement;

/**
 * A ReferenceElement as defined in DAAS document <br/>
 * A reference element is a data element that defines a reference to another
 * element within the same or another AAS or a reference to an external object
 * or entity.
 * 
 * @author pschorn, schnicke
 *
 */
public class ReferenceElement extends DataElement {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public ReferenceElement() {
		put("value", null);
	}

	/**
	 * @param ref
	 *            Reference to any other referable element of the same or any other
	 *            AAS or a reference to an external object or entity
	 */
	public ReferenceElement(Reference ref) {
		put("value", ref);
	}

}
