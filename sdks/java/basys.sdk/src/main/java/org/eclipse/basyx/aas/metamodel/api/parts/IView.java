package org.eclipse.basyx.aas.metamodel.api.parts;

import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
/**
 * Interface for View
 * @author rajashek
 *
*/

public interface IView extends IHasSemantics,IHasDataSpecification,IReferable {
	public Set<IReference> getContainedElement();
}
