package org.eclipse.basyx.aas.impl.onem2m.connected;

import org.eclipse.basyx.aas.api.resources.basic.Event;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedEvent;
import org.eclipse.basyx.aas.impl.onem2m.BasyxResourcesUtil;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;

public class OneM2MConnectedEvent extends ConnectedEvent {

	protected OneM2MResourceManager resourceManager;
	protected String m2m_ri;
	protected String m2m_ri_DATA;

	public OneM2MConnectedEvent(OneM2MResourceManager resourceManager, String m2m_ri, String m2m_ri_DATA) {
		this.resourceManager = resourceManager;
		this.m2m_ri = m2m_ri;
		this.m2m_ri_DATA = m2m_ri_DATA;
	}

	public OneM2MConnectedEvent(OneM2MResourceManager resourceManager, String m2m_ri, String m2m_ri_DATA, Event event) {
		this(resourceManager, m2m_ri, m2m_ri_DATA);
		this.setDataType(event.getDataType());
		this.setId(event.getId());
		this.setName(event.getName());
	}

	@Override
	public void throwEvent(Object value) throws Exception {
		this.resourceManager.createContentInstance01(this.m2m_ri_DATA,
				BasyxResourcesUtil.toM2MValue(value, this.dataType), false);
	}

}
