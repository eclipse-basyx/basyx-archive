/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.submodel.metamodel.api.submodelelement.entity;

import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.IdentifierKeyValuePair;

/**
 * An entity is a submodel element that is used to model entities.
 * 
 * @author schnicke
 *
 */
public interface IEntity extends ISubmodelElement {
	/**
	 * Gets statements applicable to the entity by a set of submodel elements,
	 * typically with a qualified value.
	 * 
	 * @return
	 */
	Collection<ISubmodelElement> getStatements();

	/**
	 * Gets EntityType describing whether the entity is a comanaged entity or a
	 * self-managed entity.
	 * 
	 * @return
	 */
	EntityType getEntityType();
	
	/**
	 * gets global asset id
	 * Reference to the asset the entity is representing.
	 * Constraint AASd-014: Either the attribute globalAssetId or specificAssetId of an Entity must be set if Entity/entityType is set to “SelfManagedEntity”. They are not existing otherwise.
	 * @return {@link IReference}
	 */
	IReference getGlobalAssetId();
	
	/**
	 * gets reference to an identifier key value pair representing a specific identifier of the asset represented by the asset administration shell.
	 * @return
	 */
	List<IdentifierKeyValuePair> getSpecificAssetId();
}
