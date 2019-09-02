package org.eclipse.basyx.aas.backend.connected.facades;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * Facade providing access to a map containing the ConnectedHasKindFacade structure
 * @author rajashek
 *
 */
public class ConnectedHasKindFacade  extends ConnectedElement implements IHasKind {

	public ConnectedHasKindFacade(VABElementProxy proxy) {
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
