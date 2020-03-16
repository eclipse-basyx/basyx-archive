package org.eclipse.basyx.regression.docker;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.IAsset;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxDockerConfiguration;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITInXMLAAS {
	private static Logger logger = LoggerFactory.getLogger(ITInXMLAAS.class);

	private IAASRegistryService registry;

	private static String aasShortId = "aas1";
	private static IIdentifier aasId = new ModelUrn("www.admin-shell.io/aas-sample/2/0");
	private static String aasEndpoint;
	private static String submodelShortId = "submodel1";
	private static String assetId = "asset1";

	@BeforeClass
	public static void setUpClass() {
		logger.info("Running integration test...");

		logger.info("Loading servlet configuration");
		// Load the servlet configuration inside of the docker configuration from properties file
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);

		// Load the docker environment configuration from properties file
		logger.info("Loading docker configuration");
		BaSyxDockerConfiguration dockerConfig = new BaSyxDockerConfiguration();
		dockerConfig.loadFromResource(BaSyxDockerConfiguration.DEFAULT_CONFIG_PATH);

		aasEndpoint = "http://localhost:" + dockerConfig.getHostPort() + contextConfig.getContextPath() + "/"
				+ aasShortId + "/aas";
		logger.info("AAS URL for integration test: " + aasEndpoint);
	}

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

	/**
	 * Remove registry entries after each test
	 */
	@After
	public void tearDown() {
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

	@Test
	public void testGetSingleAsset() throws Exception {
		ConnectedAssetAdministrationShell connectedAssetAdministrationShell = getConnectedAssetAdministrationShell();
		IAsset asset = connectedAssetAdministrationShell.getAsset();
		assertEquals(asset.getIdShort(), assetId);
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
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry,
				connectorProvider);

		// return the connected AAS
		return manager.retrieveAAS(aasId);
	}
}
