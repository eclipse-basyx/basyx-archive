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
	public ConnectedKey(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	@Override
	public String getType() {
	return (String)getProxy().getModelPropertyValue(constructPath(Key.TYPE));
	}
	@Override
	public boolean isLocal() {
		return (boolean)getProxy().getModelPropertyValue(constructPath(Key.LOCAL));
	}
	@Override
	public String getValue() {
		return (String)getProxy().getModelPropertyValue(constructPath(Key.VALUE));
	}
	@Override
	public String getidType() {
		return (String)getProxy().getModelPropertyValue(constructPath(Key.IDTYPE));
	}
	@Override
	public void setType(String type) {
		getProxy().setModelPropertyValue(constructPath(Key.TYPE),type);
	}
	@Override
	public void setLocal(boolean local) {
		getProxy().setModelPropertyValue(constructPath(Key.LOCAL), local);
		
	}
	@Override
	public void setValue(String value) {
		getProxy().setModelPropertyValue(constructPath(Key.VALUE),value );
	}
	@Override
	public void setIdType(String idType) {
		getProxy().setModelPropertyValue(constructPath(Key.IDTYPE), idType);
	}

}
