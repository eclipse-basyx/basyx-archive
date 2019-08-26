package org.eclipse.basyx.aas.backend.connected.aas.reference;

import java.util.List;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IKey;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IReference
 * @author rajashek
 *
 */
public class ConnectedReference extends ConnectedElement implements IReference {
	
	public ConnectedReference(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<IKey> getKeys() {
		return (List<IKey>)getProxy().getModelPropertyValue(constructPath(Reference.KEY));
	}

	@Override
	public void setKeys(List<IKey> keys) {
		getProxy().setModelPropertyValue(constructPath(Reference.KEY),keys);
		
	}
}
