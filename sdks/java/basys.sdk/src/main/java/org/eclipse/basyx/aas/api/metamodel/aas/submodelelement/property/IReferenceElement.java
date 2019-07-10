package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

/**
 * Interface for ReferenceElement
 * @author rajashek
 *
*/

public interface IReferenceElement {
	
	
	public void setValue(IReference ref);
	
	public IReference getValue();

}
