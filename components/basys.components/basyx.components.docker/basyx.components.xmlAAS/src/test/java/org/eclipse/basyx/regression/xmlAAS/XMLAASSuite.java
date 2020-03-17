package org.eclipse.basyx.regression.xmlAAS;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
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
public class XMLAASSuite {

	protected IAASRegistryService registry;

	protected static final String aasShortId = "aas1";
	protected static final IIdentifier aasId = new ModelUrn("www.admin-shell.io/aas-sample/2/0");
	protected static final String submodelShortId = "submodel1";

	protected static String aasEndpoint;

	/**
	 * Before each test, a dummy registry is created and an AAS is added in the
	 * registry
	 */
	@Before
	public void setUp() {
		try {
			// Create a dummy registry to test integration of XML AAS
			registry = new InMemoryRegistry();
			AASDescriptor descriptor = new AASDescriptor(aasShortId, aasId, aasEndpoint);
			registry.register(descriptor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSingleAAS() throws Exception {
		ConnectedAssetAdministrationShell connectedAssetAdministrationShell = getConnectedAssetAdministrationShell();
		assertEquals(aasShortId, connectedAssetAdministrationShell.getIdShort());
	}

	@Test
	public void testGetSingleSubmodel() throws Exception {
		ConnectedAssetAdministrationShell connectedAssetAdministrationShell = getConnectedAssetAdministrationShell();
		Map<String, ISubModel> submodels = connectedAssetAdministrationShell.getSubModels();
		ISubModel subModel = submodels.get(submodelShortId);
		assertEquals(submodelShortId, subModel.getIdShort());
	}

	/**
	 * Get connected Asset Administration Shell from the registry
	 * 
	 * @return connected AAS
	 * @throws Exception
	 */
	private ConnectedAssetAdministrationShell getConnectedAssetAdministrationShell() throws Exception {
		// Create a ConnectedAssetAdministrationShell using a
		// ConnectedAssetAdministrationShellManager
		IConnectorProvider connectorProvider = new HTTPConnectorProvider();
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry, connectorProvider);

		// return the connected AAS
		return manager.retrieveAAS(aasId);
	}
}
