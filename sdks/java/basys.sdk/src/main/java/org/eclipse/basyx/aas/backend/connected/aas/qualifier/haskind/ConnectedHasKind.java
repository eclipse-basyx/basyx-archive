package org.eclipse.basyx.aas.backend.connected.aas.qualifier.haskind;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IHasKind
 * @author rajashek
 *
 */
public class ConnectedHasKind extends ConnectedElement implements IHasKind {

	public ConnectedHasKind(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public String getHasKindReference() {
		return (String) getProxy().getModelPropertyValue(constructPath(HasKind.KIND));
	}

	@Override
	public void setHasKindReference(String kind) {
		getProxy().setModelPropertyValue(constructPath(HasKind.KIND), kind);
		
	}
	
}
