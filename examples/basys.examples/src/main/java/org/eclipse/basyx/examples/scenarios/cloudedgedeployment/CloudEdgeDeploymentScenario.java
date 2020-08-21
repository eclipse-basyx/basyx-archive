package org.eclipse.basyx.examples.scenarios.cloudedgedeployment;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.AASServerComponent;
import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.components.InMemoryRegistryComponent;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;


/**
 * Example scenario demonstrating a deployment with two servers
 * 
 * Server A is created as an empty server in the cloud.
 * An AAS and a Submodel is pushed to it.
 * 
 * Server B is created as a server hosted near a machine.
 * It provides a Submodel containing sensor value.
 * 
 * @author conradi, schnicke
 *
 */
public class CloudEdgeDeploymentScenario {
	
	/**
	 * The registry used in the manager
	 */
	private IAASRegistryService registry;
	public static String registryPath = "http://localhost:8080/registry";
	
	/**
	 * AASManager used to handle registration and server communication
	 */
	private ConnectedAssetAdministrationShellManager aasManager;
	
	/**
	 * Identifier of the AAS hosted in the cloud.
	 */
	public IIdentifier aasIdentifier = ComponentBuilder.getAAS().getIdentification();

	/**
	 * Identifier of the SM hosted in the cloud.
	 * It contains never changing properties of the machine.
	 */
	public IIdentifier docuSmIdentifier = ComponentBuilder.getDocuSMDescriptor().getIdentifier();

	/**
	 * Identifier of the SM hosted near the machine.
	 * It contains constantly changing sensor data.
	 */
	public IIdentifier edgeSmIdentifier = ComponentBuilder.getEdgeSubmodelDescriptor().getIdentifier();

	// Used for shutting down the scenario
	private List<IComponent> startedComponents = new ArrayList<>();
	private AASHTTPServer edgeServer;

	/**
	 * Main method to start the scenario
	 * 
	 */
	public static void main(String[] args) {
		new CloudEdgeDeploymentScenario();
	}
	
	/**
	 * Setup the scenario
	 * 
	 */
	public CloudEdgeDeploymentScenario() {
		
		startupRegistryServer();
		startupEdgeServer();
		startupCloudServer();
		
		
		// Create a InMemoryRegistry to be used by the manager
		registry = new AASRegistryProxy(registryPath);
		
		// Create a ConnectedAASManager with the registry created above
		aasManager = new ConnectedAssetAdministrationShellManager(registry);
		
		
		// Push the AAS to the cloud server
		// The manager automatically registers it in the registry
		aasManager.createAAS(ComponentBuilder.getAAS(), aasIdentifier, "http://localhost:8081/cloud");
		
		
		// Get the docuSubmodel from the ComponentBuilder
		SubModel docuSubmodel = ComponentBuilder.getDocuSM();
		
		// Push the docuSubmodel to the cloud
		// The manager automatically registers it in the registry
		aasManager.createSubModel(aasIdentifier, docuSubmodel);
		

		// Add the already existing edgeSM to the descriptor of the aas
		registry.register(aasIdentifier, ComponentBuilder.getEdgeSubmodelDescriptor());
	}
	
	/**
	 * Startup an empty registry at "http://localhost:8080/registry"
	 * 
	 */
	private void startupRegistryServer() {
		IComponent component = new InMemoryRegistryComponent("localhost", 8080, "registry", "");
		component.startComponent();
		startedComponents.add(component);
	}

	/**
	 * Startup a server responsible for hosting the "current_temp" edgeSubModel
	 * at the endpoint "http://localhost:8082/oven/current_temp"
	 * 
	 * In this example this server is hosted close to the machine
	 * and the values it provides are constantly updated.
	 * 
	 */
	private void startupEdgeServer() {
		
		// Create a BaSyxConetxt for port 8082 with an empty endpoint and the tmpdir for storing its data
		BaSyxContext context = new BaSyxContext("", System.getProperty("java.io.tmpdir"), "localhost", 8082);
		
		// Get the edgeSubmodel from the ComponentBuilder
		SubModel edgeSubmodel = ComponentBuilder.createEdgeSubModel();
		
		// Create a new SubmodelServlet containing the edgeSubmodel
		SubmodelServlet smServlet = new SubmodelServlet(edgeSubmodel);
		
		// Add the SubmodelServlet mapping to the context at the path "/oven/current_temp"
		context.addServletMapping("/oven/current_temp/*", smServlet);
		
		
		// Create and start a HTTP server with the context created above
		edgeServer = new AASHTTPServer(context);
		edgeServer.start();
	}
	
	/**
	 * Startup an empty server at "http://localhost:8081/cloud"
	 * 
	 * In this example this server is hosted in the cloud.
	 * It holds the AAS and the Submodels which are containing static non changing values.
	 * 
	 */
	private void startupCloudServer() {
		
		// Create a server at port 8081 with the endpoint "/cloud"
		AASServerComponent cloudServer = new AASServerComponent("localhost", 8081, "/cloud", "/");
		
		// Start the created server
		cloudServer.startComponent();
		startedComponents.add(cloudServer);
	}

	public void stop() {
		startedComponents.stream().forEach(IComponent::stopComponent);
		edgeServer.shutdown();
	}

}