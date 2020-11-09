package org.eclipse.basyx.testsuite.regression.aas.manager;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
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
		VABElementProxy proxy = new VABElementProxy("/shells", provider);
		connectorProvider.addMapping("shells", proxy);
		connectorProvider.addMapping("", proxy);
 
		// Create an AAS containing a reference to the created SubModel
		AssetAdministrationShell aas = new AssetAdministrationShell(aasIdShort, aasId, new Asset("assetIdShort", new ModelUrn("assetId"), AssetKind.INSTANCE));
		manager.createAAS(aas, aasId, "/shells");

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

}
