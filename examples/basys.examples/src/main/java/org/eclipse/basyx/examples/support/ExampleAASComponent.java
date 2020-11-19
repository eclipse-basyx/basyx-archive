package org.eclipse.basyx.examples.support;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;

/**
 * This class is used to startup an AAS-Server using the AASServerComponent
 * 
 * @author conradi
 *
 */
public class ExampleAASComponent {

	public static final String CONTEXT_PATH = "aasComponent"; 
	
	private int port;
	
	private IAASRegistryService registry;
	
	// Hold a reference to the server to be able to shut it down
	private AASServerComponent aasServer = null;
	
	
	public ExampleAASComponent(int port, IAASRegistryService registry) {
		this.port = port;
		this.registry = registry;
	}
	
	public void startupAASServer() {
		// Create a Configuration telling the component the port to use and the contextPath
		// The contextPath is attached to the address of the server "http://localhost:8080/{contextPath}"
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(port, CONTEXT_PATH);
		aasServer = new AASServerComponent(contextConfig);
		
		// Set the registry to be used by the server
		aasServer.setRegistry(registry);
		
		// Startup the AASServer
		aasServer.startComponent();
	}
	
	public void shutdownAASServer() {
		// If an AASServer was started -> stop it
		if(aasServer != null) {
			aasServer.stopComponent();
		}
	}
	
	public String getAASServerPath() {
		return "http://localhost:" + port + "/" + CONTEXT_PATH;
	}
}
