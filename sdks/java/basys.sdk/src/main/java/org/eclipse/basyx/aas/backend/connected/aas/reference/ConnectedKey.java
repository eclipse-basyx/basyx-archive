package org.eclipse.basyx.aas.backend.connected.aas.reference;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IKey;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IKey
 * @author rajashek
 *
 */
public class ConnectedKey extends ConnectedElement implements IKey {
	public ConnectedKey(VABElementProxy proxy) {
		super(proxy);		
	}
	@Override
	public String getType() {
	return (String)getProxy().getModelPropertyValue(Key.TYPE);
	}
	@Override
	public boolean isLocal() {
		return (boolean)getProxy().getModelPropertyValue(Key.LOCAL);
	}
	@Override
	public String getValue() {
		return (String)getProxy().getModelPropertyValue(Key.VALUE);
	}
	@Override
	public String getidType() {
		return (String)getProxy().getModelPropertyValue(Key.IDTYPE);
	}
	@Override
	public void setType(String type) {
		getProxy().setModelPropertyValue(Key.TYPE,type);
	}
	@Override
	public void setLocal(boolean local) {
		getProxy().setModelPropertyValue(Key.LOCAL, local);
		
	}
	@Override
	public void setValue(String value) {
		getProxy().setModelPropertyValue(Key.VALUE,value );
	}
	@Override
	public void setIdType(String idType) {
		getProxy().setModelPropertyValue(Key.IDTYPE, idType);
	}

}
