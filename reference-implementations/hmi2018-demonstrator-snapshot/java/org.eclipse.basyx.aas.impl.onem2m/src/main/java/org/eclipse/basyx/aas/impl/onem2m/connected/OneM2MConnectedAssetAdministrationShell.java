package org.eclipse.basyx.aas.impl.onem2m.connected;

import org.eclipse.basyx.aas.api.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.SubModel;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedSubModel;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;

public class OneM2MConnectedAssetAdministrationShell extends ConnectedAssetAdministrationShell {

	protected OneM2MResourceManager resourceManager;
	protected String m2m_ri;

	public OneM2MConnectedAssetAdministrationShell(OneM2MResourceManager resourceManager, String m2m_ri) {
		this.resourceManager = resourceManager;
		this.m2m_ri = m2m_ri;
	}

	public OneM2MConnectedAssetAdministrationShell(OneM2MResourceManager resourceManager, String m2m_ri,
			AssetAdministrationShell aas) {
		this(resourceManager, m2m_ri);
		this.setAssetId(aas.getAssetId());
		this.setAssetKind(aas.getAssetKind());
		this.setAssetTypeDefinition(aas.getAssetTypeDefinition());
		this.setDisplayName(aas.getDisplayName());
		this.setId(aas.getId());
		this.setName(aas.getName());
	}

	public String getM2MRi() {
		return this.m2m_ri;
	}

	@Override
	public synchronized void addSubModel(SubModel subModel) {
		if (!(subModel instanceof ConnectedSubModel)) {
			throw new IllegalArgumentException("Only ConnectedSubModel is allowed");
		}
		super.addSubModel(subModel);
	}

}
