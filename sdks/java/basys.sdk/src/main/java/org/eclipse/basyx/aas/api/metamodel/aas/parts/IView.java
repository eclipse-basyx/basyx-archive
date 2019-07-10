package org.eclipse.basyx.aas.api.metamodel.aas.parts;

import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IReferable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
/**
 * Interface for View
 * @author rajashek
 *
*/

public interface IView extends IHasSemantics,IHasDataSpecification,IReferable {
	
	public void setContainedElement(Set<IReference> references);
	
	public Set<IReference> getContainedElement();

}
