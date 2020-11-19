package org.eclipse.basyx.components.registry.servlet;

import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * A registry servlet based on an InMemory Registry. The servlet therefore provides an implementation
 * for the IAASRegistryService interface without a permanent storage capability.
 * 
 * Do not use this registry in a productive environment - the entries are not persistent!
 * 
 * @author espen
 */
public class InMemoryRegistryServlet extends VABHTTPInterface<DirectoryModelProvider> {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with ModelProvider based on an InMemoryRegistry
	 */
	public InMemoryRegistryServlet() {
		super(new DirectoryModelProvider(new InMemoryRegistry()));

	}
}