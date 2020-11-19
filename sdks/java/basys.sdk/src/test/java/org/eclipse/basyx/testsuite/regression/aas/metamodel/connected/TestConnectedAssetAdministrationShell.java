package org.eclipse.basyx.testsuite.regression.aas.metamodel.connected;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.aas.metamodel.AssetAdministrationShellSuite;
import org.eclipse.basyx.testsuite.regression.vab.gateway.ConnectorProviderStub;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.support.TypeDestroyer;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the connected implementation of {@link IAssetAdministrationShell} based
 * on the AAS test suite <br />
 * 
 * @author schnicke
 *
 */
public class TestConnectedAssetAdministrationShell extends AssetAdministrationShellSuite {

	static ConnectedAssetAdministrationShell connectedAAS;

	@Before
	public void build() throws Exception {
		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider();
		AssetAdministrationShell shell = retrieveBaselineShell();
		provider.setAssetAdministrationShell(new AASModelProvider(AssetAdministrationShell.createAsFacade(TypeDestroyer.destroyType(shell))));

		SubModel sm = retrieveBaselineSM();
		sm.setParent(shell.getReference());
		provider.addSubmodel(new SubModelProvider(SubModel.createAsFacade(TypeDestroyer.destroyType(sm))));

		// Create AAS registry
		IAASRegistryService registry = new InMemoryRegistry();
		// Create AAS Descriptor
		AASDescriptor aasDescriptor = new AASDescriptor(AASID, "/aas");
		// Create Submodel Descriptor
		SubmodelDescriptor smDescriptor2 = new SubmodelDescriptor(SMIDSHORT, SMID, "/aas/submodels/" + SMIDSHORT + "/submodel");
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
		connectedAAS = manager.retrieveAAS(AASID);
	}

	@Override
	protected ConnectedAssetAdministrationShell retrieveShell() {
		return connectedAAS;
	}

	@Test
	public void testGetSpecificSubmodel() {
		ISubModel sm = retrieveShell().getSubmodel(SMID);
		assertEquals(SMIDSHORT, sm.getIdShort());
	}

	@Test
	public void testDeleteSubmodel() {
		retrieveShell().removeSubmodel(SMID);
		assertFalse(retrieveShell().getSubModels().containsKey(SMIDSHORT));
	}

	@Test
	public void testGetLocalCopy() {
		AASModelProvider aasProvider = new AASModelProvider(retrieveBaselineShell());
		ConnectedAssetAdministrationShell localCAAS = new ConnectedAssetAdministrationShell(new VABElementProxy("", aasProvider));

		assertEquals(retrieveBaselineShell(), localCAAS.getLocalCopy());
	}
}
