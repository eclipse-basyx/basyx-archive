package org.eclipse.basyx.components.executable;

import org.eclipse.basyx.components.AASServerComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts an HTTP server that is able to receive AAS and submodels pushed from
 * remote <br />
 * They are made available at
 * <i>localhost:4000/aasServer/shells/${aasId}/aas</i>. Submodels are available
 * at
 * <i>localhost:4000/aasServer/shells/${aasId}/submodels/${submodelId}/submodel</i><br
 * />
 * 
 * @author schnicke
 */
public class AASServerExecutable {
	// Creates a Logger based on the current class
	private static Logger logger = LoggerFactory.getLogger(AASServerExecutable.class);

	public static void main(String[] args) {
		logger.info("Starting BaSyx AASServer component...");
		// Load context configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromDefaultSource();

		AASServerComponent component = new AASServerComponent(contextConfig);
		component.startComponent();

		logger.info("BaSyx AAS Server component started");
	}
}
