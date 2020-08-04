package org.eclipse.basyx.components.sqlregistry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import org.eclipse.basyx.aas.registration.memory.AASRegistry;
import org.eclipse.basyx.aas.registration.memory.MapRegistryHandler;
import org.eclipse.basyx.components.configuration.BaSyxSQLConfiguration;
import org.eclipse.basyx.tools.sqlproxy.SQLRootElement;

/**
 * Implements a local registry based on an SQL database
 * 
 * @author espen
 *
 */
public class SQLRegistry extends AASRegistry {
	private static final String DEFAULT_SQL_CONFIG_PATH = "registry.properties";

	/**
	 * Receives the path of the configuration.properties file in it's constructor.
	 * 
	 * @param configFilePath
	 */
	public SQLRegistry(String configFilePath) {
		super(new MapRegistryHandler(new AASDescriptorMap(createRootMap(configFilePath))));
	}

	/**
	 * Constructor using default sql connections
	 */
	public SQLRegistry() {
		this(DEFAULT_SQL_CONFIG_PATH);
	}

	/**
	 * Creates a SQLRegistry from a sql configuration
	 */
	public SQLRegistry(BaSyxSQLConfiguration configuration) {
		super(new MapRegistryHandler(new AASDescriptorMap(createRootMap(configuration))));
	}

	private static Map<String, Object> createRootMap(String configFilePath) {
		BaSyxSQLConfiguration config = new BaSyxSQLConfiguration();
		config.loadFromResource(configFilePath);
		return createRootMap(config);
	}

	private static Map<String, Object> createRootMap(BaSyxSQLConfiguration config) {
		SQLRootElement sqlRootElement = initSQLConnection(config);
		sqlRootElement.drop();
		sqlRootElement.create();

		return sqlRootElement.createMap(sqlRootElement.getNextIdentifier());
	}

	/**
	 * Initialize sqlDriver
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 * @throws ServletException
	 */
	private static final SQLRootElement initSQLConnection(BaSyxSQLConfiguration config) {
		// SQL parameter
		String path = config.getPath();
		String user = config.getUser();
		String pass = config.getPass();
		String qryPfx = config.getPrefix();
		String qDrvCls = config.getDrv();

		// Create SQL driver instance
		return new SQLRootElement(user, pass, path, qDrvCls, qryPfx, "root_registry");
	}
}
