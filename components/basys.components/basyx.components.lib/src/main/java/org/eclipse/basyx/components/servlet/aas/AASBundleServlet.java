/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.components.servlet.aas;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiAASProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * Servlet providing multiple AAS based on a passed {@link AASBundle}
 * 
 * @author schnicke
 *
 */
public class AASBundleServlet extends VABHTTPInterface<MultiAASProvider> {
	private static final long serialVersionUID = 4441135540490088430L;

	/**
	 * Creates a servlet hosting the AAS and its submodels specified in the bundle
	 * 
	 * @param bundle
	 */
	public AASBundleServlet(AASBundle bundle) {
		this(Collections.singleton(bundle));
	}

	/**
	 * Creates a servlet hosting multiple AAS and their submodels as specified in
	 * the bundle list
	 * 
	 * @param bundles
	 */
	public AASBundleServlet(Collection<AASBundle> bundles) {
		super(new MultiAASProvider());

		MultiAASProvider multiAASProvider = getModelProvider();

		for (AASBundle bundle : bundles) {
			multiAASProvider.addMultiSubmodelProvider(bundle.getAAS().getIdShort(), createMultiSubmodelProvider(bundle));
		}
	}

	/**
	 * Creates the MultiSubmodelProvider based on a single bundle
	 * 
	 * @param bundle
	 * @return
	 */
	private MultiSubmodelProvider createMultiSubmodelProvider(AASBundle bundle) {
		MultiSubmodelProvider provider = new MultiSubmodelProvider();
		IAssetAdministrationShell shell = bundle.getAAS();

		// Check for correct type
		if (!(shell instanceof AssetAdministrationShell)) {
			throw new RuntimeException("Only instances of AssetAdministrationShell are allowed here");
		}

		provider.setAssetAdministrationShell(new AASModelProvider((AssetAdministrationShell) shell));
		for (ISubmodel sm : bundle.getSubmodels()) {

			if (!(sm instanceof Submodel)) {
				throw new RuntimeException("Only instances of Submodel are allowed here");
			}

			provider.addSubmodel(new SubmodelProvider((Submodel) sm));
		}
		return provider;
	}
}
