/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.vab;

import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provider that contains a SimpleVABElement for testing purposes that can be reset.
 * For this, invoke the provider at path /reset with any parameter.
 * 
 * @author espen
 *
 */
public class SimpleVABProvider extends VABMapProvider {
	private static Logger logger = LoggerFactory.getLogger(SimpleVABProvider.class);

	public SimpleVABProvider() {
		super(new SimpleVABElement());
	}

	/**
	 * Adds a "reset" operation to reset the SimpleVABElement at /reset
	 */
	@Override
	public Object invokeOperation(String path, Object... parameters) {
		logger.info("Invoke path: " + path);
		if (path == null) {
			return super.invokeOperation(path, parameters);
		}
		String[] parts = VABPathTools.splitPath(path);
		String lastElement = VABPathTools.getLastElement(path);
		if (parts.length == 1 && lastElement.equals("reset")) {
			super.elements = new SimpleVABElement();
			logger.info("Reset element");
			return null;
		} else {
			return super.invokeOperation(path, parameters);
		}
	}
}
