package org.eclipse.basyx.aas.impl.onem2m.connected;

import org.eclipse.basyx.aas.api.resources.basic.SubModel;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedSubModel;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;

public class OneM2MConnectedSubModel extends ConnectedSubModel {
	protected OneM2MResourceManager resourceManager;
	protected String m2m_ri;

	public OneM2MConnectedSubModel(OneM2MResourceManager resourceManager, String m2m_ri) {
		this.resourceManager = resourceManager;
		this.m2m_ri = m2m_ri;
	}

	public OneM2MConnectedSubModel(OneM2MResourceManager resourceManager, String m2m_ri, SubModel sm) {
		this(resourceManager, m2m_ri);
		this.setAssetKind(sm.getAssetKind());
		this.setId(sm.getId());
		this.setName(sm.getName());
		this.setTypeDefinition(sm.getTypeDefinition());
	}

	public String getM2MRi() {
		return this.m2m_ri;
	}

}
