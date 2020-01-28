package org.eclipse.basyx.testsuite.regression.aas.metamodel.connected;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.vab.gateway.ConnectorProviderStub;
import org.eclipse.basyx.testsuite.regression.vab.protocol.TypeDestroyer;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedAssetAdministrationShell can be created and used
 * correctly
 * 
 * @author schnicke
 *
 */
public class TestConnectedAssetAdministrationShell {

	// String constants used in this test case
	private static final IIdentifier smId = new Identifier(IdentifierType.CUSTOM, "smId");
	private static final IIdentifier aasId = new Identifier(IdentifierType.CUSTOM, "aasId");
	private static final String smIdShort = "smName";
	private static final String aasIdShort = "aasName";
	private static final String propId = "propId";
	private static final int propVal = 11;

	IAssetAdministrationShell connectedAAS;

	@Before
	public void build() throws Exception {
		// Create a SubModel containing no operations and one property
		Property p = new Property(propVal);
		p.setIdShort(propId);

		SubModel sm = new SubModel();
		sm.addSubModelElement(p);
		sm.setIdShort(smIdShort);

		// Create descriptor for the SubModel
		SubmodelDescriptor smDescriptor = new SubmodelDescriptor(sm, "");

		// Create an AAS containing a reference to the created SubModel
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.addSubModel(smDescriptor);
		aas.setIdShort(aasIdShort);
	
		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider();
		provider.addSubmodel(smIdShort, new SubModelProvider(TypeDestroyer.destroyType(sm)));
		provider.setAssetAdministrationShell(new AASModelProvider(TypeDestroyer.destroyType(aas)));
	
		// Create AAS registry
		IAASRegistryService registry = new InMemoryRegistry();
		// Create AAS Descriptor
		AASDescriptor aasDescriptor = new AASDescriptor(aasId, "/aas");
		// Create Submodel Descriptor
		SubmodelDescriptor smDescriptor2 = new SubmodelDescriptor(smIdShort, smId, "/aas/submodels/" + smIdShort);
		// Add Submodel descriptor to aas descriptor
		aasDescriptor.addSubmodelDescriptor(smDescriptor2);

		registry.register(aasDescriptor);

		// Create connector provider stub, map address to provider
		ConnectorProviderStub connectorProvider = new ConnectorProviderStub();
		connectorProvider.addMapping("", provider);

		// Create connection manager using the dummy
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry,
				connectorProvider);

		// Create ConnectedAssetAdministrationShell
		connectedAAS = manager.retrieveAAS(aasId);
	}

	/**
	 * Tests the getId() function
	 */
	@Test
	public void testGetId() {
		assertEquals(aasIdShort, connectedAAS.getIdShort());
	}

	/**
	 * Tests retrieving the contained SubModels
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetSubmodel() throws Exception {
		// Check if the number of SubModels is as expected
		assertEquals(1, connectedAAS.getSubModels().size());

		// Check if the contained SubModel id is as expected
		assertTrue(connectedAAS.getSubModels().containsKey(smIdShort));

		// Check if the submodel has been retrieved correctly
		ISubModel sm = connectedAAS.getSubModels().get(smIdShort);
		ISingleProperty prop = (ISingleProperty) sm.getDataElements().get(propId);
		assertEquals(propVal, prop.get());
	}
}
