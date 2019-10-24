/**
 * 
 */
package org.eclipse.basyx.aas.manager;

import java.util.Collection;

import org.eclipse.basyx.aas.manager.api.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.vab.exception.FeatureNotImplementedException;
import org.eclipse.basyx.vab.factory.java.ModelProxyFactory;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;

/**
 * Implement a AAS manager backend that communicates via HTTP/REST<br />
 * <br />
 * 
 * @author kuhn, schnicke
 * 
 */
public class ConnectedAssetAdministrationShellManager implements IAssetAdministrationShellManager {

	protected IAASRegistryService aasDirectory;
	protected IConnectorProvider connectorProvider;
	protected ModelProxyFactory proxyFactory;

	/**
	 * @param networkDirectoryService
	 * @param providerProvider
	 */
	public ConnectedAssetAdministrationShellManager(IAASRegistryService directory,
			IConnectorProvider provider) {
		this.aasDirectory = directory;
		this.connectorProvider = provider;
		this.proxyFactory = new ModelProxyFactory(provider);
	}

	@Override
	public ISubModel retrieveSubModel(IIdentifier aasId, IIdentifier smId) {
		// look up AAS descriptor in the registry
		AASDescriptor aasDescriptor = aasDirectory.lookupAAS(aasId);

		// Get submodel descriptor from the aas descriptor
		SubmodelDescriptor smDescriptor = aasDescriptor.getSubModelDescriptor(smId.getId());

		// get address of the submodel descriptor
		String addr = smDescriptor.getFirstEndpoint();

		// Return a new VABElementProxy
		return new ConnectedSubModel(proxyFactory.createProxy(addr));
	}

	@Override
	public ConnectedAssetAdministrationShell retrieveAAS(IIdentifier aasId) throws Exception {
		VABElementProxy proxy = getAASProxyFromId(aasId);
		return new ConnectedAssetAdministrationShell(proxy, this);
	}

	@Override
	public void createAAS(AssetAdministrationShell aas, IIdentifier aasId) {
		VABElementProxy proxy = getAASProxyFromId(aasId);
		proxy.createValue("/", aas);
	}

	private VABElementProxy getAASProxyFromId(IIdentifier aasId) {
		// Lookup AAS descriptor
		AASDescriptor aasDescriptor = aasDirectory.lookupAAS(aasId);

		// Get AAD address from AAS descriptor
		String addr = aasDescriptor.getFirstEndpoint();

		// Return a new VABElementProxy
		return proxyFactory.createProxy(addr);
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
	public void createSubModel(IIdentifier aasId, SubModel submodel) {
		// Lookup AAS descriptor
		AASDescriptor aasDescriptor = aasDirectory.lookupAAS(aasId);

		// Get aas endpoint
		String addr = aasDescriptor.getFirstEndpoint();

		// Return a new VABElementProxy
		VABElementProxy proxy = proxyFactory.createProxy(addr);

		// Create sm
		proxy.createValue(AssetAdministrationShell.SUBMODELS, submodel);
	}
}
