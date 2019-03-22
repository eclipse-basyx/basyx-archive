package org.eclipse.basyx.aas.backend.connected;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Connected implementation of the AAS <br />
 * Allows access to a remote AAS
 * 
 * @author schnicke
 *
 */
public class ConnectedAssetAdministrationShell extends ConnectedElement implements IAssetAdministrationShell {

	VABConnectionManager manager;

	/**
	 * Constructor creating a ConnectedAAS pointing to the AAS represented by proxy
	 * and path
	 * 
	 * @param path
	 * @param proxy
	 * @param manager
	 */
	public ConnectedAssetAdministrationShell(String path, VABElementProxy proxy, VABConnectionManager manager) {
		super(path, proxy);
		this.manager = manager;
	}

	/**
	 * Copy constructor, allowing to create a ConnectedAAS pointing to the same AAS
	 * as <i>shell</i>
	 * 
	 * @param shell
	 */
	public ConnectedAssetAdministrationShell(ConnectedAssetAdministrationShell shell) {
		super(shell.getPath(), shell.getProxy());
		this.manager = shell.manager;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubModel> getSubModels() {
		Set<Map<?, ?>> refs = (Set<Map<?, ?>>) getProxy().readElementValue(constructPath("submodel"));
		Map<String, ISubModel> ret = new HashMap<>();
		for (Map<?, ?> key : refs) {
			String id = (String) ((Map<?, ?>) ((List<?>) key.get("key")).get(0)).get("value");
			VABElementProxy elem = manager.connectToVABElement(id);
			ISubModel sm = new ConnectedSubModel("/aas/submodels/" + id, elem);
			ret.put(id, sm);
		}

		return ret;
	}

	@Override
	public void addSubModel(ISubModel subModel) {
		// TODO Auto-generated method stub
	}

	protected VABConnectionManager getManager() {
		return manager;
	}

}
