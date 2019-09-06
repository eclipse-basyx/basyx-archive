package org.eclipse.basyx.aas.backend.connected.aas.qualifier.haskind;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IHasKind
 * @author rajashek
 *
 */
public class ConnectedHasKind extends ConnectedElement implements IHasKind {

	public ConnectedHasKind(VABElementProxy proxy) {
		super(proxy);		
	}
	
	@Override
	public String getHasKindReference() {
		return (String) getProxy().getModelPropertyValue(HasKind.KIND);
	}

	@Override
	public void setHasKindReference(String kind) {
		getProxy().setModelPropertyValue(HasKind.KIND, kind);
		
	}
	
}
