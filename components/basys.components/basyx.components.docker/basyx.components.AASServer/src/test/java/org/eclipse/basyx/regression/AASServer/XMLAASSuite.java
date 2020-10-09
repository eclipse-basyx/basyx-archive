package org.eclipse.basyx.regression.AASServer;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
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
public abstract class XMLAASSuite {

	protected IAASRegistryService registry;

	protected static final String aasShortId = "aas1";
	protected static final ModelUrn aasId = new ModelUrn("www.admin-shell.io/aas-sample/2/0");
	protected static final ModelUrn smId = new ModelUrn("http://www.zvei.de/demo/submodel/12345679");
	protected static final String smShortId = "submodel1";

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
		registry = new InMemoryRegistry();
		AASDescriptor descriptor = new AASDescriptor(aasShortId, aasId, aasEndpoint);
		descriptor.addSubmodelDescriptor(new SubmodelDescriptor(smShortId, smId, smEndpoint));
		registry.register(descriptor);

		// Create a ConnectedAssetAdministrationShell using a
		// ConnectedAssetAdministrationShellManager
		IConnectorProvider connectorProvider = new HTTPConnectorProvider();
		manager = new ConnectedAssetAdministrationShellManager(registry, connectorProvider);
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
