package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IReferenceElement;
import org.eclipse.basyx.aas.metamodel.facades.ReferenceElementFacade;
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
public class ReferenceElement extends HashMap<String, Object> implements IReferenceElement {
	private static final long serialVersionUID = 1L;
	
	public static final String VALUE="value";

	public ReferenceElement() {
		put(VALUE, null);
	}

	/**
	 * 
	 * @param ref
	 *            Reference to any other referable element of the same of any other
	 *            AAS or a reference to an external object or entity.
	 */
	public ReferenceElement(Reference ref) {
		put(VALUE, ref);
	}

	@Override
	public void setValue(Reference ref) {
		new ReferenceElementFacade(this).setValue(ref);
		
	}

	@Override
	public Reference getValue() {
		return new ReferenceElementFacade(this).getValue();
	}
}
