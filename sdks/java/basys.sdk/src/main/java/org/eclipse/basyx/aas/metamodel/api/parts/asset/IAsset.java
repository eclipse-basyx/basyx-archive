/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.aas.metamodel.api.parts.asset;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
/**
 * An Asset describes meta data of an asset that is represented by an AAS. The
 * asset may either represent an asset type or an asset instance. The asset has
 * a globally unique identifier plus – if needed – additional domain specific
 * (proprietary) identifiers.
 * 
 * @author rajashek, schnicke
 *
 */

public interface IAsset extends IHasDataSpecification, IIdentifiable {
}
