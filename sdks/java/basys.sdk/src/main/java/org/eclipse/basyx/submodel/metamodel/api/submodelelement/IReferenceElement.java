package org.eclipse.basyx.submodel.metamodel.api.submodelelement;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
/**
 * Interface for ReferenceElement
 * @author rajashek
 *
*/

public interface IReferenceElement extends ISubmodelElement {
	
	public void setValue(IReference ref);
	
	public IReference getValue();

}
