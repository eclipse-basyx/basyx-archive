package org.eclipse.basyx.components.servlet;

import org.eclipse.basyx.aas.registration.memory.AASRegistry;
import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.components.mongodbregistry.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.components.mongodbregistry.MongoDBRegistryHandler;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * A registry servlet based on an SQL database. The servlet therefore provides an implementation
 * for the IAASRegistryService interface with a permanent storage solution.
 * 
 * @author espen
 */
public class MongoDBRegistryServlet extends VABHTTPInterface<DirectoryModelProvider> {
	private static final long serialVersionUID = 1L;

	/**
	 * Provide HTTP interface with JSONProvider to handle serialization and
	 * SQLDirectoryProvider as backend
	 */
	public MongoDBRegistryServlet() {
		super(new DirectoryModelProvider(new AASRegistry(new MongoDBRegistryHandler())));
	}

	public MongoDBRegistryServlet(BaSyxMongoDBConfiguration config) {
		super(new DirectoryModelProvider(new AASRegistry(new MongoDBRegistryHandler(config))));
	}
}