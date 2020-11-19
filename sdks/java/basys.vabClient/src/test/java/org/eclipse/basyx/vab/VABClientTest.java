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
	public static String url = "basyx://localhost:8384/";

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
