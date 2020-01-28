package org.eclipse.basyx.submodel.metamodel.api.qualifier;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * Element that can have a semantic definition.
 * 
 * @author rajashek
 *
 */
public interface IHasSemantics {

	/**
	 * Gets the identifier of the semantic definition of the element. It is called
	 * semantic id of the element. <br />
	 * <br />
	 * The semantic id may either reference an external global id or it may
	 * reference a referable model element of kind=Template that defines the
	 * semantics of the element.
	 * 
	 * @return
	 */
	public IReference getSemanticId();
}
