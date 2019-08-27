/**
 * 
 */
package org.eclipse.basyx.aas.backend.connected;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.manager.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.registry.IAASRegistryService;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.backend.connected.aas.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.backend.connected.aas.ConnectedSubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.vab.core.IConnectorProvider;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.core.tools.VABPathTools;

/**
 * Implement a AAS manager backend that communicates via HTTP/REST<br />
 * <br />
 * 
 * @author kuhn, schnicke
 *
 */
public class ConnectedAssetAdministrationShellManager implements IAssetAdministrationShellManager {

	protected IAASRegistryService aasDirectory;

	protected IConnectorProvider providerProvider;

	/**
	 * @param networkDirectoryService
	 * @param providerProvider
	 */
	public ConnectedAssetAdministrationShellManager(IAASRegistryService directory,
			IConnectorProvider provider) {
		this.aasDirectory = directory;
		this.providerProvider = provider;
	}

	@Override
	public IAssetAdministrationShell createAAS(IAssetAdministrationShell aas) throws Exception {
		throw new FeatureNotImplementedException();
	}

	public ConnectedSubModel retrieveSM(String smid, ModelUrn aasUrn) {
		// look up AAS descriptor in the registry
		AASDescriptor aasDescriptor = aasDirectory.lookupAAS(aasUrn);

		// Get submodel descriptor from the aas descriptor
		SubmodelDescriptor smDescriptor = aasDescriptor.getSubModelDescriptor(smid);

		// get address of the submodel descriptor
		String addr = smDescriptor.getFirstEndpoint();

		// Return a new VABElementProxy
		VABElementProxy proxy = new VABElementProxy(VABPathTools.removeAddressEntry(addr),
				providerProvider.getConnector(addr));
		return new ConnectedSubModel("/aas/submodels/" + smid, proxy);
	}

	@Override
	public ConnectedAssetAdministrationShell retrieveAAS(ModelUrn aasUrn) throws Exception {
		// Lookup AAS descriptor
		AASDescriptor aasDescriptor = aasDirectory.lookupAAS(aasUrn);

		// Get AAD address from AAS descriptor
		String addr = aasDescriptor.getFirstEndpoint();

		// Return a new VABElementProxy
		VABElementProxy proxy = new VABElementProxy(VABPathTools.removeAddressEntry(addr),
				providerProvider.getConnector(addr));
		return new ConnectedAssetAdministrationShell("/aas", proxy, this);
	}

	@Override
	public Collection<IAssetAdministrationShell> retrieveAASAll() {
		throw new FeatureNotImplementedException();
	}

	@Override
	public void deleteAAS(String id) throws Exception {
		throw new FeatureNotImplementedException();
	}

}
