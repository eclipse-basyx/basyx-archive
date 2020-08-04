/**
 * 
 */
package org.eclipse.basyx.aas.manager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.aggregator.proxy.AASAggregatorProxy;
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
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;

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
	 * Creates a manager assuming an HTTP connection
	 * 
	 * @param directory
	 */
	public ConnectedAssetAdministrationShellManager(IAASRegistryService directory) {
		this(directory, new HTTPConnectorProvider());
	}

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
		SubmodelDescriptor smDescriptor = aasDescriptor.getSubModelDescriptorFromIdentifierId(smId.getId());

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
	public Map<String, ISubModel> retrieveSubmodels(IIdentifier aasId) {
		AASDescriptor aasDesc = aasDirectory.lookupAAS(aasId);
		Collection<SubmodelDescriptor> smDescriptors = aasDesc.getSubModelDescriptors();
		Map<String, ISubModel> submodels = new HashMap<>();
		for (SubmodelDescriptor smDesc : smDescriptors) {
			String smEndpoint = smDesc.getFirstEndpoint();
			String smIdShort = smDesc.getIdShort();
			VABElementProxy smProxy = proxyFactory.createProxy(smEndpoint);
			ConnectedSubModel connectedSM = new ConnectedSubModel(smProxy);
			submodels.put(smIdShort, connectedSM);
		}
		return submodels;
	}

	@Override
	public void createAAS(AssetAdministrationShell aas, IIdentifier aasId, String endpoint) {
		IModelProvider provider = connectorProvider.getConnector(endpoint);
		AASAggregatorProxy proxy = new AASAggregatorProxy(provider);
		proxy.createAAS(aas);
		try {
			String combinedEndpoint = VABPathTools.concatenatePaths(endpoint, "aasList", URLEncoder.encode(aas.getIdentification().getId(), "UTF-8"), "aas");
			aasDirectory.register(new AASDescriptor(aas, combinedEndpoint));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Encoding failed. This should never happen");
		}
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
		
		// Push the SM to the server using the ConnectedAAS
		try {
			retrieveAAS(aasId).addSubModel(submodel);
		} catch (Exception e) {
			throw new RuntimeException("Could not create Submodel on server", e);
		}
		
		// Lookup AAS descriptor
		AASDescriptor aasDescriptor = aasDirectory.lookupAAS(aasId);

		// Get aas endpoint
		String addr = aasDescriptor.getFirstEndpoint();
		
		// Register the SM
		String smEndpoint = VABPathTools.concatenatePaths(addr, AssetAdministrationShell.SUBMODELS, submodel.getIdShort());
		aasDirectory.register(aasId, new SubmodelDescriptor(submodel, smEndpoint));
	}
}
