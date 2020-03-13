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

	private static String registryUrl;
	private IAASRegistryService registry;

	private String aasShortId = "aas1";
	private IIdentifier aasId = new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:1:registryAAS#001");
	private String aasEndpoint = "http://localhost:4000/registry/aas1/aas";
	private String submodelShortId = "submodel1";
	private String assetId = "asset1";

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

		// registryUrl = "http://localhost:4000/registry";
		registryUrl = "http://localhost:" + dockerConfig.getHostPort() + contextConfig.getContextPath();
		logger.info("Registry URL for integration test: " + registryUrl);
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
