package org.eclipse.basyx.submodel.metamodel.api.parts;

import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * The semantics of a property or other elements that may have a semantic
 * description is defined by a concept description. <br />
 * <br />
 * The description of the concept should follow a standardized schema (realized
 * as data specification template).
 * 
 * @author rajashek, schnicke
 *
 */
public interface IConceptDescription extends IHasDataSpecification, IIdentifiable {
	/**
	 * Gets the global reference to an external definition the concept is compatible
	 * to or was derived from.
	 * 
	 * @return
	 */
	public Set<IReference> getIsCaseOf();
}
