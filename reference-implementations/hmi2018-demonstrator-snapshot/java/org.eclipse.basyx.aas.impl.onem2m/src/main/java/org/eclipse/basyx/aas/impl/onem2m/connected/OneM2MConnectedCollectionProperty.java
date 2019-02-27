package org.eclipse.basyx.aas.impl.onem2m.connected;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.aas.api.resources.basic.Property;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedCollectionProperty;
import org.eclipse.basyx.aas.impl.onem2m.BasyxResourcesUtil;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;
import org.eclipse.basyx.onem2m.manager.ResourceResult;
import org.eclipse.basyx.onem2m.xml.protocols.ChildResourceRef;
import org.eclipse.basyx.onem2m.xml.protocols.Cin;
import org.eclipse.basyx.onem2m.xml.protocols.Cnt;
import org.eclipse.basyx.onem2m.xml.protocols.FilterCriteria;
import org.eclipse.basyx.onem2m.xml.protocols.PrimitiveContent;
import org.eclipse.basyx.onem2m.xml.protocols.Rqp;
import org.eclipse.basyx.onem2m.xml.protocols.Rsp;

public class OneM2MConnectedCollectionProperty extends ConnectedCollectionProperty {

	protected OneM2MResourceManager resourceManager;
	protected String m2m_ri;
	protected String m2m_ri_DATA;
	protected String m2m_la_DATA;

	public OneM2MConnectedCollectionProperty(OneM2MResourceManager resourceManager, String m2m_ri, String m2m_ri_DATA,
			String m2m_la_DATA) {
		this.resourceManager = resourceManager;
		this.m2m_ri = m2m_ri;
		this.m2m_ri_DATA = m2m_ri_DATA;
		this.m2m_la_DATA = m2m_la_DATA;
	}

	public String getRiDATA() {
		return m2m_ri_DATA;
	}

	public String getLaDATA() {
		return m2m_la_DATA;
	}

	public OneM2MConnectedCollectionProperty(OneM2MResourceManager resourceManager, String m2m_ri, String m2m_ri_DATA,
			String m2m_la_DATA, Property prop) {
		this(resourceManager, m2m_ri, m2m_ri_DATA, m2m_la_DATA);
		this.setCollection(prop.isCollection());
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
	public void set(String key, Object value) throws Exception {
		if (this.isCollection() == false) {
			throw new IllegalStateException("This property is object is not a collection!");
		}

		if (key == null || key.isEmpty()) {
			throw new IllegalArgumentException("Key must have an actual value!");

		}

		String urlLa = this.m2m_la_DATA;
		String urlData = urlLa.substring(0, urlLa.length() - 3);
		String urlKey = urlData + "/" + key;
		ResourceResult<Cnt> rr1 = this.resourceManager.retrieveContainer(this.stripHierarchical(urlKey), true);

		if (rr1.getResponse().getRsc().equals(BigInteger.valueOf(4004))) {
			ResourceResult<Cnt> rr2 = this.resourceManager.createContainer02(this.stripHierarchical(urlData), key,
					true);
			if (rr2.getResponse().getRsc().equals(BigInteger.valueOf(2001))) {
				// success nothing to do
			} else {
				throw new IllegalStateException();
			}
		}
		String cinValue = BasyxResourcesUtil.toM2MValue(value, this.dataType);
		ResourceResult<Cin> rr3 = this.resourceManager.createContentInstance01(this.stripHierarchical(urlKey), cinValue, true);
		if (rr3.getResponse().getRsc().equals(BigInteger.valueOf(2001))) {
			// success nothing to do
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public Object get(String key) throws Exception {
		if (this.isCollection()) {
			String url = this.m2m_la_DATA;
			url = url.substring(0, url.length() - 2) + key + "/la";
			ResourceResult<Cin> rr = this.resourceManager.retrieveContentInstance(this.stripHierarchical(url), true);
			if (rr.getResponse().getRsc().equals(BigInteger.valueOf(2000))) {
				String con = rr.getResource().getCon().toString();
				return BasyxResourcesUtil.fromM2MValue(con, this.getDataType());
			} else {
				return null;
			}
		} else {
			throw new IllegalStateException("Object is not a collection!");
		}
	}

	@Override
	public void remove(String key) throws Exception {
		if (this.isCollection()) {
			String url = this.m2m_la_DATA;
			url = url.substring(0, url.length() - 2) + key;
			this.resourceManager.deleteResource(this.stripHierarchical(url), true);
		} else {
			throw new IllegalStateException("Object is not a collection!");
		}

	}

	@Override
	public Collection<String> getKeys() throws Exception {

		String urlLa = this.m2m_la_DATA;
		String urlData = urlLa.substring(0, urlLa.length() - 3);

		Rqp rqp = this.resourceManager.getClient().createDefaultRequest(urlData, false);
		rqp.setOp(BigInteger.valueOf(2));
		rqp.setRcn(BigInteger.valueOf(5));

		rqp.setFc(new FilterCriteria());
		rqp.getFc().setLvl(BigInteger.valueOf(1));
		rqp.setPc(new PrimitiveContent());

		Rsp rsp = this.resourceManager.getClient().send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2000))) {
			Cnt cnt = (Cnt) rsp.getPc().getAnyOrAny().iterator().next();
			List<String> keys = new ArrayList<>();
			for (ChildResourceRef ref : cnt.getCh()) {
				if (ref.getTyp().intValue() == 3) {
					keys.add(ref.getNm());
				}
			}
			return keys;
		}
		return null;
	}

}
