package org.eclipse.basyx.aas.metamodel.api.parts;

import java.util.Collection;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * A view is a collection of referable elements w.r.t. to a specific viewpoint
 * of one or more stakeholders.
 * 
 * @author rajashek, schnicke
 *
 */

public interface IView extends IHasSemantics, IHasDataSpecification, IReferable {
	/**
	 * Gets the referable elements that are contained in the view.
	 * 
	 * @return
	 */
	public Collection<IReference> getContainedElement();
}
