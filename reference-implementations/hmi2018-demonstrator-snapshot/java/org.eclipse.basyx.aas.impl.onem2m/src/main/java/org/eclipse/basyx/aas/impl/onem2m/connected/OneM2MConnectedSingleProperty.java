package org.eclipse.basyx.aas.impl.onem2m.connected;

import java.math.BigInteger;

import org.eclipse.basyx.aas.api.resources.basic.DataType;
import org.eclipse.basyx.aas.api.resources.basic.Property;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedOperation;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedSingleProperty;
import org.eclipse.basyx.aas.impl.onem2m.BasyxResourcesUtil;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;
import org.eclipse.basyx.onem2m.manager.ResourceResult;
import org.eclipse.basyx.onem2m.xml.protocols.Cin;

public class OneM2MConnectedSingleProperty extends ConnectedSingleProperty {

	protected OneM2MResourceManager resourceManager;
	protected String m2m_ri;
	protected String m2m_ri_DATA;
	protected String m2m_la_DATA;

	ConnectedOperation opSET; // unused
	ConnectedOperation opGET; // unused

	public String getRiDATA() {
		return m2m_ri_DATA;
	}

	public OneM2MConnectedSingleProperty(OneM2MResourceManager resourceManager, String m2m_ri, String m2m_ri_DATA,
			String m2m_la_DATA) {
		this.resourceManager = resourceManager;
		this.m2m_ri = m2m_ri;
		this.m2m_ri_DATA = m2m_ri_DATA;
		this.m2m_la_DATA = m2m_la_DATA;
	}

	public OneM2MConnectedSingleProperty(OneM2MResourceManager resourceManager, String m2m_ri, String m2m_ri_DATA,
			String m2m_la_DATA, Property prop) {
		this(resourceManager, m2m_ri, m2m_ri_DATA, m2m_la_DATA);
		this.setDataType(prop.getDataType());
		this.setEventable(prop.isEventable());
		this.setId(prop.getId());
		this.setName(prop.getName());
		this.setReadable(prop.isReadable());
		this.setWriteable(prop.isWriteable());
		this.getStatements().putAll(prop.getStatements());
	}

	private String stripHierarchical(String address) {
		int numOverheadParts = 4;
		String parts[] = address.split("/", numOverheadParts);
		int offset = numOverheadParts - 1;
		for (int i = 0; i < numOverheadParts - 1; ++i) {
			offset += parts[i].length();
		}
		return address.substring(offset);
	}

	@Override
	public void set(Object value) throws Exception {
		if (this.isCollection()) {
			throw new IllegalStateException("Object must not be a collection!");
		}
		Cin cin = new Cin();
		cin.setCon(BasyxResourcesUtil.toM2MValue(value, this.dataType));
		cin.setCr("Toolstation");
		this.resourceManager.createContentInstance(this.m2m_ri_DATA, cin, false);
	}

	@Override
	public Object get() throws Exception {
		if (this.isCollection()) {
			throw new IllegalStateException("Object must not be a collection!");
		}
		ResourceResult<Cin> rr = this.resourceManager.retrieveContentInstance(this.stripHierarchical(this.m2m_la_DATA),
				true);
		if (rr.getResponse().getRsc().equals(BigInteger.valueOf(2000))) {
			return BasyxResourcesUtil.fromM2MValue(rr.getResource().getCon().toString(),
					(this.dataType == null ? DataType.STRING : this.dataType));
		} else {
			return null;
		}

	}

}
