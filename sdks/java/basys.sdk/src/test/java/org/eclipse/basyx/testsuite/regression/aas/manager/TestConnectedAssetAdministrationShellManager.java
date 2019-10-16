package org.eclipse.basyx.testsuite.regression.aas.manager;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.preconfigured.PreconfiguredRegistry;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.testsuite.regression.vab.gateway.ConnectorProviderStub;
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
		registry = new PreconfiguredRegistry();
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
		String aasId = "aasId";
		ModelUrn urn = new ModelUrn(aasId);

		// Register AAS at directory
		registry.register(urn, new AASDescriptor(aasId, IdentifierType.URI, "/aas"));
		connectorProvider.addMapping("/aas", new VABMultiSubmodelProvider());

		// Create an AAS containing a reference to the created SubModel
		createAAS(urn, aasId);

		// Retrieve it
		assertEquals(aasId, manager.retrieveAAS(urn).getId());
	}

	private void createAAS(ModelUrn urn, String id) {
		AssetAdministrationShell aas = new MetaModelElementFactory().create(new AssetAdministrationShell(), new HashSet<>());
		aas.setId(id);
		manager.createAAS(aas, urn);
	}

	@Test
	public void testCreateSubModel() {
		String aasId = "aasId";
		String smId = "smId";
		ModelUrn urn = new ModelUrn(aasId);

		// Register AAS at directory
		AASDescriptor desc = new AASDescriptor(aasId, IdentifierType.URI, "/aas");
		desc.addSubmodelDescriptor(new SubmodelDescriptor(smId, IdentifierType.URI, "/aas/submodels/" + smId));
		registry.register(urn, desc);
		IModelProvider provider = new VABMultiSubmodelProvider(new AASModelProvider(new HashMap<>()));
		connectorProvider.addMapping("/aas", provider);
		connectorProvider.addMapping("/aas/submodels/" + smId, provider);

		// Create sub model
		SubModel submodel = new SubModel();

		// - Add example properties to sub model
		submodel.setId(smId);
		SingleProperty prop1 = new SingleProperty(7);
		prop1.setId("prop1");
		submodel.addSubModelElement(prop1);

		SingleProperty prop2 = new SingleProperty("myStr");
		prop1.setId("prop2");
		submodel.addSubModelElement(prop2);

		manager.createSubModel(urn, submodel);

		ISubModel sm = manager.retrieveSubModel(urn, smId);
		assertEquals(smId, sm.getId());
	}

}
