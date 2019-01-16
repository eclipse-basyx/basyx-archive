package org.eclipse.basyx.aas.backend.connected;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

public class ConnectedAssetAdministrationShell extends ConnectedElement implements IAssetAdministrationShell {

	VABConnectionManager manager;

	public ConnectedAssetAdministrationShell(String path, VABElementProxy proxy, VABConnectionManager manager) {
		super(path, proxy);
		this.manager = manager;
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

}
