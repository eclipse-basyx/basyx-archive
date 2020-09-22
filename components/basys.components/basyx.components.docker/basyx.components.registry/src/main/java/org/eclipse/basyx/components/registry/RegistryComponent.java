package org.eclipse.basyx.components.registry;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxSQLConfiguration;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;
import org.eclipse.basyx.components.registry.servlet.InMemoryRegistryServlet;
import org.eclipse.basyx.components.registry.servlet.MongoDBRegistryServlet;
import org.eclipse.basyx.components.registry.servlet.SQLRegistryServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic registry that can start and stop a registry with different kinds of backends.
 * Currently supports MongoDB and SQL. For development purposes, the component can also start a
 * registry without a backend and without persistency.
 * 
 * @author espen
 *
 */
public class RegistryComponent implements IComponent {
	private static Logger logger = LoggerFactory.getLogger(RegistryComponent.class);
	
	// The server with the servlet that will be created
	private AASHTTPServer server;

	// The component configuration
	private BaSyxContextConfiguration contextConfig;
	private BaSyxRegistryConfiguration registryConfig;

	/**
	 * Default constructor that loads default configurations
	 */
	public RegistryComponent() {
		contextConfig = new BaSyxContextConfiguration();
		registryConfig = new BaSyxRegistryConfiguration();
	}

	/**
	 * Constructor with given configuration for the registry and its server context
	 * 
	 * @param contextConfig  The context configuration
	 * @param registryConfig The registry configuration
	 */
	public RegistryComponent(BaSyxContextConfiguration contextConfig, BaSyxRegistryConfiguration registryConfig) {
		this.contextConfig = contextConfig;
		this.registryConfig = registryConfig;
	}

	/**
	 * Starts the context at http://${hostName}:${port}/${path}
	 */
	@Override
	public void startComponent() {
		BaSyxContext context = contextConfig.createBaSyxContext();
		context.addServletMapping("/*", loadRegistryServlet());
		server = new AASHTTPServer(context);
		server.start();
		logger.info("Registry server started");
	}

	/**
	 * Loads a registry with a backend according to the registryConfig
	 * 
	 * @return
	 */
	private HttpServlet loadRegistryServlet() {
		HttpServlet registryServlet = null;
		RegistryBackend backendType = registryConfig.getRegistryBackend();
		switch(backendType) {
			case MONGODB:
				registryServlet = loadMongoDBRegistryServlet();
				break;
			case SQL:
				registryServlet = loadSQLRegistryServlet();
				break;
			case INMEMORY:
				registryServlet = loadInMemoryRegistryServlet();
				break;
		}
		return registryServlet;
	}

	/**
	 * Creates a registry servlet with an sql backend
	 * 
	 * @return
	 */
	private HttpServlet loadSQLRegistryServlet() {
		logger.info("Loading SQLRegistry");
		BaSyxSQLConfiguration sqlConfig = new BaSyxSQLConfiguration();
		sqlConfig.loadFromDefaultSource();
		return new SQLRegistryServlet(sqlConfig);
	}

	/**
	 * Creates a registry servlet with an mongodb backend
	 * 
	 * @return
	 */
	private HttpServlet loadMongoDBRegistryServlet() {
		logger.info("Loading MongoDBRegistry");
		BaSyxMongoDBConfiguration mConfig = new BaSyxMongoDBConfiguration();
		mConfig.loadFromDefaultSource();
		return new MongoDBRegistryServlet(mConfig);
	}

	/**
	 * Creates an registry servlet with in memory data (=> not persistent)
	 * 
	 * @return
	 */
	private HttpServlet loadInMemoryRegistryServlet() {
		logger.info("Loading InMemoryRegistry");
		return new InMemoryRegistryServlet();
	}

	@Override
	public void stopComponent() {
		server.shutdown();
		logger.info("Registry server stopped");
	}
}
