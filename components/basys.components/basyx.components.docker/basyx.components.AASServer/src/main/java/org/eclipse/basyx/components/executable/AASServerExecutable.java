package org.eclipse.basyx.components.executable;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.AASServerComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Starts an HTTP server that is able to receive AAS and submodels pushed from
 * remote <br />
 * They are made available at
 * <i>localhost:4000/aasServer/aasList/${aasId}/aas</i>. Submodels are available
 * at
 * <i>localhost:4000/aasServer/aasList/${aasId}/submodels/${submodelId}/submodel</i><br
 * />
 * 
 * @author schnicke
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

		AASServerComponent component = new AASServerComponent(config.getHostname(), config.getPort(), config.getContextPath(), config.getDocBasePath());
		component.startComponent();

		logger.info("BaSyx AAS Server component started");
	}
}
