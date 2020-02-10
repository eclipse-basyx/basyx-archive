package org.eclipse.basyx.components.servlet;

import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.components.sqlregistry.SQLRegistry;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * A registry servlet based on an SQL database. The servlet therefore provides an implementation
 * for the IAASRegistryService interface with a permanent storage solution.
 * 
 * @author kuhn, pschorn, espen
 */
public class SQLRegistryServlet extends VABHTTPInterface<DirectoryModelProvider> {
	private static final long serialVersionUID = 1L;

	/**
	 * Provide HTTP interface with JSONProvider to handle serialization and
	 * SQLDirectoryProvider as backend
	 */
	public SQLRegistryServlet() {
		super(new DirectoryModelProvider(new SQLRegistry()));
	}

	public SQLRegistryServlet(String customConfigFilePath) {
		super(new DirectoryModelProvider(new SQLRegistry(customConfigFilePath)));
	}
}