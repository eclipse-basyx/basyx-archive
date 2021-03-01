/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.aas.metamodel.api;

import java.util.Collection;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.IAsset;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;

public interface IAasEnv {

	Collection<IAsset> getAssets();
	
	Collection<IAssetAdministrationShell> getAssetAdministrationShells();
	
	Collection<ISubModel> getSubmodels();
	
	Collection<IConceptDescription> getConceptDescriptions();
}