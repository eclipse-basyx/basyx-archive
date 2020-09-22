package org.eclipse.basyx.components.executable;

import org.eclipse.basyx.components.SQLRegistryComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxSQLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry servlet based on an SQL database. The servlet therefore provides an implementation
 * for the IAASRegistryService interface with a permanent storage solution. The properties for the 
 * SQL connection will be read from executables.properties in the resource folder.
 * 
 * @author espen
 */
public class SQLRegistryExecutable {
	private static Logger logger = LoggerFactory.getLogger(SQLRegistryExecutable.class);

	private SQLRegistryExecutable() {
	}

	public static void main(String[] args) {
		logger.info("Starting BaSyx SQL Registry component...");
		// Load context configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromDefaultSource();

		// Load db configuration
		BaSyxSQLConfiguration dbConfig = new BaSyxSQLConfiguration();
		dbConfig.loadFromDefaultSource();

		// Create and start component according to the configuration
		SQLRegistryComponent component = new SQLRegistryComponent(contextConfig, dbConfig);
		component.startComponent();
		logger.info("BaSyx SQL Registry component started");
	}
}