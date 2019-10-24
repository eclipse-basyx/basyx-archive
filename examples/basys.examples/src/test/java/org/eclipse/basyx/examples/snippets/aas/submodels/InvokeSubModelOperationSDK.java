package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Code snippet that illustrates the definition and invocation of sub model operations
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object.
 * 
 * @author kuhn
 *
 */
public class InvokeSubModelOperationSDK {

	
	/**
	 * Example sub model. This example sub model is created with the BaSyx SDK factory and defines the AAS meta model properties
	 */
	static class SampleSubModel extends SubModel {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor - create sub model
		 * 
		 * This sub model contains operations
		 */
		public SampleSubModel() {
			// Set sub model ID
			setIdShort("sm-001");

			// Support creation of properties and operations
			MetaModelElementFactory fac = new MetaModelElementFactory();

			// Define operations
			Operation op1 = new Operation();
			op1.setIdShort("operation1");
			getOperations().put("operation1", fac.createOperation(op1, (Function<Object[], Object>) (v) -> {
				return operation1();
			}));
			Operation op2 = new Operation();
			op2.setIdShort("operation2");
			getOperations().put("operation2", fac.createOperation(op2, (Function<Object[], Object>) (v) -> {
				return operation2((int) v[0], (int) v[1]);
			}));

		}
		
		/**
		 * Example operation implementation
		 */
		public int operation1() {
			return 4;
		}
		
		/**
		 * Example operation with parameter
		 */
		public int operation2(int par1, int par2) {
			return par1+par2;
		}
	}

	
	
	/**
	 * Create VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to 
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(
			// Add example specific mappings
			new ExampleAASRegistry()
					.addAASMapping("aas-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/")
					.addSubmodelMapping("aas-001", "sm-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/"),
			// We connect via HTTP
			new HTTPConnectorProvider());
	
	
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
					addServletMapping("/Testsuite/components/BaSys/1.0/SampleModel/*",  new SubmodelServlet(new SampleSubModel()))
			);

	
	/**
	 * Run code snippet. Access sub model, query data
	 */
	@Test
	public void accessSubModel() throws Exception {
		// Retrieve sub model (created by factory) with SDK connector
		{
			// Create and connect SDK connector
			ISubModel subModel = manager.retrieveSubModel(new ModelUrn("aas-001"), new ModelUrn("sm-001"));
			
			// Sub model operations
			Map<String, IOperation> operations = subModel.getOperations();
			
			// Get first operation by name
			IOperation op1 = operations.get("operation1");
			// - Invoke operation
			int result1 = (int) op1.invoke();

			// Get second operation by name
			IOperation op2 = operations.get("operation2");
			// - Invoke operation
			int result2 = (int) op2.invoke(7, 5);

			
			// Check results
			assertTrue(result1 ==  4);
			assertTrue(result2 == 12);
		}
	}
}


