package org.eclipse.basyx.regression.AASServer;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.AASServerBackend;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Suite for testing that the JSONAAS servlet is set up correctly. The tests here
 * can be used by the servlet test itself and the integration test
 * 
 * @author JSON
 *
 */
public class TestJSONAASServer {
	private static Logger logger = LoggerFactory.getLogger(TestJSONAASServer.class);

	protected static final String aasShortId = "ExampleMotor";
	protected static final ModelUrn aasId = new ModelUrn("http://customer.com/aas/9175_7013_7091_9168");
	protected static final ModelUrn smId = new ModelUrn("http.//i40.customer.com/type/1/1/7A7104BDAB57E184");
	protected static final String smShortId = "TechnicalData";
	protected static final String smShortId2 = "Documentation";
	protected static final String smShortId3 = "OperationalData";

	// Has to be individualized by each test inheriting from this suite
	protected static String aasEndpoint;
	protected static String smEndpoint;

	// Registry and AAS component
	protected static IAASRegistryService registry;
	protected static AASServerComponent component;
	protected static ConnectedAssetAdministrationShellManager manager;

	@BeforeClass
	public static void setUp() {
		// Setup component's test configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		BaSyxAASServerConfiguration aasConfig = new BaSyxAASServerConfiguration(AASServerBackend.INMEMORY,
				"json/aas.json");

		// Setup endpoints
		String rootEndpoint = "http://" + contextConfig.getHostname() + ":" + contextConfig.getPort() + "/"
				+ contextConfig.getContextPath() + "/";
		aasEndpoint = rootEndpoint + "/" + AASAggregatorProvider.PREFIX + "/" + aasId.getEncodedURN() + "/aas";
		smEndpoint = aasEndpoint + "/submodels/" + smShortId + "/submodel";
		logger.info("AAS URL for servlet test: " + aasEndpoint);

		// Create and start AASServer component
		component = new AASServerComponent(contextConfig, aasConfig);
		registry = new InMemoryRegistry();
		component.setRegistry(registry);
		component.startComponent();

		// Create a ConnectedAssetAdministrationShell using a
		// ConnectedAssetAdministrationShellManager
		IConnectorProvider connectorProvider = new HTTPConnectorProvider();
		manager = new ConnectedAssetAdministrationShellManager(registry, connectorProvider);
	}


	@AfterClass
	public static void tearDown() {
		component.stopComponent();
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

	@Test
	public void testGetAllSubmodels() throws Exception {
		Map<String, ISubModel> subModels = getAllConnectedSubmodels();
		assertEquals(3, subModels.size());
		assertEquals(smShortId, subModels.get(smShortId).getIdShort());
		assertEquals(smShortId2, subModels.get(smShortId2).getIdShort());
		assertEquals(smShortId3, subModels.get(smShortId3).getIdShort());
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

	/**
	 * Gets all connected Submodels
	 * 
	 * @return connected SM
	 * @throws Exception
	 */
	private Map<String, ISubModel> getAllConnectedSubmodels() {
		return manager.retrieveSubmodels(aasId);
	}
}
