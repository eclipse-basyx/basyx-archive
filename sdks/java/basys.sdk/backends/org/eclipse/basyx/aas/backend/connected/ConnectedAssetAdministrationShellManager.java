/**
 * 
 */
package org.eclipse.basyx.aas.backend.connected;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.manager.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.vab.core.VABConnectionManager;

/**
 * Implement a AAS manager backend that communicates via HTTP/REST<br />
 * <br />
 * 
 * @author kuhn, schnicke
 *
 */
public class ConnectedAssetAdministrationShellManager implements IAssetAdministrationShellManager {

	private VABConnectionManager manager;

	/**
	 * @param networkDirectoryService
	 * @param providerProvider
	 */
	public ConnectedAssetAdministrationShellManager(VABConnectionManager manager) {
		this.manager = manager;
	}

	@Override
	public IAssetAdministrationShell createAAS(IAssetAdministrationShell aas) throws Exception {
		throw new FeatureNotImplementedException();
	}

	public ISubModel retrieveSM(String id) {
		return new ConnectedSubModel("/aas/submodels/" + id, manager.connectToVABElement(id));
	}

	@Override
	public IAssetAdministrationShell retrieveAAS(String id) throws Exception {
		return new ConnectedAssetAdministrationShell("/aas", manager.connectToVABElement(id), manager);
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
