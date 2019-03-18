package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
/**
 * Interface for ReferenceElement
 * @author rajashek
 *
*/

public interface IReferenceElement {
	
	public void setValue(Reference ref);
	
	public Reference getValue();

}
