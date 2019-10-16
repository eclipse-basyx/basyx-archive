package org.eclipse.basyx.submodel.metamodel.api.submodelelement.property;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;

/**
 * Interface for ReferenceElement
 * @author rajashek
 *
*/

public interface IReferenceElement extends IDataElement {
	
	
	public void setValue(IReference ref);
	
	public IReference getValue();

}
