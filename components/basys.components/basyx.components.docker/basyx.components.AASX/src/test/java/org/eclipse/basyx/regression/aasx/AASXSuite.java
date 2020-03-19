package org.eclipse.basyx.regression.aasx;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Suite for testing that the XMLAAS servlet is set up correctly. The tests here
 * can be used by the servlet test itself and the integration test
 * 
 * @author schnicke
 *
 */
public class AASXSuite {
	protected IAASRegistryService aasRegistry;

	protected static final String aasShortId = "Festo_3S7PM0CP4BD";
	protected static final IIdentifier aasId = new ModelUrn("smart.festo.com/demo/aas/1/1/454576463545648365874");
	protected static final IIdentifier smId = new ModelUrn("www.company.com/ids/sm/4343_5072_7091_3242");
	protected static final String smShortId = "Nameplate";

	// Has to be individualized by each test inheriting from this suite
	protected static String aasEndpoint;
	protected static String smEndpoint;

	private ConnectedAssetAdministrationShellManager manager;

	/**
	 * Before each test, a dummy registry is created and an AAS is added in the
	 * registry
	 */
	@Before
	public void setUp() {
		// Create a dummy registry to test integration of XML AAS
		aasRegistry = new InMemoryRegistry();
		AASDescriptor descriptor = new AASDescriptor(aasShortId, aasId, aasEndpoint);
		descriptor.addSubmodelDescriptor(new SubmodelDescriptor(smShortId, smId, smEndpoint));
		aasRegistry.register(descriptor);

		// Create a ConnectedAssetAdministrationShell using a
		// ConnectedAssetAdministrationShellManager
		IConnectorProvider connectorProvider = new HTTPConnectorProvider();
		manager = new ConnectedAssetAdministrationShellManager(aasRegistry, connectorProvider);
	}

	@Test
	public void testGetSingleAAS() throws Exception {
		ConnectedAssetAdministrationShell connectedAssetAdministrationShell = getConnectedAssetAdministrationShell();
		assertEquals(aasShortId, connectedAssetAdministrationShell.getIdShort());
	}

	@Test
	public void testGetSingleSubmodel() throws Exception {
		ISubModel subModel = getConnectedSubmodel();
		assertEquals(smShortId, subModel.getIdShort());
	}

	/**
	 * Gets the connected Asset Administration Shell
	 * 
	 * @return connected AAS
	 * @throws Exception
	 */
	private ConnectedAssetAdministrationShell getConnectedAssetAdministrationShell() throws Exception {
		return manager.retrieveAAS(aasId);
	}

	/**
	 * Gets the connected Submodel
	 * 
	 * @return connected SM
	 * @throws Exception
	 */
	private ISubModel getConnectedSubmodel() {
		return manager.retrieveSubModel(aasId, smId);
	}
}
