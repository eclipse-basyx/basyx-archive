package org.eclipse.basyx.components.servlet.registry;


import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.components.directory.SQLRegistry;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;


/**
 * SQL database based directory provider
 * 
 * This directory provider provides a static directory. It therefore only
 * supports get() operations. Modification of the directory via
 * PUT/POST/PATCH/DELETE operations is not supported.
 * 
 * @author kuhn, pschorn
 *
 */
public class SQLRegistryServlet extends VABHTTPInterface<DirectoryModelProvider> {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Path to the directory.properties file, that contains config data for the SQL
	 * connection
	 */
	private static String configFilePath = "./WebContent/WEB-INF/config/directory/sqldirectory/directory.properties";

	/**
	 * Provide HTTP interface with JSONProvider to handle serialization and
	 * SQLDirectoryProvider as backend
	 */
	public SQLRegistryServlet() {
		super(new DirectoryModelProvider(new SQLRegistry(configFilePath)));

	}

	public SQLRegistryServlet(String customConfigFilePath) {
		super(new DirectoryModelProvider(new SQLRegistry(customConfigFilePath)));
	}

	
}

