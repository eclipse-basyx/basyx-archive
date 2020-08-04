package org.eclipse.basyx.components.executable;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.XMLAASComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Starts an HTTP server providing multiple AAS and submodels as described in
 * the XML file specified in the properties file <br />
 * They are made available at <i>localhost:4000/xmlAAS/$aasId/aas</i><br />
 * <br />
 * <b>Please note:</b> Neither the AASs nor the Submodels are added to the
 * registry. Additionally, the Submodel descriptors inside the AAS are missing.
 * <br />
 * There reason for this is, that the executable does not know about the outside
 * context (e.g. docker, ...)!
 * 
 * @author haque, schnicke
 */
public class XMLExecutable {
	// Creates a Logger based on the current class
	private static Logger logger = LoggerFactory.getLogger(XMLExecutable.class);

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		logger.info("Starting BaSyx XML component");

		// Load configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		if (args.length > 0 && args[0] instanceof String) {
			// file path available? => load configs from file
			config.loadFromFile(args[0]);
		} else {
			// fallback: load default configs (in resources)
			config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		}

		XMLAASComponent component = new XMLAASComponent(config.getHostname(), config.getPort(), config.getContextPath(),
				config.getDocBasePath(), config.getProperty("xmlPath"), config.getProperty("registry"));
		component.startComponent();
	}
}