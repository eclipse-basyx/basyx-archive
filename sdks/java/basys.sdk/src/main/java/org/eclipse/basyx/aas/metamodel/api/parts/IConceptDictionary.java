package org.eclipse.basyx.aas.metamodel.api.parts;

import java.util.Collection;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * A dictionary contains elements that can be reused. <br />
 * <br />
 * The concept dictionary contains concept descriptions. <br />
 * <br />
 * Typically a concept description dictionary of an AAS contains only concept
 * descriptions of elements used within submodels of the AAS.
 * 
 * @author rajashek, schnicke
 *
 */

public interface IConceptDictionary extends IReferable {

	/**
	 * Returns the concept description that defines a concept.
	 * 
	 * @return
	 */
	public Collection<IReference> getConceptDescription();
}
