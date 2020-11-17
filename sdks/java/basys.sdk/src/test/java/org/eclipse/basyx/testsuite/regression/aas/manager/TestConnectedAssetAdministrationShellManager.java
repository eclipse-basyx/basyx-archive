package org.eclipse.basyx.testsuite.regression.aas.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.testsuite.regression.vab.gateway.ConnectorProviderStub;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests ConnectedAssetAdministrationShellManager class
 * 
 * @author schnicke
 *
 */
public class TestConnectedAssetAdministrationShellManager {
	ConnectedAssetAdministrationShellManager manager;
	ConnectorProviderStub connectorProvider;
	IAASRegistryService registry;
	
	/**
	 * Create infrastructure
	 */
	@Before
	public void build() {
		registry = new InMemoryRegistry();
		connectorProvider = new ConnectorProviderStub();

		// Create connection manager using the dummy
		manager = new ConnectedAssetAdministrationShellManager(registry, connectorProvider);
	}

	/**
	 * Tests creation of aas
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateAAS() throws Exception {
		// Register AAS at directory
		IIdentifier aasId = new Identifier(IdentifierType.CUSTOM, "aasId");
		String aasIdShort = "aasName";
		IModelProvider provider = new AASAggregatorProvider(new AASAggregator());
		prepareConnectorProvider(provider);
 
		// Create an AAS containing a reference to the created SubModel
		AssetAdministrationShell aas = createTestAAS(aasId, aasIdShort);
		manager.createAAS(aas, "/shells");

		// Check descriptor for correct endpoint
		String endpoint = registry.lookupAAS(aasId).getFirstEndpoint();
		assertEquals(AASAggregatorProvider.PREFIX + "/" + aasId.getId() + "/aas", endpoint);

		// Retrieve it
		ConnectedAssetAdministrationShell connectedAAS = manager.retrieveAAS(aasId);
		assertEquals(aasIdShort, connectedAAS.getIdShort());
		assertEquals(aasId.getId(), connectedAAS.getIdentification().getId());
		assertEquals(aasId.getIdType(), connectedAAS.getIdentification().getIdType());
	}


	@Test
	public void testCreateSubModel() throws Exception {
		IIdentifier aasId = new Identifier(IdentifierType.CUSTOM, "aasId");
		IIdentifier smId = new Identifier(IdentifierType.CUSTOM, "smId");
		String smIdShort = "smName";

		// Register AAS at directory
		AASDescriptor desc = new AASDescriptor(aasId, "/aas");
		registry.register(desc);
		IModelProvider provider = new VABMultiSubmodelProvider(new AASModelProvider(new AssetAdministrationShell()));
		connectorProvider.addMapping("", provider);

		// Create sub model
		SubModel submodel = new SubModel();
		submodel.setIdShort(smIdShort);
		submodel.setIdentification(smId.getIdType(), smId.getId());

		// - Add example properties to sub model
		Property prop1 = new Property(7);
		prop1.setIdShort("prop1");
		submodel.addSubModelElement(prop1);

		Property prop2 = new Property("myStr");
		prop2.setIdShort("prop2");
		submodel.addSubModelElement(prop2);

		// - Retrieve submodel using the SDK connector
		manager.createSubModel(aasId, submodel);
		ISubModel sm = manager.retrieveSubModel(aasId, smId);

		// - check id and properties
		IProperty prop1Connected = sm.getProperties().get("prop1");
		IProperty prop2Connected = sm.getProperties().get("prop2");

		assertEquals(smIdShort, sm.getIdShort());
		assertEquals(smId.getId(), sm.getIdentification().getId());
		assertEquals(smId.getIdType(), sm.getIdentification().getIdType());
		assertEquals("prop1", prop1Connected.getIdShort());
		assertEquals(7, prop1Connected.getValue());
		assertEquals("prop2", prop2Connected.getIdShort());
		assertEquals("myStr", prop2Connected.getValue());
	}

	@Test
	public void testDeleteSubmodel() {
		IIdentifier aasId = new Identifier(IdentifierType.CUSTOM, "aasId");
		String aasIdShort = "aasName";

		IIdentifier smId = new Identifier(IdentifierType.CUSTOM, "smId");
		String smIdShort = "smName";
		
		IModelProvider provider = new AASAggregatorProvider(new AASAggregator());
		prepareConnectorProvider(provider);

		AssetAdministrationShell aas = createTestAAS(aasId, aasIdShort);
		manager.createAAS(aas, "/shells");

		SubModel sm = new SubModel(smIdShort, smId);
		manager.createSubModel(aasId, sm);

		// Assert everything was created correctly
		IAssetAdministrationShell connectedAAS = manager.retrieveAAS(aasId);
		ISubModel connectedSm = connectedAAS.getSubModels().get(sm.getIdShort());

		assertEquals(sm.getIdShort(), connectedSm.getIdShort());

		manager.deleteSubModel(aasId, smId);
		assertFalse(connectedAAS.getSubModels().containsKey(smIdShort));
	}

	@Test
	public void testDeleteAAS() {
		IIdentifier aasId = new Identifier(IdentifierType.CUSTOM, "aasId");
		String aasIdShort = "aasName";

		IModelProvider provider = new AASAggregatorProvider(new AASAggregator());
		prepareConnectorProvider(provider);

		AssetAdministrationShell aas = createTestAAS(aasId, aasIdShort);
		manager.createAAS(aas, "/shells");
		manager.deleteAAS(aas.getIdentification());
		try {
			manager.retrieveAAS(aas.getIdentification());
			fail();
		} catch (ResourceNotFoundException e) {
			// Expected
		}
	}

	/**
	 * Tries to retrieve a nonexistent AAS
	 */
	@Test
	public void testRetrieveNonexistentAAS() {
		IModelProvider provider = new AASAggregatorProvider(new AASAggregator());
		prepareConnectorProvider(provider);
		
		IIdentifier nonexistentAASId = new Identifier(IdentifierType.CUSTOM, "nonexistentAAS");
		
		// Try to retrieve a nonexistent AAS
		try {
			manager.retrieveAAS(nonexistentAASId);
			fail();
		} catch (ResourceNotFoundException e) {
		}
	}
	
