package org.eclipse.basyx.aas.backend.connected;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.core.ref.VABElementRef;

public class ConnectedAssetAdministrationShell extends ConnectedElement implements IAssetAdministrationShell {

	VABConnectionManager manager;

	public ConnectedAssetAdministrationShell(String path, VABElementProxy proxy, VABConnectionManager manager) {
		super(path, proxy);
		this.manager = manager;
	}

	@Override
	public IElement getElement(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, IElement> getElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubModel> getSubModels() {
		Set<VABElementRef> refs = (Set<VABElementRef>) getProxy().readElementValue(constructPath("body/submodels"));
		Map<String, ISubModel> ret = new HashMap<>();
		for (VABElementRef ref : refs) {
			VABElementProxy elem = manager.connectToVABElement(ref.getPath());
			ISubModel sm = new ConnectedSubModel("", elem);
			sm.setId(ref.getPath());
			ret.put(sm.getId(), sm);
		}
		return ret;
	}

	@Override
	public void addSubModel(ISubModel subModel) {
		// TODO Auto-generated method stub
	}

}
