package org.eclipse.basyx.components.executable;

import org.eclipse.basyx.components.MongoDBAASServerComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts an HTTP server backed by a MongoDB that is able to receive AAS and
 * submodels pushed from remote <br />
 * They are made available at
 * <i>localhost:4000/aasServer/shells/${aasId}/aas</i>. Submodels are available
 * at
 * <i>localhost:4000/aasServer/shells/${aasId}/submodels/${submodelId}/submodel</i><br
 * />
 * 
 * @author espen
 */
public class AASServerExecutable {
	// Creates a Logger based on the current class
	private static Logger logger = LoggerFactory.getLogger(AASServerExecutable.class);

	public static void main(String[] args) {
		logger.info("Starting BaSyx MongoDB AASServer component...");
		// Load context configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromDefaultSource();
		
		// Load db configuration
		BaSyxMongoDBConfiguration dbConfig = new BaSyxMongoDBConfiguration();
		dbConfig.loadFromDefaultSource();
		
		// Create and start component according to the configuration
		MongoDBAASServerComponent component = new MongoDBAASServerComponent(config, dbConfig);
		component.startComponent();
		logger.info("BaSyx MongoDB AASServer component started");
	}
}
