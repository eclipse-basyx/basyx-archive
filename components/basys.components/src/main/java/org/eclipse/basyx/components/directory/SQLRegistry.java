package org.eclipse.basyx.components.directory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;

import org.eclipse.basyx.aas.registration.memory.MapRegistry;
import org.eclipse.basyx.tools.sqlproxy.SQLRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements a local registry based on an SQL database
 * 
 * @author espen
 *
 */
public class SQLRegistry extends MapRegistry {
	private static Logger logger = LoggerFactory.getLogger(SQLRegistry.class);

	/**
	 * Receives the path of the configuration.properties file in it's constructor.
	 * 
	 * @param configFilePath
	 */
	public SQLRegistry(String configFilePath) {
		super(new AASDescriptorMap(initRootMap(configFilePath)));

	}

	private static Map<String, Object> initRootMap(String configFilePath) {
		SQLRootElement sqlRootElement = initSQLConnection(configFilePath);
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
	private static final SQLRootElement initSQLConnection(String configFilePath) {
		try (InputStream input = new FileInputStream(configFilePath)) {
			// Instantiate property structure
			Properties properties = new Properties();
			properties.load(input);

			// SQL parameter
			String path = properties.getProperty("dburl");
			String user = properties.getProperty("dbuser");
			String pass = properties.getProperty("dbpass");
			String qryPfx = properties.getProperty("sqlPrefix");
			String qDrvCls = properties.getProperty("sqlDriver");

			// Create SQL driver instance
			return new SQLRootElement(user, pass, path, qDrvCls, qryPfx, "root_registry");
		} catch (IOException e) {
			logger.error("Could not init SQL connection from config file", e);
		}

		return null;
	}
}
