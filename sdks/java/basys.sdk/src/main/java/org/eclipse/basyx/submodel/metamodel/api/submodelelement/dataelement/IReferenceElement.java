package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;

/**
 * A reference element is a data element that defines a logical reference to
 * another element within the same or another AAS or a reference to an external
 * object or entity.
 * 
 * @author rajashek, schnicke
 *
 */
public interface IReferenceElement extends ISubmodelElement {
	/**
	 * Gets the reference to any other referable element of the same or of any other
	 * AAS or a reference to an external object or entity.
	 * 
	 * @return
	 */
	public IReference getValue();

}
