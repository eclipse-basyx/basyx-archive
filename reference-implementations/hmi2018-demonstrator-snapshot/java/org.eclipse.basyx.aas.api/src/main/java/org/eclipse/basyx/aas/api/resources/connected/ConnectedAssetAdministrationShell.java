package org.eclipse.basyx.aas.api.resources.connected;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.SubModel;

public abstract class ConnectedAssetAdministrationShell extends AssetAdministrationShell {
	
	public Map<String, ConnectedSubModel> getConnectedSubModels() {
		Map<String, ConnectedSubModel> cSubModels = new HashMap<>();
		for (String key : super.getSubModels().keySet()) {
			SubModel sm = super.getSubModels().get(key);
			if (sm instanceof ConnectedSubModel) {
				cSubModels.put(key, (ConnectedSubModel) sm);
			} else {
				throw new IllegalStateException("Should contain only connected submodels!");
			}
		}
		return cSubModels;
	}

	public Map<String, ConnectedAssetAdministrationShell> getConnectedAssetAdministrationShells() {
		Map<String, ConnectedAssetAdministrationShell> cShells = new HashMap<>();
		for (String key : super.getAssetAdministrationShells().keySet()) {
			AssetAdministrationShell aas = super.getAssetAdministrationShells().get(key);
			if (aas instanceof ConnectedAssetAdministrationShell) {
				cShells.put(key, (ConnectedAssetAdministrationShell) aas);
			} else {
				throw new IllegalStateException("Should contain only connected administration shells!");
			}
		}
		return cShells;
	}
	
}
