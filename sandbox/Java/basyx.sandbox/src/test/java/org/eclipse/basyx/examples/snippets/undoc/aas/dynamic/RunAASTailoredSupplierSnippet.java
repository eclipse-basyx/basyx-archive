package org.eclipse.basyx.examples.snippets.undoc.aas.dynamic;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.basyx.components.servlet.vab.VABLambdaServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Example for a tailored BaSyx supplier
 * 
 * - BaSyx will serialize this class (and all contained references) and transmit it to the AAS server
 * 
 * @author kuhn
 *
 */
class TailoredBaSyxSupplier implements Supplier<Object>, Serializable {

	/**
	 * Version number of serialized instances
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Return value
	 */
	@Override
	public Object get() {
		// Delegate call to tailored BaSyx supplier base class
		return getInternal();
	}

	
	/**
	 * Example function of tailored BaSyx supplier base class
	 */
	protected String getInternal() {
		return "BaSyxSupplier!";
	}
}




/**
 * Illustrate the dynamic deployment of AAS operations with a tailored consumer
 * 
 * @author kuhn
 *
 */
public class RunAASTailoredSupplierSnippet {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			new ExamplesPreconfiguredDirectory()
				// Add example specific mappings
			    .addMapping("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/"),
			new HTTPConnectorProvider());

	
	/**
	 * Instantiate and start context elements for this example. BaSyxDeployment contexts instantiate all
	 * components on the IP address of the host. Therefore, all components use the same IP address. 
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
				// Simulated servlets
				// - BaSys topology with one AAS Server and one SQL directory
				new BaSyxExamplesContext().
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/devicestatusVAB/*", new VABLambdaServlet())
			);

		
	
	/**
	 * Test basic queries
	 */
	@Test
	public void snippet() throws Exception {

		// Server connections
		// - Connect to device (VAB object)
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		
		// Create properties on AAS

		// - Add example properties
		SubModel submodel = new SubModel();
		submodel.setIdShort("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");
		Property prop1 = new Property(7);
		prop1.setIdShort("prop1");
		submodel.addSubModelElement(prop1);

		Property prop2 = new Property("myStr");
		prop2.setIdShort("prop2");
		submodel.addSubModelElement(prop2);

		// - Transfer sub model to server
		connSubModel1.setModelPropertyValue("/", submodel);

		
		// Read property values
		String prop2Val = (String) connSubModel1.getModelPropertyValue("submodelElements/prop2/value");
		// - Check property values
		assertTrue(prop2Val.equals("myStr"));

		
		// Create dynamic get/set operation as lambda expression
		Map<String, Object> dynamicPropertyVal = VABLambdaProviderHelper.createSimple(new TailoredBaSyxSupplier(), null);
		// - Update property properties/dynamicExample with dynamic get/set operation
		connSubModel1.setModelPropertyValue("submodelElements/prop2/value", dynamicPropertyVal);

		// Read dynamicExample property
		prop2Val = (String) connSubModel1.getModelPropertyValue("submodelElements/prop2/value");

		// - Check value
		assertTrue(prop2Val.equals("BaSyxSupplier!"));
	}
}

