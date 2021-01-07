package org.eclipse.basyx.examples.snippets.property;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.examples.snippets.submodel.HostPreconfiguredSubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnector;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.junit.After;
import org.junit.Test;

/**
 * Test for the CreateLambdaProperty snippet
 * 
 * @author schnicke
 *
 */
public class TestCreateLambdaProperty {

	// Value to be facaded by the lambda property
	private int testValue = 15;

	// Used for test case tear down
	private AASHTTPServer server;

	@Test
	public void testCreateLambdaProperty() {
		// The type cast in the setter is necessary due to the Consumer<Object> definition
		Property lambdaProp = CreateLambdaProperty.createLambdaProperty("lambdaProp", PropertyValueTypeDef.Integer, () -> testValue, (v) -> testValue = (int) v);

		// Package it in a test submodel and host it
		SubModel sm = new SubModel("testSubmodelIdShort", new CustomId("testSubmodelId"));
		sm.addSubModelElement(lambdaProp);
		
		// Create the context configuration and host the submodel containg the lambda property
		BaSyxContextConfiguration config = new BaSyxContextConfiguration(4040, "");
		server = HostPreconfiguredSubmodel.hostPreconfiguredSubmodel(config, sm);

		// Here, for simplicity reason of the test, the ConnectedSubmodel is created by hand.
		// In a real-world application, the AASManager would be used instead.
		String smPath = "http://localhost:4040/" + sm.getIdShort() + "/submodel";

		ConnectedSubModel cSm = new ConnectedSubModel(new VABElementProxy("", new JSONConnector(new HTTPConnector(smPath))));
		IProperty cLambdaProp = (IProperty) cSm.getSubmodelElement(lambdaProp.getIdShort());

		// Test getting
		assertEquals(testValue, cLambdaProp.getValue());

		// Test setting
		int newVal = 100;
		cLambdaProp.setValue(newVal);
		assertEquals(newVal, cLambdaProp.getValue());
		assertEquals(newVal, testValue);
	}

	// Ensures that the started server is shut down regardless of test result
	@After
	public void tearDown() {
		if (server != null) {
			server.shutdown();
		}
	}
}