	/**
	 * Tries to retrieve a nonexistent Submodel from a nonexistent AAS
	 */
	@Test
	public void testRetrieveNonexistentSMFromNonexistentSM() {
		IModelProvider provider = new AASAggregatorProvider(new AASAggregator());
		prepareConnectorProvider(provider);
		
		IIdentifier nonexistentAASId = new Identifier(IdentifierType.CUSTOM, "nonexistentAAS");
		IIdentifier nonexistentSMId = new Identifier(IdentifierType.CUSTOM, "nonexistentSM");
		
		// Try to retrieve a nonexistent Submodel from a nonexistent AAS
		try {
			manager.retrieveSubModel(nonexistentAASId, nonexistentSMId);
			fail();
		} catch (ResourceNotFoundException e) {
		}
	}
	
	/**
	 * Tries to retrieve a nonexistent Submodel from an existing AAS
	 */
	@Test
	public void testRetrieveNonexistentSMFromExistentAAS() {
		IModelProvider provider = new AASAggregatorProvider(new AASAggregator());
		prepareConnectorProvider(provider);
		
		IIdentifier aasId = new Identifier(IdentifierType.CUSTOM, "aasId");
		IIdentifier nonexistentSMId = new Identifier(IdentifierType.CUSTOM, "nonexistentSM");
		
		// Try to retrieve a nonexistent Submodel from an existing AAS
		try {
			manager.retrieveSubModel(aasId, nonexistentSMId);
			fail();
		} catch (ResourceNotFoundException e) {
		}
	}
	
	/**
	 * @param provider
	 */
	private void prepareConnectorProvider(IModelProvider provider) {
		VABElementProxy proxy = new VABElementProxy("/shells", provider);
		connectorProvider.addMapping("shells", proxy);
		connectorProvider.addMapping("", proxy);
	}

	private AssetAdministrationShell createTestAAS(IIdentifier aasId, String aasIdShort) {
		AssetAdministrationShell aas = new AssetAdministrationShell(aasIdShort, aasId, new Asset("assetIdShort", new ModelUrn("assetId"), AssetKind.INSTANCE));
		return aas;
	}
}
