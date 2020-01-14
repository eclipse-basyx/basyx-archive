package org.eclipse.basyx.testsuite.regression.aas.registration.restapi;

import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * A registry servlet based on an InMemory Registry. The servlet therefore provides an implementation
 * for the IAASRegistryService interface without a permanent storage capability.
 * 
 * @author espen
 */
public class DirectoryProviderServlet extends VABHTTPInterface<DirectoryModelProvider> {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with ModelProvider based on an InMemoryRegistry
	 */
	public DirectoryProviderServlet() {
		super(new DirectoryModelProvider(new InMemoryRegistry()));

	}
}

