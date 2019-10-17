package org.eclipse.basyx.examples.snippets.aas.deployment.http;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.components.servlet.submodel.AASServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Code snippet that illustrates the creation of an Asset Administration Shell (AAS) by extending an SDK class
 * 
 * The AAS is deployed to an AASServlet instance running on a Apache Tomcat HTTP server. The AASServlet exports
 * an Asset Administration Shell via the defined BaSyx REST interface. 
 * 
 * @author kuhn
 *
 */
public class ConnectToRemoteAAS {

	
	/**
	 * Example Asset Administration Shell
	 */
	static class ExampleAssetAdministrationShell extends AssetAdministrationShell {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor
		 */
		public ExampleAssetAdministrationShell() {
			// Set Asset Administration Shell ID
			setIdShort("aas-001");
		}
	}

	
	
	/**
	 * The BaSyx Deployment instantiates and starts context elements for this example. 
	 * 
	 * This example instantiates the BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory
	 * example context that creates one AAS server, and one SQL based AAS registry.
	 * 
	 * BaSyxDeployment contexts instantiate all components on the IP address of the host. 
	 * Therefore, all components use the same IP address. 
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
				// Servlets for example snippet
				new BaSyxExamplesContext_Empty().
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/SampleAAS/*",         new AASServlet(new ExampleAssetAdministrationShell()))
			);

	
	
	/**
	 * Run code snippet. Connect to AAS on server, access AAS properties. 
	 */
	@Test
	public void connectToAAS() throws Exception {
		// Create AAS Registry to store meta-infomation using aas descriptor
		// This is a pre-configured aas registry that resolves urn to aas-descriptor
		ExampleAASRegistry registry = new ExampleAASRegistry();
		registry.addAASMapping("aas-001",
				"http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleAAS/aas");

		// Create manager using the directory stub an the HTTPConnectorProvider
		// - Connect to VAB object by ID. The connection manager looks up this ID in
		//   its directory
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry,
				// We connect via HTTP
				new HTTPConnectorProvider());
		
		// Retrieve the AAS from the AAS server with SDK connector
		// - IAssetAdministrationShell is the interface for the local AAS proxy
		IAssetAdministrationShell shell = manager
				.retrieveAAS(new ModelUrn("aas-001"));
		// - Retrieve AAS values and compare to expected values
		Object propertyId = shell.getIdShort();
		
		
		// Check result
		assertTrue(propertyId.equals("aas-001"));
	}
}


