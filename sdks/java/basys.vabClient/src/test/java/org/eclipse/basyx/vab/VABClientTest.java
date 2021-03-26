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

import org.eclipse.basyx.vab.directory.api.IVABDirectoryService;
import org.eclipse.basyx.vab.directory.memory.InMemoryDirectory;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.protocol.basyx.connector.BaSyxConnectorProvider;

/**
 * The client side for a Java-Cpp integration test
 * 
 * @author espen
 *
 */
public class VABClientTest extends CppTestProvider {
	public static String url = "basyx://127.0.0.1:7001/";

	protected VABConnectionManager connManager;

	public VABClientTest() {
		// Setup the directory based on the SDK-VAB tests
		IVABDirectoryService directory = new InMemoryDirectory();
		String simpleVABID = "urn:fhg:es.iese:vab:1:1:simplevabelement";
		directory.addMapping(simpleVABID, url);
		connManager = new VABConnectionManager(directory, new BaSyxConnectorProvider());
	}

	@Override
	protected VABConnectionManager getConnectionManager() {
		return connManager;
	}
}
