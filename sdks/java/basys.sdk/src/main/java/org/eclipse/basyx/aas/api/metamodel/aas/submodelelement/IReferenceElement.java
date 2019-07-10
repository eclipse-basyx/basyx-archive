package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.resources.IProperty;
/**
 * Interface for ReferenceElement
 * @author rajashek
 *
*/

public interface IReferenceElement extends IProperty {
	
	public void setValue(IReference ref);
	
	public IReference getValue();

}
