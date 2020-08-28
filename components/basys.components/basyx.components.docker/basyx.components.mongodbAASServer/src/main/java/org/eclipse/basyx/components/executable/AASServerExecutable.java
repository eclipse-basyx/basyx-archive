package org.eclipse.basyx.components.executable;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.MongoDBAASServerComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Starts an HTTP server backed by a MongoDB that is able to receive AAS and submodels pushed from
 * remote <br />
 * They are made available at
 * <i>localhost:4000/aasServer/aasList/${aasId}/aas</i>. Submodels are available
 * at
 * <i>localhost:4000/aasServer/aasList/${aasId}/submodels/${submodelId}/submodel</i><br
 * />
 * 
 * @author espen
 */
public class AASServerExecutable {
	// Creates a Logger based on the current class
	private static Logger logger = LoggerFactory.getLogger(AASServerExecutable.class);

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// Load configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		if (args.length > 0 && args[0] instanceof String) {
			// file path available? => load configs from file
			config.loadFromFile(args[0]);
		} else {
			// fallback: load default configs (in resources)
			config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		}

		// Load configuration
		BaSyxMongoDBConfiguration dbConfig = new BaSyxMongoDBConfiguration();
		MongoDBAASServerComponent component;
		if (args.length > 1 && args[1] instanceof String) {
			// file path available? => load mongodb configs from file
			dbConfig.loadFromFile(args[1]);
		}
		component = new MongoDBAASServerComponent(config.getHostname(), config.getPort(), config.getContextPath(),
				config.getDocBasePath(), dbConfig);
		component.startComponent();

		logger.info("BaSyx AAS Server component started");
	}
}
