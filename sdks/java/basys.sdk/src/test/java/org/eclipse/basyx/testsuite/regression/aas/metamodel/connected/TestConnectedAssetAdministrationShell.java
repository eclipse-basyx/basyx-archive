package org.eclipse.basyx.testsuite.regression.aas.metamodel.connected;

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
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.aas.metamodel.AssetAdministrationShellSuite;
import org.eclipse.basyx.testsuite.regression.vab.gateway.ConnectorProviderStub;
import org.eclipse.basyx.vab.support.TypeDestroyer;
import org.junit.Before;

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
		provider.addSubmodel(SMIDSHORT, new SubModelProvider(SubModel.createAsFacade(TypeDestroyer.destroyType(sm))));

		// Create AAS registry
		IAASRegistryService registry = new InMemoryRegistry();
		// Create AAS Descriptor
		AASDescriptor aasDescriptor = new AASDescriptor(AASID, "/aas");
		// Create Submodel Descriptor
		SubmodelDescriptor smDescriptor2 = new SubmodelDescriptor(SMIDSHORT, SMID, "/aas/submodels/" + SMIDSHORT);
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
}
