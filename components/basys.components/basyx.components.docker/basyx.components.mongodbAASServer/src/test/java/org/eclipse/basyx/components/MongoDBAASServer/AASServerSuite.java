package org.eclipse.basyx.components.MongoDBAASServer;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Suite for testing that the AAS Server component is set up correctly. The
 * tests here can be used by the component test itself and the integration test
 * 
 * @author espen
 *
 */
public abstract class AASServerSuite {
	protected IAASRegistryService aasRegistry;
	private ConnectedAssetAdministrationShellManager manager;

	private String aasId = "testId";

	protected abstract String getURL();

	@Before
	public void setUp() {
		// Create a dummy registry to test integration of XML AAS
		aasRegistry = new InMemoryRegistry();

		// Create ConnectedAASManager
		IConnectorProvider connectorProvider = new HTTPConnectorProvider();
		manager = new ConnectedAssetAdministrationShellManager(aasRegistry, connectorProvider);
	}

	@Test
	public void testAddAAS() throws Exception {
		AssetAdministrationShell shell = new AssetAdministrationShell();
		IIdentifier identifier = new ModelUrn(aasId);
		shell.setIdentification(identifier);
		shell.setIdShort("aasIdShort");
		manager.createAAS(shell, identifier, getURL());

		IAssetAdministrationShell remote = manager.retrieveAAS(identifier);
		assertEquals(shell.getIdShort(), remote.getIdShort());
	}

}
