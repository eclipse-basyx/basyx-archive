package org.eclipse.basyx.regression.processengineconnector.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.preconfigured.PreconfiguredRegistry;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.identifier.IdentifierType;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;


/**
 * 
 * 
 * @author zhangzai
 *
 */
public class TestDeviceAdministrationShell {
	private  ConnectedAssetAdministrationShellManager manager;
	ConnectedAssetAdministrationShell connectedAAS;
	/**
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = new AASHTTPServerResource(new ComponentsRegressionContext());

	@Before
	public void setupConnection() {
		PreconfiguredRegistry registry = new PreconfiguredRegistry();
		ModelUrn coilcarUrn = new ModelUrn("coilcar");
		IIdentifier id = new Identifier(IdentifierType.Custom, "coilcar");
		AASDescriptor ccDescriptor = new AASDescriptor(id,
				"http://localhost:8080/basys.components/Testsuite/Processengine/coilcar/aas");
		IIdentifier smId = new Identifier(IdentifierType.Custom, "submodel1");
		SubmodelDescriptor smDescriptor = new SubmodelDescriptor("submodel1Name", smId,
				"http://localhost:8080/basys.components/Testsuite/Processengine/coilcar/aas/submodels/submodel1");
		ccDescriptor.addSubmodelDescriptor(smDescriptor);
		registry.register(ccDescriptor);
		
		//set-up the administration shell manager to create connected aas
		manager = new ConnectedAssetAdministrationShellManager(registry, new HTTPConnectorProvider());
		
		// create the connected AAS using the manager
		try {
			connectedAAS = manager.retrieveAAS(coilcarUrn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void operationTest() throws Exception {
		assertEquals(1, connectedAAS.getSubModels().size());

		// Check if the contained SubModel id is as expected
		assertTrue(connectedAAS.getSubModels().containsKey("submodel1"));
		ISubModel sm = connectedAAS
				.getSubModels()
				.get("submodel1");
		
		Map<String, IOperation> operations = sm.getOperations();
		assertEquals(2, operations.size());
		
		IOperation op1 = operations.get("liftTo");
		op1.invoke(5);
		IOperation op2 = operations.get("moveTo");
		op2.invoke(55);
	}
}
