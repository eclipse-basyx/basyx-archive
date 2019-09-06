package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;

/**
 * Interface for ReferenceElement
 * @author rajashek
 *
*/

public interface IReferenceElement extends IDataElement {
	
	
	public void setValue(IReference ref);
	
	public IReference getValue();

}
