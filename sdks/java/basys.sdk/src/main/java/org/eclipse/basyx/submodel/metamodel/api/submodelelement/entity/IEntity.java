package org.eclipse.basyx.submodel.metamodel.api.submodelelement.entity;

import java.util.List;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;

/**
 * An entity is a submodel element that is used to model entities.
 * 
 * @author schnicke
 *
 */
public interface IEntity {
	/**
	 * Gets statements applicable to the entity by a set of submodel elements,
	 * typically with a qualified value.
	 * 
	 * @return
	 */
	List<ISubmodelElement> getStatements();

	/**
	 * Gets EntityType describing whether the entity is a comanaged entity or a
	 * self-managed entity.
	 * 
	 * @return
	 */
	EntityType getEntityType();

	/**
	 * Gets the reference to the asset the entity is representing.
	 * 
	 * @return
	 */
	IReference getAsset();
}
