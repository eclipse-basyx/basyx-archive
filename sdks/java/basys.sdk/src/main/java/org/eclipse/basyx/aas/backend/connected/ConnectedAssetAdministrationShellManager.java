/**
 * 
 */
package org.eclipse.basyx.aas.backend.connected;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.manager.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.api.metamodel.aas.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.metamodel.aas.ISubModel;
import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.registry.IAASRegistryService;
import org.eclipse.basyx.aas.backend.connected.aas.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.backend.connected.aas.ConnectedSubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.descriptor.SubmodelDescriptor;
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
	public ISubModel retrieveSubModel(ModelUrn aasUrn, String smid) {
		// look up AAS descriptor in the registry
		AASDescriptor aasDescriptor = aasDirectory.lookupAAS(aasUrn);

		// Get submodel descriptor from the aas descriptor
		SubmodelDescriptor smDescriptor = aasDescriptor.getSubModelDescriptor(smid);

		// get address of the submodel descriptor
		String addr = smDescriptor.getFirstEndpoint();

		// Return a new VABElementProxy
		VABElementProxy proxy = new VABElementProxy(VABPathTools.removeAddressEntry(addr),
				providerProvider.getConnector(addr));
		return new ConnectedSubModel(proxy);
	}

	@Override
	public ConnectedAssetAdministrationShell retrieveAAS(ModelUrn aasUrn) throws Exception {
		VABElementProxy proxy = getAASProxyFromURN(aasUrn);
		return new ConnectedAssetAdministrationShell(proxy, this);
	}

	@Override
	public void createAAS(AssetAdministrationShell aas, ModelUrn urn) {
		VABElementProxy proxy = getAASProxyFromURN(urn);

		proxy.createValue("/", aas);
	}

	private VABElementProxy getAASProxyFromURN(ModelUrn aasUrn) {
		// Lookup AAS descriptor
		AASDescriptor aasDescriptor = aasDirectory.lookupAAS(aasUrn);

		// Get AAD address from AAS descriptor
		String addr = aasDescriptor.getFirstEndpoint();

		// Return a new VABElementProxy
		return new VABElementProxy(VABPathTools.removeAddressEntry(addr), providerProvider.getConnector(addr));
	}

	@Override
	public Collection<IAssetAdministrationShell> retrieveAASAll() {
		throw new FeatureNotImplementedException();
	}

	@Override
	public void deleteAAS(String id) throws Exception {
		throw new FeatureNotImplementedException();
	}

	@Override
	public void createSubModel(ModelUrn aasUrn, SubModel submodel) {
		// Lookup AAS descriptor
		AASDescriptor aasDescriptor = aasDirectory.lookupAAS(aasUrn);

		// Get aas endpoint
		String addr = aasDescriptor.getFirstEndpoint();

		// Return a new VABElementProxy
		VABElementProxy proxy = new VABElementProxy(VABPathTools.removeAddressEntry(addr),
				providerProvider.getConnector(addr));

		// Create sm
		proxy.createValue(AssetAdministrationShell.SUBMODELS, submodel);
	}
}
