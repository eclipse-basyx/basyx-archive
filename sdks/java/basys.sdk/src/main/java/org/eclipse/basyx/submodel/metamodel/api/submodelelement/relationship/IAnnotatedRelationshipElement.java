package org.eclipse.basyx.submodel.metamodel.api.submodelelement.relationship;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.AnnotatedRelationshipElementValue;

/**
 * An annotated relationship element is a relationship element that can be
 * annotated with additional data elements.
 * 
 * @author schnicke
 *
 */
public interface IAnnotatedRelationshipElement extends IRelationshipElement {
	@Override
	AnnotatedRelationshipElementValue getValue();

	void setValue(AnnotatedRelationshipElementValue value);
}
