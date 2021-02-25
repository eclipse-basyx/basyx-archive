/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.testsuite.regression.vab.directory.restapi;

import org.eclipse.basyx.testsuite.regression.vab.directory.proxy.TestDirectory;
import org.eclipse.basyx.vab.directory.api.IVABDirectoryService;
import org.eclipse.basyx.vab.directory.proxy.VABDirectoryProxy;
import org.eclipse.basyx.vab.directory.restapi.DirectoryModelProvider;

/**
 * Tests the directory provider using the TestDirectory Suite
 * 
 * @author schnicke
 *
 */
public class TestDirectoryProvider extends TestDirectory {

	@Override
	protected IVABDirectoryService getRegistry() {
		DirectoryModelProvider provider = new DirectoryModelProvider();
		return new VABDirectoryProxy(provider);
	}

}
