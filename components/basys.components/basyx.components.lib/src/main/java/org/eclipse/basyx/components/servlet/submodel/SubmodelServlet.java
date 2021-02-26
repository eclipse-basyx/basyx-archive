/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.components.servlet.submodel;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * A sub model servlet class that exports a given sub model
 * 
 * @author kuhn
 *
 */
public class SubmodelServlet extends VABHTTPInterface<SubModelProvider> {

	/**
	 * ID of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 */
	public SubmodelServlet() {
		// Invoke base constructor
		super(new SubModelProvider());
	}

	/**
	 * Constructor with a predefined submodel
	 */
	public SubmodelServlet(SubModel exportedModel) {
		// Invoke base constructor
		super(new SubModelProvider(exportedModel));
	}
}
