package org.eclipse.basyx.submodel.metamodel.api.submodelelement.relationship;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * An annotated relationship element is a relationship element that can be
 * annotated withadditional data elements.
 * 
 * @author schnicke
 *
 */
public interface IAnnotatedRelationShipElement extends IRelationshipElement {
	/**
	 * Annotations that hold for the relationships between the two elements.
	 * 
	 * @return
	 */
	IReference getAnnotation();
}
