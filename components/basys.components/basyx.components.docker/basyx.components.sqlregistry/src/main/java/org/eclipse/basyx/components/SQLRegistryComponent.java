package org.eclipse.basyx.components;

import org.eclipse.basyx.components.servlet.SQLRegistryServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry component based on an SQL database.
 * 
 * @author espen
 */
public class SQLRegistryComponent implements IComponent {
	private static Logger logger = LoggerFactory.getLogger(SQLRegistryComponent.class);

	// BaSyx context information
	private String hostName;
	private int port;
	private String path;
	private String docBasePath;
	private String sqlPropertyPath;

	// The server with the servlet that will be created
	private AASHTTPServer server;

	public SQLRegistryComponent(String hostName, int port, String path, String docBasePath, String sqlPropertyPath) {
		this.sqlPropertyPath = sqlPropertyPath;
		// Sets the server context
		this.hostName = hostName;
		this.port = port;
		this.path = path;
		this.docBasePath = docBasePath;
	}

	/**
	 * Starts the SQLRegistry at http://${hostName}:${port}/${path}
	 */
	@Override
	public void startComponent() {
		logger.info("Create the server...");
		// Init HTTP context and add an InMemoryRegistryServlet according to the configuration
		BaSyxContext context = new BaSyxContext(path, docBasePath, hostName, port);
		context.addServletMapping("/*", new SQLRegistryServlet(sqlPropertyPath));
		server = new AASHTTPServer(context);

		logger.info("Start the server...");
		server.start();
	}

	@Override
	public void stopComponent() {
		server.shutdown();
	}
}
